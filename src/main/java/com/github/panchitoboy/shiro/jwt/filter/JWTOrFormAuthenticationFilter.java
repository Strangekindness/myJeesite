package com.github.panchitoboy.shiro.jwt.filter;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JWSObject;
import com.thinkgem.jeesite.common.utils.StringUtils;

public final class JWTOrFormAuthenticationFilter extends AuthenticatingFilter {

    public static final String USER_ID = "username";
    public static final String PASSWORD = "password";
    
    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;

    protected static final String AUTHORIZATION_HEADER = "Authorization";

    public JWTOrFormAuthenticationFilter() {
        setLoginUrl(DEFAULT_LOGIN_URL);
    }
    
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "请检查您的token。";
            return onLoginFailure(token, new AuthenticationException(msg), request, response);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }    

    @Override
    public void setLoginUrl(String loginUrl) {
        String previous = getLoginUrl();
        if (previous != null) {
            this.appliedPaths.remove(previous);
        }
        super.setLoginUrl(loginUrl);
        this.appliedPaths.put(getLoginUrl(), null);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false;

        if (isLoginRequest(request, response) || isLoggedAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }

        if (!loggedIn) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return loggedIn;
    }
    
    /**
     * 这个方法主要为了覆盖父类中的方法，每次都需要进行验证登录
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean loggedIn = false;
        
        if(isLoginRequest(request, response)) {
        	//Subject subject = getSubject(request, response);
        	//loggedIn = subject.isAuthenticated();
        }

        return loggedIn;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws IOException {

        if (isLoginRequest(request, response)) {
            String json = IOUtils.toString(request.getInputStream());

            if (json != null && !json.isEmpty()) {
            	JSONObject jsonObject =  JSONObject.parseObject(json);
            	String username = jsonObject.getString(USER_ID);
                String password = jsonObject.getString(PASSWORD);
                
                if (password==null){
        			password = "";
        		}
        		boolean rememberMe = isRememberMe(request);
        		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
        		boolean mobile = isMobileLogin(request);
        		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, mobile, true);
                
//                try (JsonReader jr = Json.createReader(new StringReader(json))) {
//                    JsonObject object = jr.readObject();
//                    String username = object.getString(USER_ID);
//                    String password = object.getString(PASSWORD);
//                    return new UsernamePasswordToken(username, password);
//                }

            }
        }

        if (isLoggedAttempt(request, response)) {
            String jwtToken = getAuthzHeader(request);
            if (jwtToken != null) {
                return createToken(jwtToken);
            }
        }

        return new UsernamePasswordToken();
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

    	String className = e.getClass().getName(), message = "";
    	if (isLoginRequest(request, response)){
			if (IncorrectCredentialsException.class.getName().equals(className)
					|| UnknownAccountException.class.getName().equals(className)){
				message = "用户或密码错误, 请重试.";
			}
			else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
				message = StringUtils.replace(e.getMessage(), "msg:", "");
			}
			else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "No account information found for authentication token")) {
				message = "用户或密码错误, 请重试.";
			}
			else{
				message = "系统出现点问题，请稍后再试！";
				e.printStackTrace(); // 输出到控制台
			}
    	}
    	if(isLoggedAttempt(request, response)) {
    		if (AuthenticationException.class.getName().equals(className)){
				message = e.getMessage();
			} else {
				message = "验证失败，请先登录！";
			}
    	}
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json");
        try {
			httpResponse.getWriter().write("{\"code\": 1, \"message\":\"" + message + "\"}");
			httpResponse.getWriter().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return false;
    }

    protected boolean isLoggedAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = getAuthzHeader(request);
        return authzHeader != null;
    }

    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    public JWTAuthenticationToken createToken(String token) {
    	//如果token为空或者前端传来null，则返回null
    	if(StringUtils.isBlank(token) || token.equals("null")) {
    		return null;
    	}
    	
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String decrypted = jwsObject.getPayload().toString();
            
            JSONObject jsonObject =  JSONObject.parseObject(decrypted);
            String userId = jsonObject.getString("sub") != null ? jsonObject.getString("sub"): null;
            return new JWTAuthenticationToken(userId, token);
//            try (JsonReader jr = Json.createReader(new StringReader(decrypted))) {
//                JsonObject object = jr.readObject();
//
//                String userId = object.getString("sub", null);
//                return new JWTAuthenticationToken(userId, token);
//            }

        } catch (ParseException ex) {
            throw new AuthenticationException(ex);
        }

    }
    
    /**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }

}

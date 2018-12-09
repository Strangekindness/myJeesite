package com.github.panchitoboy.shiro.jwt.realm;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.panchitoboy.shiro.jwt.filter.JWTAuthenticationToken;
import com.github.panchitoboy.shiro.jwt.repository.Principal;
import com.github.panchitoboy.shiro.jwt.repository.UserRepository;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

@Service
public class JwtRealm extends AuthorizingRealm {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;
	
	@Autowired
	private UserRepository userRepository;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof JWTAuthenticationToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        JWTAuthenticationToken upToken = (JWTAuthenticationToken) token;
        //User user = (User) upToken.getUserId();
        User user =  getSystemService().getUser(String.valueOf(upToken.getUserId()));
        if (user != null && userRepository.validateToken(upToken.getToken())) {
            //SimpleAccount account = new SimpleAccount(user, upToken.getToken(), getName());
        	SimpleAccount account = new SimpleAccount(new Principal(user, false), upToken.getToken(), getName());
            return account;
        }
        return null;
    }

    /**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

}

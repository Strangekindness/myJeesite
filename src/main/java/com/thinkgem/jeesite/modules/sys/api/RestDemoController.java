/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.panchitoboy.shiro.jwt.repository.Principal;
import com.github.panchitoboy.shiro.jwt.repository.TokenResponse;
import com.github.panchitoboy.shiro.jwt.repository.UserRepository;
import com.thinkgem.jeesite.common.persistence.ResponseResult;
import com.thinkgem.jeesite.common.persistence.ResultCode;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * restful demo
 * 所有的api返回值均为ResponseResult，在ResponseResult内赋予相关的对象
 * @author waile23
 * @version 2017-11-12
 */
@RestController
@RequestMapping(value = "${apiPath}/demo")
public class RestDemoController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private UserRepository userRepository;
	

	/**
	 * 返回用户信息
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ResponseResult user(){
        return ResponseResult.success(UserUtils.getPrincipal());
	}
	
	/**
	 * 成功，只返回code和message
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "sucess", method = RequestMethod.GET)
	public ResponseResult sucess(){
        return ResponseResult.success();
	}
	
	/**
	 * 成功，返回code和message，并返回一个object对象
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "sucess/data/object", method = RequestMethod.GET)
	public ResponseResult object(){
		Principal principal = UserUtils.getPrincipal();
        TokenResponse token = userRepository.createToken(principal);
        return ResponseResult.success(token);
	}
	
	/**
	 * 成功，返回code和message，并返回一个List对象
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "sucess/data/list", method = RequestMethod.GET)
	public ResponseResult list(){
		Principal principal = UserUtils.getPrincipal();
        TokenResponse token = userRepository.createToken(principal);
        
        List<TokenResponse> tokenList = new ArrayList<TokenResponse>();
        tokenList.add(token);
        tokenList.add(token);
        tokenList.add(token);
        return ResponseResult.success(tokenList);
	}
	
	/**
	 * 成功，返回code和message，并返回一个map对象
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "sucess/data/map", method = RequestMethod.GET)
	public ResponseResult map(){
		Principal principal = UserUtils.getPrincipal();
        TokenResponse token = userRepository.createToken(principal);
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("tokenData", token);
        return ResponseResult.success(results);
	}
	
	/**
	 * 失败
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "failure", method = RequestMethod.GET)
	public ResponseResult failure(){
		return ResponseResult.failure(ResultCode.FAILED);
	}
	
	/**
	 * 失败，自定义错误信息
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * 服务器在shiro中验证Token是否符合要求
	 * @return
	 */
	@RequestMapping(value = "error", method = RequestMethod.GET)
	public ResponseResult errorMsg(){
		return ResponseResult.failure("出现错误！");
	}
	
	/**
	 * POST Map
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "post/map", method = RequestMethod.POST)
	public ResponseResult postMap(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response){
		return ResponseResult.success(map);
	}
	
	/**
	 * POST Obj
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "post/obj", method = RequestMethod.POST)
	public ResponseResult postObj(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		return ResponseResult.success(user);
	}
	
	/**
	 * POST Obj
	 * 需要在请求Header中将Login返回的Token写入Authorization字段中。
	 * @param principal
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "post/principal", method = RequestMethod.POST)
	public ResponseResult postObj(@RequestBody Principal principal, HttpServletRequest request, HttpServletResponse response){
		return ResponseResult.success(principal);
	}	
}

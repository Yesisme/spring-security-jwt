package com.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.security.config.JWTConfig;
import com.security.entity.model.SelfUserModel;
import com.security.util.JWTTokenUtil;
import com.security.util.ResultUtil;
//登录成功处理类
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SelfUserModel selfUserEntity = (SelfUserModel)authentication.getPrincipal();
		String token = JWTConfig.tokenPrefix+JWTTokenUtil.createAccessToken(selfUserEntity);
		Map<String,Object> resultData = new HashMap<>();
	    resultData.put("code","200");
	    resultData.put("msg", "登录成功");
	    resultData.put("token",token);
	    ResultUtil.responseJson(response,resultData);
	}

}

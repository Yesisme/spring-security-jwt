package com.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import com.security.util.ResultUtil;
//登出处理类
@Service
public class UserLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		 Map<String,Object> resultData = new HashMap<>();
	     resultData.put("code","200");
	     resultData.put("msg", "登出成功");
	     SecurityContextHolder.clearContext();
	     ResultUtil.responseJson(response,ResultUtil.resultSuccess(resultData));	
	}

}

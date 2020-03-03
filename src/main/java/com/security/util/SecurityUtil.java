package com.security.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.security.entity.model.SelfUserModel;

public class SecurityUtil {
	
	private SecurityUtil() {}
	
	public static SelfUserModel getUserInfo() {
		SelfUserModel selfUserEntity = (SelfUserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return selfUserEntity;
	}
	
	public static String getUserName() {
		return getUserInfo().getUsername();
	}
	
	public static Long getPassword() {
		return getUserInfo().getUserId();
	}
	

}

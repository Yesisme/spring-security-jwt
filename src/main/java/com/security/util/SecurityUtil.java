package com.security.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.security.entity.SelfUserEntity;

public class SecurityUtil {
	
	private SecurityUtil() {}
	
	public static SelfUserEntity getUserInfo() {
		SelfUserEntity selfUserEntity = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return selfUserEntity;
	}
	
	public static String getUserName() {
		return getUserInfo().getUsername();
	}
	
	public static Long getPassword() {
		return getUserInfo().getUserId();
	}
	

}

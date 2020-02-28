package com.security.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security.entity.SelfUserEntity;
import com.security.entity.Po.SysUserPO;
import com.security.service.SysUserService;

@Component
public class SelfUserDetailsService implements UserDetailsService{
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public SelfUserEntity loadUserByUsername(String userName) throws UsernameNotFoundException {
		SysUserPO sysUserPO = sysUserService.selectByUserName(userName);
		if(sysUserPO!=null) {
			SelfUserEntity selfUserEntity = new SelfUserEntity();
			BeanUtils.copyProperties(sysUserPO, selfUserEntity);
			return selfUserEntity;
		}
		return null;
	}
}

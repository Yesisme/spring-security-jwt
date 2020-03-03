package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.entity.po.SysMenuPO;
import com.security.entity.po.SysRolePO;
import com.security.entity.po.SysUserPO;
import com.security.mapper.SysMenuMapper;
import com.security.mapper.SysRoleMapper;
import com.security.mapper.SysUserMapper;
import com.security.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public SysUserPO selectByUserName(String userName) {
		
		return sysUserMapper.selectByUserName(userName);
	}

	@Override
	public List<SysRolePO> selectSysRoleByUserId(Long userId) {
		
		return sysRoleMapper.selectSysRoleByUserId(userId);
	}

	@Override
	public List<SysMenuPO> selectSysMenuByUserId(Long userId) {
		return sysMenuMapper.selectSysMenuByUserId(userId);
	}

	@Override
	public List<SysUserPO> selectAllSysUser() {
		
		return sysUserMapper.selectAll();
	}

}

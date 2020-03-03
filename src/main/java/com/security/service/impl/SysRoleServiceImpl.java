package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.entity.po.SysRolePO;
import com.security.mapper.SysRoleMapper;
import com.security.service.SysRoleService;
@Service
public class SysRoleServiceImpl implements SysRoleService{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public List<SysRolePO> selectAllRolePo() {
		
		return sysRoleMapper.selectAll();
	}

	
}

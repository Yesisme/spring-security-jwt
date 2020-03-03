package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.entity.po.SysMenuPO;
import com.security.mapper.SysMenuMapper;
import com.security.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenuPO> selectMenuAll() {
		return sysMenuMapper.selectAll();
	}

}

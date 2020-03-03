package com.security.mapper;

import java.util.List;

import com.security.entity.po.SysMenuPO;

import tk.mybatis.mapper.common.BaseMapper;

public interface SysMenuMapper extends BaseMapper<SysMenuPO>{

	
	List<SysMenuPO> selectSysMenuByUserId(Long userId);
}

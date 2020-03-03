package com.security.service;

import java.util.List;

import com.security.entity.po.SysMenuPO;
import com.security.entity.po.SysRolePO;
import com.security.entity.po.SysUserPO;

public interface SysUserService {

	SysUserPO selectByUserName(String userName);
	
	//查询用户角色集合
	List<SysRolePO> selectSysRoleByUserId(Long userId);
	
	//查询用户权限集合
	List<SysMenuPO> selectSysMenuByUserId(Long userId);
	
	List<SysUserPO> selectAllSysUser();
}

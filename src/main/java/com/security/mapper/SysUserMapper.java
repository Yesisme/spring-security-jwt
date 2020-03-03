package com.security.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.security.entity.po.SysUserPO;

import tk.mybatis.mapper.common.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUserPO>{
	
	@Select("Select user_id,username,password,status from sys_user where username =#{userName,jdbcType=VARCHAR}")
	SysUserPO selectByUserName(@Param("userName") String userName);
	
}

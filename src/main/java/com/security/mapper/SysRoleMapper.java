package com.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.security.entity.Po.SysRolePO;

import tk.mybatis.mapper.common.BaseMapper;

public interface SysRoleMapper extends BaseMapper<SysRolePO>{

	@Select("SELECT sr.* FROM sys_role sr LEFT JOIN sys_user_role se ON se.role_id = sr.role_id WHERE se.user_id = #{userId,jdbcType=BIGINT}")
	List<SysRolePO> selectSysRoleByUserId(@Param("userId")Long userId);
}

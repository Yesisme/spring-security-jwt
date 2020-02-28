package com.security.entity.Po;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.alibaba.fastjson.JSON;
import com.security.mapper.SysMenuMapper;
import com.security.mapper.SysRoleMapper;
import com.security.mapper.SysUserMapper;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@Slf4j
public class PoTest {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Test
	public void testSelectByUserName() {
		SysUserPO sysUser = sysUserMapper.selectByUserName("admin");
		log.info("[{}]",JSON.toJSONString(sysUser));
		Assert.assertNotNull(sysUser);
	}
	
	@Test
	public void testSelectRoleByUserId() {
		List<SysRolePO> roleList = sysRoleMapper.selectSysRoleByUserId(1L);
		Assert.assertTrue(roleList.size()>0);
		roleList.stream().forEach(s->System.out.println(JSON.toJSONString(s)));	
	}
	
	@Test
	public void testSelectSysUserAll() {
		List<SysUserPO> allSysUser = sysUserMapper.selectAll();
		Assert.assertTrue(allSysUser.size()>0);
		allSysUser.stream().forEach(sysUser->System.out.println(JSON.toJSONString(sysUser)));
	}
	
	@Test
	public void testSelectAllMenu() {
		List<SysMenuPO> sysMenuList = sysMenuMapper.selectAll();
		Assert.assertTrue(sysMenuList.size()>0);
		sysMenuList.stream().forEach(sysUser->System.out.println(JSON.toJSONString(sysUser)));	
	}
}

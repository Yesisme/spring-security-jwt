package com.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.SelfUserEntity;
import com.security.entity.Po.SysMenuPO;
import com.security.entity.Po.SysRolePO;
import com.security.entity.Po.SysUserPO;
import com.security.service.SysMenuService;
import com.security.service.SysRoleService;
import com.security.service.SysUserService;
import com.security.util.ResultUtil;
import com.security.util.SecurityUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 管理端信息
	 * 
	 * @CreateTime 2019/10/2 14:22
	 * @Return Map<String,Object> 返回数据MAP
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Map<String, Object> userLogin() {
		Map<String, Object> result = new HashMap<>();
		SelfUserEntity userDetails = SecurityUtil.getUserInfo();
		result.put("title", "管理端信息");
		result.put("data", userDetails);
		return ResultUtil.resultSuccess(result);
	}
	
	  /**
     * 拥有ADMIN或者USER角色可以访问
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String,Object> 返回数据MAP
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<String,Object> list(){
        Map<String,Object> result = new HashMap<>();
        List<SysUserPO> sysUserEntityList = sysUserService.selectAllSysUser();
        result.put("title","拥有用户或者管理员角色都可以查看");
        result.put("data",sysUserEntityList);
        return ResultUtil.resultSuccess(result);
    }
    
    
    /**
     * 拥有ADMIN和USER角色可以访问
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String,Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @RequestMapping(value = "/menuList",method = RequestMethod.GET)
    public Map<String,Object> menuList(){
        Map<String,Object> result = new HashMap<>();
        List<SysMenuPO> sysMenuEntityList = sysMenuService.selectMenuAll();
        result.put("title","拥有用户和管理员角色都可以查看");
        result.put("data",sysMenuEntityList);
        return ResultUtil.resultSuccess(result);
    }
    
    /**
     * 拥有sys:user:info权限可以访问
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String,Object> 返回数据MAP
     */
    @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public Map<String,Object> userList(){
        Map<String,Object> result = new HashMap<>();
        List<SysUserPO> sysUserPOList = sysUserService.selectAllSysUser();
        result.put("title","拥有sys:user:info权限都可以查看");
        result.put("data",sysUserPOList);
        return ResultUtil.resultSuccess(result);
    }


    /**
     * 拥有ADMIN角色和sys:role:info权限可以访问
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String,Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('/admin/adminRoleList','sys:role:info')")
    @RequestMapping(value = "/adminRoleList",method = RequestMethod.GET)
    public Map<String,Object> adminRoleList(){
        Map<String,Object> result = new HashMap<>();
        List<SysRolePO> sysRolePoList = sysRoleService.selectAllRolePo();
        result.put("title","拥有ADMIN角色和sys:role:info权限可以访问");
        result.put("data",sysRolePoList);
        return ResultUtil.resultSuccess(result);
    }
}

package com.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.security.entity.model.SelfUserModel;
import com.security.entity.po.SysRolePO;
import com.security.service.SysUserService;
import com.security.service.impl.SelfUserDetailsService;

//自定义登录验证
@Component
public class UserAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private SelfUserDetailsService selfUserDetailsService;
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// 获取表单输入中返回的用户名
		String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
		String password = (String) authentication.getPrincipal();
        // 查询用户是否存在
		SelfUserModel userNameInDB = selfUserDetailsService.loadUserByUsername(userName);
		if(userNameInDB==null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
		if(new BCryptPasswordEncoder().matches(password, userNameInDB.getPassword())) {
			throw new BadCredentialsException("密码不正确");
		}
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
		if (userNameInDB.getStatus().equals("PROHIBIT")){
	        throw new LockedException("该用户已被冻结");
	     }
        // 角色集合
		Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        // 查询用户角色
		List<SysRolePO> roleList = sysUserService.selectSysRoleByUserId(userNameInDB.getUserId());
		for (SysRolePO sysRolePO : roleList) {
			grantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_" + sysRolePO.getRoleName()));
		}
		userNameInDB.setAuthorities(grantedAuthoritySet);
        // 进行登录
		 return new UsernamePasswordAuthenticationToken(userNameInDB, password, grantedAuthoritySet);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}

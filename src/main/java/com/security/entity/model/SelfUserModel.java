package com.security.entity.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class SelfUserModel implements Serializable,UserDetails{

	
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String username;
	
	private String password;
	
	private String status;
	
	/**
	 * 用户角色
	 */
	private Collection<GrantedAuthority> authorities;
	/**
	 * 账户是否过期
	 */
	private boolean isAccountNonExpired = false;
	/**
	 * 账户是否被锁定
	 */
	private boolean isAccountNonLocked = false;
	/**
	 * 证书是否过期
	 */
	private boolean isCredentialsNonExpired = false;
	/**
	 * 账户是否有效
	 */
	private boolean isEnabled = true;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}
}

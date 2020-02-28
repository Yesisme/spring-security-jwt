package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.security.UserAuthenticationProvider;
import com.security.UserPermissionEvaluator;
import com.security.handler.UserAuthAccessDeniedHandler;
import com.security.handler.UserAuthenticationEntryPointHandler;
import com.security.handler.UserLoginFailureHandler;
import com.security.handler.UserLoginSuccessHandler;
import com.security.handler.UserLogoutSuccessHandler;
import com.security.jwt.JWTAuthenticationTokenFilter;

//SpringSecurity核心配置类
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
	
	@Autowired
	private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
	
	@Autowired
	private UserLoginFailureHandler userLoginFailureHandler;
	
	@Autowired
	private UserLoginSuccessHandler userLoginSuccessHandler;
	
	@Autowired
	private UserLogoutSuccessHandler userLogoutSuccessHandler;
	
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	
    /**
     * 加密方式
     * @Author Sans
     * @CreateTime 2019/10/1 14:00
     */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	//注入自定义权限注解验证
	@Bean
	public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
	    handler.setPermissionEvaluator(new UserPermissionEvaluator());
	    return handler;
	}
	
	/**
     * 配置登录验证逻辑
     */
	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userAuthenticationProvider);
	}
	
	 /**
     * 配置security的控制逻辑
     * @Author Sans
     * @CreateTime 2019/10/1 16:56
     * @Param  http 请求
     */
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		 //不进行权限验证的请求或资源(从配置文件中读取)
		 http.authorizeRequests()
		 .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
		  //其他的需要登陆后才能访问
		  .anyRequest().authenticated()
		  .and()
		  //配置未登录自定义处理类
		  .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
		  .and()
		  //配置登录地址
		  .formLogin()
		  .loginProcessingUrl("/login/userLogin")
		  //配置登录成功自定义处理类
		  .successHandler(userLoginSuccessHandler)
		  //配置登录失败自定义处理类
		  .failureHandler(userLoginFailureHandler)
		  .and()
		  //配置登出地址
		  .logout()
		  .logoutUrl("/login/userLogout")
		  //配置用户登出自定义处理类
		  .logoutSuccessHandler(userLogoutSuccessHandler)
		  .and()
		  //配置没有权限自定义处理类
		  .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
		  .and()
		  // 开启跨域
		  .cors()
		  .and()
		  // 取消跨站请求伪造防护
		  .csrf().disable();
		 // 基于Token不需要session
		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 // 禁用缓存
		 http.headers().cacheControl();
		 // 添加JWT过滤器
		 http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
	 } 
}

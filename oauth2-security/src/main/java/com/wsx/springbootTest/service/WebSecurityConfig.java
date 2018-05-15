package com.wsx.springbootTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity // 开启spring security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;

	// 指明用户信息获取由自定义的类完成，否则需要显示设置
	// 比如auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 将验证过程交给自定义验证工具
		auth.authenticationProvider(myAuthenticationProvider);
	}

	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private MyLoginSuccessHandler myLoginSuccessHandler;

	@Autowired
	private MyLogoutHandler myLogoutHandler;

	// 设置具体得访问地址对应具体的权限
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests()//
				.antMatchers("/mybatis", "/auth/me", "/hello", "/getUserInfo/**", "/login", "/public/**").permitAll()// 设置不需要登陆地址
				.anyRequest().authenticated() // 此外所有请求都需要 登录后可以访问
				.and().formLogin().loginPage("/login")// 登陆页面
				.usernameParameter("myusername")// 自定义表单 用户名字段
				.passwordParameter("mypassword")// 自定义表单 密码字段
				.loginProcessingUrl("/login")// 登录请求拦截的url,也就是form表单提交时指定的action
				.defaultSuccessUrl("/")// 登陆成功页面
				.successHandler(myLoginSuccessHandler) // 登录成功后进入自定义处理
				.failureUrl("/login?error")// 登陆错误页面
				.permitAll() // 登录页面用户任意访问
				.and().logout().logoutSuccessUrl("/login?logout") // 退出登录后跳转页面
				.addLogoutHandler(myLogoutHandler).permitAll()// 注销行为任意访问
				.and().sessionManagement().maximumSessions(1)// 同时在线数量
				.expiredUrl("/login?expired")// 超时后跳转连接
				.sessionRegistry(sessionRegistry);
		// 添加权限验证拦截
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}

}

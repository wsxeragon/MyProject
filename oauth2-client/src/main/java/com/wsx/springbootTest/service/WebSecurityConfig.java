package com.wsx.springbootTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;

@Configuration
@EnableWebSecurity // 开启spring security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SessionRegistry sessionRegistry;

	// 指明用户信息获取由自定义的类完成，否则需要显示设置
	// 比如auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("123456").roles("USER").and().withUser("user1")

				.password("123456").roles("USER");
	}

	// 设置具体得访问地址对应具体的权限
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()//
				.antMatchers("/login", "/js/**", "/public/**", "/thirdlogin", "/sessionId").permitAll()// 设置不需要权限得地址
				.anyRequest().authenticated() // 此外所有请求都需要 登录后可以访问
				.and().formLogin().loginPage("/login")// 登陆页面
				.loginProcessingUrl("/login")// 登录请求拦截的url,也就是form表单提交时指定的action
				.defaultSuccessUrl("/")// 登陆成功页面
				.failureUrl("/login?error")// 登陆错误页面
				.permitAll() // 登录页面用户任意访问
				.and().logout().logoutSuccessUrl("/login?logout") // 退出登录后跳转页面
				.permitAll()// 注销行为任意访问
				.and().sessionManagement().maximumSessions(1)// 同时在线数量
				.expiredUrl("/login?expired")// 超时后跳转连接
				.sessionRegistry(sessionRegistry);
	}
}

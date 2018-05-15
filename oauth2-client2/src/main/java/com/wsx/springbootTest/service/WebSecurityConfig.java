package com.wsx.springbootTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired
	// private SessionRegistry sessionRegistry;

	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	// 设置具体得访问地址对应具体的权限
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests()//
				.antMatchers("/hello").permitAll()// 设置不需要权限得地址
				.anyRequest().authenticated();//
		// .and().sessionManagement().maximumSessions(1)// 同时在线数量
		// .sessionRegistry(sessionRegistry);
		// 添加权限验证拦截
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}

}

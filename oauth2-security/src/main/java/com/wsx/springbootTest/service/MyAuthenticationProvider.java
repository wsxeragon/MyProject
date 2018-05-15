package com.wsx.springbootTest.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//自定义密码验证过程
@Service
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// authentication 包含页面获取到的信息
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		long fromTime = Long.parseLong(password.split("=")[1]);
		String psw = password.split("=")[0];
		// UserDetails 包含数据库查询到的信息
		UserDetails user = (UserDetails) myUserDetailsService.loadUserByUsername(username);

		if (Math.abs(new Date().getTime() - fromTime) > 0.5 * 60 * 1000) {
			throw new BadCredentialsException("密码错误");
		}
		if (!psw.equals(user.getPassword())) {
			throw new BadCredentialsException("密码错误");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}

package com.wsx.springbootTest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	// 键为url
	// 值为 url对应得权限信息，权限名，权限描述，等等
	private Map<String, List<ConfigAttribute>> map = new HashMap<>();

	// 获取url需要得权限
	// 具体验证是否有权访问是在 decide 方法中处理
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		System.out.println(request.getRequestURL());
		AntPathRequestMatcher matcher1 = new AntPathRequestMatcher("/user/**");
		AntPathRequestMatcher matcher2 = new AntPathRequestMatcher("/admin/**");
		List<ConfigAttribute> list = new ArrayList<>();
		if (matcher1.matches(request)) {
			list.add(new SecurityConfig("USER_PERMISSION"));
		} else if (matcher2.matches(request)) {
			list.add(new SecurityConfig("ADMIN_PERMISSION"));

		}
		return list;

	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}

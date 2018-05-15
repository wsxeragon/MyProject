package com.wsx.springbootTest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.dao.PermissionDao;
import com.wsx.springbootTest.domain.SysPermission;

@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private PermissionDao permissionDao;

	// 键为url
	// 值为 url对应得权限信息，权限名，权限描述，等等
	private Map<String, List<ConfigAttribute>> map = new HashMap<>();

	// 获取url需要得权限
	// 具体验证是否有权访问是在 decide 方法中处理
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// 每次用户访问url都会进如此方法，所以只加载一次
		if (map.isEmpty()) {
			// 拿到所有权限，转换成map
			List<SysPermission> permissions = permissionDao.findAll();
			for (SysPermission sp : permissions) {
				List<ConfigAttribute> configAttributes = new ArrayList<>();
				// 添加权限得一些信息
				configAttributes.add(new SecurityConfig(sp.getName()));
				List<ConfigAttribute> tmpConfigAttributes = map.get(sp.getUrl());
				if (tmpConfigAttributes == null) {
					map.put(sp.getUrl(), configAttributes);
				} else {
					tmpConfigAttributes.addAll(configAttributes);
					map.put(sp.getUrl(), tmpConfigAttributes);
				}

			}
		}
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		System.out.println(request.getRequestURL());
		for (String k : map.keySet()) {
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(k);
			if (matcher.matches(request)) {
				return map.get(k);
			}
		}
		return null;
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

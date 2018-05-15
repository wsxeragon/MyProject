package com.wsx.springbootTest.service;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	// decide 方法是判定用户是否拥有权限

	// authentication 包含了CustomUserService中返回的GrantedAuthority集合.

	// object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request =
	// ((FilterInvocation) object).getHttpRequest();

	// configAttributes
	// 为MyInvocationSecurityMetadataSource的getAttributes(Object
	// object)这个方法返回的结果
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// url需要得权限为空，说明用户可访问url
		if (configAttributes == null || configAttributes.size() <= 0) {
			return;
		}
		// 用户拥有的权限
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			String p1 = ga.getAuthority();
			// url需要得权限
			for (ConfigAttribute ca : configAttributes) {
				String p2 = ca.getAttribute();
				// 只要有交集，就说明用户可访问url
				if (p1.equals(p2)) {
					return;
				}
			}
		}
		throw new AccessDeniedException("permission denied");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}

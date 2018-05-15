package com.wsx.springbootTest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.wsx.springbootTest.domain.MyClientDetails;
import com.wsx.springbootTest.domain.RESOURCES_ID;

@Component
public class ClientDao {
	public MyClientDetails findByClientId(String clientId) {
		MyClientDetails myClientDetails = new MyClientDetails();
		myClientDetails.setClientId("client");
		myClientDetails.setClientSecret("123456");
		Set<String> authorizedGrantTypes = new HashSet<>();
		authorizedGrantTypes.add("authorization_code");
		authorizedGrantTypes.add("refresh_token");
		myClientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
		Set<String> resourceIds = new HashSet<>();
		// 资源必须匹配
		// Invalid token does not contain resource id (1)access_denied
		resourceIds.add(RESOURCES_ID.LEVEL_ONE);
		resourceIds.add(RESOURCES_ID.LEVEL_TWO);
		myClientDetails.setResourceIds(resourceIds);
		Set<String> scopes = new HashSet<>();
		scopes.add("app");
		myClientDetails.setScope(scopes);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("client"));
		myClientDetails.setAuthorities(authorities);
		myClientDetails.setAutoApprove(false);
		return myClientDetails;
	}
}

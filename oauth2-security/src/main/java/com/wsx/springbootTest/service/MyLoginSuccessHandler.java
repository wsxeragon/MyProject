package com.wsx.springbootTest.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.wsx.springbootTest.domain.MyUserDetails;

@Service
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println(authentication.getName() + "登陆成功");
		ValueOperations<String, Long> operations = redisTemplate.opsForValue();
		operations.set(authentication.getName(), new Date().getTime());
		super.onAuthenticationSuccess(request, response, authentication);
	}

	private void invalidateSession(String username) {
		List<Object> o = sessionRegistry.getAllPrincipals();
		for (Object principal : o) {
			if (principal instanceof MyUserDetails) {
				final MyUserDetails loggedUser = (MyUserDetails) principal;
				if (username.equals(loggedUser.getUsername())) {
					List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
					if (null != sessionsInfo && sessionsInfo.size() > 0) {
						for (SessionInformation sessionInformation : sessionsInfo) {
							sessionInformation.expireNow();
						}
					}
				}
			}
		}
	}

}

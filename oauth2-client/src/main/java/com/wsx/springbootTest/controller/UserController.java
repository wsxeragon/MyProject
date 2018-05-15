package com.wsx.springbootTest.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsx.springbootTest.tool.MyTool;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	SessionRegistry sessionRegistry;
	// @Autowired
	// RestTemplate restTemplate;

	@ResponseBody
	@GetMapping("/public/getToken")
	public Map<String, Object> user1(String grant_type, String code, String redirect_uri)
			throws UnsupportedEncodingException {
		// PostOauth();
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", grant_type);
		params.put("code", code);
		params.put("redirect_uri", redirect_uri);
		Map<String, Object> map = MyTool.postForm("http://client:123456@localhost:9093/server/oauth/token", params);
		return map;
	}

	@ResponseBody
	@GetMapping("/public/getInfo")
	public Map<String, Object> info(HttpServletRequest request, String token) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<>();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		User user = new User("user", "123456", authorities);
		sessionRegistry.registerNewSession(request.getSession().getId(), user);
		List<Object> principals = sessionRegistry.getAllPrincipals();
		if (principals != null && principals.size() > 0) {
			for (Object p : principals) {
				map.put(p.toString(), p.getClass());
				map.put(((User) p).getUsername(), sessionRegistry.getAllSessions(p, false));
			}
		}
		map.put("info", sessionRegistry.getSessionInformation(request.getSession().getId()));
		map.put("sessionId", request.getSession().getId());
		// sessionRegistry.registerNewSession(request.getSession().getId(),
		// principal);
		return map;
	}

	@ResponseBody
	@GetMapping("/sessionId")
	public Map<String, Object> se(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("sessionId", request.getSession().getId());
		return map;
	}

	@GetMapping("/")
	public String user(HttpServletRequest request) {
		return "user";
	}

	@GetMapping("/public/autoLogin")
	public String autoLogin(HttpServletRequest request, Map<String, Object> map, String token) {
		Map<String, String> params = new HashMap<>();
		if (token != null && token != "") {
			params.put("access_token", token);
			Map<String, Object> result = MyTool.HttpGet("http://localhost:9083/resource/auth/me", params);
			Map<String, Object> info = (Map<String, Object>) ((Map<String, Object>) result.get("respbody"));
			// 获取用户信息，后台注册账户
			System.out.println(info);
			map.put("username", "user");
			map.put("password", "123456");
		}
		return "autoLogin";
	}

	@GetMapping("/login")
	public String user2(HttpServletRequest request) {
		return "/login";
	}
	// @GetMapping("/login")
	// public String user1() {
	// return "/login";
	// }

	public void PostOauth() {
		AccessTokenRequest accessTokenRequest = new DefaultAccessTokenRequest();
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		List<String> scopes = new ArrayList<>();
		scopes.add("app");
		resourceDetails.setScope(scopes);
		resourceDetails.setAccessTokenUri("http://localhost:9093/server/oauth/token");
		resourceDetails.setClientId("client");
		resourceDetails.setPassword("123456");
		resourceDetails.setGrantType("code");
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails,
				new DefaultOAuth2ClientContext(accessTokenRequest));
		try {
			System.out.println(oAuth2RestTemplate.getAccessToken());
			System.out.println(oAuth2RestTemplate.getAccessToken().getRefreshToken());
		} catch (OAuth2AccessDeniedException e) {
			System.out.println("登入失敗" + e.getHttpErrorCode()); // 403

			System.out.println("登入失敗" + e.getOAuth2ErrorCode()); // access_denied

			System.out.println("登入失敗" + e.getMessage()); // Access token denied.

			System.out.println("登入失敗" + e.getLocalizedMessage()); // Access
																	// token
																	// denied.

			System.out.println("登入失敗" + e.getSummary()); // error="access_denied",
															// error_description="Access
															// token denied."

		}
	}
}
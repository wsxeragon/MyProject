package com.wsx.springbootTest.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsx.springbootTest.domain.Msg;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	SessionRegistry sessionRegistry;
	// @Autowired
	// RestTemplate restTemplate;

	@GetMapping("/")
	public String user(HttpServletRequest request) {
		return "user";
	}

	// sso系统中，子系统的session中存的principal 只是用户名,主系统的session中存有MyUserDetails详细信息
	@ResponseBody
	@GetMapping("/info1")
	public Map<String, Object> info(HttpServletRequest request, Principal principalp) {
		Map<String, Object> map = new HashMap<>();
		map.put("principalp", principalp);
		map.put("getAllPrincipals()", sessionRegistry.getAllPrincipals());
		int i = 1;
		for (Object o : sessionRegistry.getAllPrincipals()) {
			map.put("class" + i, o.getClass());// String 但在主系统中类型为MyUserDetails
			map.put("session" + i, sessionRegistry.getAllSessions(o, false));
		}
		map.put("success", true);
		return map;
	}

	@ResponseBody
	@GetMapping("/hello")
	public Map<String, Object> test(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}

	// 子系统权限失效，虽然权限信息可以获取到，但是需要重写url鉴权过程
	@RequestMapping("/admin/admin")
	public String indexHtml(Map<String, Object> map) {
		map.put("msg", "hello world");
		return "/admin/admin";
	}

	@RequestMapping("/user/hello0")
	public String hello(Map<String, Object> map) {
		Msg msg = new Msg("测试标题", "测试内容", "你需要拥有管理员权限");
		map.put("msg", msg);
		return "/user/hello0";
	}

}
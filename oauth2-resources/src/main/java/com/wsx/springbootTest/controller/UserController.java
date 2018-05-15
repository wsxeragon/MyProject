package com.wsx.springbootTest.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	// 设定为受保护资源
	@ResponseBody
	@GetMapping("/auth/me")
	public Principal user(Principal principal) {
		Map<String, Object> map = new HashMap<>();
		map.put("p", principal);
		map.put("msg", "被发现了！！！");
		return principal;
	}

	@ResponseBody
	@GetMapping("/public/me")
	public Map<String, Object> user1() {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "都可以看");
		return map;
	}

}
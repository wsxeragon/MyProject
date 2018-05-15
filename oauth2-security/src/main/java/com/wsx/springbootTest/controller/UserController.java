package com.wsx.springbootTest.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsx.springbootTest.dao.PermissionDao;
import com.wsx.springbootTest.dao.UserDao;
import com.wsx.springbootTest.domain.Msg;
import com.wsx.springbootTest.domain.MyProps;
import com.wsx.springbootTest.domain.MyUserDetails;
import com.wsx.springbootTest.domain.SysUser;
import com.wsx.springbootTest.domain.User;
import com.wsx.springbootTest.service.UserService;
import com.wsx.springbootTest.tool.SpringUtil;

@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MyProps myprops;

	@RequestMapping("/getUserInfo/{id}")
	@ResponseBody
	public Map<String, Object> getUserInfo(@PathVariable(value = "id") String id) {
		List<User> users = userService.findUserInfo(id);
		Map<String, Object> map = new HashMap<>();
		User user1 = (User) SpringUtil.getBean("user123", User.class);
		map.put("mysqluser", users);
		map.put("myprops", myprops);
		MyProps myProps1 = new MyProps();
		myProps1.setSimpleProp("test");
		map.put("myProps1", myProps1);
		map.put("user1", user1);
		return map;
	}

	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private RedisTemplate redisTemplate;

	@Scheduled(cron = "0 0/10 * * * ?")
	public void ScanUserOnline() {
		// 获取所有在线用户
		List<Object> o = sessionRegistry.getAllPrincipals();
		for (Object principal : o) {
			if (principal instanceof MyUserDetails) {
				MyUserDetails loggedUser = (MyUserDetails) principal;
				ValueOperations<String, Long> operations = redisTemplate.opsForValue();
				Long start = operations.get(loggedUser.getUsername());
				if (start > 0 && new Date().getTime() - start > 1000 * 600) {
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

	@RequestMapping("/admin/admin")
	public String indexHtml(Map<String, Object> map) {
		map.put("msg", "hello world");
		return "/admin/admin";
	}

	@RequestMapping("/login")
	public String login(Map<String, Object> map) {
		return "/login";
	}

	@RequestMapping("/user/hello0")
	public String hello(Map<String, Object> map) {
		Msg msg = new Msg("测试标题", "测试内容", "你需要拥有管理员权限");
		map.put("msg", msg);
		return "/user/hello0";
	}

	@RequestMapping("/")
	public String index(Map<String, Object> map, HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("-----");
		System.out.println(principal);
		System.out.println(request.getUserPrincipal());
		System.out.println("-----");
		if (principal instanceof MyUserDetails) {
			MyUserDetails userDetails = (MyUserDetails) principal;
			System.out.println(userDetails.getPhone());
		}
		return "/index";
	}

	@Autowired
	private PermissionDao permissionDao;

	@ResponseBody
	@RequestMapping(value = "/mybatis", method = RequestMethod.GET)
	public Map<String, Object> mybatis() {
		Map<String, Object> map = new HashMap<>();
		SysUser sysUser = userService.findByUserName("root");
		SysUser sysuser1 = permissionDao.findByUsername("root");
		map.put("sysUser", sysUser);
		map.put("sysuser1", sysuser1);
		return map;
	}

	// 设定为受保护资源
	@ResponseBody
	@GetMapping("/auth/me")
	public Map<String, Object> user() {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "被发现了！！！");
		return map;
	}

	@ResponseBody
	@GetMapping("/info1")
	public Map<String, Object> info(HttpServletRequest request, Principal principalp) {
		Map<String, Object> map = new HashMap<>();
		map.put("principalp", principalp);
		map.put("getAllPrincipals()", sessionRegistry.getAllPrincipals());
		int i = 1;
		for (Object o : sessionRegistry.getAllPrincipals()) {
			map.put("class" + i, o.getClass());// MyUserDetails
			map.put("session" + i, sessionRegistry.getAllSessions(o, false));
		}
		map.put("success", true);
		return map;
	}

}
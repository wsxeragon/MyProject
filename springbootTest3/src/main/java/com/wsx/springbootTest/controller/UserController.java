package com.wsx.springbootTest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsx.springbootTest.domain.MyProps;
import com.wsx.springbootTest.domain.User;
import com.wsx.springbootTest.service.UserService;
import com.wsx.springbootTest.tool.SpringUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MyProps myprops;

	@RequestMapping("/test")
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

	// 获取所有用户
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserList() {

		List<User> users = userService.getUserList();
		Map<String, Object> map = new HashMap<>();
		map.put("users", users);
		return map;
	}

	// 获取所有用户
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test() {
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "hello");
		return map;
	}

	// 获取某个用户
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUser(@PathVariable String id) {
		User user = userService.getUser(id);
		Map<String, Object> map = new HashMap<>();
		map.put("users", user);
		map.put("port", 9093);
		return map;
	}

	// post 添加一个用户
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUser(@ModelAttribute User user) {
		boolean flag = userService.addUser(user);
		Map<String, Object> map = new HashMap<>();
		map.put("success", flag);
		return map;
	}

	// delete 删除一个用户
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteUser(@PathVariable String id) {
		boolean flag = userService.deleteUser(id);
		Map<String, Object> map = new HashMap<>();
		map.put("flag", flag);
		return map;
	}

	/**
	 * tomcat默认不接收put请求的请求体 
	 * springmvc项目需要在xml中配置org.springframework.web.filter. HttpPutFormContentFilter过滤器  
	 * springboot项目需要手动配置过滤器，例如PutFilter.java  
	 * 最后把form表单的enctype属性设置为application/x- www-form-urlencoded
	 * 
	 */
	// put 修改用户
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> updateUser(@PathVariable String id, @ModelAttribute User user) {
		User oldUser = userService.getUser(id);
		oldUser.setAge(user.getAge());
		oldUser.setName(user.getName());
		boolean flag = userService.updateUser(oldUser);
		Map<String, Object> map = new HashMap<>();
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/testZuul")
	@ResponseBody
	public Map<String, Object> test1() {
		Map<String, Object> map = new HashMap<>();
		map.put("port", 9093);
		return map;
	}
}
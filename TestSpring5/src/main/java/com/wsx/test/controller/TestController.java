package com.wsx.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsx.test.domain.Person2;

@RequestMapping("/controller")
@Controller
public class TestController {

	@ResponseBody
	@RequestMapping("/testBefore")
	public Map<String, Object> testBefore(@RequestParam(required = false) Map<String, Object> map) {
		map.put("1", "a");
		System.out.println(map);
		return map;
	}

	@Resource(name = "p1")
	Person2 p4;

	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		System.out.println(p4);
		return map;
	}

}

package com.wsx.springbootTest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = { Exception.class })
	public Map<String, String> defaultErrorHandler(HttpServletRequest req, Exception e) {

		// 打印异常信息：
		System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
		Map<String, String> map = new HashMap<>();
		map.put("msg", e.getMessage());
		return map;
		/*
		 * 返回json数据或者String数据： 那么需要在方法上加上注解：@ResponseBody 添加return即可。
		 */

		/*
		 * 返回视图： 定义一个ModelAndView即可， 然后return;
		 * 定义视图文件(比如：error.html,error.ftl,error.jsp);
		 *
		 */
	}

}
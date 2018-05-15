package com.wsx.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wsx.test.domain.Person2;

@Configuration // 相当与xml中的beans标签
public class BeanService {

	@Value("123456") // 为id赋值123456
	private int id;

	// @Value("${org_name}")//自定义的MyPropertyPlaceholderConfigurer加载的值也可以取到
	@Value("${jdbc.username}") // 为username赋值，值为从容器中取出的jdbc.username的值
	private String username;

	@Bean(name = "p2") // 相当与xml中的bean标签
	public Person2 getPerson() {
		Person2 p1 = new Person2(id, username, 19);
		return p1;
	}
}

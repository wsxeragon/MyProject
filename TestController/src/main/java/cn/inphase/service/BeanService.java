package cn.inphase.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.inphase.domain.Person;

@Configuration // 相当与xml中的beans标签
public class BeanService {

	@Value("123456") // 为id赋值123456
	private int id;

	// @Value("${org_name}")//自定义的MyPropertyPlaceholderConfigurer加载的值也可以取到
	@Value("${jdbc.username}") // 为username赋值，值为从容器中取出的jdbc.username的值
	private String username;

	@Bean(name = "myPerson") // 相当与xml中的bean标签
	public Person getPerson() {
		Person p1 = new Person();
		p1.setId(id);
		p1.setAge(21);
		p1.setName(username);
		return p1;
	}
}

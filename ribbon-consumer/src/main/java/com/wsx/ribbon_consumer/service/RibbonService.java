package com.wsx.ribbon_consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RibbonService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	// @HystrixCommand(fallbackMethod = "doError")
	public String doSomething() {
		return restTemplate.getForEntity("http://my-service/user/testZuul", String.class).getBody();
	}

	// @HystrixCommand(fallbackMethod = "doError")
	public String doSomething1(String id) {
		// ServiceInstance instance =
		// loadBalancerClient.choose("other-service");//
		// // 访问策略
		// System.out.println(instance);
		// return restTemplate
		// .getForEntity("http://" + instance.getHost() + ":" +
		// instance.getPort() + "/user/" + id, String.class)
		// .getBody();

		return restTemplate.getForEntity("http://my-service/user/" + id, String.class).getBody();
	}

	// public String doError() {
	// Map<String, String> map = new HashMap<>();
	// map.put("code", "-2");
	// map.put("msg", "啊哈哈哈哈出错了");
	// return new Gson().toJson(map);
	// }
	//
	// public String doError(String id) {
	// Map<String, String> map = new HashMap<>();
	// map.put("code", "-2");
	// map.put("msg", "查询id为" + id + "的用户失败，啊哈哈哈哈出错了");
	// return new Gson().toJson(map);
	// }

}
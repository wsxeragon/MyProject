package com.wsx.ribbon_consumer.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wsx.ribbon_consumer.service.RibbonService;

@RestController
public class RibbonController {

	private final CounterService counterService;
	private final GaugeService gaugeService;

	@Autowired
	public RibbonController(CounterService counterService, GaugeService gaugeService) {
		this.counterService = counterService;
		this.gaugeService = gaugeService;
	}

	@Autowired
	RibbonService ribbonServer;

	@GetMapping("/testZuul")
	public Map<String, String> helloConsumer() {
		counterService.increment("hello.count");
		gaugeService.submit("hello.gauge", 12345.0);
		String json = ribbonServer.doSomething();
		System.out.println(json);
		Map<String, String> map = new HashMap<>();
		map.put("data", json);
		map.put("from", "ribbon-consumer");
		return map;
	}

	@RequestMapping(value = "/user/{id}")
	public Map<String, String> doSomething1(@PathVariable String id) {
		String json = ribbonServer.doSomething1(id);
		System.out.println(json);
		Map<String, String> map = new HashMap<>();
		map.put("data", json);
		map.put("from", "ribbon-consumer");
		return map;
	}

	@Test
	public void test() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate1 = new RestTemplate(requestFactory);
		System.out.println(restTemplate1.getForEntity("http://localhost:9093/hello", String.class).getBody());
	}

	@Test
	public void testRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate1 = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
		headers.setContentType(mediaType);
		headers.add("imei", "123123");
		headers.add("token", "3998ee18-b23e-4617-947f-ff8bee611607");
		headers.add("phone", "18620543981");
		headers.add("userid", "29305065989");

		// post表单时 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// 也支持中文
		params.add("data", "抽奖");
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

		// post json 字符串
		// MediaType mediaType =
		// MediaType.parseMediaType("application/json;charset=UTF-8");
		// Map<String, String> map = new HashMap<>();
		// map.put("data", "123");
		// HttpEntity<String> httpEntity = new HttpEntity<>(new
		// Gson().toJson(map), headers);

		restTemplate1.postForEntity("http://110.190.90.247:8002/DemondDetailsService/usermanager/valid", httpEntity,
				String.class);
		// restTemplate1.getForEntity("http://110.190.90.247:8002/DemondDetailsService/usermanager/valid?data=123",
		// String.class);
	}
}
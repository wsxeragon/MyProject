package com.wsx.Zuul.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

@Component
public class ZuulDao {
	public Map<String, ZuulRoute> getRoutes() {
		Map<String, ZuulRoute> routeMap = new LinkedHashMap<>();
		// ZuulRoute zuulRoute1 = new ZuulRoute("api-1", "/b/**", null,
		// "http://localhost:9091/", true, true, null);
		// ZuulRoute zuulRoute2 = new ZuulRoute("api-2", "/c/**", null,
		// "http://localhost:9092/", true, true, null);
		// routeMap.put(zuulRoute1.getPath(), zuulRoute1);
		// routeMap.put(zuulRoute2.getPath(), zuulRoute2);
		ZuulRoute zuulRoute1 = new ZuulRoute("api-1", "/test-service/**", "ribbon-consumer", null, true, true, null);
		routeMap.put(zuulRoute1.getPath(), zuulRoute1);
		return routeMap;
	}

}

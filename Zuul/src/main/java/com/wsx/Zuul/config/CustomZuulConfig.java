package com.wsx.Zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wsx.Zuul.dao.ZuulDao;
import com.wsx.Zuul.service.CustomRouteLocator;

@Configuration
public class CustomZuulConfig {

	@Autowired
	private ZuulProperties zuulProperties;

	@Autowired
	private ServerProperties serverProperties;

	@Autowired
	private ZuulDao zuulDao;

	@Bean("routeLocator")
	public CustomRouteLocator getRouteLocator() {
		CustomRouteLocator customRouteLocator = new CustomRouteLocator(serverProperties.getServletPrefix(),
				zuulProperties);
		customRouteLocator.setZuulDao(zuulDao);
		return customRouteLocator;
	}
}

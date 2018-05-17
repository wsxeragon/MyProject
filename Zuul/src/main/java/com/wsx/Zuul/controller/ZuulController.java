package com.wsx.Zuul.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wsx.Zuul.service.RefreshRouteService;

@RestController("zuul")
public class ZuulController {

	@Autowired
	private RefreshRouteService refreshRouteService;

	@GetMapping("/refreshRoute")
	public Map<String, String> refreshRoute() {
		Map<String, String> map = new HashMap<>();
		refreshRouteService.refreshRoute();
		map.put("success", "true");
		return map;
	}
}

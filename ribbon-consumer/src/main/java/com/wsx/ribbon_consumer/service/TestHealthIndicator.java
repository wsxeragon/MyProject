package com.wsx.ribbon_consumer.service;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TestHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		long l = new Date().getTime();
		if (l % 2 == 0) {
			return Health.down().withDetail("key", "error  101").build();
		}

		return Health.unknown().withDetail("key", "unknown 102").build();
	}

}

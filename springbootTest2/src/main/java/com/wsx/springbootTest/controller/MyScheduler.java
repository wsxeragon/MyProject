package com.wsx.springbootTest.controller;

import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class MyScheduler {
	@Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
	public void scheduler() {
		System.out.println(">>>>>>>>> SchedulingConfig.scheduler()");
	}
}

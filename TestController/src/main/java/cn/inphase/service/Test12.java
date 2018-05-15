package cn.inphase.service;

import org.springframework.stereotype.Component;

@Component
public class Test12 {
	public int produceException() {
		int i = 1 / 0;
		return 1;
	}

}

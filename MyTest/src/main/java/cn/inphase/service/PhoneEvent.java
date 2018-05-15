package cn.inphase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;

public class PhoneEvent extends ApplicationEvent {

	private String phoneNo;

	private static List<String> blackList;

	static {
		blackList = new ArrayList<>();
		blackList.add("123456789");
	}

	public PhoneEvent(Object source) {
		super(source);
	}

	public PhoneEvent(Object source, String phoneNo) {
		super(source);
		this.phoneNo = phoneNo;
	}

	public boolean isBlock() {
		if (blackList.contains(phoneNo)) {
			return true;
		} else {
			return false;
		}
	}

}

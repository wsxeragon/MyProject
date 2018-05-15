package com.wsx.springbootTest.domain;

public class Customer {
	private String phone;
	private String nickname;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Customer [phone=" + phone + ", nickname=" + nickname + "]";
	}

}

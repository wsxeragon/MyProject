package com.wsx.springbootTest.domain;

public class User {
	private String age;
	private String name;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + "]";
	}

}

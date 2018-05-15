package cn.inphase.domain;

import java.io.Serializable;

public class Peter implements Serializable {
	private String name;
	private int age;
	private Person friend;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person getFriend() {
		return friend;
	}

	public void setFriend(Person friend) {
		this.friend = friend;
	}

	@Override
	public String toString() {
		return "Peter [name=" + name + ", age=" + age + ", friend=" + friend + "]";
	}

}
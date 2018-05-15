package cn.inphase.domain;

import java.io.Serializable;

public abstract class Peter implements Serializable {
	private static int id;
	private String name;
	private int age;
	private Person friend;

	{
		id += 1;
	}

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

	public abstract Person getFriend();

	public void setFriend(Person friend) {
		this.friend = friend;
	}

	@Override
	public String toString() {
		return "Peter [id=" + id + ", name=" + name + ", age=" + age + ", friend=" + friend + "]";
	}

}
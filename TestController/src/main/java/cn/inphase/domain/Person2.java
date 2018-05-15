package cn.inphase.domain;

import java.io.Serializable;

public class Person2 implements Serializable {

	public Person2() {
	}

	public Person2(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	private int id;
	private String name;
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
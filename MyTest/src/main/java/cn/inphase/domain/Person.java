package cn.inphase.domain;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.google.gson.annotations.SerializedName;

public class Person implements Serializable {
	private static int id;
	// value的意思是序列化是将name替换成name0值，反序列化时name0识别成name，反序列化时注意原始的name不再被识别
	// alternate的意思是反序列化时name0，NAME，NA都会被识别出成name
	@SerializedName(value = "name0", alternate = { "NAME", "NA" })
	private String name;
	private int age;

	private static String test = "hahahah";

	public static String getTest() {
		return test;
	}

	public static void setTest(String test) {
		Person.test = test;
	}

	{
		id += 1;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Person.id = id;
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
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", test=" + test + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@PreDestroy
	public void destroy() throws Exception {
		System.out.println("一个bean死亡");
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("一个bean新生");
	}

}
package cn.inphase.domain;

public class Customer {
	private String name;
	private String age;

	public Customer(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}

	public Customer() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + "]";
	}

}

package cn.inphase.domain;

import java.util.List;

public class User {
	private String name;
	private String age;
	private Address address;
	private List<Order> orderList;

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public User(String name, String age, Address address, List<Order> orderList) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.orderList = orderList;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", address=" + address + ", orderList=" + orderList + "]";
	}

}

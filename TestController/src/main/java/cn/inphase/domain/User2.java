package cn.inphase.domain;

public class User2 extends User {

	private String protect;

	public User2() {
		super.protect = "12";
		System.out.println(super.protect);
		System.out.println(this.protect);
		this.protect = "21";
		System.out.println(super.protect);
		System.out.println(this.protect);
	}
}
package cn.inphase.domain;

import com.google.gson.annotations.SerializedName;

public class Customer {
	@SerializedName(value = "id", alternate = { "ID", "Id" })
	private String id;
	private String account;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", account=" + account + "]";
	}

}
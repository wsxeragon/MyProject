package cn.inphase.domain;

import java.io.Serializable;

public class Info implements Serializable {
	private String msg;
	private int code;

	public Info() {
		super();
	}

	public Info(String msg, int code) {
		super();
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Info [msg=" + msg + ", code=" + code + "]";
	}

}

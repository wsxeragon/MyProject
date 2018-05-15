package com.inphase.sparrow.entity;

/**   
 * @Description:异步返回数据统一封装
 * @author: sunchao
 */
public class ResponseMessage<T>{
	
	/**
	 * @Description 返回错误码
	 */
	private int code;
	
	/**
	 * @Description 返回提示消息
	 */
	private String message;
	
	/**
	 * @Description 返回的具体数据
	 */
	private T data;
	
	public ResponseMessage(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public ResponseMessage(int code, String message, T data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}

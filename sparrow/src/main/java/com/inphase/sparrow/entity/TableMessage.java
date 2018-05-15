package com.inphase.sparrow.entity;

/**      
 * @Description:对页面列表控件的返回数据封装
 * @author: sunchao
 */
public class TableMessage<T> {

	private int iTotalRecords;
	
	private String sEcho;
	
	private int iTotalDisplayRecords;
	
	private T aaData;
	
	public TableMessage(int total, String sEcho, T data){
		this.iTotalDisplayRecords = total;
		this.sEcho = sEcho;
		this.iTotalDisplayRecords = total;
		this.aaData = data;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public T getAaData() {
		return aaData;
	}

	public void setAaData(T aaData) {
		this.aaData = aaData;
	}
}

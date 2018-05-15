package com.inphase.sparrow.entity;

import java.util.Map;

/**      
 * @Description:table控件接收的统一参数封装
 * @author: sunchao
 */
public class TableParam {
	
	/**
	 * @Description 要显示的页数
	 */
	private int iDisplayStart;
	
	/**
	 * @Description 每页显示的数量
	 */
	private int iDisplayLength;
	
	/**
	 * @Description 按照第几列排序
	 */
	private int iSortCol_0;
	
	/**
	 * @Description 升序或者降序
	 */
	private String sSortDir_0;
	
	/**
	 * @Description table控件默认返回消息
	 */
	private String sEcho;
	
	/**
	 * @Description 查询总数
	 */
	private int sTotalRecord;

	/**
	 * @Description 前端查询条件
	 */
	private Map<String, Object> searchCondition;

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiSortCol() {
		return iSortCol_0;
	}

	public void setiSortCol(int iSortCol) {
		this.iSortCol_0 = iSortCol;
	}

	public String getsSortDir() {
		return sSortDir_0;
	}

	public void setsSortDir(String sSortDir) {
		this.sSortDir_0 = sSortDir;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public Map<String, Object> getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(Map<String, Object> searchCondition) {
		this.searchCondition = searchCondition;
	}

	public int getsTotalRecord() {
		return sTotalRecord;
	}

	public void setsTotalRecord(int sTotalRecord) {
		this.sTotalRecord = sTotalRecord;
	}
}

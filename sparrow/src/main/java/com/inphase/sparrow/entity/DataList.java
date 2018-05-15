/**
 * 
 */
package com.inphase.sparrow.entity;

/**      
 * @Description:列表数据实体类
 * @author: sunchao
 */
public class DataList<T> {

	private int count;
	
	private T list;
	
	public DataList(int count, T list){
		this.count = count;
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public T getList() {
		return list;
	}

	public void setList(T list) {
		this.list = list;
	}
}

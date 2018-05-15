package com.inphase.sparrow.entity.system;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
	
	private long operId;
	
	private String operName;
	
	private String operLogin;
	
	private String operPassword;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date operCreatedate;
	
	private int operStatus;
	
	private String operPhone;
	
	private int operSex;
	
	private String operQQ;
	
	private String operHeadpic;
	
	private String operAddress;
	
	private String operEmail;
	
	private String operIP;
	
	private long operProvience;
	
	private long operCity;
	
	private long operDistrict;
	
	private String operRole;
	
	private String operArea;
	
	private List<FunctionItem> functionItemList;
	
	public long getOperId() {
		return operId;
	}

	public void setOperId(long operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperLogin() {
		return operLogin;
	}

	public void setOperLogin(String operLogin) {
		this.operLogin = operLogin;
	}

	public String getOperPassword() {
		return operPassword;
	}

	public void setOperPassword(String operPassword) {
		this.operPassword = operPassword;
	}

	public Date getOperCreatedate() {
		return operCreatedate;
	}

	public void setOperCreatedate(Date operCreatedate) {
		this.operCreatedate = operCreatedate;
	}

	public int getOperStatus() {
		return operStatus;
	}

	public void setOperStatus(int operStatus) {
		this.operStatus = operStatus;
	}

	public String getOperPhone() {
		return operPhone;
	}

	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	public int getOperSex() {
		return operSex;
	}

	public void setOperSex(int operSex) {
		this.operSex = operSex;
	}

	public String getOperQQ() {
		return operQQ;
	}

	public void setOperQQ(String operQQ) {
		this.operQQ = operQQ;
	}

	public String getOperHeadpic() {
		return operHeadpic;
	}

	public void setOperHeadpic(String operHeadpic) {
		this.operHeadpic = operHeadpic;
	}

	public String getOperAddress() {
		return operAddress;
	}

	public void setOperAddress(String operAddress) {
		this.operAddress = operAddress;
	}

	public String getOperEmail() {
		return operEmail;
	}

	public void setOperEmail(String operEmail) {
		this.operEmail = operEmail;
	}

	public String getOperIP() {
		return operIP;
	}

	public void setOperIP(String operIP) {
		this.operIP = operIP;
	}

	public List<FunctionItem> getFunctionItemList() {
		return functionItemList;
	}

	public void setFunctionItemList(List<FunctionItem> functionItemList) {
		this.functionItemList = functionItemList;
	}

	public String getOperRole() {
		return operRole;
	}

	public void setOperRole(String operRole) {
		this.operRole = operRole;
	}

	public String getOperArea() {
		return operArea;
	}

	public void setOperArea(String operArea) {
		this.operArea = operArea;
	}

	public long getOperProvience() {
		return operProvience;
	}

	public void setOperProvience(long operProvience) {
		this.operProvience = operProvience;
	}

	public long getOperCity() {
		return operCity;
	}

	public void setOperCity(long operCity) {
		this.operCity = operCity;
	}

	public long getOperDistrict() {
		return operDistrict;
	}

	public void setOperDistrict(long operDistrict) {
		this.operDistrict = operDistrict;
	}
}

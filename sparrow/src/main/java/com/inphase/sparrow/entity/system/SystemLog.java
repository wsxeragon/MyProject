package com.inphase.sparrow.entity.system;

public class SystemLog {
	
	private long logId;
	
	private long operId;
	
	private String logDatetime;
	
	private int logMode;
	
	private String logContent;
	
	private String logIP;
	
	private String operName;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public long getOperId() {
		return operId;
	}

	public void setOperId(long operId) {
		this.operId = operId;
	}

	public String getLogDatetime() {
		return logDatetime;
	}

	public void setLogDatetime(String logDatetime) {
		this.logDatetime = logDatetime;
	}

	public int getLogMode() {
		return logMode;
	}

	public void setLogMode(int logMode) {
		this.logMode = logMode;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getLogIP() {
		return logIP;
	}

	public void setLogIP(String logIP) {
		this.logIP = logIP;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}
}

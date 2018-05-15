package cn.inphase.domain;

import java.io.Serializable;

public class SubjectAndWeeklyPeriod implements Serializable {
	private String gId;
	private String sName;
	private int cWeektime;
	private String sId;

	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public int getcWeektime() {
		return cWeektime;
	}

	public void setcWeektime(int cWeektime) {
		this.cWeektime = cWeektime;
	}

	@Override
	public String toString() {
		return "SubjectAndWeeklyPeriod [gId=" + gId + ", sName=" + sName + ", cWeektime=" + cWeektime + ", sId=" + sId
				+ "]";
	}

}

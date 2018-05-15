package cn.inphase.domain;

import java.io.Serializable;

public class MyCourse implements Serializable {
	private String cId;
	private String cType;
	private String cStatus;
	private String sId;
	private String seId;
	private String gId;
	private String cLimit;
	private String cImportant;
	private String cLimitclass;
	private String cScience;
	private int cSingle;
	private int cDouble;

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getSeId() {
		return seId;
	}

	public void setSeId(String seId) {
		this.seId = seId;
	}

	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String getcLimit() {
		return cLimit;
	}

	public void setcLimit(String cLimit) {
		this.cLimit = cLimit;
	}

	public String getcImportant() {
		return cImportant;
	}

	public void setcImportant(String cImportant) {
		this.cImportant = cImportant;
	}

	public String getcLimitclass() {
		return cLimitclass;
	}

	public void setcLimitclass(String cLimitclass) {
		this.cLimitclass = cLimitclass;
	}

	public String getcScience() {
		return cScience;
	}

	public void setcScience(String cScience) {
		this.cScience = cScience;
	}

	public int getcSingle() {
		return cSingle;
	}

	public void setcSingle(int cSingle) {
		this.cSingle = cSingle;
	}

	public int getcDouble() {
		return cDouble;
	}

	public void setcDouble(int cDouble) {
		this.cDouble = cDouble;
	}

	@Override
	public String toString() {
		return "MyCourse [cId=" + cId + ", cType=" + cType + ", cStatus=" + cStatus + ", sId=" + sId + ", seId=" + seId
				+ ", gId=" + gId + ", cLimit=" + cLimit + ", cImportant=" + cImportant + ", cLimitclass=" + cLimitclass
				+ ", cScience=" + cScience + ", cSingle=" + cSingle + ", cDouble=" + cDouble + "]";
	}

}

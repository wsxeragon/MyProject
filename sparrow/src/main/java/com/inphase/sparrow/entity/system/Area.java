package com.inphase.sparrow.entity.system;

import java.util.List;

import com.inphase.sparrow.base.annotation.ZTreeField;
import com.inphase.sparrow.base.annotation.ZTreeProperty;

public class Area {

	/**
	 * @Fields areaId : 地域Id（逻辑ID）
	 */
	@ZTreeProperty(value=ZTreeField.ID)
	private long areaId;
	
	/**
	 * @Fields areaFatherId : 父级地域Id（逻辑ID）
	 */
	@ZTreeProperty(value=ZTreeField.PID)
	private long areaFatherId;
	
	/**
	 * @Fields areaCityCode : 行政区域编码
	 */
	private String areaCityCode;
	
	/**
	 * @Fields areaName : 行政区域名字
	 */
	@ZTreeProperty(value=ZTreeField.NAME)
	private String areaName;
	
	/**
	 * @Description 是否被选中 1-选中 0-未选中
	 */
	@ZTreeProperty(value=ZTreeField.CHECKED)
	private int checked;
	
	private List<Area> children;

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public long getAreaFatherId() {
		return areaFatherId;
	}

	public void setAreaFatherId(long areaFatherId) {
		this.areaFatherId = areaFatherId;
	}

	public String getAreaCityCode() {
		return areaCityCode;
	}

	public void setAreaCityCode(String areaCityCode) {
		this.areaCityCode = areaCityCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public List<Area> getChildren() {
		return children;
	}

	public void setChildren(List<Area> children) {
		this.children = children;
	}
}

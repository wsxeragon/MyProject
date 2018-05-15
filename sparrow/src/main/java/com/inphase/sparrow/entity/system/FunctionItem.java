package com.inphase.sparrow.entity.system;

import java.util.List;

import com.inphase.sparrow.base.annotation.ZTreeProperty;
import com.inphase.sparrow.base.annotation.ZTreeField;

public class FunctionItem {

	@ZTreeProperty(value=ZTreeField.ID)
	private long funnId;
	
	@ZTreeProperty(value=ZTreeField.PID)
	private long parentId;
	
	@ZTreeProperty(value=ZTreeField.NAME)
	private String funnName;
	
	private int funnType;
	
	private String funnDescription;
	
	private long funnSort;
	
	private String funnUrl;
	
	private String funnImage;
	
	private int funnStatus;
	
	@ZTreeProperty(value=ZTreeField.CHECKED)
	private int funnSelected;
	
	private List<FunctionItem> children;

	public long getFunnId() {
		return funnId;
	}

	public void setFunnId(long funnId) {
		this.funnId = funnId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getFunnName() {
		return funnName;
	}

	public void setFunnName(String funnName) {
		this.funnName = funnName;
	}

	public int getFunnType() {
		return funnType;
	}

	public void setFunnType(int funnType) {
		this.funnType = funnType;
	}

	public String getFunnDescription() {
		return funnDescription;
	}

	public void setFunnDescription(String funnDescription) {
		this.funnDescription = funnDescription;
	}

	public long getFunnSort() {
		return funnSort;
	}

	public void setFunnSort(long funnSort) {
		this.funnSort = funnSort;
	}

	public String getFunnUrl() {
		return funnUrl;
	}

	public void setFunnUrl(String funnUrl) {
		this.funnUrl = funnUrl;
	}

	public String getFunnImage() {
		return funnImage;
	}

	public void setFunnImage(String funnImage) {
		this.funnImage = funnImage;
	}

	public int getFunnStatus() {
		return funnStatus;
	}

	public void setFunnStatus(int funnStatus) {
		this.funnStatus = funnStatus;
	}

	public List<FunctionItem> getChildren() {
		return children;
	}

	public void setChildren(List<FunctionItem> children) {
		this.children = children;
	}

	public int getFunnSelected() {
		return funnSelected;
	}

	public void setFunnSelected(int funnSelected) {
		this.funnSelected = funnSelected;
	}
}

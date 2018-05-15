package com.inphase.sparrow.entity.system;

public class Role {

	private long roleId;

	private String roleName;

	private String roleDescription;

	private long roleIsprivate;
	
	private String roleSelected;
	
	private String functionIds;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public long getRoleIsprivate() {
		return roleIsprivate;
	}

	public void setRoleIsprivate(long roleIsprivate) {
		this.roleIsprivate = roleIsprivate;
	}

	public String getRoleSelected() {
		return roleSelected;
	}

	public void setRoleSelected(String roleSelected) {
		this.roleSelected = roleSelected;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
}

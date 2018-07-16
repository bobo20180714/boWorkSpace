package com.xpoplarsoft.limits.role.bean;

/**
 * 类功能: 角色实体类
 * 
 * @author chen.jie
 * @date 2013-7-2
 */
public class PoplarRole {

	/**
	 * 角色id
	 */
	private String pkId;

	/**
	 * 角色编码
	 */
	private String roleCode;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 创建人ID
	 */
	private String createUserCode;

	/**
	 * 创建日期
	 */
	private String createTime;
	
	/**
	 * 更新人ID
	 */
	private String updateUserCode;
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	
	/**
	 * 状态
	 */
	private String state;

	public String getUpdateUserCode() {
		return updateUserCode;
	}

	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}

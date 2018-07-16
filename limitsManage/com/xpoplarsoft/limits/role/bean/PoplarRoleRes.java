package com.xpoplarsoft.limits.role.bean;

/**
 * 类功能: 角色资源类
 * 
 * @author chen.jie
 * @date 2013-7-2
 */
public class PoplarRoleRes {

	/**
	 * 角色编码
	 */
	private String roleCode;

	/**
	 * 资源列表
	 */
	private String resCodes;

	/**
	 * 创建人ID
	 */
	private String updateUserCode;

	public String getUpdateUserCode() {
		return updateUserCode;
	}

	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getResCodes() {
		return resCodes;
	}

	public void setResCodes(String resCodes) {
		this.resCodes = resCodes;
	}
	
}

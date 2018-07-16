package com.xpoplarsoft.limits.login.bean;

import java.util.List;

import com.xpoplarsoft.limits.role.bean.PoplarRole;


/**
 * 类功能: 登陆后session类
 * 
 * @author chenjie
 * @date 2014-3-26
 */
public class LoginUser {
	
	/**
	 * 账号
	 */
	private String userAccount;
	
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 部门编号
	 */
	private String org_code;
	
	/**
	 * 部门编号
	 */
	private String org_name;
	
	private List<PoplarRole> userRoleList;  

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<PoplarRole> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<PoplarRole> userRoleList) {
		this.userRoleList = userRoleList;
	}

}

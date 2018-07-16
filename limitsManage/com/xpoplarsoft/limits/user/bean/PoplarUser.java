package com.xpoplarsoft.limits.user.bean;

/**
 * 用户信息实体类
 * 
 * @author 王晓东
 * @date 2015-01-19
 */
public class PoplarUser {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 登录系统账号，唯一
	 */
	private String userAccount;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String userPwd;

	/**
	 * 用户状态 0：未启用；1：启用；2：禁用。默认0
	 */
	private String state;

	/**
	 * 创建人ID
	 */
	private String createUserId;

	/**
	 * 创建日期yyyymmddhh24miss
	 */
	private String createDate;

	/**
	 * 创建机构ID
	 */
	private String createOrg;

	/**
	 * 修改人ID
	 */
	private String updateUserId;

	/**
	 * 修改日期yyyymmddhh24miss
	 */
	private String updateDate;

	/**
	 * 修改机构ID
	 */
	private String updateOrgId;

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String creatUserId) {
		this.createUserId = creatUserId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String creatDate) {
		this.createDate = creatDate;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

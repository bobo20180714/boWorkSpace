package com.xpoplarsoft.limits.login.bean;

/**
 * 类功能: 登陆javabean类
 * 
 * @author chenjie
 * @date 2014-3-26
 */
public class LoginBean {
	
	/**
	 * 账号
	 */
	private String userAccount;
	
	/**
	 * 登陆密码
	 */
	private String password;
	
	/**
	 * 验证码
	 */
	private String checkCode;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}

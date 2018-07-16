package com.xpoplarsoft.limits.user.bean;

import com.xpoplarsoft.framework.bean.WhereBean;

public class UserWhere extends WhereBean {

	private String account;

	private String name;
	
	private String staffId;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

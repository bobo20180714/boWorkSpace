package com.xpoplarsoft.limits.role.bean;

import com.xpoplarsoft.framework.bean.WhereBean;

public class RoleWhere extends WhereBean {

	private String name;
	
	private String state;
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

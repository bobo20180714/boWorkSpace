package com.xpoplarsoft.limits.resources.bean;

import com.xpoplarsoft.framework.bean.WhereBean;

public class ResWhere extends WhereBean {

	private String res_name;

	private String state;
	
	private String resCode;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getRes_name() {
		return res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}

package com.xpoplarsoft.limits.staff.bean;

import com.xpoplarsoft.framework.bean.WhereBean;

public class StaffWhere extends WhereBean {

	private String staffCode;

	private String staffName;

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}

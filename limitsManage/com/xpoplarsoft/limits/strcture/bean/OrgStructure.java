package com.xpoplarsoft.limits.strcture.bean;

public class OrgStructure {

	private String pkId;//主键
	private String orgCode;//机构编号
	private String orgName;//机构名称
	private String orgAdress;//机构地址
	private String registCorporation; //法人代表
	private String orgLinkNo;//联系电话
	private String parentId; //上级id
	private String companyId; //公司id
	private String state; // 0，删除;1，有效;
	private String updateUserID;
	private String updateTime;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAdress() {
		return orgAdress;
	}

	public void setOrgAdress(String orgAdress) {
		this.orgAdress = orgAdress;
	}

	public String getRegistCorporation() {
		return registCorporation;
	}

	public void setRegistCorporation(String registCorporation) {
		this.registCorporation = registCorporation;
	}

	public String getOrgLinkNo() {
		return orgLinkNo;
	}

	public void setOrgLinkNo(String orgLinkNo) {
		this.orgLinkNo = orgLinkNo;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUpdateUserID() {
		return updateUserID;
	}

	public void setUpdateUserID(String updateUserID) {
		this.updateUserID = updateUserID;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}

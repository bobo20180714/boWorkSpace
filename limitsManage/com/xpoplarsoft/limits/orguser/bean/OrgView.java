package com.xpoplarsoft.limits.orguser.bean;

/**
 * @ClassName: OrgView
 * @Description: 组织机构信息
 * @author jingkewen
 * @data 2014-11-11 上午11:51:07
 *
 */
public class OrgView {
	private String id;
	private String text;
	private String org_id;
	private String org_name;
	private String org_code;
	private String org_desc;
	private String org_parent_id;
	private String parent_id;
	private String leaf;
	private String struct;
	private String create_user;//业务需求
	
	
	
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_desc() {
		return org_desc;
	}
	public void setOrg_desc(String org_desc) {
		this.org_desc = org_desc;
	}
	public String getOrg_parent_id() {
		return org_parent_id;
	}
	public void setOrg_parent_id(String org_parent_id) {
		this.org_parent_id = org_parent_id;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getStruct() {
		return struct;
	}
	public void setStruct(String struct) {
		this.struct = struct;
	}
	
	public String getDetail(){
		//拼接业务数据，只获取含业务含义的字段
		return org_code + "," + org_name + "," + org_desc + "," + org_parent_id;
	}
}

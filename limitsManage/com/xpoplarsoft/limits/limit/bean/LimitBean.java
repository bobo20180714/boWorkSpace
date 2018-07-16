package com.xpoplarsoft.limits.limit.bean;

/**
 * 资源与用户（机构）关系表
 * @author Administrator
 *
 */
public class LimitBean {

	private String grant_id;  //主键
	private String sys_resource_id; //资源主键（卫星或参数）
	private String ug_id;  //用户（机构）主键
	private int grant_manage_type; //0全部 1编辑 2只读 3预留 
	private String grant_type; //0 设备 1 遥测参数 2计算结果 3 关键参数包 4 空间环境 5 预留 6预留 
	private String grant_status;
	private String end_time;  //  有效截止日
	private String create_user;
	private String create_time;
	
	public String getGrant_id() {
		return grant_id;
	}
	public void setGrant_id(String grant_id) {
		this.grant_id = grant_id;
	}
	public String getSys_resource_id() {
		return sys_resource_id;
	}
	public void setSys_resource_id(String sys_resource_id) {
		this.sys_resource_id = sys_resource_id;
	}
	public String getUg_id() {
		return ug_id;
	}
	public void setUg_id(String ug_id) {
		this.ug_id = ug_id;
	}
	public int getGrant_manage_type() {
		return grant_manage_type;
	}
	public void setGrant_manage_type(int grant_manage_type) {
		this.grant_manage_type = grant_manage_type;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getGrant_status() {
		return grant_status;
	}
	public void setGrant_status(String grant_status) {
		this.grant_status = grant_status;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	} 
	
	public String getDetail(){
		return "";
	}
}

package com.xpoplarsoft.baseInfo.subinfo.bean;

/**
 * @ClassName: SubSystemInfo
 * @Description: 分系统基本信息
 * @author jingkewen
 * @data 2014-11-13 下午5:34:10
 *
 */
public class SubSystemInfo {
	private int sub_system_id;
	private String sub_system_name;
	private String sub_system_code;
	private String sub_system_type;
	private String sub_system_type_name;
	private String status;
	private String create_user;
	private String create_user_name;
	private String create_time;
	private String create_time_start;//查询条件需要
	private String create_time_end;
	
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	
	public int getSub_system_id() {
		return sub_system_id;
	}
	public void setSub_system_id(int sub_system_id) {
		this.sub_system_id = sub_system_id;
	}
	public String getSub_system_name() {
		return sub_system_name;
	}
	public void setSub_system_name(String sub_system_name) {
		this.sub_system_name = sub_system_name;
	}
	public String getSub_system_code() {
		return sub_system_code;
	}
	public void setSub_system_code(String sub_system_code) {
		this.sub_system_code = sub_system_code;
	}
	public String getSub_system_type() {
		return sub_system_type;
	}
	public void setSub_system_type(String sub_system_type) {
		this.sub_system_type = sub_system_type;
	}
	public String getSub_system_type_name() {
		return sub_system_type_name;
	}
	public void setSub_system_type_name(String sub_system_type_name) {
		this.sub_system_type_name = sub_system_type_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}

package com.xpoplarsoft.baseInfo.orbitrelated.bean;

/**
 * @ClassName: OrbitRelatedBean
 * @Description: 航天器相关信息
 * @author jingkewen
 * @data 2014-10-9 下午5:51:19
 *
 */
public class OrbitRelatedBean {
	private int jsjg_id;
	private String jsjg_name;
	private String jsjg_code;
	private String jsjg_desc;
	private String jsjg_status;
	private String create_user;
	private String create_user_name;
	private String create_time;
	private String grant_status;
	private String sys_resource_id;
	private int  is_time_range;
	private String start_time;
	private String end_time;
	private String view_col;
	private String create_time_start;//查询条件需要
	private String create_time_end;
	
	public String getSys_resource_id() {
		return sys_resource_id;
	}
	public void setSys_resource_id(String sys_resource_id) {
		this.sys_resource_id = sys_resource_id;
	}
	public String getGrant_status() {
		return grant_status;
	}
	public void setGrant_status(String grant_status) {
		this.grant_status = grant_status;
	}
	public int getJsjg_id() {
		return jsjg_id;
	}
	public void setJsjg_id(int jsjg_id) {
		this.jsjg_id = jsjg_id;
	}
	public String getJsjg_name() {
		return jsjg_name;
	}
	public void setJsjg_name(String jsjg_name) {
		this.jsjg_name = jsjg_name;
	}
	public String getJsjg_code() {
		return jsjg_code;
	}
	public void setJsjg_code(String jsjg_code) {
		this.jsjg_code = jsjg_code;
	}
	public String getJsjg_desc() {
		return jsjg_desc;
	}
	public void setJsjg_desc(String jsjg_desc) {
		this.jsjg_desc = jsjg_desc;
	}
	public String getJsjg_status() {
		return jsjg_status;
	}
	public void setJsjg_status(String jsjg_status) {
		this.jsjg_status = jsjg_status;
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
	public int getIs_time_range() {
		return is_time_range;
	}
	public void setIs_time_range(int is_time_range) {
		this.is_time_range = is_time_range;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getView_col() {
		return view_col;
	}
	public void setView_col(String view_col) {
		this.view_col = view_col;
	}
	
}

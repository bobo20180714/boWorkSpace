package com.xpoplarsoft.baseInfo.aloneinfo.bean;

/**
 * @ClassName: StandAloneInfo
 * @Description: 单机基本信息
 * @author jingkewen
 * @data 2014-9-4 上午11:01:46
 *
 */
public class StandAloneInfo {
	private int stand_alone_id;
	private String stand_alone_name;
	private String stand_alone_code;
	private String stand_alone_type;
	private String stand_alone_type_name;
	private String status;
	private String create_user;
	private String create_user_name;
	private String create_time;
	private String create_time_start;
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
	
	public int getStand_alone_id() {
		return stand_alone_id;
	}
	public void setStand_alone_id(int stand_alone_id) {
		this.stand_alone_id = stand_alone_id;
	}
	public String getStand_alone_name() {
		return stand_alone_name;
	}
	public void setStand_alone_name(String stand_alone_name) {
		this.stand_alone_name = stand_alone_name;
	}
	public String getStand_alone_code() {
		return stand_alone_code;
	}
	public void setStand_alone_code(String stand_alone_code) {
		this.stand_alone_code = stand_alone_code;
	}
	public String getStand_alone_type() {
		return stand_alone_type;
	}
	public void setStand_alone_type(String stand_alone_type) {
		this.stand_alone_type = stand_alone_type;
	}
	public String getStand_alone_type_name() {
		return stand_alone_type_name;
	}
	public void setStand_alone_type_name(String stand_alone_type_name) {
		this.stand_alone_type_name = stand_alone_type_name;
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

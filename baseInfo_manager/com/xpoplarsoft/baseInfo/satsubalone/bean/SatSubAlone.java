package com.xpoplarsoft.baseInfo.satsubalone.bean;
/**
 * @ClassName: SatSubAlone
 * @Description: 飞行器、分系统、单机综合
 * @author jingkewen
 * @data 2014-9-4 下午3:29:04
 *
 */
public class SatSubAlone {
	private String sys_resource_id;
	private String name;
	private String code;
	private String status;
	private String create_user;
	private String create_time;
	private String owner_id;
	private int type;
	private String leaf;
	private int mid;
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getSys_resource_id() {
		return sys_resource_id;
	}
	public void setSys_resource_id(String sys_resource_id) {
		this.sys_resource_id = sys_resource_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}

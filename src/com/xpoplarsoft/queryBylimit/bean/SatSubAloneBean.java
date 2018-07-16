package com.xpoplarsoft.queryBylimit.bean;
/**
 * @ClassName: SatSubAlone
 * @Description: 飞行器、分系统、单机bean
 * @author mxc
 *
 */
public class SatSubAloneBean extends SatBean{
	/**
	 * 卫星或分系统或单机ID
	 */
	private String sys_resource_id;
	/**
	 * 卫星或分系统或单机名称
	 */
	private String name;
	/**
	 * 卫星或分系统或单机英文编号
	 */
	private String code;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建人
	 */
	private String create_user;
	/**
	 * 创建时间
	 */
	private String create_time;
	/**
	 * 父id;
	 *  例如，如果对象是卫星，owner_id数值是-1
	 *  如果对象是分系统，owner_id数值是卫星id
	 *  如果对象是单机，owner_id数值是分系统id
	 */
	private String owner_id;
	
	/**
	 * 类型
	 *   0：卫星 
	 *   5：分系统
	 *   6：单机
	 */
	private int type;

	/**
	 * 是否是末级
	 *   true：是
	 *   false:否
	 */
	private String leaf;
	
	/**
	 * 卫星任务代号
	 */
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

package com.xpoplarsoft.queryBylimit.bean;

/**
 * 卫星对象
 * 
 * @author mxc
 * @date 2017-05-22
 *
 */
public class SatBean{
	
	/**
	 * 卫星ID
	 */
	private int sat_id;
	/**
	 * 卫星名称
	 */
	private String sat_name;
	/**
	 * 卫星英文编号
	 */
	private String sat_code;
	/**
	 * 卫星任务代号
	 */
	private int mid;
	
	public int getSat_id() {
		return sat_id;
	}
	public void setSat_id(int sat_id) {
		this.sat_id = sat_id;
	}
	public String getSat_name() {
		return sat_name;
	}
	public void setSat_name(String sat_name) {
		this.sat_name = sat_name;
	}
	public String getSat_code() {
		return sat_code;
	}
	public void setSat_code(String sat_code) {
		this.sat_code = sat_code;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
}

package com.xpoplarsoft.baseInfo.deviceInfo.bean;

/**
 * 设备信息bean
 * @author mxc
 *
 */
public class DeviceInfoBean {

	public int pk_id;
	public String code;
	public String name;
	public int device_sid;
	public int parent_id;
	public String remark;
	public String address;
	public int port;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getPk_id() {
		return pk_id;
	}
	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDevice_sid() {
		return device_sid;
	}
	public void setDevice_sid(int device_sid) {
		this.device_sid = device_sid;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}

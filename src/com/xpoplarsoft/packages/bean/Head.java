package com.xpoplarsoft.packages.bean;

/**
 * 采样数据业务头
 * @author zhouxignlu
 * 2016年1月8日
 */
public class Head {
	/**
	 * 数据版本号
	 */
	private int version = 1;

	/**
	 * 时间 (9字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	private String dateTime;

	/**
	 * 信源 (2字节)
	 */
	private int source;

	/**
	 * 信宿(2字节)
	 */
	private int target;

	/**
	 * 地面设备序号 (2字节)
	 */
	private int deviceId;
	
	/**
	 * 设备序号 (2字节)
	 */
	private int satId;
	
	/**
	 * 采样数据类型(1字节)
	 */
	private int type;

	/**
	 * 帧可信度
	 */
	private int reliability;

	public String toString() {
		StringBuilder strSB = new StringBuilder();
		strSB.append("dateTime=[").append(this.dateTime).append("]");
		strSB.append("source=[").append(this.source).append("]");
		strSB.append("target=[").append(this.target).append("]");
		strSB.append("deviceId=[").append(this.deviceId).append("]");
		strSB.append("satId=[").append(this.satId).append("]");
		strSB.append("type=[").append(this.type).append("]");

		return strSB.toString();
	}

	/**
	 * @return 数据版本号
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version 数据版本号
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return 时间 (9字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @return 帧可信度
	 */
	public int getReliability() {
		return reliability;
	}

	/**
	 * @param reliability 帧可信度
	 */
	public void setReliability(int reliability) {
		this.reliability = reliability;
	}


	/**
	 * @param dateTime 时间 (9字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}


	/**
	 * @return 信源,地面站标识
	 */
	public int getSource() {
		return source;
	}

	/**
	 * @param source 信源,地面站标识
	 */
	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * @return 设备序号 
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId 设备序号 
	 */
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return 采样数据类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type 采样数据类型
	 */
	public void setType(int type) {
		this.type = type;
	}

	public int getSatId() {
		return satId;
	}

	public void setSatId(int satId) {
		this.satId = satId;
	}

}

package com.xpoplarsoft.packages.bean;

/**
 * 非采样数据包头
 * @author mxc
 *
 */
public class NoParamHead {
	 /**
	  *  数据长度
	 */
	private int length;
	/**
	 * 数据版本号
	 */
	private int version = 1;
	/**
	 * 信源 (2字节)
	 */
	private int source;

	/**
	 * 信宿(2字节)
	 */
	private int target;
	/**
	 * 卫星唯一标识mid
	 */
	private int mid;
	/**
	 * 信息类型
	 */
	private String infoType;
	/**
	 * 发送时间 (8字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	private String dateTime;
	/**
	 * 预留
	 */
	private int remain;
	
	/**
	 * @return 卫星唯一标识mid
	 */
	public int getMid() {
		return mid;
	}
	/**
	 * @param mid 卫星唯一标识mid
	 */
	public void setMid(int mid) {
		this.mid = mid;
	}
	/**
	 * @return 信息类型
	 */
	public String getInfoType() {
		return infoType;
	}
	/**
	 * @param infoType 信息类型
	 */
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	/**
	 * @return 预留
	 */
	public int getRemain() {
		return remain;
	}
	/**
	 * @param remain 预留
	 */
	public void setRemain(int remain) {
		this.remain = remain;
	}
	/**
	 * @return 发送时间 (8字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime 发送时间 (8字节) 转换后的格式 yyyy-MM-dd HH:mm:ss.SSS
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @return 数据长度,不包含4字节的长度头
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length 数据长度,不包含4字节的长度头
	 */
	public void setLength(int length) {
		this.length = length;
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
	/**
	 * @return 信宿,接收端标识
	 */
	public int getTarget() {
		return target;
	}
	/**
	 * @param target 信宿,接收端标识
	 */
	public void setTarget(int target) {
		this.target = target;
	}
}

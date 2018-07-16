package com.jianshi.pack;

/**
 * 链路信息
 * @author mxc
 *
 */
public class NetLink {

	/**
	 * 设备标识
	 */
	private String id;
	
	/**
	 * 发送时间
	 */
	private String sendTime;
	
	/**
	 * 链路状态0：正常，1：异常
	 */
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

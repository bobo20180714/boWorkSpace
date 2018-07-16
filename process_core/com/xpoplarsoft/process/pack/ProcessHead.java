package com.xpoplarsoft.process.pack;

/**
 * 进程调度报文业务头
 * @author zhouxignlu
 * 2017年2月21日
 */
public class ProcessHead {
	//报文发送时间(8字节)
	private String dateTime;
	//业务类型(2字节)
	private int type;
	//是否需要反馈
	private int needReedback;
	//信源(4字节)
	private int source;
	//信宿(4字节)
	private int target;
	
	public String toString() {
		StringBuilder strSB = new StringBuilder();
		strSB.append("dateTime=[").append(this.dateTime).append("]");
		strSB.append("source=[").append(this.source).append("]");
		strSB.append("target=[").append(this.target).append("]");
		strSB.append("needReedback=[").append(this.needReedback).append("]");
		strSB.append("type=[").append(this.type).append("]");

		return strSB.toString();
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNeedReedback() {
		return needReedback;
	}

	public void setNeedReedback(int needReedback) {
		this.needReedback = needReedback;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}
}

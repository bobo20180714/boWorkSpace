package com.xpoplarsoft.process.pack;

public class ProcessReedbackBody extends ProcessBody {
	//被反馈报文代码
	private int rbMessageId;
	//被反馈报文发送时间
	private String rbTime;
	//被反馈报文类型
	private int rbType;
	//反馈内容长度
	private int length;
	//反馈内容
	private String reedback;
	
	public String toString() {
		StringBuilder strSB = new StringBuilder();
		strSB.append("messageId=[").append(this.getMessageId()).append("]");
		strSB.append("satNum=[").append(this.getSatNum()).append("]");
		strSB.append("rbMessageId=[").append(this.rbMessageId).append("]");
		strSB.append("rbTime=[").append(this.rbTime).append("]");
		strSB.append("rbType=[").append(this.rbType).append("]");
		strSB.append("length=[").append(this.length).append("]");
		strSB.append("reedback=[").append(this.reedback).append("]");
		return strSB.toString();
	}

	public int getRbMessageId() {
		return rbMessageId;
	}

	public void setRbMessageId(int rbMessageId) {
		this.rbMessageId = rbMessageId;
	}

	public String getRbTime() {
		return rbTime;
	}

	public void setRbTime(String rbTime) {
		this.rbTime = rbTime;
	}

	public int getRbType() {
		return rbType;
	}

	public void setRbType(int rbType) {
		this.rbType = rbType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getReedback() {
		return reedback;
	}

	public void setReedback(String reedback) {
		this.reedback = reedback;
	}
}

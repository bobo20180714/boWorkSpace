package com.xpoplarsoft.process.pack;

public class ProcessHeartbeatBody extends ProcessBody {
	//进程状态
	private int state;
	//附加信息长度
	private int length;
	//附加信息
	private String content;

	public String toString() {
		StringBuilder strSB = new StringBuilder();
		strSB.append("messageId=[").append(this.getMessageId()).append("]");
		strSB.append("satNum=[").append(this.getSatNum()).append("]");
		strSB.append("state=[").append(this.state).append("]");
		strSB.append("length=[").append(this.length).append("]");
		strSB.append("content=[").append(this.content).append("]");
		return strSB.toString();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}

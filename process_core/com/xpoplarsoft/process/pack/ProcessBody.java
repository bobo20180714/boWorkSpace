package com.xpoplarsoft.process.pack;

/**
 * 进程调度报文体
 * @author zhouxignlu
 * 2017年2月21日
 */
public class ProcessBody {
	//报文代码4字节
	private int messageId;
	//航天器任务号4字节
	private int satNum;
	//报文业务类型
	private int type;
	//反馈报文接收时限,默认1秒
	private int rbLimitTimes = 1000;
	//发送次数2字节
	private int sendNum =0;
	//是否收到反馈
	private boolean hasReedBack = false;
	//未收到反馈，重发次数上限
	private int limit;
	
	public ProcessBody(){
		messageId = (int) (System.currentTimeMillis() % 1000000000);
	}
	
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getSatNum() {
		return satNum;
	}

	public void setSatNum(int satNum) {
		this.satNum = satNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isHasReedBack() {
		return hasReedBack;
	}

	public void setHasReedBack(boolean hasReedBack) {
		this.hasReedBack = hasReedBack;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getSendNum() {
		return sendNum;
	}

	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}

	/**
	 * 获取反馈报文时限
	 * @return
	 */
	public int getRbLimitTimes() {
		return rbLimitTimes;
	}

	/**
	 * 设置反馈报文时限
	 * @param rbLimitTimes
	 */
	public void setRbLimitTimes(int rbLimitTimes) {
		this.rbLimitTimes = rbLimitTimes;
	}

	public String toString(){
		StringBuilder strSB = new StringBuilder();
		strSB.append("messageId=[").append(this.messageId).append("]");
		strSB.append("satNum=[").append(this.satNum).append("]");
		strSB.append("type=[").append(this.type).append("]");
		strSB.append("sendNum=[").append(this.sendNum).append("]");
		strSB.append("rbLimitTimes=[").append(this.rbLimitTimes).append("]");

		return strSB.toString();
	}
}

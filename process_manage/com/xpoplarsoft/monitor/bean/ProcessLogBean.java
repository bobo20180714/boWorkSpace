package com.xpoplarsoft.monitor.bean;

/**
 * 进程日志对象
 * @author mengxiangchao
 *
 */
public class ProcessLogBean {

	/**
	 * 日志生成时间
	 */
	private String createTime;

	/**
	 * 进程编号
	 */
	private String processCode;
	
	/**
	 * 日志内容
	 */
	private String logContent;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
}

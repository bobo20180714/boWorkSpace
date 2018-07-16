package com.xpoplarsoft.processManager.bean;

/**
 * 进程信息对象
 * @author mengxiangchao
 *
 */
public class ProcessInfoBean {

	private String pkId;
	private String processCode;
	private String processName;
	private String processType;
	private String computerIp;
	private String serviceProcessCode;
	private String serviceProcessName;
	private String startupType;
	private String startupPath;
	private String isMainProcess;//是否是主进程；0：主进程，1：备用进程
	private String mainProcessCode;
	
	private String satMid;
	private String satCode;
	private String satId;
	
	private String is_need_startup_param;
	private String startupParam;
	
	public String getIsMainProcess() {
		return isMainProcess;
	}
	public void setIsMainProcess(String isMainProcess) {
		this.isMainProcess = isMainProcess;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getComputerIp() {
		return computerIp;
	}
	public void setComputerIp(String computerIp) {
		this.computerIp = computerIp;
	}
	public String getServiceProcessCode() {
		return serviceProcessCode;
	}
	public void setServiceProcessCode(String serviceProcessCode) {
		this.serviceProcessCode = serviceProcessCode;
	}
	public String getStartupType() {
		return startupType;
	}
	public void setStartupType(String startupType) {
		this.startupType = startupType;
	}
	public String getStartupPath() {
		return startupPath;
	}
	public void setStartupPath(String startupPath) {
		this.startupPath = startupPath;
	}
	public String getIs_need_startup_param() {
		return is_need_startup_param;
	}
	public void setIs_need_startup_param(String is_need_startup_param) {
		this.is_need_startup_param = is_need_startup_param;
	}
	public String getStartupParam() {
		return startupParam;
	}
	public void setStartupParam(String startupParam) {
		this.startupParam = startupParam;
	}
	public String getMainProcessCode() {
		return mainProcessCode;
	}
	public void setMainProcessCode(String mainProcessCode) {
		this.mainProcessCode = mainProcessCode;
	}
	public String getSatMid() {
		return satMid;
	}
	public void setSatMid(String satMid) {
		this.satMid = satMid;
	}
	public String getSatCode() {
		return satCode;
	}
	public void setSatCode(String satCode) {
		this.satCode = satCode;
	}
	public String getSatId() {
		return satId;
	}
	public void setSatId(String satId) {
		this.satId = satId;
	}
	public String getServiceProcessName() {
		return serviceProcessName;
	}
	public void setServiceProcessName(String serviceProcessName) {
		this.serviceProcessName = serviceProcessName;
	}
}

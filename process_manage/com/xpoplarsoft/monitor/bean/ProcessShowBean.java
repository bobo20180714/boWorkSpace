package com.xpoplarsoft.monitor.bean;

/**
 * 进程监视前后台交互bean
 * @author mengxiangchao
 *
 */
public class ProcessShowBean {

	private String processId;

	private String processName;
	
	private String processCode;

	private String satMid;
	
	private String satName;
	
	private String processType;
	
	private String processTypeName;
	
	private String processState;
	
	private String exceptionInfo;
	
	/**
	 * 服务器IP
	 */
	private String computerIp;
	/**
	 * 服务代理进程标识
	 */
	private String agencyProcessCode;
	/**
	 * 服务代理进程名称
	 */
	private String agencyProcessName;
	/**
	 *是否是备用进程
	 */
	private int isBei;
	/**
	 * 主进程标识
	 */
	private String mainProcessCode;

	/**
	 * 监视列表数据是否需要删除，0：删除，1：不删除
	 */
	private String toDelete;

	/**
	 * 是否是新增的，0：是，1：否
	 */
	private String isAdd;
	
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	public String getToDelete() {
		return toDelete;
	}
	public void setToDelete(String toDelete) {
		this.toDelete = toDelete;
	}
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public String getSatName() {
		return satName;
	}
	public void setSatName(String satName) {
		this.satName = satName;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getSatMid() {
		return satMid;
	}
	public void setSatMid(String satMid) {
		this.satMid = satMid;
	}
	public String getProcessTypeName() {
		return processTypeName;
	}
	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}
	public String getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
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
	public String getAgencyProcessCode() {
		return agencyProcessCode;
	}
	public void setAgencyProcessCode(String agencyProcessCode) {
		this.agencyProcessCode = agencyProcessCode;
	}
	public String getMainProcessCode() {
		return mainProcessCode;
	}
	public void setMainProcessCode(String mainProcessCode) {
		this.mainProcessCode = mainProcessCode;
	}
	public int getIsBei() {
		return isBei;
	}
	public void setIsBei(int isBei) {
		this.isBei = isBei;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getAgencyProcessName() {
		return agencyProcessName;
	}
	public void setAgencyProcessName(String agencyProcessName) {
		this.agencyProcessName = agencyProcessName;
	}
}

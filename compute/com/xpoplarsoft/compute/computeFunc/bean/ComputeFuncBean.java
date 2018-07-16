package com.xpoplarsoft.compute.computeFunc.bean;

import java.util.List;
import java.util.Map;

/**
 * 计算功能对象
 * @author mengxiangchao
 *
 */
public class ComputeFuncBean {

	private String pkId;
	private String computeName;
	private String computeDesc;
	private String fctId; //计算模块主键
	private String fctCode;
	private String fctName;
	private String status;//0：正常，9：删除
	
	private String version;
	private int isUserDefined;
	private String userPagePath;
	private int overTime;
	private int computeCount;
	private int isSaveResult = 0;
	private int isMulticast = 0;
	private String inputParam;
	
	private String fctClassName;
	private String fctPckName;
	private String fctAllPathNamej;
	private int fctType; //功能类型
	
	private List<Map<String,String>> fieldList = null;
	
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getComputeName() {
		return computeName;
	}
	public void setComputeName(String computeName) {
		this.computeName = computeName;
	}
	public String getComputeDesc() {
		return computeDesc;
	}
	public void setComputeDesc(String computeDesc) {
		this.computeDesc = computeDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFctId() {
		return fctId;
	}
	public void setFctId(String fctId) {
		this.fctId = fctId;
	}
	public String getFctCode() {
		return fctCode;
	}
	public void setFctCode(String fctCode) {
		this.fctCode = fctCode;
	}
	public String getFctName() {
		return fctName;
	}
	public void setFctName(String fctName) {
		this.fctName = fctName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserPagePath() {
		return userPagePath;
	}
	public void setUserPagePath(String userPagePath) {
		this.userPagePath = userPagePath;
	}
	 
	public String getInputParam() {
		return inputParam;
	}
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}
	public String getFctClassName() {
		return fctClassName;
	}
	public void setFctClassName(String fctClassName) {
		this.fctClassName = fctClassName;
	}
	public String getFctPckName() {
		return fctPckName;
	}
	public void setFctPckName(String fctPckName) {
		this.fctPckName = fctPckName;
	}
	public String getFctAllPathNamej() {
		return fctAllPathNamej;
	}
	public void setFctAllPathNamej(String fctAllPathNamej) {
		this.fctAllPathNamej = fctAllPathNamej;
	}
	public int getFctType() {
		return fctType;
	}
	public void setFctType(int fctType) {
		this.fctType = fctType;
	}
	public List<Map<String, String>> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<Map<String, String>> fieldList) {
		this.fieldList = fieldList;
	}
	public int getIsUserDefined() {
		return isUserDefined;
	}
	public void setIsUserDefined(int isUserDefined) {
		this.isUserDefined = isUserDefined;
	}
	public int getOverTime() {
		return overTime;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public int getComputeCount() {
		return computeCount;
	}
	public void setComputeCount(int computeCount) {
		this.computeCount = computeCount;
	}
	public int getIsSaveResult() {
		return isSaveResult;
	}
	public void setIsSaveResult(int isSaveResult) {
		this.isSaveResult = isSaveResult;
	}
	public int getIsMulticast() {
		return isMulticast;
	}
	public void setIsMulticast(int isMulticast) {
		this.isMulticast = isMulticast;
	}
}

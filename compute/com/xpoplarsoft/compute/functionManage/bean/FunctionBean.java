package com.xpoplarsoft.compute.functionManage.bean;

import java.util.List;
import java.util.Map;

/**
 * 函数对象
 * @author mengxiangchao
 *
 */
public class FunctionBean {

	private int fctId;
	private String fctCode;
	private String fctName;
	private String fctContent;
	private String returnType;
	private String tableName;//存储表名称
	private String paramNum;
	private String fctClassName;
	private String fctAllPathNamej;
	private String engineUserName;
	private String engineTime;
	private String fctPckName;
	private int fctType; //功能类型
	private String isSaveResult;//结果是否存库
	
	/**
	 * 参数列表
	 */
	private List<Map<String,String>> paramList;
	
	private List<Map<String,String>> fieldList = null;
	
	public int getFctId() {
		return fctId;
	}
	public void setFctId(int fctId) {
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
	public String getFctContent() {
		return fctContent;
	}
	public void setFctContent(String fctContent) {
		this.fctContent = fctContent;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getParamNum() {
		return paramNum;
	}
	public void setParamNum(String paramNum) {
		this.paramNum = paramNum;
	}
	public String getFctClassName() {
		return fctClassName;
	}
	public void setFctClassName(String fctClassName) {
		this.fctClassName = fctClassName;
	}
	public String getFctAllPathNamej() {
		return fctAllPathNamej;
	}
	public void setFctAllPathNamej(String fctAllPathNamej) {
		this.fctAllPathNamej = fctAllPathNamej;
	}
	public String getEngineUserName() {
		return engineUserName;
	}
	public void setEngineUserName(String engineUserName) {
		this.engineUserName = engineUserName;
	}
	public String getEngineTime() {
		return engineTime;
	}
	public void setEngineTime(String engineTime) {
		this.engineTime = engineTime;
	}
	public String getFctPckName() {
		return fctPckName;
	}
	public void setFctPckName(String fctPckName) {
		this.fctPckName = fctPckName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Map<String,String>> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<Map<String,String>> fieldList) {
		this.fieldList = fieldList;
	}
	public int getFctType() {
		return fctType;
	}
	public void setFctType(int fctType) {
		this.fctType = fctType;
	}
	public String getIsSaveResult() {
		return isSaveResult;
	}
	public void setIsSaveResult(String isSaveResult) {
		this.isSaveResult = isSaveResult;
	}
	public List<Map<String,String>> getParamList() {
		return paramList;
	}
	public void setParamList(List<Map<String,String>> paramList) {
		this.paramList = paramList;
	}
	
}

package com.xpoplarsoft.compute.functionManage.bean;

/**
 * 方法参数对象
 * @author mengxiangchao
 *
 */
public class FunctionParam {

	private int fieldId;
	private String paramName;
	private String paramType;
	private String paramContent;
	private int fid;
	private int paramOrder;
	
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getParamContent() {
		return paramContent;
	}
	public void setParamContent(String paramContent) {
		this.paramContent = paramContent;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getParamOrder() {
		return paramOrder;
	}
	public void setParamOrder(int paramOrder) {
		this.paramOrder = paramOrder;
	}
	
}

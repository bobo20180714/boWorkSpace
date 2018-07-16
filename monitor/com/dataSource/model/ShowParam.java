package com.dataSource.model;

import com.xpoplarsoft.packages.bean.Param;

/**
 * 实时监视参数数据对象
 * @author mengxiangchao
 *
 */
public class ShowParam extends Param {

	private String paramCode;

	private String paramValue;
	
	private String devId;

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public static ShowParam getParamObj(Param p) {
		ShowParam sp = new ShowParam();
		sp.setAlarmLevel(p.getAlarmLevel());
		sp.setContent(p.getContent());
		sp.setDataType(p.getDataType());
		sp.setParamId(p.getParamId());
		sp.setReliability(p.getReliability());
		return sp;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}
	
}

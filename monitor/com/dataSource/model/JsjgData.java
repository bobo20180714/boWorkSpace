package com.dataSource.model;

import java.util.HashMap;
import java.util.Map;

public class JsjgData {


	private String satId;
	
	private String jsjgCode;
	
	//参数值列表 <参数名,参数值>
	private Map<String,String> params = new HashMap<String,String>();

	public String getSatId() {
		return satId;
	}

	public void setSatId(String satId) {
		this.satId = satId;
	}

	public String getJsjgCode() {
		return jsjgCode;
	}

	public void setJsjgCode(String jsjgCode) {
		this.jsjgCode = jsjgCode;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addVal(String key,String val){
		this.params.put(key, val);
	}
	
}

package com.xpoplarsoft.alarm.modal;

import java.util.List;
import java.util.Map;

/**
 * 获取当前值,请求参数bean
 * @author 孟祥超
 * 
 */
public class ReqParamBean {

	private List<Map<String,String>> paramlist;

	public List<Map<String, String>> getParamlist() {
		return paramlist;
	}

	public void setParamlist(List<Map<String, String>> paramlist) {
		this.paramlist = paramlist;
	}
}

package com.xpoplarsoft.packages.bean;

import java.io.Serializable;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.utils.JsonTools;

/**
 * 非采样数据
 * @author mxc
 *
 */
public class NoParamData  implements Serializable, IData, Comparable<Object> {

	private static final long serialVersionUID = 1L;

	private NoParamHead head;

	private NoParamBody body;
	
	public NoParamHead getHead() {

		if (null == this.head) {
			head = new NoParamHead();
		}

		return head;
	}

	public void setHead(NoParamHead head) {
		this.head = head;
	}

	public NoParamBody getBody() {

		if (null == this.body) {
			body = new NoParamBody();
		}

		return body;
	}

	public void setBody(NoParamBody body) {
		this.body = body;
	}

	/**
	 * 获取非采样数据
	 * 
	 * @return xmlData
	 */
	public String getParam() {

		String xmlData = null;
		if (body != null) {
			xmlData = body.getXmlData();
		}
		return xmlData;
	}

	public void clear() {
		if (null != body) {
			body.setXmlData("");
		}
	}

	public int compareTo(Object o) {
		return 0;
	}

	public String toJson() {
		Gson json = JsonTools.getGson();
		String retJson = json.toJson(this);
		return retJson;
	}
}

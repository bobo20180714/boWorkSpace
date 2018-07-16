package com.xpoplarsoft.packages.bean;

import java.io.Serializable;

import com.bydz.fltp.connector.data.IData;

public class ParamData implements Serializable, IData, Comparable<Object>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Head head;

	private ParamBody body;

	public Head getHead() {

		if (null == this.head) {
			head = new Head();
		}

		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public ParamBody getBody() {

		if (null == this.body) {
			body = new ParamBody();
		}

		return body;
	}

	public void setBody(ParamBody body) {
		this.body = body;
	}

	/**
	 * 获取遥测参数
	 * 
	 * @param num
	 *            参数序号
	 * @return
	 */
	public Param getParam(int num) {

		Param param = null;
		if (body != null) {
			param = body.getParam(num);
		}
		return param;
	}

	public void clear() {
		if (null != body) {
			body.clear();
		}
	}

	public int compareTo(Object o) {

		if (o instanceof ParamData) {
			ParamData param = (ParamData) o;
			return this.getHead().getDateTime()
					.compareTo(param.getHead().getDateTime());
		}
		return -1;
	}

}

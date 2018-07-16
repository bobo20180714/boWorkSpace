package com.xpoplarsoft.process.pack;

import java.io.Serializable;

import com.bydz.fltp.connector.data.IData;

/**
 * 进程调度报文对象
 * 
 * @author zhouxignlu 2017年2月27日
 */
public class ProcessData implements Serializable, IData, Comparable<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(Object o) {
		if (o instanceof ProcessData) {
			ProcessData process = (ProcessData) o;
			return this.getHead().getDateTime()
					.compareTo(process.getHead().getDateTime());
		}
		return -1;
	}

	@Override
	public void clear() {

	}

	private ProcessHead head;

	private ProcessBody body;

	public ProcessHead getHead() {
		return head;
	}

	public void setHead(ProcessHead head) {
		this.head = head;
	}

	public ProcessBody getBody() {
		return body;
	}

	public void setBody(ProcessBody body) {
		this.body = body;
	}

	public String toString() {
		return "ProcessHead{" + head.toString() + "} ProcessBody{"
				+ body.toString() + "}";
	}
}

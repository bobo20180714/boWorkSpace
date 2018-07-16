package com.xpoplarsoft.process.bean;

/**
 * 进程指令对象
 * @author zhouxignlu
 * 2017年2月14日
 */
public class ProcessOrder {

	private String order_id;
	
	private String order_code;
	
	private String order_name;
	
	private String order_content;
	
	//指令发送时间
	private long send_time;
	
	//规定执行时间：-1为不予执行，0为立即执行，>0为具体执行的时间毫秒值
	private long execute_time;

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}

	public long getExecute_time() {
		return execute_time;
	}

	public void setExecute_time(long execute_time) {
		this.execute_time = execute_time;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}

	public String getOrder_content() {
		return order_content;
	}

	public void setOrder_content(String order_content) {
		this.order_content = order_content;
	}
}

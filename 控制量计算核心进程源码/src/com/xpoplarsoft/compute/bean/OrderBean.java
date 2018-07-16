package com.xpoplarsoft.compute.bean;

/**
 * 特需计算订单类
 * @@pk_id		数据库主键
 * @@order_id	订单编号，业务主键
 * @@order_name	订单名称
 * @@time	订单创建时间2000-01-01 23:59:59
 * @@order_state	订单状态：0 废弃，1 新建，2 待处理，3 处理中，4 处理完成，5 处理失败，9 删除
 * @@order_content	订单描述
 * @@order_err	订单处理失败信息
 * @@is_get_data	是否选择数据获取执行类：0 否，1是
 * @@get_data_id	数据获取执行类编号
 * @@get_data_param	数据获取执行方法输入参数
 * @@comput_id	计算执行类编号
 * @@comput_param	计算执行方法输入参数
 * @@is_result	是否选择结果处理执行类：0 否，1是
 * @@result_id	结果处理执行类编号
 * @@result_param	结果处理执行方法输入参数
 * @@order_class	订单类型：1 单次运算，2 按次数循环，3 按截止时间循环，4 双条件循环
 * @@loop_space		循环间隔时间，毫秒数
 * @@loop_maxnum	循环最大次数
 * @@loop_endtime	循环截止时间2000-01-01 23:59:59
 * @author zhouxignlu
 * 2017年3月16日
 */
public class OrderBean {
	
	private String pk_id;
	private String order_id;
	private String order_name;
	private String time;
	private String order_state;
	private String order_content;
	private String order_err;
	private String is_get_data;
	private String get_data_id;
	private String get_data_class_name;
	private String get_data_param;
	private String comput_id;
	private String comput_class_name;
	private String comput_param;
	private String is_result;
	private String result_id;
	private String result_class_name;
	private String result_param;
	private String order_class;
	private String loop_space;
	private String loop_maxnum;
	private String loop_endtime;
	
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getOrder_content() {
		return order_content;
	}
	public void setOrder_content(String order_content) {
		this.order_content = order_content;
	}
	public String getOrder_err() {
		return order_err;
	}
	public void setOrder_err(String order_err) {
		this.order_err = order_err;
	}
	public String getIs_get_data() {
		return is_get_data;
	}
	public void setIs_get_data(String is_get_data) {
		this.is_get_data = is_get_data;
	}
	public String getGet_data_id() {
		return get_data_id;
	}
	public void setGet_data_id(String get_data_id) {
		this.get_data_id = get_data_id;
	}
	public String getGet_data_param() {
		return get_data_param;
	}
	public void setGet_data_param(String get_data_param) {
		this.get_data_param = get_data_param;
	}
	public String getComput_id() {
		return comput_id;
	}
	public void setComput_id(String comput_id) {
		this.comput_id = comput_id;
	}
	public String getComput_param() {
		return comput_param;
	}
	public void setComput_param(String comput_param) {
		this.comput_param = comput_param;
	}
	public String getIs_result() {
		return is_result;
	}
	public void setIs_result(String is_result) {
		this.is_result = is_result;
	}
	public String getResult_id() {
		return result_id;
	}
	public void setResult_id(String result_id) {
		this.result_id = result_id;
	}
	public String getResult_param() {
		return result_param;
	}
	public void setResult_param(String result_param) {
		this.result_param = result_param;
	}
	public String getGet_data_class_name() {
		return get_data_class_name;
	}
	public void setGet_data_class_name(String get_data_class_name) {
		this.get_data_class_name = get_data_class_name;
	}
	public String getComput_class_name() {
		return comput_class_name;
	}
	public void setComput_class_name(String comput_class_name) {
		this.comput_class_name = comput_class_name;
	}
	public String getResult_class_name() {
		return result_class_name;
	}
	public void setResult_class_name(String result_class_name) {
		this.result_class_name = result_class_name;
	}
	public String getOrder_class() {
		return order_class;
	}
	public void setOrder_class(String order_class) {
		this.order_class = order_class;
	}
	public String getLoop_space() {
		return loop_space;
	}
	public void setLoop_space(String loop_space) {
		this.loop_space = loop_space;
	}
	public String getLoop_maxnum() {
		return loop_maxnum;
	}
	public void setLoop_maxnum(String loop_maxnum) {
		this.loop_maxnum = loop_maxnum;
	}
	public String getLoop_endtime() {
		return loop_endtime;
	}
	public void setLoop_endtime(String loop_endtime) {
		this.loop_endtime = loop_endtime;
	}
	
	
}

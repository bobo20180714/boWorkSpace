/**
 * com.xpoplarsoft.compute.log
 */
package com.xpoplarsoft.compute.log;

/**
 * 计算组件日志管理接口
 * @author zhouxignlu
 * 2017年4月28日
 */
public interface IComputeLog {

	/**
	 * 订单计算组件日志记录接口
	 * @param order 订单编号
	 * @param logstr 日志内容
	 */
	public void log(String order, String logstr);
	
	/**
	 * 订单计算组件异常记录接口
	 * @param order
	 * @param errstr
	 */
	public void err(String order, String errstr);
}

/**
 * com.xpoplarsoft.service.client
 */
package com.xpoplarsoft.service.client.log;


/**
 * 进程调度日志管理器接口
 * @author zhouxignlu
 * 2017年5月2日
 */
public interface IProcessLog {
	/**
	 * 进程调度日志记录接口
	 * @param logstr 日志内容
	 */
	public void log(String logstr);
	
	/**
	 * 进程调度异常记录接口
	 * @param errstr 异常信息内容
	 */
	public void err(String errstr);
}

package com.xpoplarsoft.service;

import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 业务执行器接口定义
 * @author zhouxignlu
 * 2017年3月7日
 */
public interface IServiceExecuter extends Runnable{

	/**
	 * 业务执行
	 */
	public void execute();
	
	/**
	 * 添加业务报文体
	 * @param bodyData
	 */
	public void setProcessServiceBody(ProcessData msgData);
	
	/**
	 * 设置该业务类是否创建线程
	 * @param isThread
	 */
	public void setIsThread(boolean isThread);
	
	public boolean isThread();
}

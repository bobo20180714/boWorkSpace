package com.xpoplarsoft.process.core;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 进程调度核心接口
 * @author zhouxignlu
 * 2017年2月14日
 */
public interface IProcessCore {
	/**
	 * 系统字符集，默认UTF-8
	 */
	public final String CHARSET_NAME = "UTF-8";
	/**
	 * 心跳附加内容处理业务类型定义
	 */
	public final String HEART_SERVICE = "HEART";
	
	/**
	 * 心跳信息接收超时时间,默认3秒
	 */
	public final int HEART_LIMITTIME = 3000;
	
	/**
	 * 初始化进程
	 */
	public void init();
	
	/**
	 * 发送进程心跳
	 */
	public void sendHeartbeat();
	
	/**
	 * 发送进程运行日志
	 */
	public void sendOperatLog();
	
	/**
	 * 发送进程执行业务数据
	 */
	public void sendOperatData();

	/**
	 * 发送进程调度指令及业务指令报文，并将需要反馈的报文放入缓存中
	 * @param msg 报文内容
	 */
	public void sendProcessOrder(ProcessData msg);
	
	/**
	 * 接收报文
	 */
	public void receiveProcessMessage();
	
	/**
	 * 接收、处理心跳信息
	 */
	public void receiveHeartbeat(ProcessData processData);
	
	/**
	 * 执行指令
	 */
	public void executeProcessOrder();
	
	/**
	 * 获取本进程信息对象
	 * @return
	 */
	public ProcessBean getProcessBean();
}

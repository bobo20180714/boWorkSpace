package com.xpoplarsoft.monitor.service;

import java.util.List;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.monitor.bean.ProcessShowBean;
import com.xpoplarsoft.process.bean.ProcessBean;

/**
 * 进程监视业务层
 * @author mengxiangchao
 *
 */
public interface IMonitorService {

	/**
	 * 进程列表
	 * @param userAccount 用户登录账号
	 * @param satMidArr  卫星任务代号数组
	 * @param processInfoList
	 * @return
	 */
	public List<ProcessShowBean> processList(String userAccount,String[] satMidArr,List<ProcessShowBean> processInfoList);
	
	
	/**
	 * 服务器列表
	 * @param processInfoList
	 * @return
	 */
	public List<ProcessShowBean> computeList(List<ProcessShowBean> processInfoList);
	
	/**
	 * 停止、暂停进程
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 * @param agencyProcessCode 代理进程code
	 * @param orderNum 指令代码
	 */
	public void dispatchOrder(String satMid, int processId, int agencyProcessCode,int orderNum,String content);

	/**
	 * 发送日志 指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	public void sendLog(String satMid, int processId);

	/**
	 * 发送运行参数 指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	public void sendParam(String satMid, int processId);

	/**
	 * 发送运行结果 指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	public void sendResult(String satMid, int processId);

	/**
	 * 根据卫星任务代号和进程类型，从内存中获取进程对象
	 * @param satMid
	 * @param processType
	 * @return
	 */
	public ProcessBean queryProcessBySatAndType(String satMid,
			String processType);

	/**
	 * 查询前台需要处理的进程
	 * @param processInfoList
	 * @return
	 */
	public List<ProcessShowBean> queryChangeProcess(
			List<ProcessShowBean> processInfoList);

	/**
	 * 判断服务器是否已启动
	 * @param servcieCode  服务器标识
	 */
	public boolean judgeServiceStarted(String servcieCode);


	public ResultBean judgeCanStarted(String beiProcessCode,
			String mainProcessCode);

}

package com.xpoplarsoft.processManager.service;

import java.util.Map;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;

public interface IProcessManagerService {

	/**
	 * 界面进程列表查询
	 * @param bean
	 * @param processName
	 * @param processCode
	 * @return
	 */
	Map<String, Object> list(CommonBean bean, String processName,
			String processCode);

	/**
	 * 按进程类型进行分页查询
	 * @param bean
	 * @param processType
	 * @return
	 */
	Map<String, Object> queryProcessByType(CommonBean bean, String processType);

	/**
	 * 进程注册
	 * @param ProcessInfoBean
	 * @return
	 */
	boolean add(ProcessInfoBean bean);

	/**
	 * 移除进程
	 * @param processId
	 * @return
	 */
	boolean deleteProcess(String processId);

	/**
	 * 查看订单
	 * @param processCode
	 * @return
	 */
	ProcessInfoBean viewProcess(String processCode);

	/**
	 * 进程修改
	 * @param ProcessInfoBean
	 * @return
	 */
	boolean updateProcess(ProcessInfoBean bean);

	/**
	 * 进程启用
	 * @param processCode
	 * @return
	 */
	boolean startProcess(String processCode);

	/**
	 * 判断是否已经存在主进程
	 * @param processType
	 * @param satMid
	 * @return
	 */
	boolean judgeIsHaveMain(String processType,String satMid);

	/**
	 * 获取主进程信息
	 * @param processType
	 * @param satMid
	 * @return
	 */
	Map<String, Object> getMainProcess(String processType,String satMid);

	/**
	 * 判断服务器IP是否已经存在
	 * @param computerIp
	 * 
	 */
	boolean judgeIpExit(String computerIp);

}

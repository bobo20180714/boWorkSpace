package com.xpoplarsoft.monitor.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseinfoquery.bean.SatInfoDetail;
import com.xpoplarsoft.constant.BusinessTypeEnum;
import com.xpoplarsoft.constant.ProcessConstant;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.monitor.bean.ProcessShowBean;
import com.xpoplarsoft.monitor.constant.OrderConstant;
import com.xpoplarsoft.monitor.process.MonitorProcess;
import com.xpoplarsoft.monitor.service.IMonitorService;
import com.xpoplarsoft.monitor.service.ISatService;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessOrderBody;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;
import com.xpoplarsoft.processManager.service.IProcessManagerService;
import com.xpoplarsoft.queryBylimit.service.IQueryResourceService;
import com.xpoplarsoft.queryBylimit.service.QueryResourceService;

/**
 * 监视服务实现类
 * @author mengxiangchao
 *
 */
@Service
public class MonitorService implements IMonitorService {

	private static Log log = LogFactory.getLog(MonitorService.class);
	
	private IQueryResourceService service = new QueryResourceService();
	
	//日志时间格式
	public final static String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	@Autowired
	private ISatService satService;
	
	@Autowired
	private IProcessManagerService managerService;
	
	@Override
	public List<ProcessShowBean> processList(String userAccount,String[] satMidArr,
			List<ProcessShowBean> processInfoList) {
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorService][processList]开始执行！");
		}
		
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		
		//表格数据
		List<ProcessShowBean> rsList = new ArrayList<ProcessShowBean>();
		//获取当前进程
		ProcessBean localProcess = ProcessObjectesControl.getLocalProcess();
		
		if(log.isDebugEnabled()){
			log.debug("获取所有进程start！");
		}
		//获取产生变化的进程列表
		List<ProcessShowBean> rsBeanList = queryChangeProcess(processInfoList);
		//获取所有进程
		for (ProcessShowBean processBean : rsBeanList) {
			if(localProcess.getId().equals(processBean.getProcessId())
					|| "10".equals(processBean.getProcessType())){
				//排除当前进程
				continue;
			}
			if(processBean.getSatMid() != null && !"".equals(processBean.getSatMid())
					 && !"-1".equals(processBean.getSatMid())){
				//判断任务代号是否存在于传入的任务代号数组中
				if(satMidArr != null && !judgeExit(processBean.getSatMid(),satMidArr)){
					continue;
				}
				//判断该用户是否有某个卫星的权限
				if(!managerAccount.equals(userAccount)
						&& !service.judgeHaveSatLimit(userAccount,processBean.getSatMid())){
					continue;
				}
				//查询卫星信息
				SatInfoDetail satInfo = satService.getSatInfoByMid(processBean.getSatMid());
				processBean.setSatName(satInfo.getSat_name()+"("+satInfo.getSat_code()+")");
			}
			rsList.add(processBean);
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorService][processList]执行结束！");
		}
		
		return rsList;
	}

	@Override
	public List<ProcessShowBean> queryChangeProcess(List<ProcessShowBean> processInfoList) {
		List<ProcessShowBean> rsBeanList = new ArrayList<ProcessShowBean>();
		ProcessBean processBean = null;
		//判断前台传过来的进程信息有误变化
		for (ProcessShowBean showbean : processInfoList) {
			//根据进程id获取进程对象
			processBean = ProcessObjectesControl.getProcess(showbean.getProcessId());
			if(processBean == null){
				//该进程需要移除
				showbean.setToDelete("0");
			}else{
				
				if(showbean.getProcessState().equals(processBean.getState())
						&& showbean.getProcessName().equals(processBean.getName())
						&& showbean.getAgencyProcessCode().equals(processBean.getAgencyProcessCode())
						&& (showbean.getSatMid() == null || showbean.getSatMid().equals(processBean.getSat_num()))
						){
					//状态、进程名称、服务器、卫星没有变化，不处理也不返回
					continue;
				}
				//需要展示的信息
				showbean.setProcessName(processBean.getName());
				showbean.setProcessCode(processBean.getCode());
				showbean.setProcessState(processBean.getState());
				showbean.setExceptionInfo(processBean.getErrMessage()==null?"":processBean.getErrMessage());
				showbean.setSatMid(processBean.getSat_num());
				showbean.setProcessTypeName(processBean.getClass_name());
				showbean.setProcessType(processBean.getType());
				showbean.setMainProcessCode(processBean.getMainProcessCode());
				showbean.setIsBei(processBean.getIsMain());
				
				showbean.setAgencyProcessCode(processBean.getAgencyProcessCode());
				ProcessInfoBean bean = managerService.viewProcess(processBean.getAgencyProcessCode());
				showbean.setComputerIp(bean.getComputerIp());
				showbean.setAgencyProcessName(bean.getProcessName()==null?"":bean.getProcessName());
				showbean.setSatMid(processBean.getSat_num());
			}
			rsBeanList.add(showbean);
		}
		
		//判断是否有新注册的进程
		Collection<ProcessBean> beanConllec = ProcessObjectesControl.getProcessList();
		for (ProcessBean beanTemp : beanConllec) {
			boolean flag = true;//是否是新注册的
			for (ProcessShowBean showbean : processInfoList) {
				if(beanTemp.getId().equals(showbean.getProcessId())){
					flag = false;
					break;
				}
			}
			if(flag){
				//新注册的进程添加到集合中返回
				ProcessShowBean showbean = new ProcessShowBean();
				showbean.setProcessName(beanTemp.getName());
				showbean.setProcessId(beanTemp.getId());
				showbean.setIsAdd("0");
				//需要展示的信息
				showbean.setProcessCode(beanTemp.getCode());
				showbean.setProcessState(beanTemp.getState());
				showbean.setExceptionInfo(beanTemp.getErrMessage()==null?"":beanTemp.getErrMessage());
				showbean.setSatMid(beanTemp.getSat_num());
				showbean.setProcessTypeName(beanTemp.getClass_name());
				showbean.setProcessType(beanTemp.getType());
				showbean.setMainProcessCode(beanTemp.getMainProcessCode());
				showbean.setComputerIp(beanTemp.getComputerIp());
				showbean.setAgencyProcessCode(beanTemp.getAgencyProcessCode());
				ProcessInfoBean bean = managerService.viewProcess(beanTemp.getAgencyProcessCode());
				showbean.setAgencyProcessName(bean.getProcessName()==null?"":bean.getProcessName());
				showbean.setIsBei(beanTemp.getIsMain());
				rsBeanList.add(showbean);
			}
		}
		
		return rsBeanList;
	}
	
	
	@Override
	public List<ProcessShowBean> computeList(List<ProcessShowBean> processInfoList) {
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorService][computeList]开始执行！");
		}
		
		//表格数据
		List<ProcessShowBean> rsList = new ArrayList<ProcessShowBean>();
		//获取当前进程
		ProcessBean localProcess = ProcessObjectesControl.getLocalProcess();
		
		if(log.isDebugEnabled()){
			log.debug("获取所有进程start！");
		}
		//获取产生变化的进程列表
		List<ProcessShowBean> rsBeanList = queryChangeProcess(processInfoList);
		//获取所有进程
		for (ProcessShowBean processBean : rsBeanList) {
			if(localProcess.getId().equals(processBean.getProcessId())
					|| !"10".equals(processBean.getProcessType())){
				//排除当前进程
				continue;
			}
			rsList.add(processBean);
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorService][computeList]执行结束！");
		}
		
		return rsList;
	}
	
	/**
	 * 判断任务代号是否存在于传入的任务代号数组中
	 * @param satMid
	 * @param satMidArr
	 * @return
	 */
	private boolean judgeExit(String satMid, String[] satMidArr) {
		for (String midTemp : satMidArr) {
			if(midTemp.equals(satMid)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void dispatchOrder(String satMid, int processId,int agencyProcessCode, int orderNum,String content) {
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][dispatchOrder]开始执行！");
		}
		//进程调度对象
		ProcessData processData = new ProcessData();
		
		//业务类型
		int businessType = BusinessTypeEnum.FUNCTION_ORDER.getType();
		if(log.isInfoEnabled()){
			log.info("初始化业务包头！");
		}
		ProcessHead head = new ProcessHead();
		head.setDateTime(String.valueOf(System.currentTimeMillis()));
		//业务类型
		head.setType(businessType);
		//是否需要反馈
		head.setNeedReedback(ProcessConstant.REBACK_NEED);
		//信源，发出方进程标识, 在向调度系统注册时分配
		String nowProcessId = MonitorProcess.getInstance().getProcessBean().getId();
		head.setSource(Integer.parseInt(nowProcessId));
		//信宿，接收方进程标识
		head.setTarget(agencyProcessCode);
		processData.setHead(head);
		
		if(log.isDebugEnabled()){
			log.debug("指令信息head=["+head.toString()+"]");
		}
		
		if(log.isInfoEnabled()){
			log.info("初始化业务包体！");
		}
		ProcessOrderBody body = new ProcessOrderBody();
		//业务类型
		body.setType(businessType);
		if(satMid != null && !"".equals(satMid)){
			//航天器任务号
			body.setSatNum(Integer.parseInt(satMid));
		}
		//指令代码
		body.setOrderCode(orderNum);
		//发送次数
		body.setSendNum(1);
		body.setContent(content);
		processData.setBody(body);
		if(log.isDebugEnabled()){
			log.debug("指令信息body=["+body.toString()+"]");
		}
		//调用发送命令接口
		MonitorProcess.getInstance().sendProcessOrder(processData);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][dispatchOrder]执行结束！");
		}
	}

	@Override
	public void sendLog(String satMid, int processId) {
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendLog]开始执行！");
		}
		//进程调度对象
		ProcessData processData = new ProcessData();
		
		//业务类型
		int businessType = BusinessTypeEnum.FUNCTION_ORDER.getType();
		if(log.isInfoEnabled()){
			log.info("初始化业务包头！");
		}
		ProcessHead head = new ProcessHead();
		head.setDateTime(String.valueOf(System.currentTimeMillis()));
		//业务类型
		head.setType(businessType);
		//是否需要反馈
		head.setNeedReedback(ProcessConstant.REBACK_NEED);
		//当前进程对象
		ProcessBean proceBean = MonitorProcess.getInstance().getProcessBean();
		
		//信源，发出方进程标识, 在向调度系统注册时分配
		head.setSource(Integer.parseInt(proceBean.getId()));
		//信宿，接收方进程标识，系统预先定义
		head.setTarget(processId);
		processData.setHead(head);
		
		if(log.isDebugEnabled()){
			log.debug("指令信息head=["+head.toString()+"]");
		}
		
		if(log.isInfoEnabled()){
			log.info("初始化业务包体！");
		}
		ProcessOrderBody body = new ProcessOrderBody();
		//业务类型
		body.setType(businessType);
		if(satMid != null && !"".equals(satMid)){
			//航天器任务号
			body.setSatNum(Integer.parseInt(satMid));
		}
		//指令代码
		body.setOrderCode(OrderConstant.SEND_LOG_ORDER);
		//发送次数
		body.setSendNum(1);
		//反馈报文接收时限
		body.setRbLimitTimes(proceBean.getReedbackInterval());
		//未收到反馈，重发次数上限
		body.setLimit(proceBean.getLimit());
		//附加内容设置为空
		body.setContent("");
		
		processData.setBody(body);
		if(log.isDebugEnabled()){
			log.debug("指令信息body=["+body.toString()+"]");
		}
		//调用发送命令接口
		MonitorProcess.getInstance().sendProcessOrder(processData);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendLog]执行结束！");
		}
	}

	@Override
	public void sendParam(String satMid, int processId) {
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendParam]开始执行！");
		}
		//进程调度对象
		ProcessData processData = new ProcessData();
		
		//业务类型
		int businessType = BusinessTypeEnum.FUNCTION_ORDER.getType();
		if(log.isInfoEnabled()){
			log.info("初始化业务包头！");
		}
		ProcessHead head = new ProcessHead();
		head.setDateTime(String.valueOf(System.currentTimeMillis()));
		//业务类型
		head.setType(businessType);
		//是否需要反馈
		head.setNeedReedback(ProcessConstant.REBACK_NEED);
		//当前进程对象
		ProcessBean proceBean = MonitorProcess.getInstance().getProcessBean();
		//信源，发出方进程标识, 在向调度系统注册时分配
		head.setSource(Integer.parseInt(proceBean.getId()));
		//信宿，接收方进程标识，系统预先定义
		head.setTarget(processId);
		processData.setHead(head);
		
		if(log.isDebugEnabled()){
			log.debug("指令信息head=["+head.toString()+"]");
		}
		
		if(log.isInfoEnabled()){
			log.info("初始化业务包体！");
		}
		ProcessOrderBody body = new ProcessOrderBody();
		//业务类型
		body.setType(businessType);
		if(satMid != null && !"".equals(satMid)){
			//航天器任务号
			body.setSatNum(Integer.parseInt(satMid));
		}
		//指令代码
		body.setOrderCode(OrderConstant.SEND_PARAM_ORDER);
		//发送次数
		body.setSendNum(1);
		//反馈报文接收时限
		body.setRbLimitTimes(proceBean.getReedbackInterval());
		//未收到反馈，重发次数上限
		body.setLimit(proceBean.getLimit());
		//附加内容设置为空
		body.setContent("");
		
		processData.setBody(body);
		if(log.isDebugEnabled()){
			log.debug("指令信息body=["+body.toString()+"]");
		}
		//调用发送命令接口
		MonitorProcess.getInstance().sendProcessOrder(processData);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendParam]执行结束！");
		}
	}
	
	@Override
	public void sendResult(String satMid, int processId) {
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendResult]开始执行！");
		}
		//进程调度对象
		ProcessData processData = new ProcessData();
		
		//业务类型
		int businessType = BusinessTypeEnum.FUNCTION_ORDER.getType();
		if(log.isInfoEnabled()){
			log.info("初始化业务包头！");
		}
		ProcessHead head = new ProcessHead();
		head.setDateTime(String.valueOf(System.currentTimeMillis()));
		//业务类型
		head.setType(businessType);
		//是否需要反馈
		head.setNeedReedback(ProcessConstant.REBACK_NEED);
		//当前进程对象
		ProcessBean proceBean = MonitorProcess.getInstance().getProcessBean();
		//信源，发出方进程标识, 在向调度系统注册时分配
		head.setSource(Integer.parseInt(proceBean.getId()));
		//信宿，接收方进程标识，系统预先定义
		head.setTarget(processId);
		processData.setHead(head);
		
		if(log.isDebugEnabled()){
			log.debug("指令信息head=["+head.toString()+"]");
		}
		
		if(log.isInfoEnabled()){
			log.info("初始化业务包体！");
		}
		ProcessOrderBody body = new ProcessOrderBody();
		//业务类型
		body.setType(businessType);
		if(satMid != null && !"".equals(satMid)){
			//航天器任务号
			body.setSatNum(Integer.parseInt(satMid));
		}
		//指令代码
		body.setOrderCode(OrderConstant.SEND_RESULT_ORDER);
		//发送次数
		body.setSendNum(1);
		//反馈报文接收时限
		body.setRbLimitTimes(proceBean.getReedbackInterval());
		//未收到反馈，重发次数上限
		body.setLimit(proceBean.getLimit());
		//附加内容设置为空
		body.setContent("");
		
		processData.setBody(body);
		if(log.isDebugEnabled()){
			log.debug("指令信息body=["+body.toString()+"]");
		}
		//调用发送命令接口
		MonitorProcess.getInstance().sendProcessOrder(processData);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorService][sendResult]执行结束！");
		}
	}

	@Override
	public ProcessBean queryProcessBySatAndType(String satMid,
			String processType) {
		Collection<ProcessBean> processList = ProcessObjectesControl.getProcessList();
		for (ProcessBean processBean : processList) {
			if(satMid.equals(processBean.getSat_num())
					&& processType.equals(processBean.getClass_name())){
				return processBean;
			}
		}
		return null;
	}

	@Override
	public boolean judgeServiceStarted(String servcieCode) {
		ProcessBean bean = ProcessObjectesControl.getProcess(servcieCode);
		if(bean != null && "1".equals(bean.getState())){
			return true;
		}
		return false;
	}

	@Override
	public ResultBean judgeCanStarted(String beiProcessCode,
			String mainProcessCode) {
		ResultBean rb = new ResultBean();
		//判断主进程是否已启动
		ProcessBean bean = ProcessObjectesControl.getProcess(mainProcessCode);
		if(!"1".equals(bean.getState())){
			rb.setSuccess("false");
			rb.setMessage("请先启动进程标识为["+mainProcessCode+"]的主进程");
			return rb;
		}
		//判断备用进程是否已启动
		ProcessBean beiBean = ProcessObjectesControl.getProcess(beiProcessCode);
		if(beiBean.getSat_num() != null && bean.getSat_num() != null 
				&& !beiBean.getSat_num().contains(bean.getSat_num())){
			rb.setSuccess("false");
			rb.setMessage("备用进程["+beiProcessCode+"]与主进程["+mainProcessCode+"]卫星编号信息不一致！");
			return rb;
		}
		
		return rb;
	}
	
}

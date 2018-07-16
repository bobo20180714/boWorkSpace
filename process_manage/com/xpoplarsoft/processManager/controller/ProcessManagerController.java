package com.xpoplarsoft.processManager.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;
import com.xpoplarsoft.processManager.service.IProcessManagerService;

/**
 * 进程管理
 * @author mengxiangchao
 *
 */
@Controller
@RequestMapping("/processManager")
public class ProcessManagerController {

	private static Log log = LogFactory.getLog(ProcessManagerController.class);
	
	private static Gson gson = new Gson();

	@Autowired
	private IProcessManagerService service;
	
	@RequestMapping("list")
	public @ResponseBody String list(CommonBean bean,String processName,String processCode){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][list]开始执行！");
		}
		
		Map<String,Object> rsMap = service.list(bean,processName,processCode);
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][list]执行结束！");
		}
		
		return gson.toJson(rsMap);
	}
	
	/**
	 * 按进程类型进行分页查询
	 * @param bean
	 * @param processType
	 * @return
	 */
	@RequestMapping("queryProcessByType")
	public @ResponseBody String queryProcessByType(CommonBean bean,String processType){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][queryProcessByType]开始执行！");
		}
		
		Map<String,Object> rsMap = service.queryProcessByType(bean,processType);
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][queryProcessByType]执行结束！");
		}
		
		return gson.toJson(rsMap);
	}
	
	/**
	 * 进程注册
	 * @param ProcessInfoBean
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody String add(ProcessInfoBean bean){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][add]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		if("10".equals(bean.getProcessType())){
			//判断服务器IP是否已注册
			if(service.judgeIpExit(bean.getComputerIp())){
				rb.setMessage("服务器IP已经存在！");
				rb.setSuccess("false");
				return gson.toJson(rb);
			}
			
			boolean flag = service.add(bean);
			if(flag){
				rb.setMessage("注册服务器成功！");
				rb.setSuccess("true");
			}else{
				rb.setMessage("注册服务器失败！");
				rb.setSuccess("false");
			}
		}else{
			boolean flag = service.add(bean);
			if(flag){
				rb.setMessage("注册进程成功！");
				rb.setSuccess("true");
			}else{
				rb.setMessage("注册进程失败！");
				rb.setSuccess("false");
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][add]执行结束！");
		}
		
		return gson.toJson(rb);
	}

	/**
	 * 判断是否有主进程
	 * @param processType
	 * @return
	 */
	@RequestMapping("judgeIsHaveMain")
	public @ResponseBody String judgeIsHaveMain(String processType,String satMid){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][judgeIsHaveMain]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.judgeIsHaveMain(processType,satMid);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][judgeIsHaveMain]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 进程启用
	 * @param processCode
	 * @return
	 */
	@RequestMapping("startProcess")
	public @ResponseBody String startProcess(String processCode){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][startProcess]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.startProcess(processCode);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][startProcess]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 进程删除
	 * @param processCode
	 * @return
	 */
	@RequestMapping("deleteProcess")
	public @ResponseBody String deleteProcess(String processCode){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][deleteProcess]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.deleteProcess(processCode);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][deleteProcess]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 进程查看
	 * @param processCode
	 * @return
	 */
	@RequestMapping("viewProcess")
	public @ResponseBody String viewProcess(String processCode){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][viewProcess]开始执行！");
		}
		
		ProcessInfoBean dataMap = service.viewProcess(processCode);
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][viewProcess]执行结束！");
		}
		
		return gson.toJson(dataMap);
	}
	
	/**
	 * 获取主进程信息
	 * @param processType
	 * @param satMid
	 * @return
	 */
	@RequestMapping("getMainProcess")
	public @ResponseBody String getMainProcessCode(String processType,String satMid){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][getMainProcess]开始执行！");
		}
		
		Map<String,Object> dataMap = service.getMainProcess(processType,satMid);
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][getMainProcess]执行结束！");
		}
		
		return gson.toJson(dataMap);
	}
	
	/**
	 * 进程修改
	 * @param ProcessInfoBean
	 * @return
	 */
	@RequestMapping("update")
	public @ResponseBody String updateProcess(ProcessInfoBean bean){
		
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][viewProcess]开始执行！");
		}

		ResultBean rb = new ResultBean();
		boolean flag = service.updateProcess(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessManagerController][viewProcess]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
}

package com.xpoplarsoft.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.monitor.bean.ProcessShowBean;
import com.xpoplarsoft.monitor.constant.OrderConstant;
import com.xpoplarsoft.monitor.service.IMonitorService;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.processManager.bean.ProcessInfoBean;
import com.xpoplarsoft.processManager.service.IProcessManagerService;

@Controller
@RequestMapping("/monitor")
public class MonitorController {

	private static Log log = LogFactory.getLog(MonitorController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired
	private IProcessManagerService managerService;
	
	/**
	 * 查询变化的进程
	 * @param processInfoArrStr
	 * 
	 *   [{"processId":"4001","processState":"1","processName":"1","serviceCode":"1","satId":"1"},
	 *   {"processId":"3000","processState":"0","processName":"1","serviceCode":"1","satId":"1"},
	 *   {"processId":"4005","processState":"3","processName":"1","serviceCode":"1","satId":"1"}]
	 * @return
	 */
	@RequestMapping("queryChangeProcess")
	public @ResponseBody String queryChangeProcess(HttpSession session,String processInfoArrStr,
			String satMids){
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorController][queryChangeProcess]开始执行！");
		}
		
		String userAccount = session.getAttribute("userAccount").toString();
		
		List<ProcessShowBean> rsBeanList = new ArrayList<ProcessShowBean>();
		List<ProcessShowBean> processInfoList = new ArrayList<ProcessShowBean>();
		if(processInfoArrStr != null && !"".equals(processInfoArrStr)){
			//转化为集合
			JSONArray array = JSONObject.parseArray(processInfoArrStr);
			JSONObject object = null;
			ProcessShowBean bean = null;
			for (int i = 0; i < array.size(); i++) {
				bean = new ProcessShowBean();
				object = (JSONObject) array.get(i);
				bean.setProcessId(object.getString("processId"));
				bean.setProcessState(object.getString("processState"));
				bean.setProcessName(object.getString("processName"));
				bean.setAgencyProcessCode(object.getString("serviceCode"));
				if(object.get("satId") != null){
					bean.setSatMid(object.getString("satId"));
				}
				processInfoList.add(bean);
			}
		}
		String[] satMidArr = null;
		if(satMids != null && !"".equals(satMids)){
			satMidArr = satMids.split(";");
		}
		rsBeanList = monitorService.processList(userAccount,satMidArr,processInfoList);
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorController][queryChangeProcess]执行结束！");
		}
		
		return gson.toJson(rsBeanList);
	}
	
	/**
	 * 查询变化的机器进程
	 * @param processInfoArrStr
	 * 
	 *   [{"processId":"4001","processState":"1"},
	 *   {"processId":"3000","processState":"0"},
	 *   {"processId":"4005","processState":"3"}]
	 * @return
	 */
	@RequestMapping("queryChangeCompute")
	public @ResponseBody String queryChangeCompute(String processInfoArrStr){
		
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorController][queryChangeCompute]开始执行！");
		}
		List<ProcessShowBean> rsBeanList = new ArrayList<ProcessShowBean>();
		List<ProcessShowBean> processInfoList = new ArrayList<ProcessShowBean>();
		if(processInfoArrStr != null && !"".equals(processInfoArrStr)){
			//转化为集合
			JSONArray array = JSONObject.parseArray(processInfoArrStr);
			JSONObject object = null;
			ProcessShowBean bean = null;
			for (int i = 0; i < array.size(); i++) {
				bean = new ProcessShowBean();
				object = (JSONObject) array.get(i);
				bean.setProcessId(object.getString("processId"));
				bean.setProcessState(object.getString("processState"));
				bean.setProcessType(object.getString("processType"));
				bean.setProcessName(object.getString("processName"));
				bean.setAgencyProcessCode(object.getString("serviceCode"));
				if(object.get("satId") != null){
					bean.setSatMid(object.getString("satId"));
				}
				processInfoList.add(bean);
			}
		}
		rsBeanList = monitorService.computeList(processInfoList);
		if(log.isDebugEnabled()){
			log.debug("组件[MonitorController][queryChangeCompute]执行结束！");
		}
		
		return gson.toJson(rsBeanList);
	}

	/**
	 * 启动进程
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程code
	 * @param agencyProcessCode 代理进程code
	 * @param startupParamData 启动参数
	 */
	@RequestMapping("start")
	public @ResponseBody String start(String satMid,String processId,String agencyProcessCode,String startupParamData){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][start]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		if(log.isDebugEnabled()){
			log.debug("传入参数satMid=["+satMid+"],processId=["+processId+"],agencyProcessCode=["+agencyProcessCode+"],startupParamData=["+startupParamData+"]");
		}
		if(processId == null){
			rb.setSuccess("false");
			rb.setMessage("传入参数为空！");
		}else{
			//获取启动报文附件内容
			String content = getStartContent(processId);
			if(agencyProcessCode == null){
				agencyProcessCode = "-1";
			}
			monitorService.dispatchOrder(satMid,Integer.parseInt(processId),Integer.parseInt(agencyProcessCode),
					OrderConstant.START_ORDER,content);
			rb.setSuccess("true");
			rb.setMessage("启动成功！");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][start]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 获取启动报文中的附件内容信息
	 * 	{"type":"jar","path":"c:/test.jar","processCode":"1001","params":[{"param":"1"},{"param":"2"}]}
	 * @param processId
	 * @return
	 */
	private String getStartContent(String processId) {
		//根据进程编号查询进程信息
		ProcessInfoBean processBean = managerService.viewProcess(processId);
		Map<String,Object> contentMap = new HashMap<String,Object>();
		contentMap.put("type", processBean.getStartupType());
		contentMap.put("path", processBean.getStartupPath());
		contentMap.put("processCode", processId);
		contentMap.put("processName", "process"+processId);
		String startupParam = processBean.getStartupParam();
		List<Map<String,String>> paramList = new ArrayList<Map<String,String>>();
		Map<String,String> paramMap = new HashMap<String,String>();
		if("6".equals(processBean.getProcessType())
				|| "3".equals(processBean.getProcessType())){
			//如果是数据存储或处理的话，参数集合中第一个参数设置为satMid
			paramMap.put("param", processBean.getSatMid());
			paramList.add(paramMap);
		}
		//设置备用进程和主进程标识
		if("1".equals(processBean.getIsMainProcess())){
			paramMap = new HashMap<String,String>();
			paramMap.put("param", "1");
			paramList.add(paramMap);
			paramMap = new HashMap<String,String>();
			paramMap.put("param", processBean.getMainProcessCode());
			paramList.add(paramMap);
		}
		//设置启动参数
		if(startupParam != null && !"".equals(startupParam)){
			String[] paramArr = startupParam.split(";");
			paramMap = new HashMap<String,String>();
			for (int i = 0; i < paramArr.length; i++) {
				paramMap = new HashMap<String,String>();
				paramMap.put("param", paramArr[i]);
				paramList.add(paramMap);
			}
		}
		contentMap.put("params", paramList);
		return gson.toJson(contentMap);
	}

	@RequestMapping("judgeAlreadConf")
	public @ResponseBody String judgeAlreadConf(String satMid,String processType){
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeAlreadConf]开始执行！");
		}
		ResultBean rb = new ResultBean();
		
		ProcessBean bean = monitorService.queryProcessBySatAndType(satMid,processType);
		if(bean == null){
			rb.setSuccess("false");
		}else{
			//存在
			rb.setSuccess("true");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeAlreadConf]执行结束！");
		}
		return gson.toJson(rb);
	}

	/**
	 * 停止进程
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	@RequestMapping("stop")
	public @ResponseBody String stop(String satMid,String processId,String agencyProcessCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][stop]开始执行！");
		}
		
		if(log.isDebugEnabled()){
			log.debug("satMid=["+satMid+"],processId=["+processId+"]");
		}
		ResultBean rb = new ResultBean();
		if(processId == null){
			rb.setSuccess("false");
			rb.setMessage("传入参数为空！");
		}else{
			String content = getStartContent(processId);
			monitorService.dispatchOrder(satMid,Integer.parseInt(processId),Integer.parseInt(agencyProcessCode),
					OrderConstant.STOP_ORDER,content);
			rb.setSuccess("true");
			rb.setMessage("启动成功！");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][stop]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 发送日志指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	@RequestMapping("sendLog")
	public @ResponseBody String sendLog(String satMid,String processId){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendLog]开始执行！");
		}
		if(log.isDebugEnabled()){
			log.debug("satMid=["+satMid+"],processId=["+processId+"]");
		}
		ResultBean rb = new ResultBean();
		if(processId == null){
			rb.setSuccess("false");
			rb.setMessage("传入参数为空！");
		}else{
			monitorService.sendLog(satMid,Integer.parseInt(processId));
			rb.setSuccess("true");
			rb.setMessage("启动成功！");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendLog]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 发送运行参数  指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	@RequestMapping("sendParam")
	public @ResponseBody String sendParam(String satMid,String processId){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendParam]开始执行！");
		}
		if(log.isDebugEnabled()){
			log.debug("satMid=["+satMid+"],processId=["+processId+"]");
		}
		ResultBean rb = new ResultBean();
		if(processId == null){
			rb.setSuccess("false");
			rb.setMessage("传入参数为空！");
		}else{
			monitorService.sendParam(satMid,Integer.parseInt(processId));
			rb.setSuccess("true");
			rb.setMessage("启动成功！");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendParam]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 发送运行结果  指令
	 * @param satMid  卫星唯一任务代号
	 * @param processId 进程唯一标识
	 */
	@RequestMapping("sendResult")
	public @ResponseBody String sendResult(String satMid,String processId){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendResult]开始执行！");
		}
		if(log.isDebugEnabled()){
			log.debug("satMid=["+satMid+"],processId=["+processId+"]");
		}
		ResultBean rb = new ResultBean();
		if(processId == null){
			rb.setSuccess("false");
			rb.setMessage("传入参数为空！");
		}else{
			monitorService.sendResult(satMid,Integer.parseInt(processId));
			rb.setSuccess("true");
			rb.setMessage("启动成功！");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][sendResult]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 判断服务器是否已启动
	 * @param servcieCode  服务器标识
	 */
	@RequestMapping("judgeServiceStarted")
	public @ResponseBody String judgeServiceStarted(String servcieCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeServiceStarted]开始执行！");
		}
		if(log.isDebugEnabled()){
			log.debug("servcieCode=["+servcieCode+"]");
		}
		ResultBean rb = new ResultBean();
		boolean flag = monitorService.judgeServiceStarted(servcieCode);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeServiceStarted]执行结束！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 判断服务器是否可以启动
	 * @param beiProcessCode  
	 * @param mainProcessCode  
	 */
	@RequestMapping("judgeCanStarted")
	public @ResponseBody String judgeCanStarted(String beiProcessCode,String mainProcessCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeCanStarted]开始执行！");
		}
		ResultBean rb = monitorService.judgeCanStarted(beiProcessCode,mainProcessCode);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][judgeCanStarted]执行结束！");
		}
		return gson.toJson(rb);
	}
}

package com.xpoplarsoft.alarm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.alarm.modal.PageBean;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.service.IAlarmInfoService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.utils.BeanTools;
import com.xpoplarsoft.framework.utils.StringTools;
import com.xpoplarsoft.query.logger.LoggerFactory;
import com.xpoplarsoft.query.logger.bean.OperateLogBean;

import compiler.CompilerOutput;

@Controller
@RequestMapping("/alarmInfo")
public class AlarmInfoController {
	private static Log log = LogFactory.getLog(AlarmInfoController.class);

	private static final Gson GSON = new Gson();
	
	@Autowired
	private IAlarmInfoService alarmInfoService;

	/**
	 * 设置遥测报警配置可用
	 * @param request
	 * @param tmserialid
	 * @return
	 */
	@RequestMapping("alarmInfoCanUse")
	public @ResponseBody
	String alarmInfoCanUse(HttpServletRequest request, String tmid, String ruleid){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][alarmInfoCanUse]开始执行");
		}
		String json = "";
		if(alarmInfoService.updateTmAlarmInfoCanalarm(tmid, ruleid, 0)){
			json = json + "{\"success\": true}";
		}else{
			json = json + "{\"success\": false}";
		}
		return json;
	}
	
	/**
	 * 设置遥测报警配置不可用
	 * @param request
	 * @param tmserialid
	 * @return
	 */
	@RequestMapping("alarmInfoNotUse")
	public @ResponseBody
	String alarmInfoNotUse(HttpServletRequest request, String tmid, String ruleid){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][alarmInfoNotUse]开始执行");
		}
		String json = "";
		if(alarmInfoService.updateTmAlarmInfoCanalarm(tmid, ruleid, 1)){
			json = json + "{\"success\": true}";
		}else{
			json = json + "{\"success\": false}";
		}
		return json;
	}
	
	/**
	 * 设置遥测报警配置为门限报警
	 * @param request
	 * @param device_id  设备id
	 * @param tmserialid
	 * @return
	 */
	@RequestMapping("updateTmAlarmForLimit")
	public @ResponseBody
	String updateTmAlarmForLimit(HttpServletRequest request, String device_id,String tmserialid){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][updateTmAlarmForLimit]开始执行");
		}
		String json = "";
		if(alarmInfoService.updateTmAlarmForLimit(tmserialid)){
			AlarmCacheUtil.queryThenUpdate(device_id, tmserialid);
			json = json + "{\"success\": true}";
			// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setOperateContent("将参数["+tmserialid+"]修改为门限报警参数");
			logBean.setEntityCode(tmserialid);
			logBean.setEndTime();
			logBean.setOperateType(11);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
		}else{
			json = json + "{\"success\": false}";
		}
		return json;
	}
	
	/**
	 * 设置遥测报警配置为状态字报警
	 * @param request
	 * @param device_id  设备id
	 * @param tmserialid  参数id
	 * @return
	 */
	@RequestMapping("updateTmAlarmForState")
	public @ResponseBody
	String updateTmAlarmForState(HttpServletRequest request, String device_id,String tmserialid){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][updateTmAlarmForState]开始执行");
		}
		String json = "";
		if(alarmInfoService.updateTmAlarmForState(tmserialid)){
			AlarmCacheUtil.queryThenUpdate(device_id, tmserialid);
			json = json + "{\"success\": true}";
			
			// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setOperateContent("将参数["+tmserialid+"]修改为状态字报警参数");
			logBean.setEntityCode(tmserialid);
			logBean.setEndTime();
			logBean.setOperateType(11);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
			
		}else{
			json = json + "{\"success\": false}";
		}
		return json;
	}
	
	/**
	 * old:根据id获取tm报警配置信息
	 * new :根据tmid和ruleid查询报警配置信息 孟祥超修改
	 * @param request
	 * @param tmid
	 * @param ruleid
	 * @return
	 */
	@RequestMapping("getTmAlarmInfo")
	public @ResponseBody
	String getTmAlarmInfo(HttpServletRequest request, String tmid, String ruleid){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][getTmAlarmInfo]开始执行");
		}
		String json = "";
		
		json = JSONObject.toJSONString(alarmInfoService.getLimitRuleInfo(tmid,ruleid));
		return json;
	}
	

	/**
	 * 删除状态字报警配置
	 * 
	 * @param request
	 * @param ruleInfoId
	 * @param canalarm
	 * @param satid
	 * @param tmid
	 * @return
	 */
	@RequestMapping("deleteRule")
	public @ResponseBody
	String deleteRule(HttpServletRequest request, String ruleInfoId,
			String canalarm,String satid,String tmid) {
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][deleteRule]开始执行");
		}
		String json = "";
		if (alarmInfoService.deleteRuleInfo(ruleInfoId)) {
			if("0".equals(canalarm)){
				//获取并修改内存中的为不报警
				AlarmCacheUtil.queryThenUpdate(satid,tmid);
			}
			// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setOperateContent("删除报警规则["+ruleInfoId+"]");
			logBean.setOrganCode(ruleInfoId);
			logBean.setEndTime();
			logBean.setOperateType(11);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
			json = json + "{\"success\": true}";
		} else {
			json = json + "{\"success\": false}";
		}
		return json;
	}
	
	@RequestMapping("checkRelation")
	public @ResponseBody
	String checkRelation(HttpServletRequest request, String relation){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][checkRelation]开始执行");
		}
		String json = "";
		//检测关联语句语法是否正确
		CompilerOutput output = alarmInfoService
				.checkRelationRule(relation);
		if (output.getCheckflag()) {
			json = json + "{\"success\": true,\"message\":\"关联条件表达式验证通过！\"}";
		} else {

			json = json + "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
					+ output.getInfoList() + "\"}";
		}
		return json;
	}
	
	/**
	 * 查询航天器组列表
	 * @author 孟祥超
	 * @param request
	 * @param WhereBean bean
	 * @return
	 */
	@RequestMapping("queryAlarmPageList")
	public @ResponseBody
	String queryAlarmPageList(HttpServletRequest request,CommonBean bean){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][queryAlarmPageList]开始执行");
		}
		
		String rs = alarmInfoService.queryAlarmPageList(bean);
		
		return rs;
	}
	
	/**
	 * 查询航天器组单条信息
	 * @author 孟祥超
	 * @param request
	 * @param String pageId
	 * @return
	 */
	@RequestMapping("queryAlarmInfo")
	public @ResponseBody
	String queryAlarmInfo(HttpServletRequest request,String pageId){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][queryAlarmInfo]开始执行");
		}
		
		Map<String,Object> rs = alarmInfoService.queryAlarmInfo(pageId);
		
		String rsStr = GSON.toJson(rs);
		if(log.isInfoEnabled()){
			log.debug("返回结果["+rsStr+"]");
		}
		return rsStr;
	}
	
	/**
	 * 保存航天器组
	 * @author 孟祥超
	 * @param request
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("savePageAlam")
	public @ResponseBody
	String savePageAlam(HttpServletRequest request,String pageInfo){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][savePageAlam]开始执行");
		}
		
		PageBean pageBean = null;
		if (!StringTools.isNull(pageInfo)) {
			pageBean = BeanTools
					.jsonToBean(pageInfo, PageBean.class);
		}
		
		ResultBean rb = new ResultBean();
		
		// 日志对象
		OperateLogBean logBean = new OperateLogBean();
		logBean.setStartTime();
		if(request.getSession().getAttribute("LoginUser") != null){
			LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
			logBean.setUserCode(loginUser.getUserId());
			logBean.setOrganCode(loginUser.getOrganizationId());
			logBean.setClientIp(loginUser.getClientIp());
		}
		if(pageBean == null){
			rb.setSuccess("false");
		}else{
			if("".equals(pageBean.getPageId())){
				//新增
				rb = alarmInfoService.addPageAlam(pageBean);
				logBean.setEntityName(pageBean.getPageName());
				logBean.setOperateContent("新增航天器组["+pageBean.getPageName()+"]");
			}else{
				//修改
				rb = alarmInfoService.updatePageAlam(pageBean);
				logBean.setEntityCode(pageBean.getPageId());
				logBean.setOperateContent("修改航天器组["+pageBean.getPageId()+"]");
			}
		}
		logBean.setEndTime();
		logBean.setOperateType(0);
		LoggerFactory.getLoggerComponent().operateLog(logBean);
		String rsStr = GSON.toJson(rb);
		if(log.isInfoEnabled()){
			log.debug("返回结果["+rsStr+"]");
		}
		return rsStr;
	}
	
	/**
	 * 判断航天器组名称是否存在,存在返回true
	 * @author 孟祥超
	 * @param request
	 * @param pageName
	 * @param pageId
	 * @return
	 */
	@RequestMapping("judgePageIsexit")
	public @ResponseBody
	String judgePageIsexit(HttpServletRequest request,String pageName,String pageId){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][judgePageIsexit]开始执行");
		}
		ResultBean rb = new ResultBean();
		boolean flag = alarmInfoService.judgePageIsexit(pageName,pageId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}
	
	/**
	 * 删除航天器组
	 * @author 孟祥超
	 * @param request
	 * @param pageIds
	 * @return
	 */
	@RequestMapping("deletePage")
	public @ResponseBody
	String deletePage(HttpServletRequest request,String pageIds){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][savePageAlam]开始执行");
		}
		String[] pageArr = null;
		if(pageIds != null && !"".equals(pageIds)){
			pageArr = pageIds.split(",");
		}
		
		ResultBean rb = alarmInfoService.deletePage(pageArr);
		
		// 日志对象
		OperateLogBean logBean = new OperateLogBean();
		logBean.setStartTime();
		if(request.getSession().getAttribute("LoginUser") != null){
			LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
			logBean.setUserCode(loginUser.getUserId());
			logBean.setOrganCode(loginUser.getOrganizationId());
			logBean.setClientIp(loginUser.getClientIp());
		}
		logBean.setOperateContent("删除航天器组["+pageIds+"]");
		logBean.setEntityCode(pageIds);
		logBean.setEndTime();
		logBean.setOperateType(0);
		LoggerFactory.getLoggerComponent().operateLog(logBean);
		
		String rsStr = GSON.toJson(rb);
		if(log.isInfoEnabled()){
			log.debug("返回结果["+rsStr+"]");
		}
		return rsStr;
	}
	
	/**
	 * 查询参数当前报警的规则
	 * @param request
	 * @param tmId
	 * @return
	 */
	@RequestMapping("getAlarmType")
	public @ResponseBody
	String getAlarmType(HttpServletRequest request,String tmId){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoController][getAlarmType]开始执行");
		}
		
		Map<String,Object> rb = alarmInfoService.getAlarmType(tmId);
		
		return GSON.toJson(rb);
	}
	
	
}

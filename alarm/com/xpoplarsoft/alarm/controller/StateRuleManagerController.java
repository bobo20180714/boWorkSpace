package com.xpoplarsoft.alarm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bydz.fltp.connector.adapter.AdapterFactory;
import com.bydz.fltp.connector.adapter.IUDPAdapter;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.alarm.pack.AlarmNoParamPackImpl;
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
@RequestMapping("/stateRule")
public class StateRuleManagerController {
	private static Log log = LogFactory.getLog(StateRuleManagerController.class);
	private static final Gson GSON = new Gson();
	@Autowired
	private IAlarmInfoService alarmInfoService;

	/**
	 * 根据卫星和类型查询遥测参数信息
	 * @param request
	 * @param satid
	 * @param key
	 * @param judgetype
	 * @param CommonBean
	 * @return
	 */
	@RequestMapping("findTmStateRule")
	public @ResponseBody
	String findTmStateRuleList(HttpServletRequest request, String satid,
			String key,String judgetype, CommonBean commonBean) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][findTmStateRuleList]开始执行");
		}
		String json = GSON.toJson(alarmInfoService.findStateRuleInfos(satid, key,judgetype,commonBean));
		
		return json;
	}

	/**
	 * 添加状态字报警配置
	 * 
	 * @param request
	 * @param stateRuleInfo
	 * @return
	 */
	@RequestMapping("addStateRule")
	public @ResponseBody
	String addTmLimitRoule(HttpServletRequest request,
			String stateInfoStr) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][addTmLimitRoule]开始执行");
		}
		String json = "";
		
		boolean flag = false;
		StateRuleInfo sonStateInfo = new StateRuleInfo();
		if (!StringTools.isNull(stateInfoStr)) {
			sonStateInfo = BeanTools
					.jsonToBean(stateInfoStr, StateRuleInfo.class);
			
			//判断掩码是否重复
			String mask = sonStateInfo.getMask();
			String tmid = sonStateInfo.getTmid();
			flag = alarmInfoService.queryRuleByTmAndMask(tmid,mask,"");
			if(flag){
				//已存在
				json = "{\"success\": false,\"message\":\"掩码已经存在！\"}";
				return json;
			}
			if("0".equals(sonStateInfo.getCanalarm())){
				//当页面选择报警是，需进行判断
				//判断是否已有规则报警
				boolean flagCanalarm = alarmInfoService.queryRuleByTmAndCanalarm(tmid,"");
				if(flagCanalarm){
					//已存在
					json = "{\"success\": false,\"message\":\"报警的规则已经存在！\"}";
					return json;
				}
			}
			if (sonStateInfo.getRelationValid() != null
					&& sonStateInfo.getRelationValid().equals("0")) {
				CompilerOutput output = alarmInfoService
						.checkRelationRule(sonStateInfo.getRelation());
				if (!output.getCheckflag()) {
					json = json
							+ "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
							+ output.getInfoList() + "\"}";
					return json;
				}
			}
			flag = alarmInfoService.addTmStateAlarmInfo(sonStateInfo);
		}
		if (flag) {
			//孟祥超 add 若新增的是报警的规则，将规则放入内存中。
			if("0".equals(sonStateInfo.getCanalarm())){
				//AlarmCacheUtil.putAlarmRule(sonStateInfo);
				//发送门限规则报文
				
				XStream x = new XStream();
				x.processAnnotations(StateRuleInfo.class);
				String rulecontent = x.toXML(sonStateInfo);
				sonStateInfo.setRulecontent(rulecontent);
				
				AlarmNoParamPackImpl apk = new AlarmNoParamPackImpl();
				byte[] packData = apk.pack(sonStateInfo);
				// 添加 通信头
				byte[] sendData = ConnectorTools.addProtocolHead(3, packData);
				IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
						.getAdapterComponent("rule_adapter");
				adapter.multicastSend(sendData);
			}
			json = json + "{\"success\": true,\"message\":\"保存信息成功！\"}";
			
			// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setOperateContent("对参数["+sonStateInfo.getTmid()+"]新增状态字规则");
			logBean.setEndTime();
			logBean.setOperateType(11);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
		} else {
			json = json + "{\"success\": false,\"message\":\"保存信息失败！\"}";
		}
		return json;
	}

	/**
	 * 更新状态字报警配置
	 * 
	 * @param request
	 * @param stateRuleInfo
	 * @return
	 */
	@RequestMapping("updateStateRule")
	public @ResponseBody
	String updateLimitRole(HttpServletRequest request,
			String stateInfoStr,String toCach) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][updateLimitRole]开始执行");
		}
		String json = "";
		
		boolean flag = false;
		StateRuleInfo sonStateInfo = new StateRuleInfo();
		if (!StringTools.isNull(stateInfoStr)) {
			sonStateInfo = BeanTools
					.jsonToBean(stateInfoStr, StateRuleInfo.class);
			
			//判断掩码是否重复
			String mask = sonStateInfo.getMask();
			String tmid = sonStateInfo.getTmid();
			flag = alarmInfoService.queryRuleByTmAndMask(tmid,mask,sonStateInfo.getRuleid());
			if(flag){
				//已存在
				json = "{\"success\": false,\"message\":\"掩码已经存在！\"}";
				return json;
			}
			if("0".equals(sonStateInfo.getCanalarm())){
				//当页面选择报警是，需进行判断
				//判断是否已有规则报警
				boolean flagCanalarm = alarmInfoService.queryRuleByTmAndCanalarm(tmid,sonStateInfo.getRuleid());
				if(flagCanalarm){
					//已存在
					json = "{\"success\": false,\"message\":\"已经存在报警的规则！\"}";
					return json;
				}
			}
			if (sonStateInfo.getRelationValid() != null
					&& sonStateInfo.getRelationValid().equals("0")) {
				CompilerOutput output = alarmInfoService
						.checkRelationRule(sonStateInfo.getRelation());
				if (!output.getCheckflag()) {
					json = json
							+ "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
							+ output.getInfoList() + "\"}";
					return json;
				}
			}
			//孟祥超  规则内容清空，这里不需要。
			sonStateInfo.setRulecontent("");
			flag = alarmInfoService.updateTmStateAlarmInfo(sonStateInfo);
		}
		if (flag) {
			//true需要插入内存
			if("true".equals(toCach)){
				//AlarmCacheUtil.putAlarmRule(sonStateInfo);
				
				XStream x = new XStream();
				x.processAnnotations(StateRuleInfo.class);
				String rulecontent = x.toXML(sonStateInfo);
				sonStateInfo.setRulecontent(rulecontent);
				
				//发送门限规则报文
				AlarmNoParamPackImpl apk = new AlarmNoParamPackImpl();
				byte[] packData = apk.pack(sonStateInfo);
				// 添加 通信头
				byte[] sendData = ConnectorTools.addProtocolHead(3, packData);
				IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
						.getAdapterComponent("rule_adapter");
				adapter.multicastSend(sendData);
			}
			json = json + "{\"success\": true,\"message\":\"保存信息成功！\"}";
			// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setOperateContent("修改状态字规则["+sonStateInfo.getRuleid()+"]");
			logBean.setEntityCode(sonStateInfo.getRuleid());
			logBean.setEndTime();
			logBean.setOperateType(11);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
		} else {
			json = json + "{\"success\": false,\"message\":\"保存信息失败！\"}";
		}
		return json;
	}

	/**
	 * 根据tm获取拆分状态列表
	 * @author 孟祥超
	 * @param request
	 * @param tmid
	 * @return
	 */
	@RequestMapping("getStateRuleListByTM")
	public @ResponseBody
	String getStateRuleListByTM(HttpServletRequest request,
			String tmid) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][getStateRuleListByTM]开始执行");
		}
		
		Map<String,Object> map = alarmInfoService.getStateRuleListByTM(tmid);
		
		String json = GSON.toJson(map);
		if(log.isInfoEnabled()){
			log.info("返回结果：["+json+"]");
		}
		return json;
	}
	
	/**
	 * 查询拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("querySonStateRule")
	public @ResponseBody
	String querySonStateRule(HttpServletRequest request,
			String ruleId) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][querySonStateRule]开始执行");
		}
		
		Map<String,Object> map = alarmInfoService.querySonStateRule(ruleId);
		
		String json = GSON.toJson(map);
		if(log.isInfoEnabled()){
			log.info("返回结果：["+json+"]");
		}
		return json;
	}
	
	/**
	 * 删除拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("deleteStateRule")
	public @ResponseBody
	String deleteSonStateRule(HttpServletRequest request,
			String ruleId) {
		if(log.isDebugEnabled()){
			log.debug("组件[StateRuleManagerController][deleteSonStateRule]开始执行");
		}
		ResultBean rb = new ResultBean();
		boolean flag = alarmInfoService.deleteStateRule(ruleId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		String json = GSON.toJson(rb);
		if(log.isInfoEnabled()){
			log.info("返回结果：["+json+"]");
		}
		return json;
	}
	
	/**
	 * 验证关联条件规则是否正确
	 * @param request
	 * @param relationRule
	 * @return
	 */
	@RequestMapping("checkRelationRule")
	public @ResponseBody
	String checkRelationRule(HttpServletRequest request, String relationRule) {
		if (log.isDebugEnabled()) {
			log.debug("组件[LimitRuleManagerController][updateLimitRole]开始执行");
		}
		String json = "";
		CompilerOutput output = alarmInfoService
				.checkRelationRule(relationRule);
		if (output.getCheckflag()) {
			json = json + "{\"success\": true,\"message\":\"表达式验证通过！\"}";
		} else {

			json = json + "{\"success\": false,\"message\":\"表达式验证失败！\",info:\""
					+ output.getInfoList() + "\"}";
		}
		return json;
	}
}

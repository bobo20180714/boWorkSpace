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
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.pack.AlarmNoParamPackImpl;
import com.xpoplarsoft.alarm.service.IAlarmInfoService;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.query.logger.LoggerFactory;
import com.xpoplarsoft.query.logger.bean.OperateLogBean;

import compiler.CompilerOutput;

@Controller
@RequestMapping("/limitRule")
public class LimitRuleManagerController {
	private static Log log = LogFactory
			.getLog(LimitRuleManagerController.class);
	@Autowired
	private IAlarmInfoService alarmInfoService;
	
	private static final Gson GSON = new Gson();

	/**
	 * 分页查询门限报警规则配置信息
	 * 
	 * @param request
	 * @param satid
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("findTmLimitRule")
	public @ResponseBody
	String findTmLimitRuleList(HttpServletRequest request, String satid,
			String key, CommonBean commonBean) {
		if (log.isDebugEnabled()) {
			log.debug("组件[LimitRuleManagerController][findTmLimitRuleList]开始执行");
		}
		// 孟祥超 修改 用 commonBean接受，适合分页查询。
		Map rs = alarmInfoService.findLimitRuleInfos(satid, key, commonBean);
		String json = GSON.toJson(rs);
		return json;
	}

	/**
	 * 添加门限报警配置
	 * 
	 * @param request
	 * @param tmAlarmInfo
	 * @return
	 */
	@RequestMapping("addLimitRule")
	public @ResponseBody
	String addTmLimitRoule(HttpServletRequest request,
			LimitRuleInfo limitRuleInfo) {
		if (log.isDebugEnabled()) {
			log.debug("组件[LimitRuleManagerController][addTmLimitRoule]开始执行");
		}
		String json = "";
		if (limitRuleInfo.getRelationValid() != null
				&& limitRuleInfo.getRelationValid().equals("0")) {
			CompilerOutput output = alarmInfoService
					.checkRelationRule(limitRuleInfo.getRelation());
			if (!output.getCheckflag()) {
				json = json
						+ "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
						+ output.getInfoList() + "\"}";
				return json;
			}
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
		logBean.setOperateContent("对参数["+limitRuleInfo.getTmid()+"]新增门限规则");
		logBean.setEndTime();
		logBean.setOperateType(11);
		
		if (alarmInfoService.addLimitRuleInfo(limitRuleInfo)) {
			//孟祥超 add 若新增的是报警的规则，将规则放入内存中。
			if("0".equals(limitRuleInfo.getCanalarm())){
				
				XStream x = new XStream();
				x.processAnnotations(LimitRuleInfo.class);
				String rulecontent = x.toXML(limitRuleInfo);
				limitRuleInfo.setRulecontent(rulecontent);
				
				//AlarmCacheUtil.putAlarmRule(limitRuleInfo);
				//发送门限规则报文
				AlarmNoParamPackImpl apk = new AlarmNoParamPackImpl();
				byte[] packData = apk.pack(limitRuleInfo);
				// 添加 通信头
				byte[] sendData = ConnectorTools.addProtocolHead(3, packData);
				IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
						.getAdapterComponent("rule_adapter");
				adapter.multicastSend(sendData);
			}
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
			json = json + "{\"success\": true,\"message\":\"保存信息成功！\"}";
		} else {
			json = json + "{\"success\": false,\"message\":\"保存信息失败！\"}";
		}
		return json;
	}

	/**
	 * 更新门限报警配置
	 * 
	 * @param request
	 * @param tmAlarmInfo
	 * @return
	 */
	@RequestMapping("updateLimitRule")
	public @ResponseBody
	String updateLimitRole(HttpServletRequest request,
			LimitRuleInfo limitRuleInfo,String toCach) {
		if (log.isDebugEnabled()) {
			log.debug("组件[LimitRuleManagerController][updateLimitRole]开始执行");
		}
		String json = "";
		if (limitRuleInfo.getRelationValid() != null
				&& limitRuleInfo.getRelationValid().equals("0")) {
			CompilerOutput output = alarmInfoService
					.checkRelationRule(limitRuleInfo.getRelation());
			if (!output.getCheckflag()) {
				json = json
						+ "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
						+ output.getInfoList() + "\"}";
				return json;
			}
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
		logBean.setOperateContent("修改门限规则["+limitRuleInfo.getRuleid()+"]");
		logBean.setEntityCode(limitRuleInfo.getRuleid());
		logBean.setEndTime();
		logBean.setOperateType(11);
		
		if (alarmInfoService.updateLimitRuleInfo(limitRuleInfo)) {
			if("true".equals(toCach)){
				XStream x = new XStream();
				x.processAnnotations(LimitRuleInfo.class);
				String rulecontent = x.toXML(limitRuleInfo);
				limitRuleInfo.setRulecontent(rulecontent);
				//AlarmCacheUtil.putAlarmRule(limitRuleInfo);
				//发送门限规则报文
				AlarmNoParamPackImpl apk = new AlarmNoParamPackImpl();
				byte[] packData = apk.pack(limitRuleInfo);
				// 添加 通信头
				byte[] sendData = ConnectorTools.addProtocolHead(3, packData);
				IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
						.getAdapterComponent("rule_adapter");
				adapter.multicastSend(sendData);
			}
			json = json + "{\"success\": true,\"message\":\"保存信息成功！\"}";
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);
		} else {
			json = json + "{\"success\": false,\"message\":\"保存信息失败！\"}";
		}
		return json;
	}

	/**
	 * 验证关联条件规则是否正确
	 * 
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
			json = json + "{\"success\": true,\"message\":\"关联条件表达式验证通过！\"}";
		} else {

			json = json + "{\"success\": false,\"message\":\"关联条件表达式验证失败！\",info:\""
					+ output.getInfoList() + "\"}";
		}
		return json;
	}
}

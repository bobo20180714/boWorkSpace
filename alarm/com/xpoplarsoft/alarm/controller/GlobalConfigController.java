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
import com.xpoplarsoft.alarm.service.IGlobalConfigService;

@Controller
@RequestMapping("/configInfo")
public class GlobalConfigController {
	private static Log log = LogFactory.getLog(AlarmInfoController.class);

	@Autowired
	private IGlobalConfigService globalConfigService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("findGlobalConfig")
	public @ResponseBody
	String findGlobalConfig(HttpServletRequest request, String key) {
		if (log.isInfoEnabled()) {
			log.info("组件[AlarmInfoController][tmAlarmInfoQueryPage]开始执行");
		}
		String json = "";
		Map rs = globalConfigService.findGlobalConfig(key);
		json = json + "{\"success\": true,\"results\":" + rs.size()
				+ ",\"Rows\":" + JSONObject.toJSONString(rs) + "}";
		return json;
	}

	@RequestMapping("getGlobalConfig")
	public @ResponseBody
	String getGlobalConfig(HttpServletRequest request, String configItem) {
		if (log.isInfoEnabled()) {
			log.info("组件[AlarmInfoController][tmAlarmInfoQueryPage]开始执行");
		}
		String json = "";
		json = json + "{\"success\": true,\"configItem\":\""
				+ globalConfigService.getGlobalConfig(configItem) + "\"}";
		
		return json;
	}

	@RequestMapping("insertGlobalConfig")
	public @ResponseBody
	String insertGlobalConfig(HttpServletRequest request, String configItem,
			String content) {
		if (log.isInfoEnabled()) {
			log.info("组件[AlarmInfoController][tmAlarmInfoQueryPage]开始执行");
		}
		String json = "";
		if (globalConfigService.insertGlobalConfig(configItem, content)) {
			json = json + "{\"success\": true}";
		} else {
			json = json + "{\"success\": false}";
		}
		return json;
	}

	@RequestMapping("updateGlobalConfig")
	public @ResponseBody
	String updateGlobalConfig(HttpServletRequest request, String configItem,
			String content) {
		if (log.isInfoEnabled()) {
			log.info("组件[AlarmInfoController][tmAlarmInfoQueryPage]开始执行");
		}
		String json = "";
		if (globalConfigService.updateGlobalConfig(configItem, content)) {
			json = json + "{\"success\": true}";
		} else {
			json = json + "{\"success\": false}";
		}
		return json;
	}
}

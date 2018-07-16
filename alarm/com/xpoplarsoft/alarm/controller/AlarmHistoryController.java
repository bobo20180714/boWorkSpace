package com.xpoplarsoft.alarm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.alarm.service.IAlarmHistoryService;
import com.xpoplarsoft.alarm.util.AlarmUtil;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.framework.startup.FrameStartup;

@Controller
@RequestMapping("/alarmHistory")
public class AlarmHistoryController {
	private static Log log = LogFactory.getLog(AlarmInfoController.class);

	@Autowired
	private IAlarmHistoryService alarmHistoryService;
	
	private static final Gson GSON = new Gson();

	@RequestMapping("findAlarmHistory")
	public @ResponseBody
	String findAlarmHistory(HttpServletRequest request, String satID,
			String paramName,
			String alarmLevel, String alarmStartTime, String alarmEndTime,
			CommonBean commonBean) {
		if (log.isDebugEnabled()) {
			log.debug("组件[AlarmHistoryController][findAlarmHistory]开始执行");
		}
		
		if("".equals(satID)){
			return "{\"Rows\":[]}";
		}
		//等级
		String alarmLevelInt = alarmLevel == null || alarmLevel == "" ?"-1":alarmLevel;
		Map<String,Object> rs = alarmHistoryService.findAlarmHistory(satID,paramName, alarmLevelInt,
				alarmStartTime, alarmEndTime,commonBean);
		return GSON.toJson(rs);
		
	}
	
	/**
	 * 导出报警历史信息（暂时没用）
	 * @param request
	 * @param satID
	 * @param alarmLevel
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return 
	 * @return
	 */
	@RequestMapping("exportAlarmHistory")
	public @ResponseBody
	String exportAlarmHistory(HttpServletRequest request,HttpServletResponse response, String satID,
			String paramName,
			String alarmStartTime, String alarmEndTime) {
		if (log.isDebugEnabled()) {
			log.debug("组件[AlarmHistoryController][exportAlarmHistory]开始执行");
		}
		ResultBean rb = new ResultBean();
		
		//查询历史信息
		List<Map<String,String>> resultList = alarmHistoryService.findAlarmHistoryNoPage(satID,
				paramName,
				alarmStartTime, alarmEndTime);
		
		//生成文件
		Map<String,String> pathMap = createFile("historyColumnNames","historyTempPath",resultList);
		
		if(pathMap != null && pathMap.get("downloadUrl") != null){
			rb.setData(pathMap.get("downloadUrl"));
			rb.setSuccess("true");
		}
		
		return GSON.toJson(rb);
	}
	
	/**
	 * 报警日志
	 * @param request
	 * @param alarmid
	 * @param commonBean
	 * @return
	 */
	@RequestMapping("findAlarmInfoLog")
	public @ResponseBody
	String findAlarmInfoLog(HttpServletRequest request, String alarmid,
			CommonBean commonBean) {
		if (log.isDebugEnabled()) {
			log.debug("组件[AlarmHistoryController][findAlarmInfoLog]开始执行");
		}
	
		Map<String,Object> rs = alarmHistoryService.findAlarmInfoLog(alarmid,commonBean);
		return GSON.toJson(rs);
	}
	
	/**
	 * 导出报警日志信息
	 * @param request
	 * @param satID
	 * @param paramName
	 * @param alarmLevel
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return
	 */
	@RequestMapping("exportAlarmLog")
	public @ResponseBody
		String exportAlarmLog(HttpServletRequest request,HttpServletResponse response, 
				String satID,String paramName,
			String alarmLevel, String alarmStartTime, String alarmEndTime) {
		if (log.isDebugEnabled()) {
			log.debug("组件[AlarmHistoryController][exportAlarmLog]开始执行");
		}
		
		ResultBean rb = new ResultBean();
		
		//查询报警日志信息
		List<Map<String,String>> resultList = alarmHistoryService.findAlarmLogNoPage(satID,paramName,
				alarmStartTime, alarmEndTime);
		
		//生成文件
		Map<String,String> pathMap = createFile("logColumnNames","logTempPath",resultList);
		
		if(pathMap != null && pathMap.get("downloadUrl") != null){
			rb.setData(pathMap.get("downloadUrl"));
			rb.setSuccess("true");
		}
		
		return GSON.toJson(rb);
//		OutputStream out;
//		FileInputStream in;
//		try {
//			in = new FileInputStream(pathMap.get("filePath"));
//			response.setHeader("Content-Disposition", "attachment; filename=\""
//					+ pathMap.get("fileName") + "\"");
//			response.setContentType("application/octet-stream;charset=UTF-8");
//			out = response.getOutputStream();
//			byte[] buf = new byte[1024];
//			for (int i; (i = in.read(buf)) > 0;) {
//				out.write(buf, 0, i);
//			}
//			out.flush();
//			out.close();
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 生成文件
	 * @param columnNameConfig
	 * @param logTempPathConfig
	 * @param resultList
	 * @return
	 */
	private Map<String,String> createFile(String columnNameConfig, String tempPathConfig,
			List<Map<String,String>> resultList) {
		
		//生成excel文件信息，返回路径
		Map<String,String> pathMap = AlarmUtil.createExcel();
		
		//表头列英文名称
		List<String> columnNameList = new ArrayList<String>();
		String columnNames = SystemParameter.getInstance().getParameter(columnNameConfig);
		if(columnNames != null){
			String[] columnNameArr = columnNames.split(",");
			columnNameList = Arrays.asList(columnNameArr);
		}
		
		//获取模板路径
		String tempathConfig = SystemParameter.getInstance().getParameter(tempPathConfig);
		String tempath = FrameStartup.PROJECT_PATH + "/" + tempathConfig;
		//往excel添加内容
		boolean flag = AlarmUtil.addDataToExcel(tempath,pathMap.get("filePath"), resultList, columnNameList);
		
		if(flag && log.isDebugEnabled()){
			log.debug("文件已生成!路径["+pathMap.get("filePath")+"]");
		}
		
		return pathMap;
	}
}

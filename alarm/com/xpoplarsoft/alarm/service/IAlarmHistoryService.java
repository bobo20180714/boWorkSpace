package com.xpoplarsoft.alarm.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IAlarmHistoryService {
	public Map<String, Object> findAlarmHistory(String satID,String paramName, String alarmLevelInt,
			String alarmStartTime, String alarmEndTime,CommonBean commonBean);
	
	/**
	 * 查询报警历史信息（不分页）
	 * @param satID
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return
	 */
	public List<Map<String,String>> findAlarmHistoryNoPage(String satID,
			String paramName,
			 String alarmStartTime, String alarmEndTime);

	/**
	 * 查询报警日志列表
	 * @param alarmid
	 * @param commonBean
	 * @return
	 */
	public Map<String, Object> findAlarmInfoLog(String alarmid,
			CommonBean commonBean);

	/**
	 * 查询报警日志信息不分页
	 * @param satID
	 * @param paramName
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return
	 */
	public List<Map<String, String>> findAlarmLogNoPage(String satID,String paramName,
			 String alarmStartTime, String alarmEndTime);
}

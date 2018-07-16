package com.xpoplarsoft.alarm.dao;


import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IAlarmHistoryDao {

	/**
	 * 查询报警日志
	 * @param alarmid
	 * @param commonBean
	 * @return
	 */
	public DBResult findAlarmInfoLog(String alarmid,CommonBean commonBean);
	
	/**
	 * 查询报警历史信息（不分页）
	 * @param satID
	 * @param paramName
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return
	 */
	public DBResult findAlarmHistoryNoPage(String satID, String paramName,
			String alarmStartTime, String alarmEndTime);

	/**
	 * 查询报警历史信息
	 * @param satID
	 * @param paramName
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @param commonBean
	 * @return
	 */
	public DBResult findAlarmHistory(String satID,String paramName, 
			String alarmStartTime, String alarmEndTime, CommonBean commonBean);

	/**
	 * 查询报警日志信息不分页
	 * @param satID
	 * @param paramName
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @return
	 */
	public DBResult findAlarmInfoLogNoPage(String satID,String paramName, 
			String alarmStartTime, String alarmEndTime);
}

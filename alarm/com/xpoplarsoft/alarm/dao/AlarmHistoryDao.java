package com.xpoplarsoft.alarm.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
@Repository
@Qualifier("alarmHistoryDao")
public class AlarmHistoryDao implements IAlarmHistoryDao {

	/**
	 * 查询报警日志
	 * @param alarmid
	 * @param commonBean
	 * @return
	 */
	@Override
	public DBResult findAlarmInfoLog(String alarmid, CommonBean commonBean) {
		DBParameter param = new DBParameter();
		param.setObject("alarmid", alarmid);
		DBResult dbs = SQLFactory.getSqlComponent().pagingQueryInfo("alarm", "queryAlarmInfoLog", param, 
				commonBean.getPage(), commonBean.getPagesize());
		return dbs;
	}
	
	@Override
	public DBResult findAlarmInfoLogNoPage(String satID,String paramName,
			String alarmStartTime, String alarmEndTime) {
		DBParameter param = new DBParameter();
		param.setObject("satid", Integer.parseInt(satID));
		param.setObject("paramName", paramName);
		param.setObject("alarmStartTime", "".equals(alarmStartTime)?null:alarmStartTime);
		param.setObject("alarmEndTime", "".equals(alarmEndTime)?null:alarmEndTime);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm", "findAlarmInfoLogNoPage", param);
		return dbs;
	}

	/**
	 * 查询报警历史信息
	 * @param satID
	 * @param paramName
	 * @param alarmStartTime
	 * @param alarmEndTime
	 * @param commonBean
	 * @return
	 */
	@Override
	public DBResult findAlarmHistory(String satID, String paramName,
			String alarmStartTime, String alarmEndTime, CommonBean commonBean) {
		DBParameter param = new DBParameter();
		param.setObject("satid", Integer.parseInt(satID));
		param.setObject("paramName", paramName);
		param.setObject("alarmStartTime", "".equals(alarmStartTime)?null:alarmStartTime);
		param.setObject("alarmEndTime", "".equals(alarmEndTime)?null:alarmEndTime);
		DBResult dbs = SQLFactory.getSqlComponent().pagingQueryInfo("alarm", "findAlarmHistory_new", param, 
				commonBean.getPage(), commonBean.getPagesize());
		return dbs;
	}
	
	@Override
	public DBResult findAlarmHistoryNoPage(String satID, String paramName,
			String alarmStartTime, String alarmEndTime) {
		DBParameter param = new DBParameter();
		param.setObject("satid", Integer.parseInt(satID));
		param.setObject("paramName", paramName);
		param.setObject("alarmStartTime", "".equals(alarmStartTime)?null:alarmStartTime);
		param.setObject("alarmEndTime", "".equals(alarmEndTime)?null:alarmEndTime);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm", "findAlarmHistory_new", param);
		return dbs;
	}

}

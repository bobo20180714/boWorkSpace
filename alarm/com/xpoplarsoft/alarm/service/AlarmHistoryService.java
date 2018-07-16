package com.xpoplarsoft.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.alarm.dao.IAlarmHistoryDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
@Service
public class AlarmHistoryService implements IAlarmHistoryService {

	@Autowired
	@Qualifier("alarmHistoryDao")
	private IAlarmHistoryDao alarmHistoryDao;

	@Override
	public Map<String, Object> findAlarmHistory(String satID, String paramName,
			String alarmLevelInt,
			String alarmStartTime, String alarmEndTime,CommonBean commonBean) {
		DBResult dbResult = alarmHistoryDao.findAlarmHistory(satID,paramName,alarmStartTime,alarmEndTime,commonBean);
		Map<String,Object> rsMap = DBResultUtil.dbResultToPageData(dbResult);
		return rsMap;
	}

	@Override
	public List<Map<String,String>> findAlarmHistoryNoPage(String satID,String paramName,
			 String alarmStartTime, String alarmEndTime) {
		DBResult dbResult = alarmHistoryDao.findAlarmHistoryNoPage(satID,paramName,alarmStartTime,alarmEndTime);
		//列表数据
		List<Map<String,String>> infoList = new ArrayList<Map<String,String>>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,String> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,String>();
				String level_value = "";
				for (int j = 0; j < columnName.length; j++) {
					String value = dbResult.get(i, columnName[j]);
					if(columnName[j].toUpperCase().equals("ALARMLEVEL")){
						level_value = value;
					}
					cellMap.put(columnName[j].toLowerCase(), value);
				}
				String level_zh = "";
				if("1".equals(level_value)){
					level_zh = "重度";
				}else if("2".equals(level_value)){
					level_zh = "中度";
				}else if("3".equals(level_value)){
					level_zh = "轻度";
				}
				cellMap.put("level_zh", level_zh);
				infoList.add(cellMap);
			}
		}
		return infoList;
	}

	/**
	 * 查询报警日志列表
	 * @param alarmid
	 * @param commonBean
	 * @return
	 */
	@Override
	public Map<String, Object> findAlarmInfoLog(String alarmid,
			CommonBean commonBean) {
		DBResult dbResult = alarmHistoryDao.findAlarmInfoLog(alarmid,commonBean);
		Map<String,Object> rsMap = DBResultUtil.dbResultToPageData(dbResult);
		return rsMap;
	}

	@Override
	public List<Map<String, String>> findAlarmLogNoPage(String satID,String paramName,
			String alarmStartTime, String alarmEndTime) {
		DBResult dbResult = alarmHistoryDao.findAlarmInfoLogNoPage(satID,paramName,alarmStartTime,alarmEndTime);
		//列表数据
		List<Map<String,String>> infoList = new ArrayList<Map<String,String>>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,String> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,String>();
				String level_value = "";
				for (int j = 0; j < columnName.length; j++) {
					String value = dbResult.get(i, columnName[j]);
					if(columnName[j].toUpperCase().equals("ALARMLEVEL")){
						level_value = value;
					}
					cellMap.put(columnName[j].toLowerCase(), value);
				}
				String level_zh = "";
				if("1".equals(level_value)){
					level_zh = "重度";
				}else if("2".equals(level_value)){
					level_zh = "中度";
				}else if("3".equals(level_value)){
					level_zh = "轻度";
				}else if("0".equals(level_value)){
					level_zh = "正常";
				}
				cellMap.put("level_zh", level_zh);
				infoList.add(cellMap);
			}
		}
		return infoList;
	}

}

package com.xpoplarsoft.alarm.result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.result.cache.AlarmResultCache;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.SQLFactory;

public class AlarmResultManager implements IAlarmResultManager {
	private static Log log = LogFactory.getLog(AlarmResultManager.class);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public void setAlarmResult(String key, String paramValue, int level, String message,
			boolean isUpper, long putTimes) {
		if (log.isDebugEnabled()) {
			log.debug("向结果信息缓存中添加报警结果！报警内容为：" + message);

		}
		String deviceid = key.split(AlarmConst.SPLIT)[0];
		Device device = AlarmCacheUtil.getDeviceById(Integer.valueOf(deviceid));
		String tmid = key.split(AlarmConst.SPLIT)[1];
		// 获取缓存中的遥测参数
		AlarmParam param = AlarmCacheUtil.getParamById(deviceid, tmid);
		//将报警值放入内存中
		param.setValue(paramValue);

		AlarmResult oldAlarmResult = AlarmResultCache.getAlarmResult(key);
		//long putTimes = new Date().getTime();
		String sql = "";

		if(oldAlarmResult == null || oldAlarmResult.getLevel() != level){
			//插入报警历史
			saveAlarmHistory(deviceid,tmid, level, message, putTimes);
		}
		
		// 如果改为零，则写入报警结束时间并记入数据库
		if (level == 0) {
			
			Date endtime = new Date();
			oldAlarmResult.setEndTime(endtime.getTime());
			oldAlarmResult.setAlarmValue(getParamValueByString2(param));
			oldAlarmResult.setLevel(level);
			oldAlarmResult.setMessage(message);
			AlarmResultCache.putAlarmResult(key, oldAlarmResult);
			String date = format.format(endtime);
			sql = "update alarm_info set alarmlevel = '" + level
					+ "', endtime = str2timestamp('" + date
					+ "') , alarmvalue = '" + getParamValueByString2(param)
					+ "', alarmmsg = '"	+ message 
					+ "' where alarmid = '" + oldAlarmResult.getId() + "'";
			updateDB(sql);
		} else {
			// 如果大于零，判断是否为第一次报警或前一状态是否已结束报警，且已超出间隔时长
			if (isNewAlarm(oldAlarmResult, putTimes)) {
				// 如果为第一次报警或超出间隔时长，则作为新的报警信息写入数据库中
				AlarmResult newAlarmResult = new AlarmResult();
				
				newAlarmResult.setLevel(level);
				newAlarmResult.setMessage(message);
				newAlarmResult.setSatId(deviceid);
				newAlarmResult.setSatCode(device.getCode());
				newAlarmResult.setSatName(device.getName());
				newAlarmResult.setTime(putTimes);
				newAlarmResult.setTmId(tmid);
				newAlarmResult.setParamCode(param.getCode());
				newAlarmResult.setParamName(param.getName());
				newAlarmResult.setUpper(isUpper);
				newAlarmResult.setAlarmValue(getParamValueByString2(param));
				persistentAlarmReault(key, newAlarmResult);
			} else {
				
				// 否则修改报警级别，并写入数据库
				oldAlarmResult.setLevel(level);
				oldAlarmResult.setEndTime(null);
				oldAlarmResult.setAlarmValue(getParamValueByString2(param));
				oldAlarmResult.setMessage(message);
				AlarmResultCache.putAlarmResult(key, oldAlarmResult);				
				// persistentAlarmReault(key, oldAlarmResult);
				sql = "update alarm_info set alarmlevel = '" + level
						+ "', endtime = null, alarmvalue = '"
						+ getParamValueByString2(param) 
						+ "', alarmmsg = '"	+ message 
						+ "' where alarmid = '"
						+ oldAlarmResult.getId() + "'";
				updateDB(sql);
			}
		}
	}

	@Override
	public List<AlarmResult> getCurrentAlarmResult(String satId, long tims) {

		List<AlarmResult> ls = new ArrayList<AlarmResult>();
		for (String key : AlarmResultCache.getAlarmResultKeySet()) {
			if(key!= null && satId.equals(key.split(AlarmConst.SPLIT)[0])){
			//if (key.indexOf(satId) > -1) {
				AlarmResult rs = AlarmResultCache.getAlarmResult(key);
				if (rs.getTime() > tims || rs.getResponseTime() > tims)
					ls.add(rs);
			}
		}

		return ls;
	}

	@Override
	public boolean deleteAlarmResultById(String resultId) {
		for (String key : AlarmResultCache.getAlarmResultKeySet()) {
			AlarmResult rs = AlarmResultCache.getAlarmResult(key);
			if (rs.getId().equals(resultId)) {
				AlarmResultCache.deleteAlarmResult(key);
			}
		}
		return true;
	}

	@Override
	public boolean deleteAlarmResultByParam(String paramId) {
		for (String key : AlarmResultCache.getAlarmResultKeySet()) {
			AlarmResult rs = AlarmResultCache.getAlarmResult(key);
			if (rs.getTmId().equals(paramId)) {
				AlarmResultCache.deleteAlarmResult(key);
			}
		}
		return true;
	}

	@Override
	public boolean deleteAlarmResultByRule(String ruleId) {

		return true;
	}

	@Override
	public void persistentAlarmReault(String key, AlarmResult ar) {
		AlarmResultCache.putAlarmResult(key, ar);
		StringBuilder addsql = new StringBuilder();
		addsql.append("insert into alarm_info (alarmid, satid, tmid, begintime, "
				+ "alarmlevel, alarmmsg, alarmvalue) values");
		addsql.append("(");
		addsql.append("'" + ar.getId() + "'");
		addsql.append(",");
		addsql.append(ar.getSatId());
		addsql.append(",");
		addsql.append(ar.getTmId());
		addsql.append(",");
		addsql.append("str2timestamp('" + format.format(new Date(ar.getTime()))
				+ "')");
		addsql.append(",");
		addsql.append("'" + ar.getLevel() + "'");
		addsql.append(",");
		addsql.append("'" + ar.getMessage() + "'");
		addsql.append(",");
		addsql.append("'" + ar.getAlarmValue() + "'");
		
		addsql.append(")");
		updateDB(addsql.toString());
	}

	/**
	 * 更新数据到库中
	 * 
	 * @param sql
	 *            待执行的脚本
	 */
	private void updateDB(String sql) {
		SQLFactory.getSqlComponent().updateInfo(sql);
	}

	/**
	 * @param oldAlarmResult
	 * @return
	 */
	private boolean isNewAlarm(AlarmResult oldAlarmResult, long putTimes) {
		int oldLevel = oldAlarmResult.getLevel();
		if (oldLevel < 0) {
			return true;
		}
		if (oldLevel > 0) {
			return false;
		}

		return putTimes - oldAlarmResult.getEndTime() > AlarmConst.TIME_OUT;
	}

	@Override
	public void setRespone(String deviceid, String tmid, String userId,
			String userName, String response) {
		long times = new Date().getTime();
		String sql = "update alarm_info set responetime = systimestamp, userid='"
				+ userId + "', response = '" + response + "' where alarmid = '";
		String key = deviceid + AlarmConst.SPLIT + tmid;
		AlarmResult oldAlarmResult = AlarmResultCache.getAlarmResult(key);
		oldAlarmResult.setResponse(response);
		oldAlarmResult.setResponseTime(times);
		oldAlarmResult.setUserid(userId);
		oldAlarmResult.setUserName(userName);
		AlarmResultCache.putAlarmResult(key, oldAlarmResult);

		sql = sql + oldAlarmResult.getId() + "'";
		updateDB(sql);
	}

	private String getParamValueByString(AlarmParam param) {
		int type = param.getData_type();
		if (type == 1 || type == 2) {
			return String.valueOf((Long) param.getValue());
		} else if (type == 0) {
			return String.valueOf((Double) param.getValue());
		}
		if (type == 3) {
			return (String) param.getValue();
		}

		return "";
	}
	private String getParamValueByString2(AlarmParam param) {
		return String.valueOf(param.getValue());
	}

	@Override
	public List<AlarmResult> getAlarmResult(String satId) {
		List<AlarmResult> ls = new ArrayList<AlarmResult>();
		for (String key : AlarmResultCache.getAlarmResultKeySet()) {
			if(key!= null && satId.equals(key.split(AlarmConst.SPLIT)[0])){
			//if (key.indexOf(satId) > -1) {
				AlarmResult rs = AlarmResultCache.getAlarmResult(key);
				ls.add(rs);
			}
		}

		return ls;
	}

	/**
	 * 插入报警历史
	 * @param key
	 * @param level
	 * @param message
	 * @param alarmtime
	 * @return
	 */
	public boolean saveAlarmHistory(String deviceid, String tmid, int level, String message,
			long alarmtime) {
		
		Device device = AlarmCacheUtil.getDeviceById(Integer.valueOf(deviceid));
		//获取缓存中的遥测参数
		AlarmParam alarmParam = AlarmCacheUtil.getParamById(deviceid, tmid);
		
		DBParameter param = new DBParameter();
		param.setObject("pk_id", UUID.randomUUID().toString());
		param.setObject("satid", deviceid);
		param.setObject("satcode", device.getCode());
		param.setObject("satname", device.getName());
		param.setObject("tmid", tmid);
		param.setObject("tmcode", alarmParam.getCode());
		param.setObject("tmname", alarmParam.getName());
		param.setObject("alarmtime", format.format(alarmtime));
		param.setObject("alarmlevel", level);
		param.setObject("alarmmsg", message);
		param.setObject("tmvalue", getParamValueByString2(alarmParam));
		return SQLFactory.getSqlComponent().updateInfo("alarm", "add_alarm_info_log", param);
	}
}

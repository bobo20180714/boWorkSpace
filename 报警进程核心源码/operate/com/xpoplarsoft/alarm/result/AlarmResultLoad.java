package com.xpoplarsoft.alarm.result;

import java.sql.Timestamp;
import java.util.Map;

import com.bydz.fltp.connector.config.ConnectConfig;
import com.bydz.fltp.connector.config.ConnectObj;
import com.bydz.fltp.connector.startup.ConnectorServer;
import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.result.cache.AlarmResultCache;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.load.Load;

public class AlarmResultLoad implements Load {

	public AlarmResultLoad(){
		if (!AlarmCacheUtil.isInit()) {
			AlarmCacheUtil.initAlarmCache();
		}
	}
	
	@Override
	public void load(Map map) {
		this.initParam();
		
		//ConnectorConfigLoadAlarm类里面已经加载，这里不需要
//		this.initAddress();
	}

	private void initParam(){
		String sql = "select t.*, s.sat_name, s.sat_code, tm.tm_param_name, tm.tm_param_code from "
				+ " (select alarmid, satid, to_char(begintime,'yyyy-mm-dd hh24:mi:ss.ff4') begintime, "
				+ " to_char(endtime,'yyyy-mm-dd hh24:mi:ss.ff4') endtime, alarmlevel, alarmmsg, "
				+ " to_char(responetime,'yyyy-mm-dd hh24:mi:ss.ff4') responetime, userid,"
				+ " response, alarmvalue, tmid, isupper, u.user_name from alarm_info left join users u on userid = u.user_id "
				+ " where endtime is null or responetime is null) t, satellite s, tm "
				+ " where t.satid = s.sat_id and t.tmid = tm.tm_param_id order by t.begintime";
		DBResult rs = SQLFactory.getSqlComponent().queryInfo(sql);
		if (rs != null && rs.getRows() > 0) {
			for (int i = 0; i < rs.getRows(); i++) {
				String satid = rs.get(i, "satid");
				String tmid = rs.get(i, "tmid");
				AlarmResult oldAlarmResult = new AlarmResult();
				oldAlarmResult.setAlarmValue(rs.get(i, "alarmvalue"));
				if (rs.get(i, "endtime") != null) {
					oldAlarmResult.setEndTime(Timestamp.valueOf(
							rs.get(i, "endtime")).getTime());
				}
				oldAlarmResult.setId(rs.get(i, "alarmid"));
				oldAlarmResult.setLevel(Integer.parseInt(rs
						.get(i, "alarmlevel")));
				oldAlarmResult.setMessage(rs.get(i, "alarmmsg"));
				oldAlarmResult.setParamCode(rs.get(i, "tm_param_code"));
				oldAlarmResult.setParamName(rs.get(i, "tm_param_name"));
				oldAlarmResult.setResponse(rs.get(i, "response"));
				oldAlarmResult.setSatCode(rs.get(i, "sat_code"));
				oldAlarmResult.setSatId(satid);
				oldAlarmResult.setSatName(rs.get(i, "sat_name"));
				if (rs.get(i, "begintime") != null) {
					oldAlarmResult.setTime(Timestamp.valueOf(
							rs.get(i, "begintime")).getTime());
				}
				oldAlarmResult.setTmId(tmid);
				oldAlarmResult.setUserName(rs.get(i, "user_name"));
				oldAlarmResult.setUserid(rs.get(i, "userid"));
				if (rs.get(i, "responetime") != null) {
					oldAlarmResult.setResponseTime(Timestamp.valueOf(
							rs.get(i, "responetime")).getTime());
				}
				AlarmResultCache.putAlarmResult(
						satid + AlarmConst.SPLIT + tmid, oldAlarmResult);
			}
		}
	}
	
	private void initAddress(){
		String sql = "select sat_id, mid, sat_name, sat_code, create_user, create_time, status, sat_alias, multicast_address, type from satellite_view";
		DBResult rs = SQLFactory.getSqlComponent().queryInfo(sql);
		
		ConnectConfig config = ConnectConfig.getInstance();
		if (rs != null && rs.getRows() > 0) {
			for (int i = 0; i < rs.getRows(); i++) {
				String type = rs.get(i, "type");
				if(type.equals("1")){
					//启动参数数据接收线程、启动非参数数据接收进程
					ConnectObj connectObj = new ConnectObj();
					String connectName = rs.get(i, "sat_code")+"_TM";
					connectObj.setName(connectName);
					connectObj.setStartup("true");
					connectObj.setClassImpl("com.bydz.fltp.connector.udp.UDPServer");
					connectObj.setParameter("broadcast-address", rs.get(i, "multicast_address"));
					connectObj.setParameter("revicedata-length", "50000");
					connectObj.setParameter("executorPoolSize", "10");
					connectObj.setParameter("dispatcherClassImpl", "com.bydz.fltp.connector.dispatcher.impl.DispatcherImpl");
					connectObj.setParameter("cachedClassImpl", "com.xpoplarsoft.alarm.dispatcher.AlarmDispatcherImpl");
					config.setParameter(connectName, connectObj);
					new Thread(new ConnectorServer(connectObj)).start();
				}
			}
		}
	}
}

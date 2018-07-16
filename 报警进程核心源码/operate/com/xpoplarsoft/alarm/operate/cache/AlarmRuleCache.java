package com.xpoplarsoft.alarm.operate.cache;

import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.alarm.util.AlarmUtil;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 报警规则缓存类，存储被监视设备的启用的报警规则
 * 
 * @author zhouxignlu 2015年9月7日
 */
public class AlarmRuleCache {

	private Map<String, AlarmRuleInfo> alarmRuleList = new HashMap<String, AlarmRuleInfo>();
	
	

	/**
	 * 向缓存中添加报警规则
	 * 
	 * @param rule
	 */
	public void putAlarmRule(AlarmRuleInfo rule) {
		synchronized (AlarmRuleCache.class) {
			long getTimes = rule.getGetTimes();
			String key = rule.getSatid() + AlarmConst.SPLIT + rule.getTmid();
			if (new Date().getTime() - getTimes > 0) {
				rule.setChange(true);
			}
			alarmRuleList.put(key, rule);
		}
	}

	/**
	 * 根据设备id和参数id获取报警规则
	 * 
	 * @param device_id
	 * @param param_id
	 * @return
	 */
	public AlarmRuleInfo getAlarmRuleInfo(String device_id, String param_id) {
		synchronized (AlarmRuleCache.class) {
			String key = device_id + AlarmConst.SPLIT + param_id;
			long getTimes = new Date().getTime();
			AlarmRuleInfo rule = alarmRuleList.get(key);
			if(rule == null){
				return null;
			}
			rule.setGetTimes(getTimes);
			alarmRuleList.put(key, rule);
			return rule;
		}
	}

	/**
	 * 读取数据库中的报警规则信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备的报警规则
	 */
	public void putAlarmRuleByDB(String deviceID) {
		
		XStream x = new XStream();
		String sql = "select ruleid, sat_id, tm_param_id, tm_param_name, "
				+ "tm_param_code, canalarm, judgetype, judgecount, "
				+ "rulecontent from limit_rule_view where canalarm = 0";
		if (null != deviceID && !"".equals(deviceID)) {
			sql = sql + " and sat_id = '" + deviceID + "'";
		}
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo(sql);
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				AlarmRuleInfo lr = new AlarmRuleInfo();
				String judgetype = dbs.get(i, "judgetype");
				Clob cl = (Clob)dbs.getObject(i, "rulecontent");
				String ruleContent = AlarmUtil.clobToString(cl);
				if (judgetype.equals("0")) {
					x.processAnnotations(LimitRuleInfo.class);
					lr = (LimitRuleInfo) x.fromXML(ruleContent);
				} else if (judgetype.equals("2")) {
					x.processAnnotations(StateRuleInfo.class);
					lr = (StateRuleInfo) x.fromXML(ruleContent);
				} else {
					lr = new AlarmRuleInfo();
				}
				lr.setRuleid(dbs.get(i, "ruleid"));
				lr.setSatid(dbs.get(i, "sat_id"));
				lr.setTmcode(dbs.get(i, "tm_param_code"));
				lr.setTmid(dbs.get(i, "tm_param_id"));
				lr.setTmname(dbs.get(i, "tm_param_name"));
				lr.setJudgetype(judgetype);
				lr.setRulecontent(ruleContent);
				lr.setCanalarm(dbs.get(i, "canalarm"));
				lr.setJudgecount(dbs.get(i, "judgecount"));
				putAlarmRule(lr);
			}
		}
	}

	public boolean clean() {
		alarmRuleList.clear();
		return true;
	}

	/**
	 * 最近一次获取规则后，规则缓存是否有改变
	 * 
	 * @return
	 */
	public boolean isChange(String device_id, String param_id) {
		String key = device_id + AlarmConst.SPLIT + param_id;			
		AlarmRuleInfo rule = alarmRuleList.get(key);	
		if(rule == null){
			return false;
		}
		return rule.isChange();
	}
}

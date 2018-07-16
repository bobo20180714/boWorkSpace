package com.xpoplarsoft.alarm.operate.cache;

/**
 * 报警系统缓存模块常量类
 * @author zhouxignlu
 * 2015年9月7日
 */
public class AlarmCacheConst {
	
	public static final String DEVICE_SQL = "select sat_id, mid, sat_name, sat_code, create_user, create_time, status, sat_alias, multicast_address, udp_port from satellite where status = 0 ";
	
//	public static final String PARAM_SQL = "select sat_id, tm_param_id, tm_param_name, tm_param_num, tm_param_code, tm_param_type, judgetype, ruleid, rulecontent, canalarm, judgecount,create_time, create_user from limit_rule_view";
	public static final String PARAM_SQL = "select sat_id, tm_param_id, tm_param_name, tm_param_num, tm_param_code, tm_param_type, case when judgetype is null then 0 else judgetype end judgetype from "+
											" tm left join alarm_tm_info on tmid = tm_param_id  where status = 0 ";
	/**
	 * 正常
	 */
	public static final int ALARM_LEVEL0 = 0;
	/**
	 * 重度报警
	 */
	public static final int ALARM_LEVEL1 = 1;
	/**
	 * 中度报警
	 */
	public static final int ALARM_LEVEL2 = 2;
	/**
	 * 轻度报警
	 */
	public static final int ALARM_LEVEL3 = 3;
	
	/**
	 * 未超限
	 */
	public static final String NORMAL = "N";
	
	/**
	 * 超下限
	 */
	public static final String LOWER = "L";
	
	/**
	 * 超上限
	 */
	public static final String UPPER = "U";
}

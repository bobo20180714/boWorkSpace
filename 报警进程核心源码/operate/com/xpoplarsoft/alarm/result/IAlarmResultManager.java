package com.xpoplarsoft.alarm.result;

import java.util.List;

/**
 * 实时报警结果处理接口
 * @author zhouxignlu
 * 2015年9月15日
 */
public interface IAlarmResultManager {

	/**
	 * 管理实时报警结果，报警结果缓存在内存中，及时提供给展示界面，并提供储存报警结果的方法
	 * @param key 卫星id+&&&+遥测id
	 * @param level 报警级别
	 * @param message 报警信息
	 * @param isUpper 是否超上限
	 */
	public void setAlarmResult(String key, int level, String message,
			boolean isUpper);
	
	/**
	 * 报警结果响应接口
	 * @param deviceid 航天器编号
	 * @param tmid 参数编号
	 * @param userId 响应人id
	 * @param userName 响应人名称
	 * @param response 响应内容
	 */
	public void setRespone(String deviceid, String tmid, String userId, String userName, String response);

	/**
	 * 获取有变化的实时报警结果
	 * @param satId 要获取报警的卫星
	 * @param tims 上一次获取结果的时间戳
	 * @return
	 */
	public List<AlarmResult> getCurrentAlarmResult(String satId, long tims);
	public List<AlarmResult> getAlarmResult(String satId);
	
	/**
	 * 删除一个报警结果
	 * @param resultId 报警结果编号，唯一标识
	 * @return
	 */
	public boolean deleteAlarmResultById(String resultId);
	
	/**
	 * 删除报警结果， 删除一个参数相关的所有报警结果信息
	 * @param paramId 参数编号
	 * @return
	 */
	public boolean deleteAlarmResultByParam(String paramId);
	
	/**
	 * 删除报警结果，根据规则编号删除报警结果信息
	 * @param ruleId 规则编号
	 * @return
	 */
	public boolean deleteAlarmResultByRule(String ruleId);
	
	/**
	 * 持久化报警结果信息
	 * 
	 */
	public void persistentAlarmReault(String key, AlarmResult ar);
}

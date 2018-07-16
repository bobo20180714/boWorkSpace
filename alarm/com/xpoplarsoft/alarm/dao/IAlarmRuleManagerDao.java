package com.xpoplarsoft.alarm.dao;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.modal.PageBean;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

public interface IAlarmRuleManagerDao {

	/**
	 * 分页获取门限报警规则
	 * @param satid
	 * @param key
	 * @param commonBean
	 * @return
	 */
	public Map<String,Object> findLimitRuleInfos(String satid, String key, CommonBean commonBean);
	
	/**
	 * 获取门限报警规则信息总个数
	 * @param satid
	 * @param key
	 * @return
	 */
	public int getLimitRuleCount(String satid, String key);
	
	/**
	 * 分页获取状态字报警规则
	 * @param satid
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<StateRuleInfo> findStateRuleInfos(String satid, String key, int start, int limit);
	
	/**
	 * 获取状态字报警规则信息总个数
	 * @param satid
	 * @param key
	 * @return
	 */
	public int getStateRuleCount(String satid, String key);
	
	/**
	 * 获取遥测报警规则
	 * @param satId
	 * @param tmId
	 * @return
	 */
	public List<AlarmRuleInfo> getAlarmRuleInfosForTmId(String satId, String tmId);
	
	/**
	 * 获取启用的遥测报警规则
	 * @param satId
	 * @param tmId
	 * @return
	 */
	public AlarmRuleInfo getCanAlarmRuleInfosForTmId(String satId, String tmId);	
	
	/**
	 * 添加遥测报警规则信息
	 * @param alarmRuleInfo
	 * @return
	 */
	public boolean addAlarmTmInfo(AlarmRuleInfo alarmRuleInfo);
	
	/**
	 * 添加遥测门限报警配置信息
	 * @param limitRuleInfo
	 * @return
	 */
	public boolean addLimitRuleInfo(LimitRuleInfo limitRuleInfo);
	
	/**
	 * 修改遥测门限报警配置信息
	 * 
	 * @param tmAlarmInfo
	 * @return
	 */
	public boolean updateLimitRuleInfo(LimitRuleInfo limitRuleInfo);

	/**
	 * 添加状态字报警规则
	 * @param stateRuleInfo
	 * @return
	 */
	public boolean addTmStateAlarmInfo(StateRuleInfo sonStateInfo);
	
	/**
	 * 修改遥测状态字报警配置信息
	 * 
	 * @param StateRuleInfo
	 * @return
	 */
	public boolean updateTmStateAlarmInfo(StateRuleInfo stateRuleInfo);
	
	/**
	 * 删除报警规则
	 * @param ruleid
	 * @return
	 */
	public boolean deleteRuleInfo(String ruleid);

	/**
	 * 设置遥测报警配置是否可用
	 * 
	 * @param tmid
	 * @param ruleid
	 * @param canAlarm
	 * @return
	 */
	public boolean updateTmAlarmInfoCanalarm(String tmid, String ruleid, int canAlarm);

	/**
	 * 获取一条报警配置信息
	 * 
	 * @param ruleid
	 * @return
	 */
	public AlarmRuleInfo getAlarmRuleInfo(String ruleid);
	
	/**
	 * 获取门限报警配置信息
	 * @param ruleid
	 * @return
	 */
	public LimitRuleInfo getLimitRuleInfo(String tmid,String ruleid);
	
	/**
	 * 获取状态字报警配置信息
	 * @param ruleid
	 * @return
	 */
	public StateRuleInfo getStateRuleInfo(String ruleid);
	
	/**
	 * 设置遥测报警配置为门限报警
	 * 
	 * @param tmid
	 * @return
	 */
	public boolean updateTmAlarmForLimit(String tmid);
	
	/**
	 * 设置遥测报警配置为状态字报警
	 * 
	 * @param tmid
	 * @return
	 */
	public boolean updateTmAlarmForState(String tmid);

	/**
	 * 查询航天器组列表
	 * @author 孟祥超
	 * @param CommonBean bean
	 * @param DBParameter param
	 * @return
	 */
	public DBResult queryAlarmPageList(CommonBean bean,DBParameter param);

	/**
	 * 根据卫星和类型查询遥测参数信息
	 * @author 孟祥超
	 * @param satid
	 * @param key
	 * @param judgetype
	 * @param CommonBean
	 * @return
	 */
	public DBResult findStateTmList(String satid, String key,String judgetype,
			CommonBean bean);

	/**
	 * 修改航天器组信息
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	public boolean updatePageAlam(PageBean pageBean);

	/**
	 * 查询序列DATASEQ
	 * @return
	 */
	public String querySequences();

	/**
	 * 保存航天器组
	 * @param pageBean
	 * @return
	 */
	public boolean addPageAlam(PageBean pageBean);

	/**
	 * 查询规则内容
	 * @param tmid
	 * @return
	 */
	public List<StateRuleInfo> queryStateRuleListByTM(String tmid);

	/**
	 * 查询拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	public StateRuleInfo querySonStateRule(String ruleId);

	/**
	 * 删除拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	public boolean deleteStateRule(String ruleId);

	/**
	 * 查询对应的所有航天器
	 * @param alarmpageid
	 * @return
	 */
	public DBResult queryAlarmPageSatsList(String alarmpageid);

	/**
	 * 删除航天器组
	 * @param pageArr
	 * @return
	 */
	public boolean deletePage(String[] pageArr);

	/**
	 * 获取报警类型
	 * @param tmId
	 * @return
	 */
	public DBResult getAlarmType(String tmId);

	/**
	 * 判断报警界面是否已经存在
	 * @param pageName
	 * @param pageId
	 * @return
	 */
	public DBResult judgePageIsexit(String pageName, String pageId);
}

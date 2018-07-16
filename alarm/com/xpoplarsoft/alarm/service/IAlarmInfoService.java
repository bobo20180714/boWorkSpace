package com.xpoplarsoft.alarm.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.modal.PageBean;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

import compiler.CompilerOutput;

/**
 * 报警配置管理service接口
 * @author zhouxignlu
 * 2015年7月21日
 */
public interface IAlarmInfoService {
	

	/**
	 * 分页获取门限报警规则
	 * @param satid
	 * @param key
	 * @param commonBean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map findLimitRuleInfos(String satid, String key, CommonBean commonBean);
	
	/**
	 * 根据卫星和类型查询遥测参数信息
	 * @param satid
	 * @param key
	 * @param judgetype
	 * @param CommonBean
	 * @return
	 */
	public Map<String, Object> findStateRuleInfos(String satid, String key,String judgetype,CommonBean commonBean);
	
	/**
	 * 获取遥测报警规则
	 * @param satId
	 * @param tmId
	 * @return
	 */
	public List<AlarmRuleInfo> getAlarmRuleInfosForTmId(String satId, String tmId);
	
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
	 * 获取报警配置信息，若ruleid为空，根据tmid查询;若不为空，根据ruleid查询
	 * @param tmid
	 * @param ruleid
	 * @return
	 */
	public LimitRuleInfo getLimitRuleInfo(String tmid,String ruleid);
	
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
	 * @param request
	 * @param WhereBean bean
	 * @return
	 */
	public String queryAlarmPageList(CommonBean bean);

	/**
	 * 查询航天器组单条信息
	 * @author 孟祥超
	 * @param String pageId
	 * @return
	 */
	public Map<String,Object> queryAlarmInfo(String pageId);

	/**
	 * 新增航天器组信息
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	public ResultBean addPageAlam(PageBean pageBean);

	/**
	 * 修改航天器组信息
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	public ResultBean updatePageAlam(PageBean pageBean);

	/**
	 * 根据tm获取拆分状态列表
	 * @author 孟祥超
	 * @param tmid
	 * @return
	 */
	public Map<String, Object> getStateRuleListByTM(String tmid);

	/**
	 * 查询拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	public Map<String, Object> querySonStateRule(String ruleId);

	/**
	 * 删除拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	public boolean deleteStateRule(String ruleId);
	
	/**
	 * 验证关联条件是否正确
	 * @param relation 关联条件内容
	 * @return
	 */
	public CompilerOutput checkRelationRule(String relation);

	/**
	 * 根据tm和mask查询规则是否已存在
	 * @param tmid
	 * @param mask
	 * @param ruleId
	 * @return
	 */
	public boolean queryRuleByTmAndMask(String tmid, String mask,String ruleId);

	/**
	 * 删除航天器组
	 * @author 孟祥超
	 * @param request
	 * @param pageIds
	 * @return
	 */
	public ResultBean deletePage(String[] pageArr);

	/**
	 * 判断是否已有规则报警(同一个参数只会存在一个是报警的规则)
	 * @param tmid
	 * @param canalaram
	 * @param ruleid
	 * @return
	 */
	public boolean queryRuleByTmAndCanalarm(String tmid, String ruleid);

	/**
	 * 查询参数当前报警的规则
	 * @param request
	 * @param tmId
	 * @return
	 */
	public Map<String, Object> getAlarmType(String tmId);

	/**
	 * 判断航天器组名称是否存在,存在返回true
	 * @param pageName
	 * @param pageId
	 * @return
	 */
	public boolean judgePageIsexit(String pageName, String pageId);
}

package com.xpoplarsoft.alarm.operate;

import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.packages.bean.Param;

/**
 * 报警运算器接口
 * @author zhouxignlu
 * 2015年8月31日
 */
public interface IOperate {
	
	/**
	 * 添加运算规则
	 * @param alarmRuleInfo
	 */
	public void setAlarmRule(AlarmRuleInfo alarmRuleInfo);
	
	/**
	 * 添加被计算参数
	 * @param tm
	 */
	public void setAlarmParam(Param tm, String deviceID);
	
	/**
	 * 获取运算结果
	 * 门限规则运算结果String[] index：[0]参数id，[1]规则id，[2]报警信息，[3]报警级别1-3，[4]超上限U/下限L
	 * 状态字规则运算结果String[] index：[0]参数id，[1]规则id，[2]报警信息，[3]报警级别1-3
	 * @return
	 */
	public String[] getOperateResult();
	
	/**
	 *  进行计算
	 * @param deviceID	设备编号
	 * @param paramId	参数编号
	 */
	public void operating(String deviceID, String paramId);
	
	/**
	 * 是否存在报警
	 * @return
	 */
	public boolean isAlarm();
}

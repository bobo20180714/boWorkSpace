package com.xpoplarsoft.alarm.operate;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.packages.bean.Param;

/**
 * 报警运算器实现
 * 
 * @author zhouxignlu 2015年9月15日
 */
public class AlarmOperate implements IOperate {
	private static Log log = LogFactory.getLog(AlarmOperate.class);
	private String[] rsArray;// 判定结果数组
	private boolean isAlarm=false;
	@Override
	public void setAlarmRule(AlarmRuleInfo alarmRuleInfo) {

	}

	@Override
	public void setAlarmParam(Param tm, String deviceID) {
		if (tm == null) {
			return;
		}
		
		String deviceTemp = null;
		if (!AlarmCacheUtil.isInit()) {
			deviceTemp = AlarmCacheUtil.initAlarmCache(deviceID);
		} else if (!AlarmCacheUtil.isInit(deviceID)) {
			deviceTemp = AlarmCacheUtil.putDeviceByDB(deviceID);
		}else{
			deviceTemp = AlarmCacheUtil.getDeviceById(Integer.parseInt(deviceID)).toString();
		}

		if(deviceTemp == null){
			log.error("航天器id["+deviceID+"]初始化失败！");
			return;
		}
		
		String paramid = String.valueOf(tm.getParamId());
		Object newValue = tm.getContent();
		int paramType = tm.getDataType();
		if (log.isDebugEnabled()) {
			log.debug("接收到的遥测参数：num[" + paramid + "]--type[" + paramType
					+ "]--value[" + newValue + "]");
		}
		AlarmParam p = AlarmCacheUtil.getParamById(deviceID, paramid);
		if(p==null){
			log.error("接收到的遥测参数num[" + paramid + "]不存在于遥测表中！");
			return;
		}
		/*if (!compareDataType(p.getData_type(),paramType)) {
			log.error("接收到的遥测参数" + p.getName() + "(" + p.getCode()
					+ ")数据类型与遥测表中的数据类型不符");
			return;
		}*/
		Object oldValue = p.getValue();
		if (!newValue.equals(oldValue)) {
			if (log.isDebugEnabled()) {
				log.debug("遥测参数" + p.getName() + "(" + p.getCode()
						+ ")值有变化，进行规则计算");
			}
			p.setValue(newValue);
			AlarmCacheUtil.putParam(p);
			operating(deviceID, paramid);
		}
	}

	/**
	 * 比较数据类型
	 * 		自己     -  外
	 * 		2   -  1、4 
	 * 		0   -  2、5 
	 * 		3   -  3 
	 * @param data_type
	 * @param paramType
	 * @return
	 */
	private boolean compareDataType(int data_type, int paramType) {
		int tempParamType = -1;
		switch (paramType) {
		case 1:
		case 4:
			tempParamType = 2;
			break;
		case 2:
		case 5:
			tempParamType = 0;
			break;
		case 3:
			tempParamType = 3;
			break;
		}
		if(tempParamType == -1 || data_type != tempParamType){
			return false;
		}
		return true;
	}

	@Override
	public String[] getOperateResult() {

		return rsArray;
	}

	@Override
	public void operating(String deviceID, String paramId) {
		// 获取参数属性
		AlarmParam p = AlarmCacheUtil.getParamById(deviceID, paramId);
		AlarmRuleInfo rule = AlarmCacheUtil.getAlarmRuleInfo(deviceID, paramId);
		if(rule == null){
			return;
		}
		try {
			// 分门限规则与状态字规则运算
			if (AlarmConst.TYPE_LIMIT.equals(rule.getJudgetype())) {
				// 门限报警
				LimitRuleOperate lro = new LimitRuleOperate(p, rule);
				isAlarm = lro.operate();
				if (isAlarm)
					rsArray = lro.getRsArray();
			} else if (AlarmConst.TYPE_STATE.equals(rule.getJudgetype())) {
				// 状态字报警
				StateRuleOperate sro = new StateRuleOperate(p, rule);
				isAlarm = sro.operate();
				if (isAlarm)
					rsArray = sro.getRsArray();
			}
		} catch (Exception e) {
			log.error("遥测参数" + p.getName() + "(" + p.getCode()
					+ ")规则计算发生异常！");
			log.error(e);
			isAlarm = false;
		}
	}

	@Override
	public boolean isAlarm() {
		return isAlarm;
	}

}

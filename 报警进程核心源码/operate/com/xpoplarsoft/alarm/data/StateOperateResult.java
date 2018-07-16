package com.xpoplarsoft.alarm.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态字规则计算结果存储对象
 * 
 * @author zhouxignlu 2015年9月18日
 */
public class StateOperateResult {
	/**
	 * 是否可以产生报警结果信息
	 */
	private boolean canAlarm = false;

	/**
	 * 报警次数上限
	 */
	private int judgeCount;

	// 状态字前一状态
	private String tmState = "";

	// 各种状态产生的报警次数
	private Map<Long, Integer> unitAlarmCountList = new HashMap<Long, Integer>();

	public StateOperateResult() {
		init(3);
	}

	public StateOperateResult(int judgeCount) {
		init(judgeCount);
	}

	private void init(int judgeCount) {
		this.judgeCount = judgeCount;
		canAlarm = false;
		unitAlarmCountList = new HashMap<Long, Integer>();
	}

	/**
	 * 计算每种状态的报警次数，并判断是否达到报警次数上限
	 * 
	 * @param unitData
	 *            状态字节数转换的整数
	 * @return
	 */
	public boolean putAlarmOperateResult(long unitData) {
		//缓存中不存在输入的状态，则在缓存中增加一个状态计数
		if (!unitAlarmCountList.containsKey(unitData)) {
			unitAlarmCountList.put(unitData, 0);
		}
		//计算报警次数，对每种状态都进行计算，如果当前状态的计数达到报警界限，则允许报警
		for (long data : unitAlarmCountList.keySet()) {
			int alarmCount = 0;
			alarmCount = unitAlarmCountList.get(data);
			if (data == unitData) {
				if (alarmCount < judgeCount)
					alarmCount++;
			} else {
				if (alarmCount > 0)
					alarmCount--;
			}
			unitAlarmCountList.put(data, alarmCount);
			canAlarm = alarmCount == judgeCount;
			if(canAlarm){
				break;
			}
		}
		
		return canAlarm;
	}

	public String getTmState() {
		return tmState;
	}

	public void setTmState(String tmState) {
		this.tmState = tmState;
	}

}

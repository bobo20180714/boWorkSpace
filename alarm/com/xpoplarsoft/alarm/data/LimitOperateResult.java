package com.xpoplarsoft.alarm.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.alarm.operate.cache.AlarmCacheConst;

/**
 * 门限结算结果对象
 * 
 * @author zhouxignlu 2015年9月16日
 */
public class LimitOperateResult {
	private static Log log = LogFactory.getLog(LimitOperateResult.class);
	/**
	 * 是否可以产生报警结果信息
	 */
	private boolean canAlarm = false;

	/**
	 * 报警次数上限
	 */
	private int judgeCount;

	/**
	 * 超上限报警计数器，index：0，无报警；1，重度报警计数器；2，中度报警计数器；3，轻度报警计数器
	 */
	private Integer[] upperAlarmArray = new Integer[4];

	/**
	 * 超下限报警计数器，index：0，无报警；1，重度报警计数器；2，中度报警计数器；3，轻度报警计数器
	 */
	private Integer[] lowerAlarmArray = new Integer[4];
	
	private int normal = 0;

	/**
	 * 报警信息
	 */
	private String[] rsInfo = new String[4];

	public LimitOperateResult() {
		init(3);
	}

	public LimitOperateResult(int judgeCount) {
		init(judgeCount);
	}

	private void init(int judgeCount) {
		this.judgeCount = judgeCount;
		canAlarm = false;
		upperAlarmArray[0] = null;
		lowerAlarmArray[0] = null;
		for (int i = 1; i < 4; i++) {
			upperAlarmArray[i] = 0;
			lowerAlarmArray[i] = 0;
		}
	}

	/**
	 * 注入门限报警规则运算结果，并判断是否到达报警计数上限
	 * 
	 * @param oprs
	 * @return
	 */
	public boolean putAlarmOperateResult(String[] oprs) {
		if(log.isDebugEnabled()){
			log.debug("累计门限规则运算结果次数，判断是否可以产生报警结果信息！");
		}
		if(oprs[0] != null && oprs[0].equals(AlarmCacheConst.NORMAL)){
			normal++;
			if(normal>=judgeCount){
				rsInfo[0] = AlarmCacheConst.LOWER;
				canAlarm = canAlarm | true;
				return canAlarm;
			}
		}
		for (int i = 1; i < 4; i++) {
			if (oprs[i] != null && oprs[i].equals(AlarmCacheConst.UPPER)) {
				if(normal>0)
					normal--;
				upperAlarmArray[i] = upperAlarmArray[i] + 1;
				if (lowerAlarmArray[i] > 0)
					lowerAlarmArray[i] = lowerAlarmArray[i] - 1;
				if (upperAlarmArray[i] >= judgeCount) {
					rsInfo[i] = AlarmCacheConst.UPPER;
					canAlarm = canAlarm | true;
				}
			} else if (oprs[i] != null && oprs[i].equals(AlarmCacheConst.LOWER)) {
				if(normal>0)
					normal--;
				if (upperAlarmArray[i] > 0)
					upperAlarmArray[i] = upperAlarmArray[i] - 1;
				lowerAlarmArray[i] = lowerAlarmArray[i] + 1;
				if (lowerAlarmArray[i] >= judgeCount) {
					rsInfo[i] = AlarmCacheConst.LOWER;
					canAlarm = canAlarm | true;
				}
			} else {
				
				if (upperAlarmArray[i] > 0)
					upperAlarmArray[i] = upperAlarmArray[i] - 1;
				if (lowerAlarmArray[i] > 0)
					lowerAlarmArray[i] = lowerAlarmArray[i] - 1;
				canAlarm = canAlarm | false;
			}
		}

		return canAlarm;
	}

	public String[] getRsInfo() {
		return rsInfo;
	}

	public void setJudgeCount(int judgeCount) {
		this.judgeCount = judgeCount;
	}

}

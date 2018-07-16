package com.xpoplarsoft.alarm.operate.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.alarm.data.LimitOperateResult;
import com.xpoplarsoft.alarm.data.StateOperateResult;

/**
 * 规则计算结果缓存管理类，累计规则执行结果，生成结果信息
 * 
 * @author zhouxignlu 2015年9月16日
 */
public abstract class AlarmOperateCache {
	private static Log log = LogFactory.getLog(AlarmOperateCache.class);
	private static Map<String, LimitOperateResult> limitCache = new HashMap<String, LimitOperateResult>();

	private static Map<String, StateOperateResult> stateCache = new HashMap<String, StateOperateResult>();

	/**
	 * 向缓存中添加门限报警规则运算结果，计算报警判断次数，判断是否可以生成报警结果
	 * 
	 * @param key
	 *            参数id+&&&+规则id
	 * @param oprs
	 *            判定结果数组index：0，无报警；1，重度报警；2，中度报警；3，轻度报警；value："L"超下限,"U"超上限
	 * @param judgeCount
	 *            报警次数限制
	 * @return
	 */
	public static boolean setLimitOperateResult(String key, String[] oprs,
			int judgeCount) {
		if (log.isDebugEnabled()) {
			log.debug("向运算结果缓存中添加门限规则运算结果！");
		}
		boolean canAlarm = false;
		LimitOperateResult lor = limitCache.get(key);
		if (lor == null) {
			lor = judgeCount == 0 ? new LimitOperateResult()
					: new LimitOperateResult(judgeCount);
			canAlarm = lor.putAlarmOperateResult(oprs);
			limitCache.put(key, lor);
		} else {
			canAlarm = lor.putAlarmOperateResult(oprs);
			limitCache.remove(key);
			limitCache.put(key, lor);
		}
		if (log.isDebugEnabled()) {
			if (canAlarm) {
				log.debug("条件满足，可以产生门限报警结果信息！");
			} else {
				log.debug("条件未满足，不能产生门限报警结果信息！");
			}

		}
		return canAlarm;
	}

	/**
	 * 删除一个规则产生的报警结果缓存
	 * 
	 * @param key
	 *            参数id+&&&+规则id
	 */
	public static void removeLimitOperateResult(String key) {
		limitCache.remove(key);
	}

	/**
	 * 清空门限报警规则运算缓存
	 */
	public static void removeLimitOperateResult() {
		limitCache.clear();
	}

	/**
	 * 向缓存中添加状态字报警规则运算结果，计算报警判断次数，判断是否可以生成报警结果
	 * 
	 * @param unitData
	 *            遥测状态字节位转换的整数
	 * @param key
	 *            参数id+&&&+规则id
	 * @param tmState
	 *            报警显示内容--遥测状态
	 * @param judgeCount
	 *            报警限制次数
	 * @param cap
	 *            报警内容
	 * @return 报警信息
	 */
	public static String setStateOperateResult(long unitData, String key,
			String tmState, int judgeCount, String cap) {
		if (log.isDebugEnabled()) {
			log.debug("向运算结果缓存中添加状态字规则运算结果！");
		}
		String str = "";
		StateOperateResult sor = stateCache.get(key);
		// 初始化报警缓存中的内容
		if (sor == null) {
			sor = judgeCount == 0 ? new StateOperateResult()
					: new StateOperateResult(judgeCount);
			sor.setTmState("");
			stateCache.put(key, sor);
		} 
		// 判断是否需要报警
		if (sor.putAlarmOperateResult(unitData)) {
			if (log.isDebugEnabled()) {

				log.debug("条件满足，可以产生状态字报警结果信息！");

			}
			if (sor.getTmState().equals("")) {
				// 首次报警
				stateCache.put(key, sor);
				str = cap + "跳变为" + tmState;
			} else {
				// 状态改变报警
				sor.setTmState(tmState);
				str = cap + "从" + sor.getTmState() + "跳变为" + tmState;
			}
			stateCache.remove(key);
			stateCache.put(key, sor);
		}
		return str;
	}

	/**
	 * 获取缓存中的状态字报警信息
	 * 
	 * @param key
	 * @return
	 */
	public static StateOperateResult getStateOperateResult(String key) {
		return stateCache.get(key);
	}

	/**
	 * 删除一个规则产生的报警结果缓存
	 * 
	 * @param key
	 *            参数id+&&&+规则id
	 */
	public static void removeStateOperateResult(String key) {
		stateCache.remove(key);
	}

	/**
	 * 清空状态字报警规则运算缓存
	 */
	public static void removeStateOperateResult() {
		stateCache.clear();
	}

	public static void removeAllCache() {
		removeLimitOperateResult();
		removeStateOperateResult();
	}

}

package com.xpoplarsoft.alarm.result.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.xpoplarsoft.alarm.result.AlarmResult;

/**
 * 报警结果信息缓存类
 * @author zhouxignlu
 * 2015年9月18日
 */
public class AlarmResultCache {
	private static Map<String, AlarmResult> alarmResultCache = new HashMap<String, AlarmResult>();

	/**
	 * 获取缓存中的报警结果
	 * @param key
	 * @return
	 */
	public static AlarmResult getAlarmResult(String key){
		if(alarmResultCache.containsKey(key)){
			return alarmResultCache.get(key);
		}else{
			AlarmResult ar = new AlarmResult();
			alarmResultCache.put(key, ar);
			return ar;
		}
	}
	
	/**
	 * 获取缓存中报警信息的key集合
	 * @return
	 */
	public static Set<String> getAlarmResultKeySet(){
		return alarmResultCache.keySet();
	}
	
	/**
	 * 向缓存中添加报警结果
	 * @param key
	 * @param ar
	 */
	public static void putAlarmResult(String key,AlarmResult ar){
		alarmResultCache.put(key, ar);
	}
	
	/**
	 * 删除缓存中的报警结果
	 * @param key 报警结果key
	 */
	public static void deleteAlarmResult(String key){
		alarmResultCache.remove(key);
	}
}

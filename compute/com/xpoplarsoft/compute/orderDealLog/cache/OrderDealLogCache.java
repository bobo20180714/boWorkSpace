package com.xpoplarsoft.compute.orderDealLog.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.orderDealLog.bean.OrderDealLog;


public class OrderDealLogCache {

	public static String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static Map<Long,List<OrderDealLog>> logMapCache = new HashMap<Long,List<OrderDealLog>>();
	
	private static List<Long> logTimeCache = new ArrayList<Long>();
			
	private Object asycLock = new Object();
	
	private static OrderDealLogCache cacheObj = null;
	
	public static OrderDealLogCache getInstance(){
		if(cacheObj == null){
			cacheObj = new OrderDealLogCache();
		}
		return cacheObj;
	}
	
	public void putLog(long time,OrderDealLog logObj){
		//时间放入缓存
		addLogTime(time);
		synchronized (asycLock) {
			
			if(logMapCache.containsKey(time)){
				List<OrderDealLog> logList = logMapCache.get(time);
				if(logList != null){
					logList.add(logObj);
				}
			}else{
				List<OrderDealLog> logList = new ArrayList<OrderDealLog>();
				logList.add(logObj);
				logMapCache.put(time, logList);
			}
		}
	}
	
	public List<OrderDealLog> getLogDataList(){
		List<OrderDealLog> rsList = new ArrayList<OrderDealLog>();
		for (int i = logTimeCache.size() - 1; i >= 0; i--) {
			List<OrderDealLog> logList = logMapCache.get(logTimeCache.get(i));
			if(logList != null){
				rsList.addAll(logList);
			}
		}
		return rsList;
	}
	
	public void addLogTime(long time){
		synchronized (asycLock) {
			logTimeCache.add(time);
		}
	}
	
	/**
	 * 移除内存中的信息
	 */
	public void removeCahce(){
		synchronized (asycLock) {
			long nowTime = (new Date()).getTime();
			for (int i = 0; i < logTimeCache.size(); i++) {
				long time  = logTimeCache.get(i);
				if(time <= nowTime - 3*60*1000){
					logTimeCache.remove(time);
					logMapCache.remove(time);
				}
			}
		}
	}
	
}

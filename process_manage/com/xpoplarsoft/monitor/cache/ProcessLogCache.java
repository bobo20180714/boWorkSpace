package com.xpoplarsoft.monitor.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.bean.TableBean;

/**
 * 进程日志缓存
 * @author mengxiangchao
 *
 */
public class ProcessLogCache {

	private static Map<Long,Map<String,TableBean>> logMapCache = new HashMap<Long,Map<String,TableBean>>();
	
	private static List<Long> putTimeCache = new ArrayList<Long>();
			
	private Object asycLock = new Object();
	
	private static ProcessLogCache cacheObj = null;
	
	public static ProcessLogCache getInstance(){
		if(cacheObj == null){
			cacheObj = new ProcessLogCache();
		}
		return cacheObj;
	}
	
	public void putLog(long time,String processCode,TableBean bean){
		//时间放入缓存
		addPutTime(time);
		synchronized (asycLock) {
			
			if(logMapCache.containsKey(time)){
				Map<String, TableBean> beanMap = logMapCache.get(time);
				if(beanMap != null){
					beanMap.put(processCode, bean);
				}
			}else{
				Map<String, TableBean> beanMap = new HashMap<String, TableBean>();
				beanMap.put(processCode, bean);
				logMapCache.put(time, beanMap);
			}
		}
	}
	
	public List<ColumnBean> getLogGridColumn(String processCode){
		for (Map<String,TableBean> beanMap : logMapCache.values()) {
			TableBean tb = beanMap.get(processCode);
			if(tb != null){
				return tb.getColumnData();
			}
		}
		return null;
	}
	
	public List<Map<String,String>> getLogDataList(String processCode){
		List<Map<String,String>> rslist = new ArrayList<Map<String,String>>();
		for (int i = putTimeCache.size() - 1; i >= 0; i--) {
			Map<String,TableBean> beanMap = logMapCache.get(putTimeCache.get(i));
			if(beanMap != null){
				TableBean tb = beanMap.get(processCode);
				if(tb != null){
					rslist.addAll(tb.getRowData());
				}
			}
		}
		return rslist;
	}
	
	public void addPutTime(long time){
		synchronized (asycLock) {
			putTimeCache.add(time);
		}
	}
	
	/**
	 * 移除内存中的信息
	 */
	public void removeCahce(){
		synchronized (asycLock) {
			long nowTime = (new Date()).getTime();
			for (int i = 0; i < putTimeCache.size(); i++) {
				long time  = putTimeCache.get(i);
				if(time <= nowTime - 5*60*1000){
					putTimeCache.remove(time);
					logMapCache.remove(time);
				}
			}
		}
	}
	
}

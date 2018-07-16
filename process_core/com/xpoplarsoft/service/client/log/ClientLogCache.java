/**
 * com.xpoplarsoft.service.client.log
 */
package com.xpoplarsoft.service.client.log;

import java.util.Collection;
import java.util.Hashtable;

/**
 * 进程管理客户端日志缓存区
 * @author zhouxignlu
 * 2017年3月27日
 */
public class ClientLogCache {

	private static Hashtable<Long, String> logCache = new Hashtable<Long, String>();
	
	
	public static void putLog(String logStr){
		logCache.put(System.currentTimeMillis(), logStr);
	}

	public static Collection<String> getAllLogs(){
		Collection<String> logs = logCache.values();
		logCache.clear();
		return logs;
	}
}

/**
 * com.xpoplarsoft.service.client
 */
package com.xpoplarsoft.service.client;

import java.util.Hashtable;

/**
 * 服务器代理客户端已启动进程管理器
 * @author zhouxignlu
 * 2017年3月28日
 */
public class ProcessManager {
	private static Hashtable<String, Process> map = new Hashtable<String, Process>();
	
	public static void addProcess(String proCode, Process process){
		map.put(proCode, process);
	}

	/**
	 * 强行停止正在运行的业务进程
	 * @param proCode
	 */
	public static void killProcess(String proCode){
		Process process = map.remove(proCode);
		process.destroy();
	}
}

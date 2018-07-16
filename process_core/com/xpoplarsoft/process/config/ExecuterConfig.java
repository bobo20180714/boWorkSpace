package com.xpoplarsoft.process.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令执行器配置对象
 * @author zhouxignlu
 * 2017年3月3日
 */
public class ExecuterConfig {
	
	private static Map<Integer, String> config = new HashMap<Integer,String>();
	
	public static void putExecuter(int type,String className){
		config.put(type, className);
	}
	
	public static String getExecuter(int type){
		return config.get(type);
	}
}

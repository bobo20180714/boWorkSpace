package com.xpoplarsoft.processType.service;

import java.io.IOException;
import java.util.Properties;

/**
 * 进程功能类型 
 * @author mengxinaghcao
 *
 */
public class ProcessTypeUtil {

	private static Properties prop = null;
	
	private static ProcessTypeUtil cacheObj = null;
	
	private ProcessTypeUtil(){
		try {
			if(prop == null){
				prop = new Properties();
			}
			prop.load(ProcessTypeUtil.class.getClassLoader()
					.getResourceAsStream("processFuncType.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ProcessTypeUtil getInstance(){
		if(cacheObj == null){
			cacheObj = new ProcessTypeUtil();
		}
		return cacheObj;
	}
	
	public String getParameter(String name){
		return prop.getProperty(name);
	}
	
	public static void main(String[] args) {
		String name = ProcessTypeUtil.getInstance().getParameter("门限报警");
		System.out.println(name);
	}
}

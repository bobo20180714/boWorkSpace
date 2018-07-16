package com.xpoplarsoft.monitor.util;

/**
 * 进程监视工具类
 * @author mengxiangchao
 *
 */
public class MonitorUtil {

	/**
	 * 根据进程编号获取进程类型
	 * @param processCode   5001
	 * @return
	 */
	public static String getProceTypeByCode(String processCode) {
		if(processCode != null && processCode.length() >= 4){
			return processCode.substring(0, processCode.length()-3);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getProceTypeByCode("15001"));;	
	}

}

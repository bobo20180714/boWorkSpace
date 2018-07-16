/**
 * com.xpoplarsoft.process.core
 */
package com.xpoplarsoft.process.core;

/**
 * 获取本进程对象
 * @author zhouxignlu
 * 2017年3月17日
 */
public class GetProcessCoreObj {
	private static IProcessCore o=null;
	public static IProcessCore getProcessCore(){
		return o;
	}
	public static void putIProcessCore(IProcessCore obj){
		o = obj;
	}
}

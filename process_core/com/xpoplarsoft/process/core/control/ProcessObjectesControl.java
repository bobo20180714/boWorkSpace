package com.xpoplarsoft.process.core.control;

import java.util.Collection;
import java.util.Hashtable;

import com.xpoplarsoft.process.bean.ProcessBean;

/**
 * 进程对象存储空间及管理，使用带线程同步的Hashtable作为存储器
 * @author zhouxignlu
 * 2017年2月16日
 */
public class ProcessObjectesControl {
	//本地进程
	private static ProcessBean probean ;
	private static Hashtable<String, ProcessBean> processMap = new Hashtable<String,ProcessBean>();
	
	/**
	 * 缓存本地进程对象
	 * @param pro
	 */
	public static void putLocalProcess(ProcessBean pro){
		probean = pro;
		putProcess(pro);
	}
	
	/**
	 * 获取本地进程对象
	 * @return
	 */
	public static ProcessBean getLocalProcess(){
		return probean;
	}
	
	public static void putProcess(ProcessBean pro){
		processMap.put(pro.getId(), pro);
	}
	
	public static ProcessBean getProcess(String id){
		return processMap.get(id);
	}
	
	public static Collection<ProcessBean> getProcessList(){
		return processMap.values();
	}
	
	public static void remove(String id){
		processMap.remove(id);
	}
}

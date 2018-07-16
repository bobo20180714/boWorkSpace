package com.xpoplarsoft.process.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 进程启动执行的程序
 * @author zhouxignlu
 * 2017年2月23日
 */
public class ProgramBean {
	//类全名
	private String classPath;
	//是否启动线程
	private boolean isThtead;
	//最大线程数
	private int size;
	
	//入口方法
	private String method;
	//方法参数
	private List<String> args = new ArrayList<String>();
	
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public boolean isThtead() {
		return isThtead;
	}
	public void setThtead(boolean isThtead) {
		this.isThtead = isThtead;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<String> getArgs() {
		return args;
	}
	public void setArgs(List<String> args) {
		this.args = args;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public void addArg(String type){
		this.args.add(type);
	}
	
}

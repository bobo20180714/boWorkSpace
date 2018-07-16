package com.xpoplarsoft.limits.log.bean;
/**
 * 类功能: 日志管理bean类
 * 
 * @author admin
 * @date 2014-04-11
 */
public class Log {
	
	private String userId;
	private String operators;
	private String service;
	private String ip;
	private String operatorDate;
	private String operatorTime;


	public Log() {
	}

	public void setUserId(String userId){
	    this.userId=userId;
	}
 
	public String getUserId(){
	    return this.userId;
	}
 
	public void setOperators(String operators){
	    this.operators=operators;
	}
 
	public String getOperators(){
	    return this.operators;
	}
 
	public void setService(String service){
	    this.service=service;
	}
 
	public String getService(){
	    return this.service;
	}
 
	public void setIp(String ip){
	    this.ip=ip;
	}
 
	public String getIp(){
	    return this.ip;
	}
 
	public void setOperatorDate(String operatorDate){
	    this.operatorDate=operatorDate;
	}
 
	public String getOperatorDate(){
	    return this.operatorDate;
	}
 
	public void setOperatorTime(String operatorTime){
	    this.operatorTime=operatorTime;
	}
 
	public String getOperatorTime(){
	    return this.operatorTime;
	}
 

}

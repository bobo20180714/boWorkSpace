package com.xpoplarsoft.alarm.result;

import java.util.UUID;

/**
 * 报警结果类
 * @author zhouxignlu
 * 2015年9月1日
 */
public class AlarmResult {
	//产生报警的TM
	private String tmId;
	//所属设备
	private String satId;
	//报警发生时间
	private Long time;
	//运算结果信息
	private String message;
	//报警级别
	private int level;
	//是否为超上限
	private boolean isUpper;
	//报警结束时间
	private Long endTime;
	
	private String id;
	
	private String userid;
	
	private Long responseTime;
	
	private String response;
	
	//报警值
	private String alarmValue;
	
	private String satCode;
	
	private String satName;
	
	private String paramCode;
	
	private String paramName;
	
	//响应人
	private String userName;
	 
	public AlarmResult(){
		id = UUID.randomUUID().toString();
		level = -1;
	}
	
	public String getTmId() {
		return tmId;
	}
	public void setTmId(String tmId) {
		this.tmId = tmId;
	}
	public String getSatId() {
		return satId;
	}
	public void setSatId(String satId) {
		this.satId = satId;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public boolean isUpper() {
		return isUpper;
	}
	public void setUpper(boolean isUpper) {
		this.isUpper = isUpper;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	

	public String getAlarmValue() {
		return alarmValue;
	}

	public void setAlarmValue(String alarmValue) {
		this.alarmValue = alarmValue;
	}

	public String getSatCode() {
		return satCode;
	}

	public void setSatCode(String satCode) {
		this.satCode = satCode;
	}

	public String getSatName() {
		return satName;
	}

	public void setSatName(String satName) {
		this.satName = satName;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}

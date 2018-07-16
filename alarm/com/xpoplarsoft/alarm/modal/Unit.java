package com.xpoplarsoft.alarm.modal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 状态字每个字节的配置内容
 * @author zhouxignlu
 * 2015年8月28日
 */
@XStreamAlias("unit")
public class Unit {
	//报警级别0：正常，1：重度，2：中度，3：轻度
	private String alarmLevel;
	//每一种字节组合转换的整数
	private String data;
	//报警内容
	private String text;
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}

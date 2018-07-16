package com.xpoplarsoft.alarm.data;

import compiler.AlarmParser;

/**
 * 参与计算的缓存数据
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class StringData implements IData {

	private int type = AlarmParser.STRING;
	private String value = "";

	public StringData(Object value) {
		
		try {
			this.value = value.toString();
		} catch (Exception e) {
			value = "";
		}
			
	}

	public StringData(String value) {
		this.value = value;
	}

	@Override
	public int getType() {

		return type;
	}

	@Override
	public String getValue() {

		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
}

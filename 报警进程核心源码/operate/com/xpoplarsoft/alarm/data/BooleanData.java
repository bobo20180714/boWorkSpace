package com.xpoplarsoft.alarm.data;

import compiler.AlarmParser;

/**
 * 参与计算的缓存数据
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class BooleanData implements IData {

	private int type = AlarmParser.STRING;
	private Boolean value = false;

	public BooleanData(Object value) {
		try {
			if (value.getClass().isInstance(this.value)) {
				this.value = (Boolean) value;
			} else if (value.getClass().isInstance(new String())) {
				this.value = Boolean.parseBoolean((String) value);
			}
		} catch (Exception e) {
			value = true;
		}
	}

	public BooleanData(Boolean value) {
		this.value = value;
	}

	@Override
	public int getType() {

		return type;
	}

	@Override
	public Boolean getValue() {

		return value;
	}
	public void setValue(Boolean value){
		this.value = value;
	}
}

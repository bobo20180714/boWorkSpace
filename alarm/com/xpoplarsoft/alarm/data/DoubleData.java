package com.xpoplarsoft.alarm.data;

import compiler.AlarmParser;

/**
 * 参与计算的缓存数据
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class DoubleData implements IData {

	private int type = AlarmParser.FLOAT;
	private Double value = 10E100;

	public DoubleData(Object value) {
		try {
			if (value.getClass().isInstance(this.value)) {
				this.value = (Double) value;
			} else if (value.getClass().isInstance(new String())) {
				this.value = Double.parseDouble((String) value);
			}
		} catch (Exception e) {
			value = 10E100;
		}
	}

	public DoubleData(Double value) {
		this.value = value;
	}

	@Override
	public int getType() {

		return type;
	}

	@Override
	public Double getValue() {

		return value;
	}
	
	public void setValue(Double value){
		this.value = value;
	}

}

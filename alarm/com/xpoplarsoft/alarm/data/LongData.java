package com.xpoplarsoft.alarm.data;

import compiler.AlarmParser;

/**
 * 参与计算的缓存数据
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class LongData implements IData {

	private int type = AlarmParser.INT;
	private Long value = 0x5fffffffffffffffl;

	public LongData(Object value) {
		try {
			if (value.getClass().isInstance(this.value)) {
				this.value = (Long) value;
			} else if (value.getClass().isInstance(new String())) {
				this.value = Long.parseLong((String) value);
			}
		} catch (Exception e) {
			value = 0x5fffffffffffffffl;
		}
	}

	public LongData(Long value) {
		this.value = value;
	}

	@Override
	public int getType() {

		return type;
	}

	@Override
	public Long getValue() {

		return value;
	}
	public void setValue(Long value){
		this.value = value;
	}
}

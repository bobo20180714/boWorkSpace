package com.xpoplarsoft.alarm.data;

import java.util.Date;

import com.bydz.fltp.connector.param.Param;

/**
 * 类功能: 门限报警bean
 * 
 * @author chen.jie
 * @date 2013-7-2
 */
public class AlarmParam extends Param{

	/**
	 * 类型 0.门限 2.状态字
	 */
	private int judgetype;
	
	/**
	 * 参数值
	 */
	private Object value;
	
	private boolean isNew;
	
	private long putTime;
	
	public int getJudgetype() {
		return judgetype;
	}

	public void setJudgetype(int judgetype) {
		this.judgetype = judgetype;
	}

	public Object getValue() {
		isNew = false;
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		this.putTime = new Date().getTime();
		isNew = true;
	}

	public long getPutTime() {
		return putTime;
	}

	public void setPutTime(long putTime) {
		this.putTime = putTime;
	}
	public boolean isNew(){
		return isNew;
	}
}

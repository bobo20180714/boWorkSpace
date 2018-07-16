package com.xpoplarsoft.alarm.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报警内容
 * @author zhouxignlu
 * 2015年7月23日
 */
public class AlarmContent {

	public int topAlarmLevel;// 本次达到报警的最高级别
	
	public double rangeValueLower;//有效值下限
	public double rangeValueUpper;//有效值上限
	
	public int currentAlarm;//当前报警级别
	
	public boolean canAlarm;//是否报警
	// 门限数组，用下标表示报警级别，０：不报警，１：一级，２：二级，３：三级。
	public List<RangeAlarm> rangeAlarmArray = new ArrayList<RangeAlarm>();
	
	public class RangeAlarm{
		public double lower=0;
		public double upper=0;
		public boolean validity=true;
		public int alarmCountLower = 0;
		public int alarmCountUpper = 0;
	}
}

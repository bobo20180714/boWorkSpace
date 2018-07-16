package com.xpoplarsoft.alarm;

/**
 * 报警用常量
 * @author zhouxignlu
 * 2015年9月16日
 */
public class AlarmConst {
	/**
	 * 报警用分隔符
	 */
	public static final String SPLIT = "&&&";
	
	/**
	 * 门限报警
	 */
	public static final String TYPE_LIMIT = "0";
	
	/**
	 * 状态字报警
	 */
	public static final String TYPE_STATE = "2";
	
	/**
	 * 允许报警
	 */
	public static final String CAN_ALARM = "0";
	
	/**
	 * 允许关联条件
	 */
	public static final String CAN_RELATION = "0";
	
	/**
	 * 报警超时时长
	 */
	public static final long TIME_OUT = 5*60*1000;
	
	public static String level2Text(int levelVal) {
		switch (levelVal) {
		case 0:
			return "正常";
		case 1:
			return "严重";
		case 2:
			return "中度";
		case 3:
			return "轻度";
		default:
			return "未知";
		}
	}
}

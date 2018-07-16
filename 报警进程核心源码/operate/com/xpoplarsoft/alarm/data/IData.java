package com.xpoplarsoft.alarm.data;

/**
 * 关联条件执行器缓存数据接口
 * @author zhouxignlu
 * 2015年9月11日
 */
public interface IData {
	/**
	 * 获取数据类型
	 * @return
	 */
	public int getType();
	/**
	 * 获取数据值
	 * @return
	 */
	public Object getValue();
}

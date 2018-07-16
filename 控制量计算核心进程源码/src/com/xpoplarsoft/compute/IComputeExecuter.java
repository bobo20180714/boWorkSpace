package com.xpoplarsoft.compute;

/**
 * 计算组件执行接口，计算数据的获取、运算执行、结果处理的总接口
 * @author zhouxignlu
 * 2017年3月13日
 */
public interface IComputeExecuter {

	/**
	 * 数据获取 or 计算执行 or 结果处理
	 * @return 计算结果数据对象
	 */
	public Object execute();
	
	/**
	 * 数据输入接口
	 * @param param 数据数组
	 */
	public void setParameters(Object... param);
}

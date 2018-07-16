/**
 * com.xpoplarsoft.compute.test
 */
package com.xpoplarsoft.compute.test;

import com.xpoplarsoft.compute.IComputeExecuter;

/**
 * @author zhouxignlu
 * 2017年3月16日
 */
public class ComputeTest implements IComputeExecuter{
	
	private Object[] params;

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		System.out.println("开始进行运算");
		for(int i=0;i<params.length;i++){
			System.out.println("第"+i+"个参数【"+params[i]+"】");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#setParameters(java.lang.Object[])
	 */
	@Override
	public void setParameters(Object... param) {
		params = param;
		
	}

}

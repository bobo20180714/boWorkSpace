package com.xpoplarsoft.constant;

/**
 * 报文业务类型
 * @author mengxiangchao
 *
 */
public enum BusinessTypeEnum {
	
	/**
	 * 报文成功接收反馈
	 */
	FEEDBACK(9),
	
	/**
	 * 进程心跳
	 */
	NETLINK(1),
	
	/**
	 * 进程调度指令
	 */
	DISPATCH_ORDER(2),
	
	/**
	 * 进程功能指令
	 */
	FUNCTION_ORDER(3),
	
	/**
	 * 业务报文
	 */
	BUSINESS_MESSAGE(4);
	
	
	private int businessType;
	
	BusinessTypeEnum(int type){
		this.businessType = type;
	}
	
	public int getType(){
		return businessType;
	}
}

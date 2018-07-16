package com.xpoplarsoft.process.order;

import com.xpoplarsoft.framework.utils.ClassFactory;
import com.xpoplarsoft.process.config.ExecuterConfig;

/**
 * 命令执行器构造者
 * @author zhouxignlu
 * 2017年3月3日
 */
public class ExecuterFactory {

	public static IExectuer create(int type){
		String classImpl = ExecuterConfig.getExecuter(type);
		return (IExectuer)ClassFactory.getInstance(classImpl);
	}
}

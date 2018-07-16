/**
 * com.xpoplarsoft.compute.log
 */
package com.xpoplarsoft.compute.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.framework.parameter.SystemParameter;

/**
 * @author zhouxignlu
 * 2017年4月28日
 */
public class ComputeLogFactory {
	private static Log log = LogFactory.getLog(ComputeLogFactory.class);
	private static IComputeLog obj = null;
	
	public static IComputeLog getIComputeLog(){
		if(obj != null){
			return obj;
		}
		String className = SystemParameter.getInstance().getParameter("computeLogClass");
		try {
			Class<?> c = Class.forName(className);
			obj = (IComputeLog)c.newInstance();
		} catch (Exception e) {
			log.error("创建计算组件日志管理类异常。",e);
		}
		return obj;
	}
}

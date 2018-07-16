/**
 * com.xpoplarsoft.service.client
 */
package com.xpoplarsoft.service.client.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.framework.parameter.SystemParameter;

/**
 * @author zhouxignlu 2017年5月2日
 */
public class ProcessLogFactory {
	private static IProcessLog obj = null;
	private static Log log = LogFactory.getLog(ProcessLogFactory.class);

	public static IProcessLog getIProcessLog() {
		if (obj != null) {
			return obj;
		}
		String className = SystemParameter.getInstance().getParameter(
				"processLogClass");
		try {
			Class<?> c = Class.forName(className);
			obj = (IProcessLog) c.newInstance();
		} catch (Exception e) {
			log.error("创建进程调度日志管理类异常。", e);
			e.printStackTrace();
		}
		return obj;
	}
}

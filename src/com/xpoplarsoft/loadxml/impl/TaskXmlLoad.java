package com.xpoplarsoft.loadxml.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.xpoplarsoft.framework.task.load.TaskLoad;

/**
 * 加载task.xml文件
 * @author Administrator
 *
 */
public class TaskXmlLoad {

	private static Log log = LogFactory.getLog(TaskXmlLoad.class);
	
	public static void load(){
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/task.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				TaskLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[TaskXmlLoad][load]执行完成！");
				}
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(" 加载config/task.xml发生异常", e);
			}
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
}

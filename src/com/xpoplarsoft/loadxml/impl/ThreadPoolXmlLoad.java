package com.xpoplarsoft.loadxml.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.xpoplarsoft.framework.threadpool.ThreadPoolLoad;

/**
 * 加载config/threadPool.xml
 * @author Administrator
 *
 */
public class ThreadPoolXmlLoad {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ThreadPoolXmlLoad.class);

	public static void load() {
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/threadPool.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				ThreadPoolLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[ThreadPoolLoad][load]执行完成！");
				}
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(" 加载config/threadPool.xml发生异常", e);
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

package com.xpoplarsoft.loadxml.impl;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.bydz.fltp.connector.adapter.config.AdapterConfigLoad;

/**
 * 加载config/connector/adapter.xml
 * @author Administrator
 *
 */
public class AdapterXmlLoad {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(AdapterXmlLoad.class);

	public static void load() {
		if (log.isInfoEnabled()) {
			log.info("[AdapterXmlLoad]启动开始");
		}

		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/connector/adapter.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				AdapterConfigLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[AdapterXmlLoad][load]执行完成！");
				}
			}
		}catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("解析[config/connector/adapter.xml]配置文件发生异常", e);
			}
		}

	}

}

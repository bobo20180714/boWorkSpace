package com.xpoplarsoft.loadxml.impl;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.bydz.fltp.connector.config.ConnectorConfigLoad;

/**
 * 加载config/connector/connector.xml文件
 * @author Administrator
 *
 */
public class ConnectorXmlLoad {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ConnectorXmlLoad.class);

	public static void load() {
		if (log.isInfoEnabled()) {
			log.info("[ConnectorXmlLoad]启动开始");
		}

		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/connector/connector.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				ConnectorConfigLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[ConnectorXmlLoad][load]执行完成！");
				}
			}
		}catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("解析[config/connector/connector.xml]配置文件发生异常", e);
			}
		}

	}

}

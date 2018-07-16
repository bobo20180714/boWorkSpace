package com.xpoplarsoft.process.config;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.bydz.fltp.connector.config.ConnectorConfigLoad;

public class ProConnectorConfigLoad extends ConnectorConfigLoad {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ProConnectorConfigLoad.class);

	@Override
	public void load(Map map) {
		if (log.isInfoEnabled()) {
			log.info("[ProConnectorConfigLoad]启动开始");
		}

		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(ProAdapterConfigLoad.class
					.getClassLoader().getResourceAsStream("connector.xml"));
			load(doc);

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("解析进程调度[connector.xml]配置文件发生异常", e);
			}
		}

	}
}

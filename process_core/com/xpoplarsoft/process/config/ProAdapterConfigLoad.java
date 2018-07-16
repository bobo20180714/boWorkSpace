package com.xpoplarsoft.process.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.bydz.fltp.connector.adapter.config.AdapterConfigLoad;

public class ProAdapterConfigLoad extends AdapterConfigLoad {
	private static Log log = LogFactory.getLog(ProAdapterConfigLoad.class);

	@Override
	public void load(Map map) {
		if (log.isInfoEnabled()) {
			log.info("[ProAdapterConfigLoad]启动开始");
		}

		// config/detect/detect.xml

		// 默认配置路径
		// String file_path = "config/connector/adapter.xml";

		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(ProAdapterConfigLoad.class
					.getClassLoader().getResourceAsStream("adapter.xml"));
			load(doc);

		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("解析进程调度[adapter.xml]配置文件发生异常", e);
			}
		}

	}

	public static void main(String[] arg) {
		ProAdapterConfigLoad o = new ProAdapterConfigLoad();

		o.load(new HashMap());
	}
}

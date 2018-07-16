package com.xpoplarsoft.loadxml.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.xpoplarsoft.framework.load.StartLoad;

/**
 * 加载load.xml文件
 * @author Administrator
 *
 */
public class LoadxmlLoad {

	private static Log log = LogFactory.getLog(LoadxmlLoad.class);
	
	public static void load(){
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/load.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				StartLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[LoadxmlLoad][load]执行完成！");
				}
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(" 加载config/load.xml发生异常", e);
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

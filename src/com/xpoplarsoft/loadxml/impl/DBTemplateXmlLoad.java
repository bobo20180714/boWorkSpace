package com.xpoplarsoft.loadxml.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import com.xpoplarsoft.framework.db.load.DBTemplateLoad;

/**
 * 加载dbtemplate.xml文件
 * @author Administrator
 *
 */
public class DBTemplateXmlLoad {


	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(DBTemplateXmlLoad.class);
	
	public static void load() {
		if (log.isDebugEnabled()) {
			log.debug("开始加载SQL模板信息");
		}
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/dbtemplate/dbtemplate.xml");
			if (null != is) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(is);
				DBTemplateLoad.load(doc);
				if(log.isDebugEnabled()){
					log.debug("组件[DBTemplateXmlLoad][load]执行完成！");
				}
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(" 加载config/dbtemplate.xml发生异常", e);
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

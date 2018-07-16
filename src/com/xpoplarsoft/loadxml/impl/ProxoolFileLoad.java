package com.xpoplarsoft.loadxml.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * 加载config/proxool.xml
 * @author Administrator
 *
 */
public class ProxoolFileLoad {
	
	private static Log log = LogFactory.getLog(ProxoolFileLoad.class);
	
	/**
	 * 加载数据源配置文件
	 * 
	 * @return
	 */
	public static void loadProxoolFileconfig() {
		
		Reader reader = null;
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/proxool.xml");
			
			reader = new InputStreamReader(is);
			JAXPConfigurator.configure(reader, false);
			if(log.isDebugEnabled()){
				log.debug("组件[ProxoolFileLoad][loadProxoolFileconfig]执行完成！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error(" 加载config/proxool.xml发生异常", e);
			}
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}
}

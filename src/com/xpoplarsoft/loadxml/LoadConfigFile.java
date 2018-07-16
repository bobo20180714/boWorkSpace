package com.xpoplarsoft.loadxml;

import com.xpoplarsoft.loadxml.impl.AdapterXmlLoad;
import com.xpoplarsoft.loadxml.impl.ConnectorXmlLoad;
import com.xpoplarsoft.loadxml.impl.DBTemplateXmlLoad;
import com.xpoplarsoft.loadxml.impl.ProxoolFileLoad;
import com.xpoplarsoft.loadxml.impl.SystemParameterXmlLoad;
import com.xpoplarsoft.loadxml.impl.TaskXmlLoad;
import com.xpoplarsoft.loadxml.impl.ThreadPoolXmlLoad;

/**
 * 加载配置文件
 * @author Administrator
 *
 */
public class LoadConfigFile {

	public static void loadFile() {
		//加载parameters.xml
		SystemParameterXmlLoad.load();
		//加载线程池配置文件threadPool.xml
		ThreadPoolXmlLoad.load();
		//加载数据库配置文件proxool.xml
		ProxoolFileLoad.loadProxoolFileconfig();
		//加载dbtemplate.xml文件
		DBTemplateXmlLoad.load();
		//connector.xml
		ConnectorXmlLoad.load();
		//adapter.xml
		AdapterXmlLoad.load();
		//加载task.xml文件
		TaskXmlLoad.load();
		
/*		//加载load.xml文件
		LoadxmlLoad.load();*/
	}
	
}

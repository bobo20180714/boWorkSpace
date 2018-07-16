package com.xpoplarsoft.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * 命令执行器配置对象
 * @author zhouxignlu
 * 2017年3月3日
 */
public class ServiceConfig {
	private static Log log = LogFactory.getLog(ServiceConfig.class);
	
	private static Map<String, Propertis> config = new HashMap<String,Propertis>();
	
	public static void putExecuter(String serviceCode,Propertis pro){
		config.put(serviceCode, pro);
	}
	
	public static String getExecuter(String serviceCode){
		if(!config.containsKey(serviceCode)){
			SAXBuilder builder = new SAXBuilder();
			try {
				Document doc = builder.build(ServiceConfig.class
						.getClassLoader().getResourceAsStream("serviceConfig.xml"));
				load(doc);
			} catch (Exception e) {
				e.printStackTrace();
				if (log.isErrorEnabled()) {
					log.error("解析业务处理配置文件[serviceConfig.xml]发生异常", e);
				}
				return null;
			}
		}
		if(!config.containsKey(serviceCode)){
			return null;
		}
		return config.get(serviceCode).classPath;
	}
	
	public static boolean isThread(String serviceCode){
		return config.get(serviceCode).isThread;
	}
	
	private static void load(Document doc) {
		try {

			Element rootEl = doc.getRootElement();
			List<Element> executers = rootEl.getChild("executers").getChildren(
					"executer");
			for (Element e1 : executers) {
				Propertis pro = new Propertis();
				pro.code = e1.getAttributeValue("code");
				pro.classPath = e1.getAttributeValue("class");
				pro.isThread = "true".equals(e1.getAttributeValue("isThread"));
				putExecuter(pro.code, pro);
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("解析业务处理配置文件[serviceConfig.xml]发生异常", e);
			}
		}
	}
	
	static class Propertis {
		String code;
		String classPath;
		boolean isThread;
	}
}

package com.xpoplarsoft.service;

import com.xpoplarsoft.framework.utils.ClassFactory;

public class ServiceExecuterFactory {
	public static IServiceExecuter create(String serviceCode){
		String classImpl = ServiceConfig.getExecuter(serviceCode);
		if(classImpl == null || "".equals(classImpl)){
			return null;
		}
		IServiceExecuter ise = (IServiceExecuter)ClassFactory.getInstance(classImpl);
		ise.setIsThread(ServiceConfig.isThread(serviceCode));
		return ise;
	}
}

package com.xpoplarsoft.alarm.service;

import java.util.Map;

public interface IGlobalConfigService {
	public Map<String,String> findGlobalConfig(String key);
	
	public String getGlobalConfig(String configItem);
	
	public boolean insertGlobalConfig(String configItem, String content);
	
	public boolean updateGlobalConfig(String configItem, String content);
}

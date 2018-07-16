package com.xpoplarsoft.alarm.dao;

import java.util.Map;

public interface IGlobalConfigDao {
	
	public Map<String,String> findGlobalConfig(String key);
	
	public String getGlobalConfig(String configItem);
	
	public boolean insertGlobalConfig(String configItem, String content);
	
	public boolean updateGlobalConfig(String configItem, String content);
}

package com.xpoplarsoft.alarm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.alarm.dao.IGlobalConfigDao;
@Service
public class GlobalConfigService implements IGlobalConfigService {
	@Autowired
	@Qualifier("globalConfigDao")
	private IGlobalConfigDao dao;

	@Override
	public Map<String, String> findGlobalConfig(String key) {
		
		return dao.findGlobalConfig(key);
	}

	@Override
	public String getGlobalConfig(String configItem) {
		
		return dao.getGlobalConfig(configItem);
	}

	@Override
	public boolean insertGlobalConfig(String configItem, String content) {
		
		return dao.insertGlobalConfig(configItem, content);
	}

	@Override
	public boolean updateGlobalConfig(String configItem, String content) {
		
		return dao.updateGlobalConfig(configItem, content);
	}

}

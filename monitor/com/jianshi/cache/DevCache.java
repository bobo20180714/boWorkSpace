package com.jianshi.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

public class DevCache {

	private static DevCache cacheObj = null;
	
	private static Map<String,Device> devCache = new HashMap<String,Device>();

	private String sql = "select * from STATION_INFO";
	
	/**
	 * 获取实例
	 * @return
	 */
	public static DevCache getInstance(){
		if(cacheObj == null){
			cacheObj = new DevCache();
		}
		return cacheObj;
	}
	
	public void putToCache(Device dev){
		devCache.put(dev.getCode(), dev);
	}
	
	public Collection<Device> getAllDev(){
		return devCache.values();
	}
	
	public String[] getAllDevCode(){
		Object[] objArr = devCache.keySet().toArray();
		String[] strArr = new String[objArr.length];
		for (int i = 0; i < objArr.length; i++) {
			strArr[i] = objArr[i].toString();
		}
		return strArr;
	}
	
	public void getAllDevFromDB() {
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		Device dev = null;
		for (int i = 0; i < dbr.getRows(); i++) {
			dev = new Device();
			dev.setCode(dbr.get(i, "DEVICE_CODE"));
			dev.setId(Integer.parseInt(dbr.get(i, "PK_ID")));
			dev.setName(dbr.get(i, "NAME"));
			putToCache(dev);
		}
	}
	
}

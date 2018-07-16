package com.jianshi.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 航天器缓存
 * @author Administrator
 *
 */
public class SatCache {

	private static SatCache cacheObj = null;
	
	private static Map<Integer,Device> devCache = new HashMap<Integer,Device>();

	private String sql = "select * from satellite where STATUS = 0";
	
	/**
	 * 获取实例
	 * @return
	 */
	public static SatCache getInstance(){
		if(cacheObj == null){
			cacheObj = new SatCache();
		}
		return cacheObj;
	}
	
	public void putToCache(Device dev){
		devCache.put(dev.getMd(), dev);
	}
	
	public Device getSat(int mid){
		return devCache.get(mid);
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
	
	public void getAllSatFromDB() {
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		Device dev = null;
		for (int i = 0; i < dbr.getRows(); i++) {
			dev = new Device();
			dev.setCode(dbr.get(i, "SAT_CODE"));
			dev.setId(Integer.parseInt(dbr.get(i, "SAT_ID")));
			dev.setName(dbr.get(i, "SAT_NAME"));
			dev.setMd(Integer.parseInt(dbr.get(i, "MID")));
			putToCache(dev);
		}
	}
	
}

package com.jianshi.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.dataSource.model.DataPackage;
import com.jianshi.pack.NetLink;
import com.jianshi.websocket.DataManager;

public class NetLinkCache {

	private static NetLinkCache cacheObj = null;
	
	private static Map<String,NetLink> netLinkCache = new HashMap<String,NetLink>();
	
	/**
	 * 获取实例
	 * @return
	 */
	public static NetLinkCache getInstance(){
		if(cacheObj == null){
			cacheObj = new NetLinkCache();
		}
		return cacheObj;
	}
	
	public void putToCache(NetLink link){
		netLinkCache.put(link.getId(), link);
		putNetLink(link);
	}
	
	public NetLink getNetLink(String id){
		return netLinkCache.get(id);
	}
	
	public void judgeIsOverTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Object[] array = netLinkCache.values().toArray();
		NetLink link = null;
		for (int i = 0; i < array.length; i++) {
			link = (NetLink)array[i];
			try {
				long sendTime = sdf.parse(link.getSendTime()).getTime();
				if(System.currentTimeMillis() - sendTime > 3000){
					link.setState("1");
					putNetLink(link);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将链路信息转化为数据包
	 * @param link
	 */
	public void putNetLink(NetLink link){
		DataPackage dataPack = new DataPackage();
		dataPack.setDataSource(3);
		//设置数据包类型为链路监视信息（3）
		dataPack.setPackageType(3);
		dataPack.setDeviceId(link.getId());
		//链路监视信息
		dataPack.setNetLink(link);
		DataManager.putNetLink(dataPack);
	}
	
}

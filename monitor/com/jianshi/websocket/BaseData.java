package com.jianshi.websocket;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import javax.websocket.Session;

import com.alibaba.fastjson.JSONObject;
import com.dataSource.model.DataPackage;

/**
 * 基础数据管理器(无线程)
 * @author xjt 2017.2.13
 *
 */
public class BaseData {
	private static Session session;
	private Lock lock;
	
	public BaseData(Session session,Lock lock){
		BaseData.session=session;
		this.lock=lock;
	}
	
	/**
	 * 发送DataPackage数据包
	 * @param pack
	 */
	public void send(DataPackage pack) {		
		lock.lock();
		try {
			session.getBasicRemote().sendText(JSONObject.toJSONString(pack));
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		//System.out.println("发数时间   =>  "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	}
}

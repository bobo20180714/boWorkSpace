package com.jianshi.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.dataSource.model.DataPackage;
import com.dataSource.model.JsjgData;

/**
 * 数据管理器
 * @author xjt 2017.2.15
 *
 */
public class DataManager {
	private static DataManager manager;
	private Map<String, JarData> jarDatas;
	private Map<String, List<String>> sessionMaps;	
	private Map<String, List<String>> netLinkSessionMaps;	
	private Map<String, Session> sessions;	
	private Map<String, String> sidMap;	
	private Map<String, String> deviceMap; //key:sessionId,value:卫星id
//	private String sid;//测站

	private static Log log = LogFactory.getLog(DataManager.class);
	
	static{
		manager=new DataManager();
	}
	
	private DataManager() {
		jarDatas=new HashMap<String, JarData>();
		sessionMaps=new HashMap<String, List<String>>();
		netLinkSessionMaps=new HashMap<String, List<String>>();
		sessions=new HashMap<String, Session>();
		sidMap=new HashMap<String, String>();
		deviceMap=new HashMap<String, String>();
//		sid=null;
	}
	
	/**
	 * 清除所有资源
	 */
	public static void clear(){
		manager.jarDatas.clear();
		manager.sessionMaps.clear();
		manager.sessions.clear();
		manager.deviceMap.clear();
	}
	
	/*public static void setSid(String sid) {
		manager.sid=sid;
	}*/
	
	public static void setSid(Session session,String sid) {
		manager.sidMap.put(session.getId(), sid);
	}
	
	/**
	 * 添加基础数据包
	 * @param devIds
	 * @param session
	 */
	public static void addBaseData(String[] devIds,Session session) {
		String sid=session.getId();
		manager.sessions.put(sid, session);
		if(devIds.length > 0){
			manager.deviceMap.put(sid, devIds[0]);
		}
		Map<String, List<String>> maps=manager.sessionMaps;
		for (String devId : devIds) {
			String k=0+devId;
			if(!maps.containsKey(k)){
				maps.put(k, new ArrayList<String>());
			}
			if(!maps.get(k).contains(sid)){
				maps.get(k).add(sid);
			}			
		}	
		if(log.isDebugEnabled()){
			log.debug("addBaseData: =>"  + sid);
		}
	}
	
	/**
	 * 添加JAR数据包
	 * @param devIds
	 * @param session
	 */
	public static void addJarData(String[] devIds,Session session) {
		String sid=session.getId();
		JarData jarData=new JarData(sid);
		for (String devId : devIds) {
			jarData.load(devId);
		}
		manager.jarDatas.put(sid, jarData);	
		if(log.isDebugEnabled()){
			log.debug("addJarData: =>"  + sid);
		}
	}
	
	/**
	 * 移除关闭session
	 * @param sessionId
	 */
	public static void removeSession(String sessionId){
		manager.sessions.remove(sessionId);
		manager.sidMap.remove(sessionId);
		manager.jarDatas.remove(sessionId);
		manager.deviceMap.remove(sessionId);
		
		if(log.isDebugEnabled()){
			log.debug("removeSession: =>"  + sessionId);
		}
	}
	
	/**
	 * 添加基础数据数据包(DataPackage)
	 * @param pack
	 */
	public static void put(DataPackage pack) {	
		Map<String, Session> sessions=manager.sessions;
		for(Session session : sessions.values()){
			if(!manager.sidMap.containsKey(session.getId())){
				manager.sidMap.put(session.getId(),pack.getSid());
				send(session,pack);
			}else{
				if(!pack.getSid().equals(manager.sidMap.get(session.getId()))){
					continue;
				}
				send(session,pack);
			}
		}
		
		
	/*	if(manager.sid==null){//自动选择第一个测站
			manager.sid=pack.getSid();
		}
		else{//过滤掉不是指定sid测站
			if(!pack.getSid().equals(manager.sid))
				return;
		}*/
		//List<String> sessionList = manager.sessionMaps.get(0+pack.getDeviceId());
		//if(sessionList==null) return;
		//for (String sessionId : sessionList) {
//			send(pack);				
		//}
	}
	
	/**
	 * 添加计算结果数据包(JsjgData)
	 * @param jsjg
	 */
	public static synchronized void addJsjg(JsjgData jsjg) {
		String json=JSONObject.toJSONString(jsjg);
		for (Session session : manager.sessions.values()) {
			try {
				session.getBasicRemote().sendText(json);
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
	}

	/**
	 * 添加链路监视数据包
	 * @param netlinkIds
	 * @param session
	 */
	public static void addNetLinkData(String[] netlinkIds,Session session) {
		String sid=session.getId();
		manager.sessions.put(sid, session);
		Map<String, List<String>> maps=manager.netLinkSessionMaps;
		for (String devId : netlinkIds) {
			String k=0+devId;
			if(!maps.containsKey(k)){
				maps.put(k, new ArrayList<String>());
			}
			if(!maps.get(k).contains(sid)){
				maps.get(k).add(sid);
			}			
		}	
		if(log.isDebugEnabled()){
			log.debug("addNetLinkData: =>"  + sid);
		}
	}
	
	/**
	 * 添加链路监视数据包
	 * @param
	 */
	public static synchronized void putNetLink(DataPackage dataPack) {
		String json=JSONObject.toJSONString(dataPack);
		for (Session session : manager.sessions.values()) {
			try {
				session.getBasicRemote().sendText(json);
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
	}
	
	/**
	 * 发送数据包
	 * @param sessionId 会话ID
	 * @param pack 数据包
	 */
	public static synchronized void send(String sessionId,DataPackage pack) {
		Map<String, Session> sessions=manager.sessions;
		if(sessions.containsKey(sessionId)){
			try {
				sessions.get(sessionId).getBasicRemote().sendText(JSONObject.toJSONString(pack));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized void send(Session session,DataPackage pack) {
		String json=JSONObject.toJSONString(pack);
//		Map<String, Session> sessions=manager.sessions;
//		for(Session session : sessions.values()){
			
			if(!manager.sidMap.containsKey(session.getId())){
				manager.sidMap.put(session.getId(),pack.getSid());
			}else{
				if(!pack.getSid().equals(manager.sidMap.get(session.getId()))){
					return;
				}
			}
			
			if(!manager.deviceMap.containsKey(session.getId())){
				return;
			}
			String deviceId = manager.deviceMap.get(session.getId());
			if(!pack.getDeviceId().equals(deviceId)){
				return;
			}
			
			try {
				session.getBasicRemote().sendText(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}
	}
}

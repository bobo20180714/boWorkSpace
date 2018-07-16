package com.jianshi.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.jianshi.cache.DevCache;

@ServerEndpoint("/websocket")
public class WebSocket {
	
	private static Log log = LogFactory.getLog(WebSocket.class);
	
	@OnMessage 
	public void onMessage(String message, Session session)throws IOException {
		if(log.isDebugEnabled()){
			log.debug("Received: ["+message+"]");
		}
		JSONObject json=JSONObject.parseObject(message);
		String cmd=json.getString("cmd");
		switch (cmd) {
			case "start":
				JSONObject jars=json.getJSONObject("param");
				//清除资源
//				DataManager.clear();
				//接收基础数据
				DataManager.addBaseData(jars.getJSONArray("baseData").toArray(new String[0]), session);
				//接收链路监视数据
				DataManager.addNetLinkData(DevCache.getInstance().getAllDevCode(), session);
				//接收外部数据
				DataManager.addJarData(jars.getJSONArray("jarData").toArray(new String[0]), session);
				//测试：发送基础数据
//				test1.start();
				break;	
			case "sid":
				String sid=json.getString("param");
				DataManager.setSid(session,sid);
				break;
		}		
	}	   

	@OnOpen 
	public void onOpen(Session session) throws IOException {
	    if(log.isDebugEnabled()){
			log.debug("Client connected");
		}
	}
	  
	@OnClose
	public void onClose(Session session) {
		//测试：发送基础数据
		test1.stop();
		DataManager.removeSession(session.getId());		
	    if(log.isDebugEnabled()){
			log.debug("Connection closed");
		}
	}
}

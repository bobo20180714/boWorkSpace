package com.jianshi.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.websocket.Session;

import com.alibaba.fastjson.JSONObject;
import com.dataSource.model.DataPackage;

public class test extends Thread {
	private Session session;
	private Random random;
	
	public test(Session session){
		this.session=session;
		random = new Random();
	}
	
	public void run(){	
		while (true) {
			try {
				DataPackage pack=new DataPackage();
				pack.setDeviceId("1");
				pack.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				pack.addVal("1", random.nextInt(2)+"");
				pack.addVal("2", random.nextInt(300)+200+"");
				session.getBasicRemote().sendText(JSONObject.toJSONString(pack));
				Thread.sleep(2000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

package com.jianshi.websocket;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import com.dataSource.model.Config;
import com.dataSource.model.DataPackage;
import com.dataSource.pack.ICaller;
import com.dataSource.pack.IReadPackage;
import com.dataSource.util.Utils;
import com.jianshi.util.Common;

/**
 * JAR包数据管理器
 * @author xjt 2017.2.13
 *
 */
public class JarData {
	private String sessionId;
	private String path;
	
	public JarData(String sessionId) {
		this.sessionId=sessionId;
		path=Common.getConfigVal("jarPath");
	}
	
	public void load(String devId) {
		InputStream is=Common.getJarFile(path+devId+".jar", "config.xml");
		Config config=Utils.loadConfig(is);
		String handler=config.getDataSource().get("handler");
		try {
			URL url = new URL("file:"+path+devId+".jar");
			@SuppressWarnings("resource")
			URLClassLoader classLoader=new URLClassLoader(new URL[]{url},
					Thread.currentThread().getContextClassLoader());
			Class<?> instance=classLoader.loadClass(handler);
			ICaller caller=(ICaller)instance.newInstance();
			caller.loadConfig(config);
			caller.register(new ReadPackage());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	class ReadPackage implements IReadPackage{		
		@Override
		public void Read(DataPackage data) {
			DataManager.send(sessionId, data);
		}		
	}
}

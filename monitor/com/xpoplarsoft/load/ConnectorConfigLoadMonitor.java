package com.xpoplarsoft.load;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.config.ConnectConfig;
import com.bydz.fltp.connector.config.ConnectObj;
import com.bydz.fltp.connector.startup.ConnectorServer;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.load.Load;

/**
 * 加载connector配置  读数据库，不读取connector.xml文件
 * @author mxc
 *
 */
public class ConnectorConfigLoadMonitor  implements Load{

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ConnectorConfigLoadMonitor.class);

	//收发地址
	public static String addressSql = 
			"select t.multicast_address,t.type from SATELLITE_VIEW t "+
					" where t.status = 0 group by  t.multicast_address ,t.type";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void load(Map map) {
		
		if (log.isInfoEnabled()) {
			log.info("[com.xpoplarsoft.load.ConnectorConfigLoadMonitor]启动开始");
		}
		
		String startup ="true";
		//查询所有卫星的遥测收发端口
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(addressSql);
		String multicast_address = "";
		String addressType = "";
		String connectName = "";
		ConnectConfig config = ConnectConfig.getInstance();
		ConnectObj connectObj = null;
		for (int i = 0;dbr != null &&  i < dbr.getRows(); i++) {
			multicast_address = dbr.get(i, "multicast_address");
			addressType = dbr.get(i, "type");
			if("1".equals(addressType)){
				connectObj = new ConnectObj();
				connectName = multicast_address+"_"+addressType+"_monitor";
				connectObj.setName(connectName);
				connectObj.setStartup(startup);
				connectObj.setClassImpl("com.bydz.fltp.connector.udp.UDPServer");
				connectObj.setParameter("broadcast-address", multicast_address);
				connectObj.setParameter("revicedata-length", "65535");
				connectObj.setParameter("executorPoolSize", "100");
				connectObj.setParameter("dispatcherClassImpl", "com.xpoplarsoft.single.databuffer.action.TMDispatcherImpl");
				if(connectObj != null){
					if ("true".equalsIgnoreCase(startup)) {
						new Thread(new ConnectorServer(connectObj)).start();
					}
					
					config.setParameter(connectName, connectObj);
				}
			}else if("2".equals(addressType)){
				connectObj = new ConnectObj();
				connectName = multicast_address+"_"+"noparam_monitor";
				connectObj.setName(connectName);
				connectObj.setStartup(startup);
				connectObj.setClassImpl("com.bydz.fltp.connector.udp.UDPServer");
				connectObj.setParameter("broadcast-address", multicast_address);
				connectObj.setParameter("revicedata-length", "65535");
				connectObj.setParameter("executorPoolSize", "10");
				connectObj.setParameter("dispatcherClassImpl", "com.xpoplarsoft.single.databuffer.action.UNtmDispatcherImpl");
				if(connectObj != null){
					if ("true".equalsIgnoreCase(startup)) {
						new Thread(new ConnectorServer(connectObj)).start();
					}
					
					config.setParameter(connectName, connectObj);
				}
				/*connectObj = new ConnectObj();
				connectName = "netlink_monitor";
				connectObj.setName(connectName);
				connectObj.setStartup(startup);
				connectObj.setClassImpl("com.bydz.fltp.connector.udp.UDPServer");
				connectObj.setParameter("broadcast-address", multicast_address);
				connectObj.setParameter("revicedata-length", "65535");
				connectObj.setParameter("executorPoolSize", "10");
				connectObj.setParameter("dispatcherClassImpl", "com.xpoplarsoft.single.databuffer.action.NetLinkDispatcherImpl");
				if(connectObj != null){
					if ("true".equalsIgnoreCase(startup)) {
						new Thread(new ConnectorServer(connectObj)).start();
					}
					
					config.setParameter(connectName, connectObj);
				}*/
			
			}
		}
	}


	
}

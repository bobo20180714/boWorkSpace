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
 * 读取遥测、源码包、非遥测接收配置  读数据库，不读取connector.xml文件
 * @author mxc
 *
 */
public class ConnectorConfigLoadAlarm  implements Load{

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ConnectorConfigLoadAlarm.class);

	//收发地址
	public static String addressSql = "select * from SATELLITE_VIEW where status = 0";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void load(Map map) {
		
		if (log.isInfoEnabled()) {
			log.info("[com.xpoplarsoft.load.ConnectorConfigLoadAlarm]启动开始");
		}
		
		ConnectConfig config = ConnectConfig.getInstance();
		String startup ="true";
		//查询所有卫星的遥测收发端口
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(addressSql);
		String multicast_address = "";
		String addressType = "";
		String sat_id = "";
		String connectName = "";
		ConnectObj connectObj = null;
		for (int i = 0;dbr != null &&  i < dbr.getRows(); i++) {
			multicast_address = dbr.get(i, "multicast_address");
			addressType = dbr.get(i, "type");
			sat_id = dbr.get(i, "sat_id");
			if("1".equals(addressType)){
				//报警
				connectObj = new ConnectObj();
				connectName = sat_id+"_"+addressType+"_alarm";
				connectObj.setName(connectName);
				connectObj.setStartup(startup);
				connectObj.setClassImpl("com.bydz.fltp.connector.udp.UDPServer");
				connectObj.setParameter("broadcast-address", multicast_address);
				connectObj.setParameter("revicedata-length", "65535");
				connectObj.setParameter("executorPoolSize", "100");
				connectObj.setParameter("dispatcherClassImpl", "com.xpoplarsoft.alarm.dispatcher.DispatcherImpl");
				connectObj.setParameter("cachedClassImpl", "com.xpoplarsoft.alarm.dispatcher.AlarmDispatcherImpl");
				setConnectToCofig(connectName, startup, connectObj, config);
			}
		}
	}
	
	private void setConnectToCofig(String connectName,String startup,ConnectObj connectObj,ConnectConfig config){
		if(connectObj != null){
			if ("true".equalsIgnoreCase(startup)) {
				new Thread(new ConnectorServer(connectObj)).start();
			}
			config.setParameter(connectName, connectObj);
		}
	}
	
}

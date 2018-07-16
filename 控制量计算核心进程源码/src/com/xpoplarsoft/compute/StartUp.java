package com.xpoplarsoft.compute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.loadxml.LoadConfigFile;
import com.xpoplarsoft.loadxml.impl.LoadxmlLoad;

public class StartUp {
	
	private static Log log = LogFactory.getLog(StartUp.class);
	
	public static void main(String[] args) {
		if(log.isDebugEnabled()){
			log.debug("组件[StartUp]开始执行");
		}
		String proCode = "";
		int isMain = -1;
		String mainProCode = "";
//		args = new String[]{"5001","1","5000"}; //测试代码
		args = new String[]{"5000"}; //测试代码
		if (args != null && args.length > 0) {
			proCode = args[0];
			if(log.isDebugEnabled()){
				log.debug("控制量计算proCode=["+proCode+"]");
			}
			if (args.length > 1) {
				isMain = Integer.valueOf(args[1]);
			}
			if (args.length > 2) {
				mainProCode = args[2];
			}
		}
		
		//加载配置文件
		LoadConfigFile.loadFile();
		// 加载load.xml
		LoadxmlLoad.load();
		
		ComputeProcess cp = new ComputeProcess(proCode, isMain, mainProCode);
		cp.init(isMain);
		//发送进程注册
		/*ProcessJoin pj = new ProcessJoin(cp.getProcessBean());
		cp.sendProcessOrder(pj.createJoinMsg());*/
		//启动心跳发生器，发送心跳
		cp.sendHeartbeat();
		//启动报文接收
		cp.receiveProcessMessage();
		//启动报文处理轮询器
		cp.executeProcessOrder();
		
		if (isMain == 1) {
			// 备用进程启动监视主进程心跳
			new ProcessHeartbeatTask().start();
		}
		
		if(log.isDebugEnabled()){
			log.debug("组件[StartUp]执行完成");
		}
	}
}

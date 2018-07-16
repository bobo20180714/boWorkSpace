package com.xpoplarsoft.monitor.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.ProcessCoreAbstract;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.core.heartbeat.HeartbeatTask;

/**
 * 《进程监视系统》进程对象
 * @author mengxiangchao
 *
 */
public class MonitorProcess extends ProcessCoreAbstract {

	private static MonitorProcess processObj = null;
	
	private static Log log = LogFactory.getLog(MonitorProcess.class);
	
	public static MonitorProcess getInstance(){
		if(processObj == null){
			processObj = new MonitorProcess();
		}
		return processObj;
	}
	
	@Override
	public void init() {
		if (log.isDebugEnabled()) {
			log.debug("[MonitorProcess]启动开始！");
		}
		super.init();
		
		ProcessBean pro = MonitorProcess.getInstance().getProcessBean();
		ProcessObjectesControl.remove(pro.getId());
		if(pro != null){
			pro.setState("1");
			pro.setId(pro.getCode());
		}
		// 将初始化的本地进程存入缓存中
		ProcessObjectesControl.putLocalProcess(pro);
		
	}
	
	@Override
	public void sendOperatLog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendOperatData() {
		// TODO Auto-generated method stub
		
	}

	public void startup(){
		//初始化本进程信息
		init();
		//启动报文接收
		receiveProcessMessage();
		//启动报文处理轮询器
		executeProcessOrder();
		//启动心跳接收判断轮询器
		new HeartbeatTask().start();
	}
}

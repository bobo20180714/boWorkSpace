package com.xpoplarsoft.compute.orderManage.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.ProcessCoreAbstract;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 日志管理进程对象
 * @author mengxiangchao
 *
 */
public class OrderProcess extends ProcessCoreAbstract {

	private static OrderProcess processObj = null;

	private static Log log = LogFactory.getLog(OrderProcess.class);
	
	public static OrderProcess getInstance(){
		if(processObj == null){
			processObj = new OrderProcess();
		}
		return processObj;
	}
	
	@Override
	public void init() {
		if (log.isDebugEnabled()) {
			log.debug("[OrderProcess]启动开始！");
		}
		super.init();
		
		ProcessBean pro = OrderProcess.getInstance().getProcessBean();
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

	@Override
	public void receiveHeartbeat(ProcessData processData) {
		// TODO Auto-generated method stub
		
	}

}

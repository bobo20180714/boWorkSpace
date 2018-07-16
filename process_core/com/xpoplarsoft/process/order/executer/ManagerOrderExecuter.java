package com.xpoplarsoft.process.order.executer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.order.ExecuterFactory;
import com.xpoplarsoft.process.order.IExectuer;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessOrderBody;

/**
 * 进程调度指令处理器，根据指令代码获取对应的指令执行类对象
 * @author zhouxignlu
 * 2017年3月6日
 */
public class ManagerOrderExecuter implements IExectuer {
	private static Log log = LogFactory.getLog(ManagerOrderExecuter.class);
	@Override
	public void execute(ProcessData msg) {
		
		ProcessOrderBody order = (ProcessOrderBody) msg.getBody();
		int orderType = order.getOrderCode();
		if(log.isInfoEnabled()){
			log.info("进程调度指令["+orderType+"]开始执行！");
		}
		IExectuer executer = ExecuterFactory.create(orderType);
		executer.execute(msg);
		new Thread(executer).start();
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

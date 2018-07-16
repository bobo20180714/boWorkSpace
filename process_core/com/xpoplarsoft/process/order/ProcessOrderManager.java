package com.xpoplarsoft.process.order;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.core.control.ProcessMessageControl;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 进程调度报文处理程序，接收输入的待处理报文，按照业务功能分别调用处理程序。 根据报文类型获取已加载的处理类对象，调用处理方法。
 * 
 * @author zhouxignlu 2017年2月28日
 */
public class ProcessOrderManager implements Runnable {
	private static Log log = LogFactory.getLog(ProcessOrderManager.class);

	ProcessBean proBean = null;
	IProcessCore pro = null;
	long waitTime = 1000;

	public ProcessOrderManager(ProcessBean proBean, IProcessCore pro) {
		this.pro = pro;
		this.proBean = proBean;
		this.waitTime = proBean.getReedbackInterval();
	}

	// 获取待处理报文
	public void run() {
		while (true) {
			if (log.isDebugEnabled()) {
				log.debug("开始处理调度、业务报文。");
			}
			Collection<ProcessData> msg = ProcessMessageControl.removeAllReceiveMsg();
			IExectuer executer = null;
			for (ProcessData proMsg : msg) {
				int type = proMsg.getHead().getType();
				// 根据报文业务类型获取处理类对象
				if (type != 1) {
					executer = ExecuterFactory.create(type);
					executer.execute(proMsg);
					new Thread(executer).start();
				}
			}
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				log.error("调度、业务报文处理线程异常中断！", e);
				e.printStackTrace();
			}
		}
	}
}

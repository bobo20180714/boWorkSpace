package com.xpoplarsoft.process.order.executer;

import com.xpoplarsoft.process.order.IExectuer;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;
import com.xpoplarsoft.service.ServiceExecuterFactory;

/**
 * 业务报文处理器，根据业务类型代码获取对应的具体处理代码
 * 
 * @author zhouxignlu 2017年3月7日
 */
public class ManagerServiceExecuter implements IExectuer {

	@Override
	public void execute(ProcessData msg) {
		ProcessServiceBody bodyData = (ProcessServiceBody) msg.getBody();
		String serviceCode = bodyData.getServiceCode();
		IServiceExecuter obj = ServiceExecuterFactory.create(serviceCode);
		if (obj != null) {
			obj.setProcessServiceBody(msg);
			if (obj.isThread()) {
				new Thread(obj).start();
			} else {
				obj.execute();
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

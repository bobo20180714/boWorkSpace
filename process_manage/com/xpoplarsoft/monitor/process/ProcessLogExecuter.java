package com.xpoplarsoft.monitor.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.monitor.bean.TableBean;
import com.xpoplarsoft.monitor.cache.ProcessLogCache;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 处理接收的日志
 * @author mengxiangchao
 *
 */
public class ProcessLogExecuter implements IServiceExecuter{


	private static Log log = LogFactory.getLog(ProcessLogExecuter.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProcessServiceBody(ProcessData data) {
		//获取日志内容
		ProcessServiceBody bodyData = (ProcessServiceBody) data.getBody();
		String content = bodyData.getContent();
		if(content == null || "".equals(content)){
			if(log.isErrorEnabled()){
				log.equals("接收到的指令内容为空！");
			}
			return;
		}

		TableBean tableBean = AnalysisContentUtil.getTableBean(content);
		String processCode = String.valueOf(data.getHead().getSource());
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessLogExecuter]中processCode=["+processCode+"]");
		}
		//将日志信息放入内存中
		long putTime = System.currentTimeMillis();
		ProcessLogCache.getInstance().putLog(putTime,processCode, tableBean);
	}

	@Override
	public void setIsThread(boolean isThread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isThread() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}

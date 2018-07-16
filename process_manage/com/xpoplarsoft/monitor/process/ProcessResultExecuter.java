package com.xpoplarsoft.monitor.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.monitor.bean.TableBean;
import com.xpoplarsoft.monitor.cache.ProcessResultCache;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 处理接收到的输出结果
 * @author mengxiangchao
 *
 */
public class ProcessResultExecuter implements IServiceExecuter{


	private static Log log = LogFactory.getLog(ProcessResultExecuter.class);
	
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
		//获取输出结果
		ProcessServiceBody bodyData = (ProcessServiceBody) data.getBody();
		String content = bodyData.getContent();
		if(content == null || "".equals(content)){
			if(log.isErrorEnabled()){
				log.equals("接收到的输出结果为空！");
			}
			return;
		}

		TableBean tableBean = AnalysisContentUtil.getTableBean(content);
		String processCode = String.valueOf(data.getHead().getSource());
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessResultExecuter]中processCode=["+processCode+"]");
		}
		//将输出结果放入内存中
		ProcessResultCache.getInstance().putResult(processCode, tableBean);
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

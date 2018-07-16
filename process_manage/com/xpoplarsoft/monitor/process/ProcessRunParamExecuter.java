package com.xpoplarsoft.monitor.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.monitor.bean.TableBean;
import com.xpoplarsoft.monitor.cache.ProcessRunParamCache;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 处理接收的运行参数
 * @author mengxiangchao
 *
 */
public class ProcessRunParamExecuter implements IServiceExecuter{


	private static Log log = LogFactory.getLog(ProcessRunParamExecuter.class);
	
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
		//获取运行参数
		ProcessServiceBody bodyData = (ProcessServiceBody) data.getBody();
		String content = bodyData.getContent();
		if(content == null || "".equals(content)){
			if(log.isErrorEnabled()){
				log.equals("接收到的运行参数为空！");
			}
			return;
		}

		TableBean tableBean = AnalysisContentUtil.getTableBean(content);
		String processCode = String.valueOf(data.getHead().getSource());
		if(log.isDebugEnabled()){
			log.debug("组件[ProcessRunParamExecuter]中processCode=["+processCode+"]");
		}
		//将运行参数信息放入内存中
		ProcessRunParamCache.getInstance().putRunParam(processCode, tableBean);
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

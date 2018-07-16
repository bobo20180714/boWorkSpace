/**
 * com.xpoplarsoft.service.client
 */
package com.xpoplarsoft.service.client;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.common.util.JsonToMap;
import com.xpoplarsoft.process.order.IExectuer;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessOrderBody;

/**
 * 服务器代理停止业务进程处理类
 * @author zhouxignlu
 * 2017年3月28日
 */
public class DestroyProcess implements IExectuer {
	private static Log log = LogFactory.getLog(DestroyProcess.class);
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.process.order.IExectuer#execute(com.xpoplarsoft.process.pack.ProcessData)
	 */
	@Override
	public void execute(ProcessData msg) {
		if (log.isDebugEnabled()) {
			log.debug("进程停止业务处理开始执行！");
		}
		ProcessOrderBody body = (ProcessOrderBody) msg.getBody();
		if (body.getLength() <= 0 || "".equals(body.getContent())) {
			if (log.isDebugEnabled()) {
				log.debug("进程停止业务报文的报文内容为空。");
			}
			return;
		}
		String json = body.getContent();
		if (json.indexOf("processCode") < 0) {
			if (log.isDebugEnabled()) {
				log.debug("进程停止业务报文的报文内容错误，报文内容为："+json);
			}
			return;
		}
		try {
			Map<String, Object> shutdown = JsonToMap.jsonToMap(json);
			String proCode = shutdown.get("processName").toString();
			String command = "shutdownProcess.bat "+proCode;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		}catch(Exception e){
			log.error("业务进程启动异常，报文格式有误：" + json, e);
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

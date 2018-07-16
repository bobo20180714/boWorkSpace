/**
 * com.xpoplarsoft.service.client.log
 */
package com.xpoplarsoft.service.client.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.GetProcessCoreObj;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessServiceBody;


/**
 * 进程调度客户端日志管理实现类
 * @author zhouxignlu
 * 2017年5月2日
 */
public class ProcessLog implements IProcessLog {
	private static Log log = LogFactory.getLog(ProcessLog.class);
	private IProcessCore obj = GetProcessCoreObj.getProcessCore();
	private ProcessBean pro = obj.getProcessBean();
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.service.client.IProcessLog#log(java.lang.String, java.lang.String)
	 */
	@Override
	public void log(String logstr) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String time = sp.format(new Date());
		send(pro.getCode(),time,logstr);
		log.info(logstr);
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.service.client.IProcessLog#err(java.lang.String, java.lang.String)
	 */
	@Override
	public void err(String errstr) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String time = sp.format(new Date());
		send(pro.getCode(),time,errstr);
		log.info(errstr);
	}

	private void send(String code, String time, String content){
		ProcessData data = new ProcessData();
		ProcessHead head = new ProcessHead();
		head.setSource(Integer.parseInt(obj.getProcessBean().getCode()));
		head.setTarget(Integer.parseInt(obj.getProcessBean().getTargetId()));
		head.setDateTime(time);
		head.setType(4);
		data.setHead(head);
		ProcessServiceBody body = new ProcessServiceBody();
		//进程调度日志类型
		body.setServiceCode("proLog");
		StringBuilder sb = new StringBuilder();
		sb.append("{\"time\":");
		body.setContent("\""+time+"\"");
		sb.append(",\"proCode\":");
		body.setContent("\""+code+"\"");
		sb.append(",\"content\":");
		body.setContent("\""+content+"\"");
		sb.append("}");
		data.setBody(body);
		obj.sendProcessOrder(data);
	}
}

/**
 * com.xpoplarsoft.compute.log
 */
package com.xpoplarsoft.compute.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xpoplarsoft.compute.db.DBParameter;
import com.xpoplarsoft.compute.db.DBTools;
import com.xpoplarsoft.process.core.GetProcessCoreObj;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessServiceBody;

/**
 * 订单计算组件日志、异常记录处理实现类
 * 日志、异常存库，并发送到网络中
 * @author zhouxignlu
 * 2017年4月28日
 */
public class ComputeLog implements IComputeLog {
	private IProcessCore obj = GetProcessCoreObj.getProcessCore();
	private String dbSource = "compute";
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.log.IComputeLog#log(java.lang.String, java.lang.String)
	 */
	@Override
	public void log(String order, String logstr) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String time = sp.format(new Date());
		send(order,time,logstr);
		save(order,time,logstr,true);
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.log.IComputeLog#err(java.lang.String, java.lang.String)
	 */
	@Override
	public void err(String order, String errstr) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String time = sp.format(new Date());
		send(order,time,errstr);
		save(order,time,errstr,false);
	}
	
	private void send(String order, String time, String content){
		ProcessData data = new ProcessData();
		ProcessHead head = new ProcessHead();
		head.setSource(Integer.parseInt(obj.getProcessBean().getCode()));
		head.setTarget(Integer.parseInt(obj.getProcessBean().getTargetId()));
		head.setDateTime(time);
		head.setType(4);
		data.setHead(head);
		ProcessServiceBody body = new ProcessServiceBody();
		body.setServiceCode("orderLog");
		StringBuilder sb = new StringBuilder();
		sb.append("{\"time\":");
		sb.append("\""+time+"\"");
		sb.append(",\"orderCode\":");
		sb.append("\""+order+"\"");
		sb.append(",\"content\":");
		sb.append("\""+content+"\"");
		sb.append("}");
		body.setContent(sb.toString());
		data.setBody(body);
		obj.sendProcessOrder(data);
	}
	
	private void save(String order, String time, String content, boolean isLog){
		String sql = "insert into orderLog(orderid,time,log_msg,err_msg) values(?,?,?,?)";
		DBParameter para = new DBParameter();
		para.setObject("order", order);
		para.setObject("time", time);
		if(isLog){
			para.setObject("log_msg", content);
			para.setObject("err_msg", "");
		}else{
			para.setObject("log_msg", "");
			para.setObject("err_msg", content);
		}
		DBTools.update(dbSource, sql, para);
	}
}

package com.xpoplarsoft.process.core.heartbeat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.adapter.AdapterFactory;
import com.bydz.fltp.connector.adapter.IUDPAdapter;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessHeartbeatBody;
import com.xpoplarsoft.process.pack.ProcessPack;

/**
 * 心跳发生器
 * @author zhouxignlu
 * 2017年2月14日
 */
public class Heartbeat extends Thread {
	private static Log log = LogFactory.getLog(Heartbeat.class);
	private ProcessBean pro;
	
	public Heartbeat(ProcessBean pro){
		this.pro = pro;
	}
	
	public void run(){
		while(true){
			try {
				pro = ProcessObjectesControl.getLocalProcess();
				byte[] send = createPack();
				this.send(send);
				Heartbeat.sleep(Long.parseLong(pro.getRound()));
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void send(byte[] send){
		// 添加 通信头
		byte[] sendData = ConnectorTools.addProtocolHead(3, send);
		IUDPAdapter adapter = (IUDPAdapter) AdapterFactory.getFactory()
				.getAdapterComponent(pro.getAdapterName());
		adapter.multicastSend(sendData);
		if(log.isDebugEnabled()){
			log.debug("发送的16进制数据为[" + DataTools.bytesToHesString(sendData) + "]");
		}
	}
	
	private byte[] createPack(){
		ProcessData processData = new ProcessData();
		//包体
		ProcessHeartbeatBody body = new ProcessHeartbeatBody();
		//报文代码
		String times = String.valueOf(System.currentTimeMillis());
		int msgId = Integer.valueOf(times.substring(times.length()-9));
		body.setMessageId(msgId);
		
		//航天器任务号
		//body.setSatNum(Integer.parseInt(pro.getSat_num()));
		//进程状态
		int state = Integer.parseInt(pro.getState());
		body.setState(state);
		//异常信息长度
		body.setLength(0);
		//组装附加信息
		StringBuilder sb = new StringBuilder();
		sb.append("<content>");
		//异常信息
		if(state == 4 || state == 5){
			sb.append("<exception><![CDATA[");
			sb.append(pro.getErrMessage());
			sb.append("]]></exception>");
		}
		//主、备信息
		if(pro.getIsMain()==0){
			sb.append("<mainService>0</mainService>");
		}else if(pro.getIsMain()==1){
			sb.append("<mainService>1</mainService>");
			sb.append("<processCode>");
			sb.append(pro.getMainProcessCode());
			sb.append("</processCode>");
		}
		sb.append("</content>");
		body.setContent(sb.toString());
		processData.setBody(body);
		//包头
		ProcessHead head = new ProcessHead();		
		//报文业务类型
		head.setType(1);
		//是否需要反馈
		head.setNeedReedback(0);
		//信源
		head.setSource(Integer.parseInt(pro.getCode()));
		//信宿
		head.setTarget(Integer.parseInt(pro.getTargetId()));
		processData.setHead(head);
		if(log.isDebugEnabled()){
			log.debug("发送心跳信息为["+processData+"]");
		}
		IPack pack = new ProcessPack();
		byte[] send = pack.pack(processData);
		return send;
	}
	
}

/**
 * com.xpoplarsoft.monitor.process
 */
package com.xpoplarsoft.monitor.process;

import net.sf.json.JSONObject;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.GetProcessCoreObj;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 进程调度管理器处理注册进程业务
 * @author zhouxignlu
 * 2017年3月21日
 */
public class ProcessJoinExecuter implements IServiceExecuter {

	private ProcessServiceBody msg;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.service.IServiceExecuter#execute()
	 */
	/**
	 * 解析进程注册报文，获取待注册进程信息
	 * 轮询全部已注册进程，生成最新的进程标识
	 * 为新注册的进程对象赋予进程标识
	 * 将新的进程对象放入进程缓存中
	 * 发送报文，将进程标识发送给请求进程
	 */
	@Override
	public void execute() {
		//解析进程注册报文，获取待注册进程信息
		JSONObject jsonObject = JSONObject.fromObject(msg.getContent());
		String oldCode = jsonObject.get("processCode").toString();
		String processType = jsonObject.get("processType").toString();
		String processName = jsonObject.get("processName").toString();
		String satMid = jsonObject.get("satMid").toString();
		//轮询全部已注册进程，生成最新的进程标识
		String newCode = ProcessCodeCreater.createCode(processType);
		
		if(ProcessObjectesControl.getProcess(oldCode) != null){
			ProcessObjectesControl.remove(oldCode);
		}
		//为新注册的进程对象赋予进程标识
		ProcessBean pro = new ProcessBean();
		pro.setCode(newCode);
		pro.setId(newCode);
		pro.setType(processType);
		pro.setClass_name(processName);
		pro.setSat_num(satMid);
		//将新的进程对象放入进程缓存中
		ProcessObjectesControl.putProcess(pro);
		
		//发送报文，将进程标识发送给请求进程
		IProcessCore proCore =GetProcessCoreObj.getProcessCore();
		ProcessData sendMsg = new ProcessData();
		ProcessHead head = new ProcessHead();
		head.setNeedReedback(0);
		head.setSource(Integer.parseInt(proCore.getProcessBean().getCode()));
		head.setTarget(Integer.parseInt(oldCode));
		head.setType(4);
		sendMsg.setHead(head);
		
		ProcessServiceBody sendBody = new ProcessServiceBody();
		sendBody.setType(4);
		sendBody.setServiceCode("joinin");
		sendBody.setContent("{\"processCode\":\""+newCode+"\"}");
		sendMsg.setBody(sendBody);
		proCore.sendProcessOrder(sendMsg);
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.service.IServiceExecuter#setIsThread(boolean)
	 */
	@Override
	public void setIsThread(boolean isThread) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.service.IServiceExecuter#isThread()
	 */
	@Override
	public boolean isThread() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setProcessServiceBody(ProcessData msgData) {
		this.msg = (ProcessServiceBody) msgData.getBody();
	}

}

/**
 * com.xpoplarsoft.service
 */
package com.xpoplarsoft.service.executer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessServiceBody;
import com.xpoplarsoft.service.IServiceExecuter;

/**
 * 接收到进程标识发送报文，更新本进程的进程标识
 * 
 * @author zhouxignlu 2017年3月21日
 */
public class ProcessJoinGet implements IServiceExecuter {
	private static Log log = LogFactory.getLog(ProcessJoinGet.class);
	ProcessData msgData;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.service.IServiceExecuter#execute()
	 */
	@Override
	public void execute() {
		try {
			JSONObject json = new JSONObject(((ProcessServiceBody) msgData.getBody()).getContent());
			String oldCode = json.get("oldCode").toString();
			String processCode = json.get("processCode").toString();
			ProcessBean pro = ProcessObjectesControl.getProcess(oldCode);
			ProcessObjectesControl.remove(oldCode);
			pro.setCode(processCode);
			pro.setId(processCode);
			ProcessObjectesControl.putProcess(pro);
		} catch (JSONException e) {
			log.error("进程标识发送报文解析异常："+e);
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.service.IServiceExecuter#setProcessServiceBody(com.
	 * xpoplarsoft.process.pack.ProcessServiceBody)
	 */
	@Override
	public void setProcessServiceBody(ProcessData msgData) {
		this.msgData = msgData;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.service.IServiceExecuter#setIsThread(boolean)
	 */
	@Override
	public void setIsThread(boolean isThread) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpoplarsoft.service.IServiceExecuter#isThread()
	 */
	@Override
	public boolean isThread() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws JSONException {
		JSONObject j = new JSONObject("{1:1,2:2}");
	
		System.out.println(j.get("1"));
	}
}

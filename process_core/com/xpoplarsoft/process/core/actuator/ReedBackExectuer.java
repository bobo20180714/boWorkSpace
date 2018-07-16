package com.xpoplarsoft.process.core.actuator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.core.control.ProcessMessageControl;
import com.xpoplarsoft.process.order.IExectuer;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessReedbackBody;

/**
 * 反馈报文处理器
 * @author zhouxignlu
 * 2017年3月3日
 */
public class ReedBackExectuer implements IExectuer {
	private static Log log = LogFactory.getLog(ReedBackExectuer.class);
	
	public void execute(ProcessData msg){
		ProcessReedbackBody reedback = (ProcessReedbackBody)msg.getBody();
		int rbMsgId = reedback.getRbMessageId();
		ProcessData rbMsg = ProcessMessageControl.getSendMsg(rbMsgId);
		if(rbMsg != null){
			rbMsg.getBody().setHasReedBack(true);
			ProcessMessageControl.putSendMsg(rbMsg);
		}else{
			log.error("处理反馈报文----"+reedback.toString()+"未发现原报文！");
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

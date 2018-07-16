/**
 * com.xpoplarsoft.process.core.join
 */
package com.xpoplarsoft.process.core.join;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessHead;
import com.xpoplarsoft.process.pack.ProcessServiceBody;

/**
 * @author zhouxignlu 2017年3月21日
 */
public class ProcessJoin {
	private ProcessBean pro;

	public ProcessJoin(ProcessBean pro) {
		this.pro = pro;
	}

	public ProcessData createJoinMsg() {
		ProcessData sendMsg = new ProcessData();
		ProcessHead head = new ProcessHead();
		head.setNeedReedback(0);
		head.setSource(Integer.parseInt(pro.getCode()));
		head.setTarget(Integer.parseInt(pro.getTargetId()));
		head.setType(4);
		sendMsg.setHead(head);

		ProcessServiceBody sendBody = new ProcessServiceBody();
		sendBody.setType(4);
		sendBody.setServiceCode("joinin");
		sendBody.setContent("{\"processCode\":\"" + pro.getCode()
				+ "\", \"processType\":\"" + pro.getType()
				+ "\", \"processName\":\"" + pro.getClass_name()
				+ "\",\"satMid\":\"" + pro.getSat_num() + "\"}");
		sendMsg.setBody(sendBody);
		return sendMsg;
	}
}

package com.xpoplarsoft.process.start;

import com.xpoplarsoft.process.core.ProcessCoreAbstract;
import com.xpoplarsoft.process.pack.ProcessData;

/**
 * 空闲进程
 * @author zhouxignlu
 * 2017年3月7日
 */
public class BlankProcess extends ProcessCoreAbstract {

	@Override
	public void sendOperatLog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendOperatData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveHeartbeat(ProcessData processData) {
		System.out.println("接收到的心跳报文："+processData);
	}

}

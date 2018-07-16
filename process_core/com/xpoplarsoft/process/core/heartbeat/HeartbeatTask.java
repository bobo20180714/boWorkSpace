/**
 * com.xpoplarsoft.process.core.heartbeat
 */
package com.xpoplarsoft.process.core.heartbeat;

import java.util.Collection;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.IProcessCore;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;

/**
 * 心跳信息接收超时判断轮询器，发现有进程对象的心跳信息长时间未接受，则更新进程状态为连接断开--6
 * 该类提供一个进程心跳中断后的业务处理调用，继承本类后重写afterBreakOfHeart方法。
 * 业务进程分为主进程和备用进程，主业务进程无需监控心跳信息，备用业务进程监控主业务进程。
 * 属性istrue为心跳监视开关，主业务进程初始为false，备用业务进程初始为true。
 * @author zhouxignlu
 * 2017年3月30日
 */
public class HeartbeatTask extends Thread {
	protected boolean istrue = true;
	public void run(){
		ProcessBean localBean = ProcessObjectesControl.getLocalProcess();
		String proCode = localBean.getCode();
		while(istrue){
			Collection<ProcessBean> beanList = ProcessObjectesControl.getProcessList();
			if(beanList == null){
				continue;
			}
			Object[] beanArr = beanList.toArray();
			ProcessBean processBean = null;
			for (int i = 0; i < beanArr.length; i++) {
				processBean = (ProcessBean) beanArr[i];
				if(proCode.equals(processBean.getCode()) || "0".equals(processBean.getState())){
					continue;
				}
				long lastTime = processBean.getLife_time();
				long nowTime = System.currentTimeMillis();
				if(nowTime - lastTime >=  IProcessCore.HEART_LIMITTIME){
					//修改状态为连接中断
					processBean.setState("6");
					afterBreakOfHeart(localBean, processBean);
				}
			}
		}
	}
	
	/**
	 * 被监视进程心跳中断后业务处理方法，由各业务进程核心继承实现
	 */
	public void afterBreakOfHeart(ProcessBean localBean, ProcessBean breakBean){
		
	}
}

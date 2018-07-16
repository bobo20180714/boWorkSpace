/**
 * com.xpoplarsoft.process.alarm
 */
package com.xpoplarsoft.compute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.core.heartbeat.HeartbeatTask;

/**
 * 控制量计算进程，主业务进程心跳中断处理类
 * @author zhouxignlu
 * 2017年3月30日
 */
public class ProcessHeartbeatTask extends HeartbeatTask {
	

	private static Log log = LogFactory.getLog(ProcessHeartbeatTask.class);
	
	public ProcessHeartbeatTask(){
		this.istrue = true;
	}
	
	public ProcessHeartbeatTask(boolean istrue){
		this.istrue = istrue;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.process.core.heartbeat.HeartbeatTask#afterBreakOfHeart()
	 */
	@Override
	public void afterBreakOfHeart(ProcessBean localBean, ProcessBean breakBean) {
		int isMain = localBean.getIsMain();
		if(log.isDebugEnabled()){
			log.debug("主进程中断，备用进程执行！isMain=【"+isMain+"】;"
					+ "localBean.getMainProcessCode()=【"+localBean.getMainProcessCode()+"】"
					+ "breakBean.getCode()=["+breakBean.getCode()+"];");
		}
		//本地进程为备用进程，且主进程的心跳中断
		if(isMain == 1){
			if(localBean.getMainProcessCode().equals(breakBean.getCode())){
				//更新本地进程为主进程，去除原监视的主进程
				localBean.setIsMain(0);
				ProcessObjectesControl.remove(localBean.getId());
				ProcessObjectesControl.remove(breakBean.getId());
				ProcessObjectesControl.putLocalProcess(localBean);
				
				//启动订单处理
				OrderServiceExecute orderServiceExecute =new OrderServiceExecute();
				orderServiceExecute.start();
				
				// 作为主进程，停止进程心跳监视
				this.istrue = false;
			}
		}
	}
}

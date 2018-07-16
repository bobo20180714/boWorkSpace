/**
 * com.xpoplarsoft.process.alarm
 */
package com.xpoplarsoft.process.alarm;

import java.util.HashMap;

import com.xpoplarsoft.alarm.result.AlarmResultLoad;
import com.xpoplarsoft.loadxml.impl.LoadxmlLoad;
import com.xpoplarsoft.process.bean.ProcessBean;
import com.xpoplarsoft.process.core.control.ProcessObjectesControl;
import com.xpoplarsoft.process.core.heartbeat.HeartbeatTask;

/**
 * 门限判断进程，主业务进程心跳中断处理类
 * @author zhouxignlu
 * 2017年3月30日
 */
public class AlarmHeartbeatTask extends HeartbeatTask {
	public AlarmHeartbeatTask(){
		this.istrue = true;
	}
	
	public AlarmHeartbeatTask(boolean istrue){
		this.istrue = istrue;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.process.core.heartbeat.HeartbeatTask#afterBreakOfHeart()
	 */
	@Override
	public void afterBreakOfHeart(ProcessBean localBean, ProcessBean breakBean) {
		int isMain = localBean.getIsMain();
		//本地进程为备用进程，且主进程的心跳中断
		if(isMain == 1){
			if(localBean.getMainProcessCode().equals(breakBean.getCode())){
				//更新本地进程为主进程，去除原监视的主进程
				localBean.setIsMain(0);
				ProcessObjectesControl.remove(localBean.getId());
				ProcessObjectesControl.remove(breakBean.getId());
				ProcessObjectesControl.putLocalProcess(localBean);
				
				// 加载load.xml
				LoadxmlLoad.load();
				AlarmResultLoad alarmLoad = new AlarmResultLoad();
				alarmLoad.load(new HashMap<Object, Object>());
				
				// 作为主进程，停止进程心跳监视
				this.istrue = false;
			}
		}
	}
}

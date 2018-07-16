package com.jianshi.task;

import com.jianshi.cache.NetLinkCache;
import com.xpoplarsoft.framework.task.AbstractTask;
import com.xpoplarsoft.framework.task.exception.TaskException;

public class NetLinkTask extends AbstractTask{

	@Override
	public void run() throws TaskException {
		//判读链路监视信息是否正常
		NetLinkCache.getInstance().judgeIsOverTime();
	}
	
}

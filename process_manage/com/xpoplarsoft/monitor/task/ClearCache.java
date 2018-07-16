package com.xpoplarsoft.monitor.task;

import com.xpoplarsoft.framework.task.AbstractTask;
import com.xpoplarsoft.framework.task.exception.TaskException;
import com.xpoplarsoft.monitor.cache.ProcessLogCache;

public class ClearCache extends AbstractTask{

	@Override
	public void run() throws TaskException {
		//清除日志缓存
		ProcessLogCache.getInstance().removeCahce();
	}

}

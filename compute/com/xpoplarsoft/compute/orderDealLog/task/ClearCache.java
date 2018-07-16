package com.xpoplarsoft.compute.orderDealLog.task;

import com.xpoplarsoft.compute.orderDealLog.cache.OrderDealLogCache;
import com.xpoplarsoft.framework.task.AbstractTask;
import com.xpoplarsoft.framework.task.exception.TaskException;

public class ClearCache extends AbstractTask{

	@Override
	public void run() throws TaskException {
		//清除日志缓存
		OrderDealLogCache.getInstance().removeCahce();
	}

}

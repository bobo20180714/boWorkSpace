package com.jianshi.task;

import com.jianshi.cache.DevCache;
import com.jianshi.cache.SatCache;
import com.xpoplarsoft.framework.task.AbstractTask;
import com.xpoplarsoft.framework.task.exception.TaskException;


public class UpdateBaseDataTask extends AbstractTask {

	@Override
	public void run() throws TaskException {
		DevCache.getInstance().getAllDevFromDB();
		SatCache.getInstance().getAllSatFromDB();
	}

}

package com.jianshi.load;

import java.util.Map;

import com.jianshi.cache.DevCache;
import com.jianshi.cache.ParamCache;
import com.jianshi.cache.SatCache;
import com.xpoplarsoft.framework.load.Load;

public class BaseDataLaod implements Load {
	
	@Override
	public void load(Map map) {
		DevCache.getInstance().getAllDevFromDB();
		SatCache.getInstance().getAllSatFromDB();
		ParamCache.getInstance().getAllTmFromDB();
	}
	
}

package com.xpoplarsoft.monitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.cache.ProcessLogCache;
import com.xpoplarsoft.monitor.cache.ProcessResultCache;
import com.xpoplarsoft.monitor.cache.ProcessRunParamCache;
import com.xpoplarsoft.monitor.service.IRelationService;

@Service
public class RelationService implements IRelationService{

	@Override
	public List<ColumnBean> queryLogGridColumns(String processCode) {
		return ProcessLogCache.getInstance().getLogGridColumn(processCode);
	}
	
	@Override
	public List<Map<String,String>> processLogList(String processCode) {
		return ProcessLogCache.getInstance().getLogDataList(processCode);
	}
	
	@Override
	public List<Map<String, String>> queryRunParam(String processCode) {
		return ProcessRunParamCache.getInstance().getRunParamList(processCode);
	}
	
	@Override
	public List<Map<String, String>> queryResult(String processCode) {
		return ProcessResultCache.getInstance().getResultList(processCode);
	}
}

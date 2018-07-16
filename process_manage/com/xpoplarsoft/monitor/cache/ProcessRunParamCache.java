package com.xpoplarsoft.monitor.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.bean.TableBean;

/**
 * 运行参数缓存
 * @author mengxiangchao
 *
 */
public class ProcessRunParamCache {

	private static Map<String,TableBean> runParamMap = new HashMap<String, TableBean>();
	
	private static ProcessRunParamCache cacheObj = null;
	
	public static ProcessRunParamCache getInstance(){
		if(cacheObj == null){
			cacheObj = new ProcessRunParamCache();
		}
		return cacheObj;
	}
	
	public List<Map<String, String>> getRunParamList(String processCode){
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		TableBean bean = runParamMap.get(processCode);
		Map<String, String> rsMap = null;
		if(bean != null){
			List<ColumnBean> columnBeanList = bean.getColumnData();
			List<Map<String,String>> datalist = bean.getRowData();
			for (Map<String, String> dataMap : datalist) {
				for (String name : dataMap.keySet()) {
					rsMap = new HashMap<String, String>();
					for (ColumnBean columnBean : columnBeanList) {
						if(name.equals(columnBean.getName())){
							rsMap.put("text", columnBean.getDisplay());
							break;
						}
					}
					rsMap.put("value", dataMap.get(name));
					rsList.add(rsMap);
				}
			}
		}
		
		return rsList;
	}
	
	public void putRunParam(String processCode,TableBean bean){
		runParamMap.put(processCode, bean);
	}
	
}

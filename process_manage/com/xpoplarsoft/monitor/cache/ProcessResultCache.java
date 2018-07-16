package com.xpoplarsoft.monitor.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.bean.TableBean;

/**
 * 输出结果缓存
 * @author mengxiangchao
 *
 */
public class ProcessResultCache {

	private static Map<String,TableBean> resultMap = new HashMap<String, TableBean>();
	
	private static ProcessResultCache cacheObj = null;
	
	public static ProcessResultCache getInstance(){
		if(cacheObj == null){
			cacheObj = new ProcessResultCache();
		}
		return cacheObj;
	}
	
	public List<Map<String, String>> getResultList(String processCode){
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		TableBean bean = resultMap.get(processCode);
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
	
	public void putResult(String processCode,TableBean bean){
		resultMap.put(processCode, bean);
	}
	
}

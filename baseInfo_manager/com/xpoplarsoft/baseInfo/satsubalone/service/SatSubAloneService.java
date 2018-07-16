package com.xpoplarsoft.baseInfo.satsubalone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;
import com.xpoplarsoft.baseInfo.satsubalone.dao.ISatSubAloneDao;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class SatSubAloneService implements ISatSubAloneService{
	@Autowired
	private ISatSubAloneDao satSubAloneDao;
	@Override
	public List<Map<String, String>> findSatTree(String key) {
		//返回结果
		List<Map<String, String>> rsList = new ArrayList<Map<String,String>>();
		
		DBParameter param = new DBParameter();
		param.setObject("key", key==null?"":key);
		//执行查询
		DBResult result = satSubAloneDao.findSatTree(param);
		//处理结果
		if(result == null){
			return rsList;
		}
		//获取结果放入LIST
		Map<String,String> map = null;
		String[] colNames = result.getColName();
		for (int i=0;i<result.getRows();i++) {
			map = new HashMap<String, String>();
			for (int j=0;j<colNames.length;j++) {
				String colName = colNames[j];
				String value = result.getValue(i, colNames[j]);
				map.put(colName.toLowerCase(), value);
			}
			rsList.add(map);
		}
		return rsList;
	}
	
	public List<SatSubAlone> findGrantUserGroupEquipmentTree(String sys_resource_id,String key) {
		DBParameter param = new DBParameter();
		param.setObject("sys_resource_id", sys_resource_id);
		param.setObject("key", key);
		return satSubAloneDao.findGrantUserGroupEquipmentTree(param);
	}
}

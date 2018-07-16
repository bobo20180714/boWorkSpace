package com.jianshi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianshi.dao.IDynamicDao;
import com.jianshi.dao.IPlugDao;
import com.jianshi.service.IDynamicService;

@Service
public class DynamicService implements IDynamicService {
	
	@Autowired
	private IDynamicDao dynamicDao;
	@Autowired
	private IPlugDao plugDao;

	@Override
	public String getDynamic() {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String,Object>> data=dynamicDao.getDynamic();
		map.put("Rows", data);
		return JSONObject.toJSONString(map);
	}

	@Override
	public String addDynamic(String name, String comment, String icon,String uid) {
		try {
			return plugDao.addPlug(name,1,1,comment,uid,icon);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean editDynamic(String id, String name, String comment,String icon) {
		try {
			plugDao.editPlug(id,name,comment,icon);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delDynamic(String id) {
		try {
			plugDao.delPlug(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

}

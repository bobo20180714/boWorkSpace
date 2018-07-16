package com.jianshi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianshi.dao.IPlugDao;
import com.jianshi.service.IPlugService;

@Service
public class PlugService implements IPlugService {

	@Autowired
	private IPlugDao plugDao;
	
	@Override
	public String addPlug(String name, int type, String comment,String uid, String icon) {
		try {
			return plugDao.addPlug(name,type,0,comment,uid,icon);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getPlug(String uid,String key) {
		List<Map<String, Object>> data=plugDao.getPlug(uid,key);		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", data);
		return JSONObject.toJSONString(map);
	}

	@Override
	public String addStatic(String name, String comment, String type,
			String exp, String plug_id, String img) {
		try {
			return plugDao.addStatic(name,comment,type,exp,plug_id,img);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean delPlug(String id) {
		try {
			plugDao.delPlug(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String editStatic(String id, String name, String comment,
			String type, String exp, String img) {
		try {
			plugDao.editStatic(id,name,comment,type,exp,img);
			return id;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delGraph(String id) {
		try {
			plugDao.delGraph(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setComplete(String id) {
		try {
			plugDao.setComplete(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getStatic(String plugId) {
		return JSONArray.toJSONString(plugDao.getStaticData(plugId));
	}

	@Override
	public String editPlug(String id, String name, String comment,String icon) {
		try {
			plugDao.editPlug(id,name,comment,icon);
			return id;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getPlugByLibId(String libId) {
		List<Map<String, Object>> data=plugDao.getPlugByLibId(libId);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", data);
		return JSONObject.toJSONString(map);
	}

	@Override
	public String getSelectPlug(String libId) {
		List<Map<String, Object>> data=plugDao.getSelectPlug(libId);		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", data);
		return JSONObject.toJSONString(map);
	}	

}

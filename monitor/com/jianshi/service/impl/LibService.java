package com.jianshi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianshi.dao.ILibDao;
import com.jianshi.service.ILibService;

@Service
public class LibService implements ILibService {
	
	@Autowired
	private ILibDao libDao;

	@Override
	public String addLib(String name, String comment) {
		try {
			return libDao.addLib(name,comment);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getLib(String key) {		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", libDao.getLib(key));
		return JSONObject.toJSONString(map);
	}

	@Override
	public String editLib(String id, String name, String comment) {
		try {
			libDao.editLib(id,name,comment);
			return id;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean delLib(String id) {
		try {
			libDao.delLib(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addLibPlug(String values) {
		try {
			Map<String, String> map=new HashMap<String, String>();
			map.put("values", values);
			libDao.addLibPlug(map);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delLibPlug(String id) {
		try {
			libDao.delLibPlug(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getSelectLib(String proId) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows", libDao.getSelectLib(proId));
		return JSONObject.toJSONString(map);
	}

}

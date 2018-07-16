package com.jianshi.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.ILibDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class LibDao implements ILibDao {

	@Override
	public String addLib(String name, String comment) {
		DBParameter para = new DBParameter();
		String id=Common.getId("LIB_ID_SEQ");
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("comment", comment);
		SQLFactory.getSqlComponent().updateInfo("lib", "addLib", para);
		return id;
	}

	@Override
	public List<Map<String, Object>> getLib(String key) {
		DBParameter para = new DBParameter();
		para.setObject("key", key);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("lib","getLib", para);
		return Common.getMaps(result);
	}

	@Override
	public void editLib(String id, String name, String comment) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("comment", comment);
		SQLFactory.getSqlComponent().updateInfo("lib", "editLib", para);
	}

	@Override
	public void delLib(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("lib", "delLib", para);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addLibPlug(Map<String, String> map) {
		String vals=map.get("values");
		Matcher m = Pattern.compile("\\((.+?)\\)").matcher(vals);
	    while(m.find()){
	    	String val = m.group();
	    	SQLFactory.getSqlComponent().updateInfo("insert into lib_plug(lib_id,plug_id) values"+val);
	    }
	}

	@Override
	public void delLibPlug(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("lib", "delLibPlug", para);
	}

	@Override
	public List<Map<String, Object>> getSelectLib(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("lib","getSelectLib", para);
		return Common.getMaps(result);
	}

}

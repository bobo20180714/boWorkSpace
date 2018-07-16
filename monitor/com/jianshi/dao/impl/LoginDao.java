package com.jianshi.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.ILoginDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class LoginDao implements ILoginDao {

	@Override
	public int exist(String name) {
		DBParameter para = new DBParameter();
		para.setObject("name", name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("loginMonitor","exist", para);		
		return Integer.parseInt(Common.getObject(result).toString());
	}

	@Override
	public Map<String, Object> getUser(Map<String, String> map) {
		DBParameter para = new DBParameter();
		para.setObject("name", map.get("name"));
		para.setObject("password", map.get("password"));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("loginMonitor","getUser", para);		
		return Common.getMap(result);
	}

	@Override
	public String getUserPsd(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("loginMonitor","getUserPsd", para);
		return Common.getObject(result).toString();
	}

	@Override
	public void editPsd(String id, String newPsd) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		para.setObject("newPsd", newPsd);
		SQLFactory.getSqlComponent().updateInfo("loginMonitor", "editPsd", para);
	}

}

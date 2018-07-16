package com.jianshi.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.IProjectDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class ProjectDao implements IProjectDao {

	@Override
	public String addNode(String name, int own, String uid, String isleaf) {
		DBParameter para = new DBParameter();
		String id=Common.getId("SAT_TM_ID_SEQ");
		para.setObject("id", id);
		para.setObject("name", name);
		para.setObject("own", own);
		para.setObject("uid", uid);
		para.setObject("isleaf", Integer.parseInt(isleaf));
		SQLFactory.getSqlComponent().updateInfo("project", "addNode", para);
		return id;
	}

	@Override
	public List<Map<String, Object>> getProj(String uid) {
		DBParameter para = new DBParameter();
		para.setObject("uid", uid);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("project","getProj", para);
		return Common.getMaps(result);
	}
	
	@Override
	public List<Map<String, Object>> getAllProj() {
		DBParameter para = new DBParameter();
		DBResult result = SQLFactory.getSqlComponent().queryInfo("project","getAllProj", para);
		return Common.getMaps(result);
	}

	@Override
	public void editNode(String id, String name) {
		DBParameter para = new DBParameter();
		para.setObject("id", id);
		para.setObject("name", name);
		SQLFactory.getSqlComponent().updateInfo("project", "editNode", para);
	}

	@Override
	public void delNode(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", id);
		SQLFactory.getSqlComponent().updateInfo("project", "delNode", para);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addProjectLib(Map<String, String> map) {
		String vals=map.get("values");
		Matcher m = Pattern.compile("\\((.+?)\\)").matcher(vals);
	    while(m.find()){
	    	String val = m.group();
	    	SQLFactory.getSqlComponent().updateInfo("insert into proj_lib(proj_id,lib_id) values"+val);
	    }
	}

	@Override
	public List<Map<String, Object>> getProjLib(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("project","getProjLib", para);
		return Common.getMaps(result);
	}

	@Override
	public void delProjLib(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", id);
		SQLFactory.getSqlComponent().updateInfo("project", "delProjLib", para);
	}

	@Override
	public DBResult getFloadTree() {
		DBParameter para = new DBParameter();
		return SQLFactory.getSqlComponent().queryInfo("project","getFloadTree", para);
	}

	@Override
	public Map<String, Object> getProj(String uid,int owner, String name) {
		DBParameter para = new DBParameter();
		para.setObject("uid", uid);
		para.setObject("owner", owner);
		para.setObject("name", name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("project","getFileName", para);
		return Common.getMap(result);
		
	}

	@Override
	public Map<String, Object> getProj(String uid, int owner) {
		DBParameter para = new DBParameter();
		para.setObject("uid", uid);
		para.setObject("owner", owner);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("project","getNodeFile", para);
		return Common.getMap(result);
	}
}

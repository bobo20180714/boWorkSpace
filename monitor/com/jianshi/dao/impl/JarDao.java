package com.jianshi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.IJarDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class JarDao implements IJarDao {

	@Override
	public String registJar(String id, int type, String name, String ver,
			String desc) {
		DBParameter para = new DBParameter();
		String id1=Common.getId("JAR_ID_SEQ");
		para.setObject("id1", Integer.parseInt(id1));
		para.setObject("id", id);
		para.setObject("type", type);
		para.setObject("name", name);
		para.setObject("ver", ver);
		para.setObject("desc", desc);
		SQLFactory.getSqlComponent().updateInfo("jar", "registJar", para);
		return id1;
	}

	@Override
	public List<Map<String, Object>> getSelectJar(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getSelectJar", para);
		return Common.getMaps(result);
	}

	@Override
	public void addProjJar(String proId, String jarId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("jarId", Integer.parseInt(jarId));
		SQLFactory.getSqlComponent().updateInfo("jar", "addProjJar", para);
	}

	@Override
	public List<String> getSelectedJar(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getSelectedJar", para);
		List<String> jars=new ArrayList<String>();
		for(int i=0; i<result.getRows(); i++) {
			jars.add(result.getObject(i, "jarId").toString());
		}
		return jars;
	}

	@Override
	public DBResult getSelectedJarNew(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		return SQLFactory.getSqlComponent().queryInfo("jar","getSelectedJarNew", para);
	}

	@Override
	public void delJar(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("jar", "delJar", para);
	}

	@Override
	public void delProjJar(String proId, String jarId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("jarId", Integer.parseInt(jarId));
		SQLFactory.getSqlComponent().updateInfo("jar", "delProjJar", para);
	}

	@Override
	public Map<String, Object> getSingleJar(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getSingleJar", para);
		return Common.getMap(result);
	}

	@Override
	public void registParams(String jarId, String param_id, String name,
			String code) {
		DBParameter para = new DBParameter();
		para.setObject("jarId", Integer.parseInt(jarId));
		para.setObject("param_id", param_id);
		para.setObject("name", name);
		para.setObject("code", code);
		SQLFactory.getSqlComponent().updateInfo("jar", "registParams", para);
	}

	@Override
	public List<Map<String, Object>> getParams(String jarId) {
		DBParameter para = new DBParameter();
		para.setObject("jarId", Integer.parseInt(jarId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getParams", para);
		return Common.getMaps(result);
	}

	@Override
	public String getJarId(String devId, String dataSource) {
		DBParameter para = new DBParameter();
		para.setObject("devId", Integer.parseInt(devId));
		para.setObject("dataSource", Integer.parseInt(dataSource));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getJarId", para);
		return Common.getObject(result).toString();
	}

	@Override
	public List<Map<String, Object>> getParIds(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getAllParIds(String jarId) {
		DBParameter para = new DBParameter();
		para.setObject("jarId", Integer.parseInt(jarId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar","getAllParIds", para);
		return Common.getMaps(result);
	}

	@Override
	public String existJar(String id, int type) {
		DBParameter para = new DBParameter();
		para.setObject("id", id);
		para.setObject("type", type);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar", "existJar", para);
		return result.getTotal()>0?result.getObject(0, 0).toString():null;
	}

	@Override
	public void updateJar(String id, int type, String name, String ver,
			String desc) {
		DBParameter para = new DBParameter();
		para.setObject("id", id);
		para.setObject("type", type);
		para.setObject("name", name);
		para.setObject("ver", ver);
		para.setObject("desc", desc);
		SQLFactory.getSqlComponent().updateInfo("jar", "updateJar", para);
	}

	@Override
	public boolean existParams(String jarId, String param_id) {
		DBParameter para = new DBParameter();
		para.setObject("jarId", Integer.parseInt(jarId));
		para.setObject("param_id", param_id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("jar", "existParams", para);
		return Long.parseLong(result.getObject(0, 0).toString())>0;
	}

	@Override
	public void updateParams(String jarId, String param_id, String name,
			String code) {
		DBParameter para = new DBParameter();
		para.setObject("jarId", Integer.parseInt(jarId));
		para.setObject("param_id", param_id);
		para.setObject("name", name);
		para.setObject("code", code);
		SQLFactory.getSqlComponent().updateInfo("jar", "updateParams", para);
	}

}

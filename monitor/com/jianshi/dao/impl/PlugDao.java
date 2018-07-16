package com.jianshi.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.IPlugDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class PlugDao implements IPlugDao {

	@Override
	public String addPlug(String name, int type, int state, String comment,String uid, String icon) {
		DBParameter para = new DBParameter();
		String id=Common.getId("PLUG_ID_SEQ");
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("type", type);
		para.setObject("state", state);
		para.setObject("comment", comment);
		para.setObject("uid", uid);
		para.setObject("icon", icon);
		SQLFactory.getSqlComponent().updateInfo("plug", "addPlug", para);
		return id;
	}

	@Override
	public List<Map<String, Object>> getPlug(String uid, String key) {
		DBParameter para = new DBParameter();
		para.setObject("uid", uid);
		para.setObject("key", key);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("plug","getPlug", para);
		return Common.getMaps(result);
	}

	@Override
	public List<Map<String, Object>> getStaticData(String plugId) {
		DBParameter para = new DBParameter();
		para.setObject("plugId", Integer.parseInt(plugId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("plug","getStaticData", para);
		return Common.getMaps(result);
	}

	@Override
	public void delPlug(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("plug", "delPlug", para);
	}

	@Override
	public String addStatic(String name, String comment, String type, String exp,
			String plug_id, String img) {
		DBParameter para = new DBParameter();
		String id=Common.getId("STATIC_ID_SEQ");
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("comment", comment);
		para.setObject("type", type);
		para.setObject("exp", exp);
		para.setObject("plug_id", Integer.parseInt(plug_id));
		para.setObject("img", img);
		SQLFactory.getSqlComponent().updateInfo("plug", "addStatic", para);
		return id;
	}

	@Override
	public void editStatic(String id, String name, String comment, String type,
			String exp, String img) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("comment", comment);
		para.setObject("type", type);
		para.setObject("exp", exp);		
		para.setObject("img", img);
		SQLFactory.getSqlComponent().updateInfo("plug", "editStatic", para);
	}

	@Override
	public void delGraph(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("plug", "delGraph", para);
	}

	@Override
	public void setComplete(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("plug", "setComplete", para);
	}

	@Override
	public void editPlug(String id, String name, String comment,String icon) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		para.setObject("name", name);
		para.setObject("comment", comment);
		para.setObject("icon", icon);		
		SQLFactory.getSqlComponent().updateInfo("plug", "editPlug", para);
	}

	@Override
	public List<Map<String, Object>> getPlugByLibId(String libId) {
		DBParameter para = new DBParameter();
		para.setObject("libId", Integer.parseInt(libId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("plug","getPlugByLibId", para);
		return Common.getMaps(result);
	}

	@Override
	public List<Map<String, Object>> getSelectPlug(String libId) {
		DBParameter para = new DBParameter();
		para.setObject("libId", Integer.parseInt(libId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("plug","getSelectPlug", para);
		return Common.getMaps(result);
	}

}

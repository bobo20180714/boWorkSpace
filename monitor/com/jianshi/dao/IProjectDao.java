package com.jianshi.dao;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.db.DBResult;

public interface IProjectDao {

	String addNode(String name, int own,String uid,String isleaf);

	List<Map<String, Object>> getProj(String uid);
	Map<String, Object> getProj(String uid,int owner,String name);
	Map<String, Object> getProj(String uid,int owner);
	void editNode(String id, String name);

	void delNode(String id);

	void addProjectLib(Map<String, String> map);

	List<Map<String, Object>> getProjLib(String proId);

	void delProjLib(String id);

	List<Map<String, Object>> getAllProj();

	DBResult getFloadTree();

}

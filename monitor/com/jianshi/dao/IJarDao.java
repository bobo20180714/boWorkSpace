package com.jianshi.dao;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.db.DBResult;


public interface IJarDao {
	
	String registJar(String id,int type,String name,String ver,String desc);

	List<Map<String, Object>> getSelectJar(String proId);

	void addProjJar(String proId, String jarId);

	List<String> getSelectedJar(String proId);

	void delJar(String id);

	void delProjJar(String proId, String jarId);

	Map<String, Object> getSingleJar(String proId);

	void registParams(String jarId, String param_id, String name,
			String code);

	List<Map<String, Object>> getParams(String jarId);

	String getJarId(String devId, String dataSource);

	List<Map<String, Object>> getParIds(Map<String, String> map);

	List<Map<String, Object>> getAllParIds(String jarId);
	
	String existJar(String id,int type);
	
	void updateJar(String id,int type,String name,String ver,String desc);
	
	boolean existParams(String jarId, String param_id);
	
	void updateParams(String jarId, String param_id, String name,
			String code);

	DBResult getSelectedJarNew(String proId);
}

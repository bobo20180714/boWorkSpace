package com.jianshi.dao;

import java.util.List;
import java.util.Map;


public interface IPlugDao {

	String addPlug(String name, int type, int state, String comment,String uid, String icon);

	List<Map<String,Object>> getPlug(String uid,String key);
	
	List<Map<String, Object>> getStaticData(String plugId);
	
	void delPlug(String id);

	String addStatic(String name, String comment, String type, String exp,
			String plug_id, String img);

	void editStatic(String id, String name, String comment, String type,
			String exp, String img);

	void delGraph(String id);

	void setComplete(String id);

	void editPlug(String id, String name, String comment, String icon);

	List<Map<String, Object>> getPlugByLibId(String libId);

	List<Map<String, Object>> getSelectPlug(String libId);

}

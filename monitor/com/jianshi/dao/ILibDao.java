package com.jianshi.dao;

import java.util.List;
import java.util.Map;


public interface ILibDao {

	String addLib(String name, String comment);

	List<Map<String,Object>> getLib(String key);

	void editLib(String id, String name, String comment);

	void delLib(String id);

	void addLibPlug(Map<String, String> map);

	void delLibPlug(String id);

	List<Map<String,Object>> getSelectLib(String proId);

}

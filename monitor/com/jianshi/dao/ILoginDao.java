package com.jianshi.dao;

import java.util.Map;

public interface ILoginDao {

	int exist(String name);

	Map<String, Object> getUser(Map<String, String> map);

	String getUserPsd(String id);

	void editPsd(String id, String newPsd);

}

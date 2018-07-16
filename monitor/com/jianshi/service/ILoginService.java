package com.jianshi.service;

import java.util.Map;

public interface ILoginService {

	int exist(String name);

	Map<String, Object> getUser(String name, String password);

	String editPsd(String string, String oldPsd, String newPsd);

}

package com.jianshi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jianshi.dao.ILoginDao;
import com.jianshi.service.ILoginService;
import com.xpoplarsoft.framework.security.Md5;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private ILoginDao loginDao;

	@Override
	public int exist(String name) {
		try {
			return loginDao.exist(name);
		} catch (DataAccessException e) {
			return -1;
		}
	}

	@Override
	public Map<String, Object> getUser(String name, String password) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("name", name);
		map.put("password", Md5.encryptMd5(password.getBytes()));
		return loginDao.getUser(map);
	}

	@Override
	public String editPsd(String id, String oldPsd, String newPsd) {
		try {
			String psd=loginDao.getUserPsd(id);
			if(!psd.equals(oldPsd))
				return "原密码不正确！";
			loginDao.editPsd(id,newPsd);
			return "T";
		} catch (DataAccessException e) {
			return "密码修改失败！";
		}
	}

}

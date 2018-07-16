package com.xpoplarsoft.limits.login.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.login.bean.LoginBean;

public interface ILoginDao {

	/**
	 * 登陆验证
	 * 
	 * @param userId
	 * @param passwd
	 * @return
	 */
	public boolean login(String userName,String userPass);
	
	public DBResult getUserInfo(LoginBean loginBean);
}

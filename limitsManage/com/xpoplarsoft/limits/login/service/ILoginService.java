package com.xpoplarsoft.limits.login.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.resources.bean.Resources;

public interface ILoginService {

	/**
	 * 登陆验证
	 * 
	 * @param bean
	 * @return boolean
	 */
	public boolean loginUser(LoginBean loginBean);

	/**
	 * 获取用户权限
	 * 
	 * @param loginBean
	 * @return List
	 */
	public Map<String,List<Resources>> getLimitMenu(LoginUserBean loginUser);
	
	/**
	 * 获取用户权限（旧版本）
	 * 
	 * @param loginBean
	 * @return List
	 */
	public List<Resources> getLimit(LoginUserBean user);
	
	/**
	 * 获取用户信息
	 * 
	 * @param loginBean
	 * @return LoginUser
	 */
	public LoginUserBean getUserInfo(LoginBean loginBean);

}

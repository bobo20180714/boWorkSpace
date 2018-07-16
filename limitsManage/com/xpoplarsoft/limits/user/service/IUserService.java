package com.xpoplarsoft.limits.user.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.user.bean.PoplarUser;
import com.xpoplarsoft.limits.user.bean.UserWhere;

/**
 * 用户管理业务层操作接口
 * 
 * @author 崔乔乔
 * @date 2014-1-24
 */
public interface IUserService {

	/**
	 * 添加用户信息表
	 * 
	 * @param user
	 *            用户信息
	 * @return 返回显示数据类
	 */
	public ResultBean addUser(PoplarUser bean);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 返回显示数据类
	 */
	public ResultBean updateUser(PoplarUser user);

	/**
	 * 根据表格条件查询
	 * 
	 * 
	 */
	public Map getUserList(UserWhere where);
	
	/**
	 * 查询符合条件的用户集合
	 * @param where
	 * @return
	 */
	public Map getLinkUserList(UserWhere where);
	

	/**
	 * 根据角色编码查询用户集合
	 * 
	 * @param userCode
	 *            角色编码
	 * @return 用户信息集合
	 */
	public List<PoplarUser> getUserListByRole(String roleCode);

	/**
	 * 删除用户信息
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 删除用户是否成功
	 */
	public ResultBean deleteUser(String[] userList);

	/**
	 * 启用用户
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 是否成功
	 */
	public ResultBean startUser(String[] userList);

	/**
	 * 禁用用户
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 是否成功
	 */
	public ResultBean stopUser(String[] userList);

	/**
	 * 根据用户帐号、密码获取用户信息
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param userPwd
	 *            用户密码
	 * @return 获得用户
	 */
	public PoplarUser getUser(String userAccount, String userPwd);

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param pk_id
	 *            
	 * @return 用户信息集合
	 */
	public String getUserInfoByUser(String pk_id);

	/**
	 * 修改用户密码
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param oldPasswd
	 * @param newPasswd
	 * @return 用户信息集合
	 */
	public ResultBean alterUserPwd(String userAccount, String oldPasswd,
			String newPasswd);
	
	/**
	 * 根据用户名获取用户登录信息
	 * @param userAccount 用户名
	 * @return
	 */
	public LoginBean getLoginBean(String userAccount);
	
	/**
	 * 根据用户code修改密码
	 * @return
	 */
	public ResultBean updatePassWord(String userCode,String passWord);

	public PoplarUser checkAccount(String account);
}

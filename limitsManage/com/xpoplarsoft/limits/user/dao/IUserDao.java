package com.xpoplarsoft.limits.user.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.user.bean.PoplarUser;
import com.xpoplarsoft.limits.user.bean.UserWhere;

/**
 * 用户管理数据层操作接口
 * 
 * @author 崔乔乔
 * @date 2014-1-24
 */
public interface IUserDao {

	/**
	 * 添加用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 添加用户是否成功
	 */
	public Boolean addUser(PoplarUser user);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 修改用户是否成功
	 */
	public Boolean updateUser(PoplarUser user);

	/**
	 * 根据表格条件查询
	 * 
	 * @return 数据库执行语句结果
	 */
	public DBResult getUserList(UserWhere where);
	
	
	/**
	 * 根据表格条件查询
	 * 
	 * @return 数据库执行语句结果
	 */
	public DBResult getLinkUserList(UserWhere where);
	

	/**
	 * 获取用户所有的信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 数据库执行语句结果
	 */
	public DBResult getUserList(PoplarUser user);

	/**
	 * 根据用户账号查询用户信息
	 * 
	 * @param userAccount
	 *            用户账号
	 * @return 数据库执行语句结果
	 */

	public DBResult getUserInfoByUser(String userAccount);

	/**
	 * 根据角色编码查询用户集合
	 * 
	 * @param userCode
	 *            角色编码
	 * @return 数据库执行语句结果
	 */
	public DBResult getUserListByRole(String roleCode);

	/**
	 * 删除用户信息
	 * 
	 * @param userCodes
	 *            用户信息组成的字符串，例如："userCode1,userCode2,userCode3"
	 * @return 删除成功标志
	 */
	public boolean deleteUser(String[] userList);

	/**
	 * 更新用户状态
	 * 
	 * @param userList
	 * @param state
	 * 
	 * @return 成功标志
	 */
	public boolean updateUserState(String[] userList,String state);
	
	/**
	 * 更新用户密码
	 * 
	 * @param userCode
	 * @param userPwd
	 * 
	 * @return 成功标志
	 */
	public boolean updateUserPwd(String userCode, String userPwd);

	/**
	 * 根据用户帐号、密码获取登录用户信息
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param userPwd
	 *            用户密码
	 * @return 数据库执行语句结果
	 */
	public DBResult getUser(String userAccount, String userPwd);

	/**
	 * 根据角色编码查询用户信息
	 * 
	 * @param 角色编码组成的字符串
	 *            ，例如："roleCode1,roleCode2,roleCode3"
	 * @return 数据库执行结果
	 */
	public DBResult queryAutUserRoleByCode(String userCodes);

	/**
	 * 根据用户编码查询某个用户是否绑定某个角色
	 * 
	 * @param 要查询的用户编号组成的字符串
	 *            ，例如："userCode1,userCode2,userCode3"
	 * @return 数据库执行结果
	 */
	public DBResult queryAutUserIsHaveRole(String userCodes);

	/**
	 * 通过用户编号获取资源列表
	 * 
	 * @param userCode
	 * @return
	 */
	public DBResult getRoleListByUserCode(String userCode);
	
	/**
	 * 根据用户名获取用户登录信息
	 * @param userAccount 用户名
	 * @return
	 */
	public DBResult getLoginBean(String userAccount);
	
	/**
	 * 根据用户code修改密码
	 * @return
	 */
	public boolean updatePassWord(String userCode,String passWord);

	/**
	 * 验证手机号码是否唯一
	 * @param user
	 * @return
	 */
	public DBResult phoneIsOnly(PoplarUser user);
	
	public DBResult checkAccount(String account);

}

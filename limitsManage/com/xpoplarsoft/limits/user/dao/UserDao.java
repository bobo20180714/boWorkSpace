package com.xpoplarsoft.limits.user.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.utils.StringTools;
import com.xpoplarsoft.limits.user.bean.PoplarUser;
import com.xpoplarsoft.limits.user.bean.UserWhere;

/**
 * 用户管理数据层操作接口
 * 
 * @author 王晓东
 * @date 2015-01-19
 */
@Repository
public class UserDao implements IUserDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(UserDao.class);


	/**
	 * 添加用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 添加用户是否成功
	 */
	@Override
	public Boolean addUser(PoplarUser user) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][addUser]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("pk_id", user.getUserId());
		para.setObject("user_account", user.getUserAccount());
		para.setObject("user_name", user.getUserName());
		para.setObject("user_pwd", user.getUserPwd());
		para.setObject("state", user.getState());
		para.setObject("create_user_code", user.getCreateUserId());
		para.setObject("create_time", user.getCreateDate());

		boolean result = SQLFactory.getSqlComponent().updateInfo("userInfo","add_user", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("新增用户成功");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("新增用户失败");
			}
			return false;
		}
	}

	/**
	 * 根据表格条件查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return 数据库执行语句结果
	 */
	@Override
	public DBResult getUserList(UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][getUserList]开始执行");
		}
		DBParameter para = new DBParameter();
		if(where.getAccount() == null){
			where.setAccount("");
		}
		if(where.getName() == null){
			where.setName("");
		}
	
		para.setObject("user_account", where.getAccount()==""?null:"%"+where.getAccount()+"%");
		para.setObject("user_name", where.getName()==""?null:"%"+where.getName()+"%");
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo("userInfo","query_user_list", para, where.getPage(), where.getPagesize());
		return result;
	}
	
	@Override
	public DBResult getLinkUserList(UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][getLinkUserList]开始执行");
		}
		DBParameter para = new DBParameter();
		if(where.getAccount() == null){
			where.setAccount("");
		}
		if(where.getName() == null){
			where.setName("");
		}
	
		para.setObject("user_account", where.getAccount()==""?null:"%"+where.getAccount()+"%");
		para.setObject("user_name", where.getName()==""?null:"%"+where.getName()+"%");
		para.setObject("staff_id", where.getStaffId());
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo("userInfo","query_linkuser_list", para, where.getPage(), where.getPagesize());
		return result;
	}
	
	
	/**
	 * 根据角色编码查询用户集合
	 * 
	 * @param userCode
	 *            角色编码
	 * @return 数据库执行语句结果
	 */
	@Override
	public DBResult getUserListByRole(String roleCode) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][getUserListByRole]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("roleCode", roleCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo","query_aut_userlist_by_role", para);
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 修改用户是否成功
	 */
	@Override
	public Boolean updateUser(PoplarUser user) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][updateUser]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("user_account", user.getUserAccount());
		para.setObject("user_name", user.getUserName());
		para.setObject("pk_id", user.getUserId());
		para.setObject("update_user_code", user.getUpdateUserId());
		para.setObject("update_time", user.getUpdateDate());

		boolean result = SQLFactory.getSqlComponent().updateInfo("userInfo","alter_user", para);

		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("用户修改成功！");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("用户修改失败！");
			}
			return false;
		}
	}

	/**
	 * 更新用户密码
	 * 
	 * @param userCode
	 * @param userPwd
	 * 
	 * @return 成功标志
	 */
	public boolean updateUserPwd(String userCode, String userPwd) {
		DBParameter para = new DBParameter();
		para.setObject("user_account", userCode);
		para.setObject("user_name", userPwd);

		boolean result = SQLFactory.getSqlComponent().updateInfo("userInfo","alter_user_pwd", para);

		return result;
	}

	/**
	 * 获取用户所有的信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 数据库执行语句结果
	 * 
	 */
	@Override
	public DBResult getUserList(PoplarUser user) {

		StringBuilder query = new StringBuilder(" where 1=1 ");
		if (!StringTools.isNull(user.getUserAccount())) {
			query.append(" and user_account='" + user.getUserAccount() + "'");
		}
		if (!StringTools.isNull(user.getUserName())) {
			query.append(" and user_name='" + user.getUserName() + "'");
		}
		if (!StringTools.isNull(user.getState())) {
			query.append(" and state='" + user.getState() + "'");
		}
		DBParameter para = new DBParameter();
		para.setObject("where", query.toString());

		return SQLFactory.getSqlComponent().queryInfo("userInfo","query_user_list", para);
	}

	/**
	 * 根据用户账号查询用户信息
	 * 
	 * @param userCode
	 *            用户编码
	 * @return 数据库执行语句结果
	 */
	@Override
	public DBResult getUserInfoByUser(String pk_id) {
		DBParameter para = new DBParameter();
		para.setObject("pk_id", pk_id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo","query_user_by_code", para);
		return result;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userList
	 *            用户账号数组
	 * @return 删除成功标志
	 */
	@Override
	public boolean deleteUser(String[] userList) {
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> list = new ArrayList<DBParameter>();
		List<DBParameter> li = new ArrayList<DBParameter>();
		for (int i = 0; i < userList.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("user_account", userList[i]);
			list.add(para);
			li.add(para);
		}
		map.put("delete_user_role", list);
		map.put("delete_user", li);
		boolean result = SQLFactory.getSqlComponent().batchUpdate("userInfo", map);
		return result;
	}

	/**
	 * 根据角色编码查询用户信息
	 * 
	 * @param 角色编码组成的字符串，例如："roleCode1,roleCode2,roleCode3"
	 * @return 数据库执行结果
	 */
	@Override
	public DBResult queryAutUserRoleByCode(String roleCodes) {
		DBParameter para = new DBParameter();
		para.setObject("userCodes", roleCodes);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo","query_aut_userlist_by_role", para);
		return result;
	}

	/**
	 * 根据用户帐号、密码获取登录用户信息
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param userPwd
	 *            用户密码
	 * @return 数据库执行语句结果
	 */
	@Override
	public DBResult getUser(String userAccount, String userPwd) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][getUser]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("user_account", userAccount);
		para.setObject("user_pwd", userPwd);

		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo","query_user_bypwd", para);

		return result;
	}

	/**
	 * 根据用户编码查询某个用户是否绑定的角色
	 * 
	 * @param 要查询的用户编号组成的字符串，例如："userCode1,userCode2,userCode3"
	 * @return 数据库执行结果
	 */
	@Override
	public DBResult queryAutUserIsHaveRole(String userCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][queryAutUserIsHaveRole]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("userCodes", userCodes);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo","query_autuser_role_list_by_usercode", para);
		return result;
	}

	/**
	 * 更新用户状态
	 * 
	 * @param userList
	 * @param state
	 * 
	 * @return 成功标志
	 */
	public boolean updateUserState(String[] userList,String state) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][updateUserState]开始执行");
		}
		List<DBParameter>  list = new ArrayList<DBParameter>();
		for(int  i =0 ; i<userList.length;i++){
			DBParameter para = new DBParameter();
			para.setObject("user_account", userList[i]);
			para.setObject("state", state);
			list.add(para);
		}
		boolean result = SQLFactory.getSqlComponent().batchUpdate("userInfo", "update_user_state", list);
		
		return result;
	}

	/**
	 * 通过用户编号获取资源列表
	 * 
	 * @param userCode
	 * @return
	 */
	public DBResult getRoleListByUserCode(String user_account) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][getRoleListByUserCode]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("user_id", user_account);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userAndRole","query_rolelist_byuserid", para);
		return result;
	}

	@Override
	public DBResult getLoginBean(String userAccount) {
		
		DBParameter para = new DBParameter();
		para.setObject("user_account", userAccount);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("userInfo", "query_user_byaccount", para);
		return result;
	}

	@Override
	public boolean updatePassWord(String userCode, String passWord) {
		
		DBParameter para = new DBParameter();
		para.setObject("user_account", userCode);
		para.setObject("user_pwd", passWord);
		boolean result = SQLFactory.getSqlComponent().updateInfo("userInfo", "alter_user_pwd", para);
		return result;
	}

	@Override
	public DBResult phoneIsOnly(PoplarUser user) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][phoneIsOnly]开始执行");
		}
		DBResult result = SQLFactory.getSqlComponent().queryInfo("select * from xpoplar_user where USER_MOBILE = '' AND user_account !='"+user.getUserAccount()+"'");
		return result;
	}

	@Override
	public DBResult checkAccount(String account) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][checkAccount]开始执行");
		}
		
		DBParameter para = new DBParameter();
		para.setObject("user_account", account);
		DBResult result  = SQLFactory.getSqlComponent().queryInfo("userInfo", "check_account", para);
		
		if (log.isInfoEnabled()) {
			log.info("组件[UserDao][checkAccount]执行结束");
		}
		return result;
	}
}

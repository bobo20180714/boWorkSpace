package com.xpoplarsoft.limits.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.framework.security.Md5;
import com.xpoplarsoft.framework.utils.StringTools;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.role.dao.IRoleDao;
import com.xpoplarsoft.limits.user.bean.PoplarUser;
import com.xpoplarsoft.limits.user.bean.UserWhere;
import com.xpoplarsoft.limits.user.dao.IUserDao;

/**
 * 类功能: 用户服务实现类
 * 
 * @author chen.jie
 * @date 2014-3-14
 */
@Service
public class UserService implements IUserService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(UserService.class);

	@Autowired
	public IUserDao userDao;

	@Autowired
	public IRoleDao roleDao;

	/**
	 * 添加用户信息表
	 * 
	 * @param user
	 *            用户信息
	 * @return 返回显示数据类
	 */
	@Override
	public ResultBean addUser(PoplarUser bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][addUser]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = false;
		String userId=FlowNoFactory.getFlowNoComponent().getFlowNo();
		String tempStr = bean.getUserPwd();
		//对用户密码进行加密
		if (!(tempStr == null || "".equals(tempStr.trim()))) {
			String userPwd = Md5.encryptMd5(tempStr.getBytes());
			bean.setUserPwd(userPwd);
			bean.setUserId(userId);
			status = userDao.addUser(bean);
		}
		if (status) {
			result.setSuccess("true");
			result.setMessage("新增用户成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("新增用户失败！");
		}
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 *            用户信息
	 * @return 返回显示数据类
	 */
	@Override
	public ResultBean updateUser(PoplarUser user) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][updateUser]开始执行");
		}

		ResultBean result = new ResultBean();
		boolean status = userDao.updateUser(user);
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改用户成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("修改用户失败！");
		}
		return result;
	}

	/**
	 * 根据表格条件查询
	 * 
	 * @param 
	 * @return 用户信息列表json串
	 */
	@Override
	public Map getUserList(UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getUserList]开始执行");
		}

		DBResult result = userDao.getUserList(where);
		// 返回到前台的数据
		String retJson = null;
		// 页面数据
		Map pageInfo = new HashMap();
		// 一行数据
		Map cellMap = null;

		List infoList = new ArrayList();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				
				cellMap = new HashMap();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("user_account", result.getValue(i, "user_account"));
				cellMap.put("user_name", result.getValue(i, "user_name"));
				cellMap.put("staff_id", result.getValue(i, "staff_id"));
				cellMap.put("create_user_code", result.getValue(i,
						"create_user_code"));
				cellMap.put("create_time", result.getValue(i, "create_time"));
				cellMap.put("state", result.getValue(i, "state"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);

		return pageInfo;
	}
	
	
	/**
	 * 根据表格条件查询
	 * 
	 * @param 
	 * @return 用户信息列表json串
	 */
	@Override
	public Map getLinkUserList(UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getLinkUserList]开始执行");
		}

		DBResult result = userDao.getLinkUserList(where);
		// 返回到前台的数据
		String retJson = null;
		// 页面数据
		Map pageInfo = new HashMap();
		// 一行数据
		Map cellMap = null;

		List infoList = new ArrayList();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				
				cellMap = new HashMap();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("user_account", result.getValue(i, "user_account"));
				cellMap.put("user_name", result.getValue(i, "user_name"));
				cellMap.put("staff_id", result.getValue(i, "staff_id"));
				cellMap.put("create_user_code", result.getValue(i,
						"create_user_code"));
				cellMap.put("create_time", result.getValue(i, "create_time"));
				cellMap.put("state", result.getValue(i, "state"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);

		return pageInfo;
	}
	

	/**
	 * 删除用户信息
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 显示页面信息
	 */
	@Override
	public ResultBean deleteUser(String[] userList) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][deleteUser]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = userDao.deleteUser(userList);
		if (status) {
			result.setSuccess("true");
			result.setMessage("删除用户成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("删除用户失败！");
		}

		return result;
	}

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userCode
	 *            角色编码
	 * @return 用户信息集合
	 */
	@Override
	public String getUserInfoByUser(String pk_id) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getUserInfoByUser]开始执行");
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		DBResult dbResult = userDao.getUserInfoByUser(pk_id);
		Gson json = new Gson();
		if (dbResult != null && dbResult.getRows() > 0) {
			String[] resName = dbResult.getColName();

			for (int j = 0; j < resName.length; j++) {
				// 查询列对应的值
				String resValue = dbResult.getValue(0, resName[j]);
				// 放入map中
				cellMap.put(resName[j], resValue);
			}
			
			String retJson = json.toJson(cellMap);
			if (log.isDebugEnabled()) {
				log.debug("返回json数据为[" + retJson + "]");
			}

			return retJson;
		} else {
			return json.toJson(cellMap);
		}
	}

	/**
	 * 根据角色编码查询用户集合
	 * 
	 * @param userCode
	 *            角色编码
	 * @return 用户信息集合
	 */
	@Override
	public List<PoplarUser> getUserListByRole(String roleCode) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getUserListByRole]开始执行");
		}
		List<PoplarUser> list = null;
		DBResult result = userDao.getUserListByRole(roleCode);
		if (result != null) {
			list = dbresultToPoplarUser(result);
		}
		return list;
	}

	/**
	 * 根据用户帐号、密码获取用户信息
	 * 
	 * @param userAccount
	 *            用户账号
	 * @param userPwd
	 *            用户密码
	 * @return 获得用户
	 */
	@Override
	public PoplarUser getUser(String userAccount, String userPwd) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getUser]开始执行");
		}
		PoplarUser user = null;
		String userPassWord = Md5.encryptMd5(userPwd.getBytes());
		DBResult result = userDao.getUser(userAccount, userPassWord);
		if (result != null && result.getTotal() > 0) {
			List<PoplarUser> list = dbresultToPoplarUser(result);
			if (list.size() > 0) {
				user = list.get(0);
			}
		}

		return user;
	}

	/**
	 * 将数据库的查询结果转化为AutUser对象
	 * 
	 * @param result
	 *            数据库的查询结果
	 * @return 转化为PoplarUser对象集合
	 */
	public List<PoplarUser> dbresultToPoplarUser(DBResult result) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][dbresultParseToAutUser]开始执行");
		}
		List<PoplarUser> list = null;
		if (result.getRows() > 0) {
			list = new ArrayList<PoplarUser>();
			for (int i = 0; i < result.getRows(); i++) {
				PoplarUser user = new PoplarUser();
				String birthday = result.get(i, "user_birthday");
				String userSex = result.get(i, "user_sex");
				// 默认没有填写性别为男
				if (StringTools.isNull(userSex)) {
					userSex = "0";
				}
				user.setUserId(result.get(i, "pk_id"));

				user.setState(result.get(i, "state"));
				user.setUserAccount(result.get(i, "user_account"));
				list.add(user);
			}
		}
		return list;

	}

	/**
	 * 启用用户
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 是否成功
	 */
	public ResultBean startUser(String[] userList) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][startUser]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = userDao.updateUserState(userList,"1");
		if (status) {
			result.setSuccess("true");
			result.setMessage("启用用户成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("启用用户失败！");
		}

		return result;
	}

	/**
	 * 禁用用户
	 * 
	 * @param userCodeList
	 *            用户编码集合
	 * @return 是否成功
	 */
	public ResultBean stopUser(String[] userList) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][stopUser]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = userDao.updateUserState(userList,"2");
		if (status) {
			result.setSuccess("true");
			result.setMessage("禁用用户成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("禁用用户失败！");
		}

		return result;
	}

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
			String newPasswd) {

		PoplarUser user = this.getUser(userAccount, oldPasswd);
		ResultBean resultBean = new ResultBean();
		if (user == null) {
			if (log.isWarnEnabled()) {
				log.warn("用户原密码错误！");
			}
			resultBean.setSuccess("false");
			resultBean.setMessage("用户原密码错误！");
		}

		boolean updateState = userDao.updateUserPwd(userAccount, newPasswd);

		if (updateState) {
			if (log.isDebugEnabled()) {
				log.debug("用户修改密码成功！");
			}

			resultBean.setSuccess("true");
			resultBean.setMessage("用户修改密码成功！");
			return resultBean;
		} else {
			if (log.isWarnEnabled()) {
				log.warn("用户修改密码失败！");
			}

			resultBean.setSuccess("true");
			resultBean.setMessage("用户修改密码失败！");
			return resultBean;

		}

	}

	@Override
	public LoginBean getLoginBean(String userAccount) {
		DBResult result=userDao.getLoginBean(userAccount);
		if(result!=null&&result.getRows()>0) {
			LoginBean loginBean=new LoginBean();
			loginBean.setUserAccount(result.get(0, "USER_ACCOUNT"));
			loginBean.setPassword(result.get(0, "USER_PWD"));
			return loginBean;
		}
		return null;
	}
	
	/**
	 * 根据用户code修改密码
	 * @return
	 */
	@Override
	public ResultBean updatePassWord(String userCode, String passWord) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][updateUser]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = false;
		passWord = Md5.encryptMd5(passWord.getBytes());
		status = userDao.updatePassWord(userCode, passWord);
		//判断是否成功
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改密码成功");
		} else {
			result.setSuccess("false");
			result.setMessage("修改密码失败");
		}
		return result;
	}

	@Override
	public PoplarUser checkAccount(String account) {
		
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][checkAccount]开始执行");
		}
		DBResult result = userDao.checkAccount(account);
		PoplarUser user = null;
		if (result != null && result.getRows()>0) {
				user = new PoplarUser();
		}
		
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][checkAccount]执行结束");
		}
		return user;
	}
}

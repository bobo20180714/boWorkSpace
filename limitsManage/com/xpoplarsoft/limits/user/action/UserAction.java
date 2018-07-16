package com.xpoplarsoft.limits.user.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.action.Action;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.framework.security.Md5;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.user.bean.PoplarUser;
import com.xpoplarsoft.limits.user.bean.UserWhere;
import com.xpoplarsoft.limits.user.service.IUserService;

/**
 * 用户管理连接器类
 * 
 * @author 王晓东
 * @date 2014-01-19
 */
@Controller
@RequestMapping("/UserAction")
public class UserAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(PoplarUser.class);

	@Autowired
	private IUserService service;

	/**
	 * 添加用户信息
	 * 
	 * @param bean
	 *            用户信息javabean
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/addUser")
	public @ResponseBody
	String addUser(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PoplarUser bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][addUser]开始执行");
		}

		// 获取当前登录的用户编号
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String operate_time = DateTools.getCurryDateTime().substring(0, 19);
		bean.setCreateUserId(loginUser == null ? "" : loginUser
				.getUserId());
		bean.setCreateDate(operate_time);
		ResultBean tempResult = service.addUser(bean);
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param bean
	 *            用户信息javabean
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/updateUser")
	public @ResponseBody
	String updateUser(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PoplarUser bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][updateUser]开始执行");
		}
		// 获取当前登录的用户编号
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String operate_time = DateTools.getCurryDateTime().substring(0, 19);
		bean.setUpdateUserId(loginUser == null ? "" : loginUser.getUserId());
		bean.setUpdateDate(operate_time);
		ResultBean tempResult = service.updateUser(bean);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 获取用户所有的信息
	 * 
	 * @param bean
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserList")
	public @ResponseBody
	String getUserList(HttpServletRequest request,
			HttpServletResponse response, UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][getUserList]开始执行");
		}
		Map map = service.getUserList(where);
		String result = "";
		if (map != null) {
			Gson json = new Gson();
			result = json.toJson(map);
		}
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/getLinkUserList")
	public @ResponseBody
	String getLinkUserList(HttpServletRequest request,
			HttpServletResponse response, UserWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][getLinkUserList]开始执行");
		}
		Map map = service.getLinkUserList(where);
		String result = "";
		if (map != null) {
			Gson json = new Gson();
			result = json.toJson(map);
		}
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	

	/**
	 * 查询用户信息
	 * 
	 * @param request对象存放userCode
	 *            用户编码
	 * @return 用户信息javabean转成的json串
	 */
	@RequestMapping(value = "/getUserListByUser")
	public @ResponseBody
	String getUserListByUser(HttpServletRequest request, String pk_id) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][getUserListByUser]开始执行");
		}
		String result = service.getUserInfoByUser(pk_id);

		return result;
	}

	/**
	 * 根据角色编码查询用户集合
	 * 
	 * @param request中存放roleCode
	 *            角色编码
	 * @return 用户信息集合转成的json串
	 */
	@RequestMapping(value = "/getUserListByRole")
	public @ResponseBody
	String getUserListByRole(HttpServletRequest request) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][getUserListByRole]开始执行");
		}
		List<PoplarUser> list = service.getUserListByRole(request
				.getParameter("roleCode"));
		String result = "";
		if (list != null && list.size() > 0) {
			Gson json = new Gson();
			result = json.toJson(list);
		}
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 删除用户信息
	 * 
	 * @param request中存放
	 *            userCodes 用户编码的json串
	 *            {"userCodes":[{"userCode":"userCode"}...]}
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/deleteUser")
	public @ResponseBody
	String deleteUser(String userCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][deleteUser]开始执行");
		}
		// 用户编号集合
		String[] userList = userCodes.split(",");

		ResultBean tempResult = service.deleteUser(userList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 启用
	 * 
	 * @param userCodes
	 * @return
	 */
	@RequestMapping(value = "/startUser")
	public @ResponseBody
	String startUser(String userCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][startUser]开始执行");
		}
		// 用户编号集合
		String[] userList = userCodes.split(",");

		ResultBean tempResult = service.startUser(userList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 禁用
	 * 
	 * @param userCodes
	 * @return
	 */
	@RequestMapping(value = "/stopUser")
	public @ResponseBody
	String stopUser(String userCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][stopUser]开始执行");
		}
		// 用户编号集合
		String[] userList = userCodes.split(",");

		ResultBean tempResult = service.stopUser(userList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 修改密码
	 * @author 孟祥超
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updatePwd")
	public @ResponseBody
	String updatePwd(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][updatePwd]开始执行");
		}
		LoginBean loginUser = service.getLoginBean(request
				.getParameter("userAccount"));
		String newPassWord = Md5.encryptMd5(request.getParameter("oldPwd").getBytes());
		
		ResultBean tempResult = new ResultBean();
		if (loginUser != null && loginUser.getPassword() != null
				&& loginUser.getPassword().equals(newPassWord)) {
			//修改密码
			String userAccount = request.getParameter("userAccount");
			String passWord = request.getParameter("new_pwd");
			tempResult = service.updatePassWord(userAccount, passWord);
		}else{
			//原密码不对
			tempResult.setMessage("原密码输入不正确！");
			tempResult.setSuccess("false");
		}
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	/**
	 * 验证修改密码是否和原密码一致
	 * 
	 * @param request中存放
	 *            userCodes 用户编码的json串
	 *            {"userCodes":[{"userCode":"userCode"}...]}
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/samePwd")
	public @ResponseBody
	String samePwd(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][samePwd]开始执行");
		}
		LoginBean loginUser = service.getLoginBean(request
				.getParameter("userCode"));
		String newPassWord = Md5.encryptMd5(request.getParameter("oldPwd").getBytes());
		String result = "false";
		if (loginUser != null) {
			if (loginUser.getPassword() != null
					&& loginUser.getPassword().equals(newPassWord)) {
				result = "true";
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param request中存放
	 *            userCodes 用户编码的json串
	 *            {"userCodes":[{"userCode":"userCode"}...]}
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/updatePassWord")
	public @ResponseBody
	String updatePassWord(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][updatePassWord]开始执行");
		}
		String userCode = request.getParameter("userCode");
		String passWord = request.getParameter("new_pwd");
		ResultBean tempResult = service.updatePassWord(userCode, passWord);
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param request中存放
	 *            userCodes 用户编码的json串
	 *            {"userCodes":[{"userCode":"userCode"}...]}
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/resetPassWord")
	public @ResponseBody
	String resetPassWord(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][updatePassWord]开始执行");
		}
		String userCode = request.getParameter("userCode");
		String passWord = SystemParameter.getInstance().getParameter(
				"defaultPassword");
		ResultBean tempResult = service.updatePassWord(userCode, passWord);
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	@RequestMapping(value = "/checkAccount")
	public @ResponseBody
	String checkAccount(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][checkAccount]开始执行");
		}
		String account = request.getParameter("userAccount");
		PoplarUser user = service.checkAccount(account);
		Gson json = new Gson();
		ResultBean result = new ResultBean();
		if (user != null) {
			result.setSuccess("true");
		} else {
			result.setSuccess("false");
		}
		String jsonstr = json.toJson(result);
		
		if (log.isInfoEnabled()) {
			log.info("组件[UserAction][checkAccount]执行结束");
			log.info("返回数据为[" + jsonstr + "]");
		}
		return jsonstr;
	}
}

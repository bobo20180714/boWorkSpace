package com.xpoplarsoft.limits.orguser.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.framework.security.Md5;
import com.xpoplarsoft.framework.startup.FrameStartup;
import com.xpoplarsoft.limits.orguser.bean.OrgView;
import com.xpoplarsoft.limits.orguser.bean.UserInfoView;
import com.xpoplarsoft.limits.orguser.service.IOrgUserService;

@Controller
@RequestMapping("/orguser")
public class OrgUserController {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OrgUserController.class);
	
	public static Gson gson = new Gson();
	
	@Autowired
	private IOrgUserService orgUserService;
	

	/**
	 * @Title: findOrgTree
	 * @Description: 获取机构信息树
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findorgtree")
	public @ResponseBody
	String findOrgTree(HttpServletRequest request, OrgView orgView, String parent_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][findOrgTree]开始执行");
		}
		String json = "";
	/*	if (parent_id == null)
			return null;*/
		DBParameter param = new DBParameter();
		param.setObject("parent_id", parent_id);
		param.setObject("id", orgView.getId());
//		String cou = orgUserService.getFindOrgCount(param);
//		int total = Integer.parseInt(cou);
		List<OrgView> result = orgUserService.findOrgTree(param);
		json = JSONObject.toJSONString(result);
//		return "{\"Rows\":"+result+",Total:"+cou+"}";
		return json;
	}

	/**
	 * @Title: orgAdd
	 * @Description: 机构信息新增
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("orgadd")
	public @ResponseBody
	String orgAdd(HttpServletRequest request, OrgView orgView) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][orgAdd]开始执行");
		}
//		UserInfoView user = (UserInfoView) request.getSession().getAttribute(
//				"LoginUser");

		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(user.getLogin_name());
//		logBean.setUserName(user.getUser_name());
//		logBean.setOrganCode(user.getOrg_id());
//		logBean.setOrganName(user.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_ORGAN);
//		logBean.setEntityCode(orgView.getOrg_code());
//		logBean.setEntityName(orgView.getOrg_name());
//
//		logBean.setOperateContent("新增机构,机构编号[" + orgView.getOrg_code()
//				+ "]机构名称[" + orgView.getOrg_name() + "],详细信息["
//				+ orgView.getDetail() + "]");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		String create_time = sdf.format(now);
//		orgView.setCreate_user(user.getUser_id());
		DBParameter param = new DBParameter();
		param.setObject("create_time", create_time);
		param.setObject("create_user", orgView.getCreate_user());
		param.setObject("org_code", orgView.getOrg_code());
		param.setObject("org_name", orgView.getOrg_name());
		param.setObject("org_desc", orgView.getOrg_desc());
		param.setObject("org_id", orgView.getOrg_id());
		Boolean flag = orgUserService.orgAdd(param);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			// 日志对象
			/*OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean)request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setEndTime();
			logBean.setOperateType(5);
			logBean.setOperateContent("新增机构["+orgView.getOrg_name()+"]");
			logBean.setEntityName(orgView.getOrg_name());
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);*/
			retStr = "{\"success\":\"true\",\"message\":\"添加成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("新增机构失败");
			retStr = "{\"success\":\"false\",\"message\":\"添加失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	/**
	 * @Title: orgUpdate
	 * @Description: 机构信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("orgupdate")
	public @ResponseBody
	String orgUpdate(HttpServletRequest request, OrgView orgView) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][orgUpdate]开始执行");
		}
//		UserInfoView user = (UserInfoView) request.getSession().getAttribute(
//				"LoginUser");

		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(user.getLogin_name());
//		logBean.setUserName(user.getUser_name());
//		logBean.setOrganCode(user.getOrg_id());
//		logBean.setOrganName(user.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_ORGAN);
//		logBean.setEntityCode(orgView.getOrg_code());
//		logBean.setEntityName(orgView.getOrg_name());
//
//		logBean.setOperateContent("修改机构信息,机构编号[" + orgView.getOrg_code()
//				+ "]机构名称[" + orgView.getOrg_name() + "],详细信息["
//				+ orgView.getDetail() + "]");
		DBParameter param = new DBParameter();
		param.setObject("org_code", orgView.getOrg_code());
		param.setObject("org_name", orgView.getOrg_name());
		param.setObject("org_desc", orgView.getOrg_desc());
		param.setObject("org_id", orgView.getOrg_id());

		Boolean flag = orgUserService.orgUpdate(param);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			retStr = "{\"success\":\"true\",\"message\":\"修改成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("修改机构失败");
			retStr = "{\"success\":\"false\",\"message\":\"修改失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	
	
	/**
	 * @Title: findOrgById
	 * @Description: 
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findOrgById")
	public @ResponseBody
	String findOrgById(HttpServletRequest request,
			String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][findOrgById]开始执行");
		}
		DBParameter param = new DBParameter();
		param.setObject("org_id", org_id);
		OrgView org = orgUserService
				.findOrgById(param);
		return JSONObject.toJSONString(org);
	}
	/**
	 * @Title: orgDelete
	 * @Description: 机构信息删除（本质修改状态）
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("orgdelete")
	public @ResponseBody
	String orgDelete(HttpServletRequest request, String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][orgDelete]开始执行");
		}
//		UserInfoView user = (UserInfoView) request.getSession().getAttribute(
//				"LoginUser");

		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(user.getLogin_name());
//		logBean.setUserName(user.getUser_name());
//		logBean.setOrganCode(user.getOrg_id());
//		logBean.setOrganName(user.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_ORGAN);
//		logBean.setEntityCode(orgView.getOrg_id());
//
//		logBean.setOperateContent("删除机构信息,主键[" + orgView.getOrg_id() + "]");
		
		DBParameter param = new DBParameter();
		param.setObject("org_id", org_id);
		String retStr = orgUserService.orgDelete(param);
		return retStr;
	}

	/**
	 * @Title: resetPassword
	 * @Description: 用户密码初始化
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("resetpassword")
	public @ResponseBody
	String resetPassword(HttpServletRequest request, String user_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][resetPassword]开始执行");
		}
//		LoginBean user = (LoginBean) request.getSession().getAttribute(
//				"LoginUser");
//		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(user.getUserAccount());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
//		logBean.setEntityCode(user_id);
//		logBean.setOperateContent("用户密码初始化");

		String pas = Md5.encryptMd5("123456".getBytes());
		DBParameter param = new DBParameter();
		param.setObject("user_id", user_id);
		param.setObject("pas", pas);
		Boolean flag = orgUserService.setPassword(param);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			retStr = "{\"success\":\"true\",\"message\":\"重置密码成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("初始密码失败");
			retStr = "{\"success\":\"false\",\"message\":\"重置密码失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	/**
	 * @Title: resetPassword
	 * @Description: 修改密码
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("setpassword")
	public @ResponseBody
	String setPassword(HttpServletRequest request, String user_id,
			String password) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][setPassword]开始执行");
		}
		UserInfoView user = (UserInfoView) request.getSession().getAttribute(
				"LoginUser");
		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(user.getLogin_name());
//		logBean.setUserName(user.getUser_name());
//		logBean.setOrganCode(user.getOrg_id());
//		logBean.setOrganName(user.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
//		logBean.setEntityCode(user_id);
//		logBean.setOperateContent("用户修改密码");

		if (user_id == null || "".equals(user_id)) {
			user_id = user.getUser_id();
		}
		String pas = Md5.encryptMd5(password.getBytes());
		DBParameter param = new DBParameter();
		param.setObject("user_id", user_id);
		param.setObject("pas", pas);
		Boolean flag = orgUserService.setPassword(param);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			retStr = "{\"success\":\"true\",\"message\":\"修改密码成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("修改密码失败");
			retStr = "{\"success\":\"false\",\"message\":\"修改密码失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	/**
	 * @Title: findUserQueryPage
	 * @Description: 用户列表信息（分页）
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("finduserquerypage")
	public @ResponseBody
	String findUserQueryPage(HttpServletRequest request, String page,String pagesize,
			UserInfoView userInfoView) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][findUserQueryPage]开始执行");
		}
		String json = "";
		DBParameter param = new DBParameter();
		param.setObject("page", page);
		param.setObject("pagesize", pagesize);
		param.setObject("org_id", request.getParameter("id"));
		param.setObject("login_name", request.getParameter("login_name"));
		param.setObject("user_name", request.getParameter("user_name"));
		param.setObject("bumen", request.getParameter("bumen"));
		String timeStart = request.getParameter("end_time_start");
		param.setObject("end_time_start", "".equals(timeStart) || timeStart == null?null:timeStart+"00:00:00.001");
		String endStart = request.getParameter("end_time_end");
		param.setObject("end_time_end", "".equals(endStart) || endStart == null?null:endStart+"23:59:59.999");
		String cou = orgUserService.getFindUserCount(param);
		List<UserInfoView> users = orgUserService.findUserQueryPage(param);
		json = JSONObject.toJSONString(users);
		return "{\"Rows\":"+json+",\"Total\":"+cou+"}";
	}


	/**
	 * @Title: findUserByLoginName
	 * @Description: 
	 * @author jingkewen
	 * @throws
	 */
//	@SuppressWarnings( { "unchecked", "rawtypes" })
	@RequestMapping("findUserByLoginName")
	public @ResponseBody
	String findUserByLoginName(HttpServletRequest request,
			UserInfoView userInfoView) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][findUserByLoginName]开始执行");
		}
		String json = "";
		DBParameter param = new DBParameter();
		param.setObject("login_name", userInfoView.getLogin_name());
		/*UserInfoView user = orgUserService
				.findUserByLoginName(param);
		return JSONObject.toJSONString(user);*/
		json=orgUserService.findUserByLoginName(param);
		return json;
	}
	
	
	/**
	 * @Title: findOrgUserByLoginName
	 * @Description: 
	 * @author jingkewen
	 * @throws
	 */
//	@SuppressWarnings( { "unchecked", "rawtypes" })
	@RequestMapping("findOrgUserByLoginName")
	public @ResponseBody
	String findOrgUserByLoginName(HttpServletRequest request,
			UserInfoView userInfoView) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][findUserByLoginName]开始执行");
		}
		String json = "";
		DBParameter param = new DBParameter();
		param.setObject("login_name", userInfoView.getLogin_name());
		/*UserInfoView user = orgUserService
				.findUserByLoginName(param);
		return JSONObject.toJSONString(user);*/
		json=orgUserService.findOrgUserByLoginName(param);
		return json;
	}
	
	@RequestMapping("getUserById")
	public @ResponseBody
	String getUserById(HttpServletRequest request,
			String user_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][getUserById]开始执行");
		}
		Map<String, Object> rsmap = orgUserService.getUserById(user_id);
		return gson.toJson(rsmap);
	}

	/**
	 * @Title: userInfoAdd
	 * @Description: 用户信息新增
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userinfoadd")
	public @ResponseBody
	String userInfoAdd(HttpServletRequest request, UserInfoView userInfoView, String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userInfoAdd]开始执行");
		}
		
		//判断登录账号是否重复
		if(orgUserService.judgeLoginAccountExit(userInfoView.getLogin_name())){
			return "{\"success\":\"false\",\"message\":\"登录名称已经存在\"}";
		}
		
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		userInfoView.setUser_id(UUID.randomUUID().toString());
//		UserInfoView loginUser = (UserInfoView) request.getSession()
//				.getAttribute("LoginUser");
//		userInfoView.setCreate_user(loginUser.getUser_id());
		userInfoView.setPassword(Md5.encryptMd5("123456".getBytes()));
		userInfoView.setCreate_time(dateFormat.format(now));
		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(loginUser.getLogin_name());
//		logBean.setUserName(loginUser.getUser_name());
//		logBean.setOrganCode(loginUser.getOrg_id());
//		logBean.setOrganName(loginUser.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
//		logBean.setEntityCode(userInfoView.getUser_id());
//		logBean.setEntityName(userInfoView.getUser_name());
//
//		logBean
//				.setOperateContent("新增用户,详细信息[" + userInfoView.getDetail()
//						+ "]");
		LoginUserBean loginUser = (LoginUserBean) request.getSession().getAttribute("LoginUser");
		DBParameter par = new DBParameter();
		par.setObject("create_user_code", loginUser.getUserId());		
		String create_user_code = orgUserService.getUserId(par);		
		DBParameter param = new DBParameter();
		param.setObject("org_id", org_id);
		param.setObject("user_id", userInfoView.getUser_id());
		param.setObject("password", userInfoView.getPassword());
		param.setObject("create_time", userInfoView.getCreate_time());
		param.setObject("login_name", userInfoView.getLogin_name());
		param.setObject("user_name", userInfoView.getUser_name());
		param.setObject("telephone", userInfoView.getTelephone());
		param.setObject("danwei", userInfoView.getDanwei());
		param.setObject("bumen", userInfoView.getBumen());
		param.setObject("zw", userInfoView.getZw());
		param.setObject("create_user_code", create_user_code);
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.YEAR, 1);
		param.setObject("end_time", "".equals(userInfoView.getEnd_time())?dateFormat.format(cal.getTime()):userInfoView.getEnd_time());

		boolean flag = orgUserService.userInfoAdd(param);

		if (flag) {
//			logBean.setResult(true);
			// 日志对象
			/*OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setEndTime();
			logBean.setOperateType(5);
			logBean.setOperateContent("新增用户["+userInfoView.getLogin_name()+"]");
			logBean.setEntityName(userInfoView.getLogin_name());
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);*/
			return "{\"success\":\"true\",\"message\":\"新增成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("新增用户失败");
			return "{\"success\":\"false\",\"message\":\"新增失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		
	}

	/**
	 * @Title: userInfoUpdate
	 * @Description: 用户信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userinfoupdate")
	public @ResponseBody
	String userInfoUpdate(HttpServletRequest request, UserInfoView userInfoView) {
		if (userInfoView == null)
			return null;
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userInfoUpdate]开始执行");
		}
//		UserInfoView loginUser = (UserInfoView) request.getSession()
//				.getAttribute("LoginUser");

		// 日志对象
/*		OperateLogBean logBean = new OperateLogBean();
		// 设置公共日志信息
		logBean.setStartTime();
		logBean.setUserCode(loginUser.getLogin_name());
		logBean.setUserName(loginUser.getUser_name());
		logBean.setOrganCode(loginUser.getOrg_id());
		logBean.setOrganName(loginUser.getOrg_name());
		logBean.setClientIp(request.getRemoteAddr());

		// 填充日志记录内容
		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
		logBean.setEntityCode(userInfoView.getUser_id());
		logBean.setEntityName(userInfoView.getUser_name());

		logBean.setOperateContent("修改用户信息,详细信息[" + userInfoView.getDetail()
				+ "]");*/

		Boolean flag = orgUserService.userInfoUpdate(userInfoView);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
		/*	if(loginUser.getUser_id().equals(userInfoView.getUser_id())){
				loginUser.setUser_name(userInfoView.getUser_name());
				request.getSession().setAttribute("LoginUser", loginUser);
			}*/
			retStr = "{\"success\":\"true\",\"message\":\"修改成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("修改用户信息失败");
			retStr = "{\"success\":\"false\",\"message\":\"修改失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	/**
	 * @Title: userInfoDelete
	 * @Description: 用户信息删除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userinfodelete")
	public @ResponseBody
	String userInfoDelete(HttpServletRequest request, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userInfoDelete]开始执行");
		}
//		UserInfoView loginUser = (UserInfoView) request.getSession()
//				.getAttribute("LoginUser");
		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(loginUser.getLogin_name());
//		logBean.setUserName(loginUser.getUser_name());
//		logBean.setOrganCode(loginUser.getOrg_id());
//		logBean.setOrganName(loginUser.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
//		logBean.setEntityCode(ids);
//
//		logBean.setOperateContent("删除用户,主键[" + ids + "]");
		if (ids == null)
			return null;
		Boolean flag = orgUserService.userInfoDelete(ids);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			/*// 日志对象
			OperateLogBean logBean = new OperateLogBean();
			logBean.setStartTime();
			if(request.getSession().getAttribute("LoginUser") != null){
				LoginUserBean loginUser = (LoginUserBean) request.getSession().getAttribute("LoginUser");
				logBean.setUserCode(loginUser.getUserId());
				logBean.setOrganCode(loginUser.getOrganizationId());
				logBean.setClientIp(loginUser.getClientIp());
			}
			logBean.setEndTime();
			logBean.setOperateType(5);
			logBean.setOperateContent("新增用户["+ids+"]");
			logBean.setEntityCode(ids);
			//日志
			LoggerFactory.getLoggerComponent().operateLog(logBean);*/
			retStr = "{\"success\":\"true\",\"message\":\"删除成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("删除用户失败");
			retStr = "{\"success\":\"false\",\"message\":\"删除失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}

	/**
	 * @throws IOException 
	 * @Title: userDaoru
	 * @Description: 用户信息同步
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userdaoru")
	public @ResponseBody
	String userDaoru(HttpServletRequest request, String org_id,
			String uploadFilePath){
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userDaoru]开始执行");
		}
		DBParameter par = new DBParameter();
		List<String> records = new ArrayList<String>();
		String path = FrameStartup.PROJECT_PATH + uploadFilePath;
		File file = new File(path);
		records = FileUploadUtil.getCsvData(file);
		UserInfoView userInfoView = new UserInfoView();
		LoginUserBean loginUser = (LoginUserBean) request.getSession()
				.getAttribute("LoginUser");
		par.setObject("create_user_code", loginUser.getUserId());		
		String create_user_code = orgUserService.getUserId(par);
		userInfoView.setCreate_user_name(create_user_code);
		userInfoView.setCreate_user(loginUser.getUserId());
		userInfoView.setPassword(Md5.encryptMd5("123456".getBytes()));
		userInfoView.setOrg_id(org_id);

		
		userInfoView.setBumen(userInfoView.getBumen());
		userInfoView.setDanwei(userInfoView.getDanwei());
		userInfoView.setTelephone(userInfoView.getTelephone());
		String flag = orgUserService.userDaoru(records, userInfoView);

		return flag;

	}

	/**
	 * @Title: userOrgchange
	 * @Description: 用户机构修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userorgchange")
	public @ResponseBody
	String userOrgchange(HttpServletRequest request, String org_id, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userOrgchange]开始执行");
		}
		if (org_id == null)
			return null;
//		UserInfoView loginUser = (UserInfoView) request.getSession()
//				.getAttribute("LoginUser");
		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(loginUser.getLogin_name());
//		logBean.setUserName(loginUser.getUser_name());
//		logBean.setOrganCode(loginUser.getOrg_id());
//		logBean.setOrganName(loginUser.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_USER);
//		logBean.setEntityCode(ids);
//
//		logBean.setOperateContent("修改用户所属机构信息,用户编号[" + ids + "]新机构编号[" + org_id
//				+ "]");

		Boolean flag = orgUserService.userOrgchange(org_id, ids);
		
		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			retStr = "{\"success\":\"true\",\"message\":\"修改成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("修改用户所属机构信息失败");
			retStr = "{\"success\":\"false\",\"message\":\"修改失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
		
	}

	/**
	 * @Title: userExport
	 * @Description: 用户导出
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("userExport")
	public @ResponseBody
	String userExport(HttpServletRequest request,HttpServletResponse response, UserInfoView userInfoView) {		
		if (log.isInfoEnabled()) {
			log.info("组件[OrgUserController][userExport]开始执行");
		}
//		Map condition = new HashMap();
//		condition.put("u", userInfoView);
		Date d = new Date();
//		String path=getSavePath()+"/"+new SimpleDateFormat("yyyyMMddHHmmss").format(d)+".xlsx";
		String path = FrameStartup.PROJECT_PATH + File.separator +
				getUploadFilePath();
		File filePath = new File(path);
		if(!filePath.exists()){
			filePath.mkdir();
		}
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(d)+".xlsx";
		fileName = orgUserService.getUserExportFile(userInfoView, path+fileName);
		
		
		return "{\"fileUrl\":\""+getUploadFilePath() + fileName+"\"}";
		
//		fileDownload(request, response, getUploadFilePath() + fileName);
		
//		File f = new File(path);
//		//System.out.println(path);
//		OutputStream out;
//		FileInputStream in;
//		try {			
//			in=new FileInputStream(path);
//		    response.setHeader("Content-Disposition", "attachment; filename=\"" + encode(fileName) + "\"");  
//		    response.setContentType("application/octet-stream;charset=UTF-8");
//		    out = response.getOutputStream();
//		    byte[] buf = new byte[1024];   
//		    for(int i; (i = in.read(buf)) > 0;) {
//		    	out.write(buf,0,i);
//			} 
//		    out.flush();  
//		    out.close();
//		    in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
	}
	
	
	public void fileDownload(HttpServletRequest request, HttpServletResponse response,
			String fileUrl) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		if (log.isDebugEnabled()) {
			log.debug("下载的附件名称为[" + fileUrl + "]");
		}

		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");

			// 判断下载路径是否授权
			if (!fileUrl.startsWith(getUploadFilePath())) {
				if (log.isErrorEnabled()) {
					log.error("下载的路径非法");
				}
				response.getWriter().println(
						" <script language=JavaScript>alert(\"" + "下载的路径非法"
								+ "\");</script>");
				return;
			}

			// 读取文件使用绝对路径
			fileUrl = FrameStartup.PROJECT_PATH + File.separator + fileUrl;
			// 判断下载文件是否存在
			if (!new File(fileUrl).exists()) {
				if (log.isErrorEnabled()) {
					log.error("下载的文件不存在");
				}
				response.getWriter().println(
						" <script language=JavaScript>alert(\"" + "下载的文件不存在"
								+ "\");</script>");
				return;
			}

			long fileLength = new File(fileUrl).length();

			if (log.isDebugEnabled()) {
				log.debug("下载文件大小为[" + fileLength + "]");
			}
			// response.setContentType("application/x-msdownload;");

			String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			if (log.isDebugEnabled()) {
				log.debug("下载文件名称为[" + fileName + "]");
			}

			// 解决中文乱码问题
			if (request.getHeader("User-Agent").toLowerCase()
					.indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}

			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(fileUrl));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("下载附件异常", e);
			}
			try {
				response.getWriter().println(
						" <script language=JavaScript>alert(\"" + "下载附件发生异常"
								+ "\");</script>");
			} catch (IOException e1) {
			}
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
				}
		}
	}
	
	/**
	 * 获取服务器上传目录
	 * 
	 * @return
	 */
	private String getUploadFilePath() {

		String uploadFilePath = SystemParameter.getInstance().getParameter(
				"uploadFilePath");

		if (null == uploadFilePath) {
			uploadFilePath = "/fileupload/";
		}
		
		return uploadFilePath;
	}
	
}

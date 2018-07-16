package com.xpoplarsoft.limits.newrole.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.limits.newrole.bean.Role;
import com.xpoplarsoft.limits.newrole.service.INewRoleService;

@Controller
@RequestMapping("/newrole")
public class NewRoleController {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(NewRoleController.class);
	@Autowired
	private INewRoleService newRoleService;
	

	/**
	 * @Title: findRoleQueryPage
	 * @Description: 角色列表信息（分页）
	 * @author jingkewen
	 * @throws
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	@RequestMapping("findrolequerypage")
	public @ResponseBody
	String findRoleQueryPage(HttpServletRequest request, String page,
			Role role) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][findRoleQueryPage]开始执行");
		}
		String json = "";
		Map condition = new HashMap();
		condition.put("r", role);
		condition.put("page", page);
		List<Role> roles = newRoleService.findRoleQueryPage(condition);
//		page.setRecords(roles);
		json = JSONObject.toJSONString(roles);
		return json;
	}



	/**
	 * @Title: findNoRole
	 * @Description: 未拥有的角色信息
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findnorole")
	public @ResponseBody
	String findNoRole(HttpServletRequest request,String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][findNoRole]开始执行");
		}
		String json = "";
		DBParameter param = new DBParameter();
		param.setObject("org_id", org_id);
		List<Role> result = newRoleService
				.findNoRole(param);
		String cou = newRoleService.getFindNoRoleCount(param);
		int total = Integer.parseInt(cou);
		json = JSONObject.toJSONString(result);
		return "{\"Rows\":"+json+",\"Total\":"+total+"}";
	}

	/**
	 * @Title: findHasRole
	 * @Description: 已拥有的角色信息
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findhasrole")
	public @ResponseBody
	String findHasRole(HttpServletRequest request, String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][findHasRole]开始执行");
		}
		String json = "";
		DBParameter param = new DBParameter();
		param.setObject("org_id", org_id);
		List<Role> result = newRoleService
				.findHasRole(param);
		String cou = newRoleService.getFindHasRoleCount(param);
		int total = Integer.parseInt(cou);
		json = JSONObject.toJSONString(result);
		return "{\"Rows\":"+json+",\"Total\":"+total+"}";
	}

	/**
	 * @Title: userGroupRoleUpdate
	 * @Description: 角色授权
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("usergrouproleupdate")
	public @ResponseBody
	String userGroupRoleUpdate(HttpServletRequest request,
			String org_id, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][userGroupRoleUpdate]开始执行");
		}
//		LoginBean userInfoView = (LoginBean) request.getSession()
//				.getAttribute("LoginUser");
		// 日志对象
//		OperateLogBean logBean = new OperateLogBean();
//		// 设置公共日志信息
//		logBean.setStartTime();
//		logBean.setUserCode(userInfoView.getLogin_name());
//		logBean.setUserName(userInfoView.getUser_name());
//		logBean.setOrganCode(userInfoView.getOrg_id());
//		logBean.setOrganName(userInfoView.getOrg_name());
//		logBean.setClientIp(request.getRemoteAddr());
//
//		// 填充日志记录内容
//		logBean.setOperateType(OperateLogBean.OPT_TYPE_ROLE);
//		logBean.setEntityCode(userGroupRole.getUg_id());
//
//		logBean.setOperateContent("用户组授权菜单信息,组编号[" + userGroupRole.getUg_id() + "]授权菜单资源编号["+ids+"]");
		
		boolean flag = newRoleService.userGroupRoleUpdate(org_id, ids);

		String retStr = null;
		if (flag) {
//			logBean.setResult(true);
			retStr = "{\"success\":\"true\",\"message\":\"授权成功\"}";
		} else {
//			logBean.setResult(false);
//			logBean.setFaultReason("用户组授权菜单信息失败");
			retStr = "{\"success\":\"false\",\"message\":\"授权失败\"}";
		}
//		logBean.setEndTime();
//		LoggerFactory.getLoggerComponent().operateLog(logBean);

		return retStr;
	}
	
	
	/**
	 * @Title: userGroupRoleDelete
	 * @Description: 取消角色授权
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("usergrouproledelete")
	public @ResponseBody
	String userGroupRoleDelete(HttpServletRequest request,
			String org_id, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][usergrouproledelete]开始执行");
		}
//		LoginBean userInfoView = (LoginBean) request.getSession()
//				.getAttribute("LoginUser");
		
		boolean flag = newRoleService.userGroupRoleDelete(org_id, ids);

		String retStr = null;
		if (flag) {
			retStr = "{\"success\":\"true\",\"message\":\"权限移除成功\"}";
		} else {
			retStr = "{\"success\":\"false\",\"message\":\"权限移除失败\"}";
		}

		return retStr;
	}

	@RequestMapping("findRoleByRoleName")
	public @ResponseBody
	String findRoleByRoleName(HttpServletRequest request, String roleName) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleController][findRoleByRoleName]开始执行");
		}
		String json = "";
		List<Map<String, String>> result = newRoleService
				.findRoleByRoleName(roleName);
		json = JSONObject.toJSONString(result);
		return "{\"result\":" + json + "}";
	}
}

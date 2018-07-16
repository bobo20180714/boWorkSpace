package com.xpoplarsoft.limits.role.action;

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
import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.limits.role.bean.PoplarRole;
import com.xpoplarsoft.limits.role.bean.PoplarRoleRes;
import com.xpoplarsoft.limits.role.bean.RoleWhere;
import com.xpoplarsoft.limits.role.service.IRoleService;

/**
 * 类功能: 角色管理连接器类
 * 
 * @author 王晓东
 * @date 2015-01-20
 */
@Controller
@RequestMapping("/RoleAction")
public class RoleAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(RoleAction.class);

	@Autowired
	private IRoleService roleService;
	

	private Gson gson = new Gson();

	/**
	 * 新增角色
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param bean
	 * @return string
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody
	String add(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PoplarRole bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][add]开始执行");
		}
		ResultBean tempResult = new ResultBean();
		//判断角色名称是否重复
		if(roleService.judgeRoleNameExit(bean.getRoleName(),null)){
			tempResult.setSuccess("false");
			tempResult.setMessage("角色名称已经存在！");
			return gson.toJson(tempResult);
		}
		
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String opera_time = DateTools.getCurryDateTime().substring(0, 19);
		bean.setUpdateUserCode(loginUser == null ? "" : loginUser.getUserId());
		bean.setUpdateTime(opera_time);
		String id = FlowNoFactory.getFlowNoComponent().getFlowNo();
		bean.setPkId(id);
		bean.setRoleCode(id);
		tempResult = roleService.add(bean);
		String result = gson.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 修改角色
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param bean
	 * @return string
	 */
	@RequestMapping(value = "/alter")
	public @ResponseBody
	String alter(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PoplarRole bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][addRole]开始执行");
		}
		ResultBean tempResult = new ResultBean();
		//判断角色名称是否重复
		if(roleService.judgeRoleNameExit(bean.getRoleName(),bean.getRoleCode())){
			tempResult.setSuccess("false");
			tempResult.setMessage("角色名称已经存在！");
			return gson.toJson(tempResult);
		}
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String opera_time = DateTools.getCurryDateTime().substring(0, 19);
		bean.setUpdateUserCode(loginUser == null ? "" : loginUser.getUserId());
		bean.setUpdateTime(opera_time);
		tempResult = roleService.alter(bean);
		String result = gson.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 * @param roleCodes
	 *            角色编号集合
	 * @return string
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody
	String delete(HttpServletRequest request, HttpServletResponse response,
			String roleCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][deleteRole]开始执行");
		}

		// 角色编号集合
		String[] roleList = roleCodes.split(",");

		Gson json = new Gson();

		ResultBean tempResult = roleService.delete(roleList);
		return json.toJson(tempResult);
	}

	/**
	 * 启用
	 * 
	 * @param userCodes
	 * @return
	 */
	@RequestMapping(value = "/start")
	public @ResponseBody
	String start(String roleCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][start]开始执行");
		}
		// 角色编号集合
		String[] roleList = roleCodes.split(",");

		ResultBean tempResult = roleService.start(roleList);

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
	@RequestMapping(value = "/stop")
	public @ResponseBody
	String stop(String roleCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][stop]开始执行");
		}
		// 角色编号集合
		String[] roleList = roleCodes.split(",");

		ResultBean tempResult = roleService.stop(roleList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 根据角色编码获取单条角色信息
	 * 
	 * @param request
	 * @param response
	 * @param roleCode
	 * @return
	 */
	@RequestMapping(value = "/getRoleListByRole")
	public @ResponseBody
	String getRoleListByRole(HttpServletRequest request,
			HttpServletResponse response, String roleCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][getRoleListByRole]开始执行");
		}
		String result = roleService.getRoleListByRole(roleCode);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 根据角色编码获取资源树
	 * 
	 * @param request
	 * @param response
	 * @param roleCode
	 * @return
	 */
	@RequestMapping(value = "/getResTreeByRoleCode")
	public @ResponseBody
	String getResTreeByRoleCode(HttpServletRequest request,
			HttpServletResponse response, String roleCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][getResTreeByRoleCode]开始执行");
		}

		// 从数据库得到列表
		ResultBean tempResult = roleService.getResTreeByRoleCode(roleCode);
		// 将数据转为json
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}

	/**
	 * 获取角色列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getRoleList")
	public @ResponseBody
	String getRoleList(HttpServletRequest request,
			HttpServletResponse response,RoleWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][getRoleList]开始执行");
		}
		Map map = roleService.getRoleList(where);
		Gson json = new Gson();
		String result = null;
		if (map != null) {
			result = json.toJson(map);
		}
		return result;
	}

	/**
	 * 更新角色权限
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateRoleRes")
	public @ResponseBody
	String updateRoleRes(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			PoplarRoleRes bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][updateRoleRes]开始执行");
		}
		ResultBean tempResult = roleService.updateRoleRes(bean);

		// 将数据转为json
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}

	/**
	 * 根据用户编码获取角色信息集合
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getRoleListByUserCode")
	public @ResponseBody
	String getRoleListByUserCode(HttpServletRequest request,
			HttpServletResponse response, String userAccount) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][getRoleListByUserCode]开始执行");
		}

		String result  = roleService.getRoleListByUserCode(userAccount);
		return result;
	}

	/**
	 * 绑定用户、角色关系
	 * 
	 * @param request
	 * @param response
	 * @param userAccount
	 * @param roleCodes
	 * @return
	 */
	@RequestMapping(value = "/alterUserRole")
	public @ResponseBody
	String alterUserRole(HttpServletRequest request,
			HttpServletResponse response, String userAccount, String roleCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleAction][alterUserRole]开始执行");
		}

		ResultBean tempResult = roleService.alterUserRole(userAccount,
				roleCodes);
		// 将数据转为json
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}

}

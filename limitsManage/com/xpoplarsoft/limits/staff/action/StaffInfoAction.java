package com.xpoplarsoft.limits.staff.action;

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
import com.xpoplarsoft.limits.staff.bean.StaffInfo;
import com.xpoplarsoft.limits.staff.service.IStaffInfoService;

/**
 * 员工信息控制类
 * 
 * @author 王晓东
 * @date 2014-12-31
 */
@Controller
@RequestMapping("/StaffInfoAction")
public class StaffInfoAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(StaffInfoAction.class);

	@Autowired
	private IStaffInfoService service;

	/**
	 * 添加员工信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addStaff")
	public @ResponseBody
	String addStaff(HttpServletRequest request, HttpServletResponse response,
			StaffInfo staff,HttpSession session) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoAction][addStaff]开始执行");
		}
		LoginUserBean user= (LoginUserBean) session.getAttribute("LoginUser");
		Gson json = new Gson();

		String operate_time = DateTools.getCurryDateTime().substring(0, 19);
		staff.setUpdateTime(operate_time);
		staff.setPkId(FlowNoFactory.getFlowNoComponent().getFlowNo());
		staff.setUpdateUserCode(user == null ? "" : user.getUserId());
		ResultBean rb = service.addStaff(staff);
		String result = json.toJson(rb);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 根据员工编号查询员工信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStaffByCode")
	public @ResponseBody
	String getStaffByCode(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoAction][getStaffByCode]开始执行");
		}

		String staffCode = request.getParameter("staffCode");

		String result = service.getStaffByCode(staffCode);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;

	}

	/**
	 * 根据员工编号修改员工信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateStaffByCode")
	public @ResponseBody
	String updateStaffByCode(HttpServletRequest request,
			HttpServletResponse response,StaffInfo staff,HttpSession session) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoAction][updateStaffByCode]开始执行");
		}
		LoginUserBean user = (LoginUserBean) request.getSession().getAttribute(
				"LoginUser");
		
		String operate_time = DateTools.getCurryDateTime().substring(0, 19);
		staff.setUpdateTime(operate_time);
		staff.setUpdateUserCode(user == null ? "" : user.getUserId());
		ResultBean rb = service.updateStaffByCode(staff);
		Gson gson = new Gson();
		String result = gson.toJson(rb);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 根据员工编号删除员工信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteStaffByCode")
	public @ResponseBody
	String deleteStaffByCode(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoAction][deleteStaffByCode]开始执行");
		}
		Gson gson = new Gson();
		String staffCodes = request.getParameter("staffCodes");
		ResultBean rb = service.deleteStaffByCode(staffCodes);
		String result = gson.toJson(rb);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;

	}
	
	/**
	 * 关联员工和用户信息
	 * 
	 * 
	 */
	@RequestMapping(value = "/linkStaff")
	public @ResponseBody
	String linkStaff(HttpServletRequest request,HttpServletResponse response,String staff_id,
			String userIds) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoAction][linkStaff]开始执行");
		}
	    ResultBean rb = service.linkStaff(staff_id,userIds);
	    Gson gson = new Gson();
		String result = gson.toJson(rb);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	
	}
	
}

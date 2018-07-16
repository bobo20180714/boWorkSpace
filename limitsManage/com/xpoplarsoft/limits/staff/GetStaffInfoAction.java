package com.xpoplarsoft.limits.staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.action.Action;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.staff.bean.StaffWhere;

/**
 * 员工信息控制类
 * 
 * @author 王晓东
 * @date 2014-12-31
 */
@Controller
@RequestMapping("/GetStaffInfoAction")
public class GetStaffInfoAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(GetStaffInfoAction.class);

	@Autowired
	private IGetStaffInfoSer service;

	/**
	 * 查询员工
	 * 
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/staffList")
	public @ResponseBody
	String queryUsers(HttpServletRequest request, HttpServletResponse response,
			StaffWhere where ) {

		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][staffList]开始执行");
		}
		// 调用service层，查询用户
		ResultBean result = service.staffList(where);

		// 转化为JSON
		Gson gson = new Gson();
		String returnJson = gson.toJson(result.getData());

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + returnJson + "]");
		}
		return returnJson;
	}

	@RequestMapping(value = "/getStaffByOrgID")
	public @ResponseBody
	String getStaffByOrgID(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByOrgID]开始执行");
		}
		String org_id = request.getParameter("org_id");
		String result = service.getStaffByOrgID(org_id);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	

	@RequestMapping(value = "/getStaffByOrgName")
	public @ResponseBody
	String getStaffByOrgName(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByOrgName]开始执行");
		}
		String org_name = request.getParameter("org_name");
		String result = service.getStaffByOrgName(org_name);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@RequestMapping(value = "/getStaffByOrgCode")
	public @ResponseBody
	String getStaffByOrgCode(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByOrgCode]开始执行");
		}
		String org_code = request.getParameter("org_code");
		String result = service.getStaffByOrgCode(org_code);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@RequestMapping(value = "/getStaffBySex")
	public @ResponseBody
	String getStaffBySex(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffBySex]开始执行");
		}
		String sex = request.getParameter("sex");
		if ("男".equals(sex)) {
			sex = "0";
		} else if ("女".equals(sex)) {
			sex = "1";
		}
		String result = service.getStaffBySex(sex);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@RequestMapping(value = "/getStaffByName")
	public @ResponseBody
	String getStaffByName(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByName]开始执行");
		}
		String name = request.getParameter("name");
		String result = service.getStaffByName(name);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@RequestMapping(value = "/getStaffByAge")
	public @ResponseBody
	String getStaffByAge(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByAge]开始执行");
		}
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String result = service.getStaffByAge(start, end);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@RequestMapping(value = "/getStaffByJobCode")
	public @ResponseBody
	String getStaffByJobCode(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][getStaffByJobCode]开始执行");
		}
		String job_name = request.getParameter("job_name");
		String result = service.getStaffByJobCode(job_name);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	/**
	 * 选择员工
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectStaff")
	public @ResponseBody
	String selectStaff(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][selectStaff]开始执行");
		}
		String pk_ids = request.getParameter("pk_ids");
		String org_id = request.getParameter("org_id");
		ResultBean tempResult = service.selectStaff(pk_ids, org_id);
		Gson json = new Gson();
		String result = json.toJson(tempResult);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 移除员工
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeStaff")
	public @ResponseBody
	String removeStaff(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[GetStaffInfoAction][removeStaff]开始执行");
		}
		String pk_ids = request.getParameter("pk_ids");
		String company_id = request.getParameter("company_id");
		ResultBean tempResult = service.removeStaff(pk_ids, company_id);
		Gson json = new Gson();
		String result = json.toJson(tempResult);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

}

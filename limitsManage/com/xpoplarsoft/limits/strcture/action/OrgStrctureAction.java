package com.xpoplarsoft.limits.strcture.action;

import java.util.List;
import java.util.Map;

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
import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.limits.strcture.bean.OrgStructure;
import com.xpoplarsoft.limits.strcture.service.IOrgStrctureService;

/**
 * 组织机构管理的连接器
 * 
 * @author王晓东
 * @date 2015-01-06
 */
@Controller
@RequestMapping("/orgStrcture")
public class OrgStrctureAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OrgStrctureAction.class);

	@Autowired
	private IOrgStrctureService service;

	/**
	 * 添加组织机构信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/addStructure")
	public @ResponseBody
	String addStrcture(HttpServletRequest request,
			HttpServletResponse response, OrgStructure org) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][addStructure]开始执行");
		}
		LoginUserBean user = (LoginUserBean) request.getSession().getAttribute("user");
		Gson json = new Gson();
		String update_time = DateTools.getCurryDateTime().substring(0,19);
		org.setUpdateTime(update_time);
		org.setPkId(FlowNoFactory.getFlowNoComponent().getFlowNo());
		org.setUpdateUserID(user == null ? "" : user.getUserId());
		ResultBean tempResult = service.addStructure(org);

		String result = json.toJson(tempResult);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 更新组织机构信息
	 * 
	 * @param request
	 *            ,response
	 * @return 执行结果javabean转成的json串
	 */
	@RequestMapping(value = "/updateStructure")
	public @ResponseBody
	String updateStrcture(HttpServletRequest request,
			HttpServletResponse response, OrgStructure org) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][updateStructure]开始执行");
		}
		LoginUserBean user = (LoginUserBean) request.getSession().getAttribute("user");
		String update_time = DateTools.getCurryDateTime().substring(0, 19);
		org.setUpdateUserID(user == null ? "" : user.getUserId());
		org.setUpdateTime(update_time);
		ResultBean tempResult = service.updateStructure(org);
		Gson json = new Gson();
		String result = json.toJson(tempResult);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 删除组织机构
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteStructure")
	public @ResponseBody
	String deleteStrcture(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][deleteStrcture]开始执行");
		}
		String pk_id = request.getParameter("pk_id");
		String company_id = request.getParameter("company_id");
		ResultBean tempResult = service.deleteStrcture(pk_id, company_id);
		Gson json = new Gson();
		String result = json.toJson(tempResult);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 查看机构信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewStructure")
	public @ResponseBody
	String getTopOrg(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][viewStructure]开始执行");
		}
		String id = request.getParameter("pk_id");
		String result = service.viewStructure(id);
		return result;
	}

	/**
	 * 验证机构编号是否重复
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/checkCode")
	public @ResponseBody
	String checkCode(HttpServletRequest request, HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][checkCode]开始执行");
		}
		String orgCode = request.getParameter("org_code");
		ResultBean resources = service.checkCode(orgCode);
		Gson json = new Gson();
		String result = json.toJson(resources);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 获取全部组织结构详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStrctureList")
	public @ResponseBody
	String getStrctureList(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureAction][getStructureList]开始执行");
		}

		List<Map<String, Object>> list = service.getStrctureList();
		Gson json = new Gson();
		String result = json.toJson(list);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	
}

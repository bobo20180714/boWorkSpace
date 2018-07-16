package com.xpoplarsoft.limits.strcture;

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

/**
 * 组织机构管理的连接器
 * 
 * @author王晓东
 * @date 2015-01-06
 */
@Controller
@RequestMapping("/organization")
public class OrganizationAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OrganizationAction.class);

	@Autowired
	private IOrganizationService service;

	
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
			log.info("组件[OrganizationAction][getStructureList]开始执行");
		}

		List<Map<String, Object>> list = service.getStrctureList();
		Gson json = new Gson();
		String result = json.toJson(list);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	/**
	 * 根据机构名称获取全部组织结构详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStrctureByName")
	public @ResponseBody
	String getStrctureByName(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrganizationAction][getStrctureByName]开始执行");
		}
		String org_name = request.getParameter("org_name");
		List<Map<String, Object>> list = service.getStrctureByName(org_name);
		Gson json = new Gson();
		String result = json.toJson(list);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	/**
	 * 根据机构编码获取全部组织结构详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStrctureByCode")
	public @ResponseBody
	String getStrctureByCode(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrganizationAction][getStrctureByCode]开始执行");
		}
		String org_code = request.getParameter("org_code");
		List<Map<String, Object>> list = service.getStrctureByCode(org_code);
		Gson json = new Gson();
		String result = json.toJson(list);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}
	
	/**
	 * 根据机构ID取全部组织结构详细信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStrctureByID")
	public @ResponseBody
	String getStrctureByID(HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrganizationAction][getStrctureByID]开始执行");
		}
		String org_id = request.getParameter("org_id");
		List<Map<String, Object>> list = service.getStrctureByID(org_id);
		Gson json = new Gson();
		String result = json.toJson(list);

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	
}

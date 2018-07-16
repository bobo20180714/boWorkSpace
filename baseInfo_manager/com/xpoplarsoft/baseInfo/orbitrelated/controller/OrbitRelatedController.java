package com.xpoplarsoft.baseInfo.orbitrelated.controller;

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

import cn.springmvc.model.EngineRegistrationBean;
import cn.springmvc.service.basicconfig.engineregistration.IEngineRegistrationService;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedFieldBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.PageView;
import com.xpoplarsoft.baseInfo.orbitrelated.service.IOrbitRelatedService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.spring.SpringBeanFactory;
import com.xpoplarsoft.query.engine.IEngine;
import com.xpoplarsoft.query.engine.register.RegisterManager;

/**
 * 在轨相关
 * @author mxc
 *
 */
@Controller
@RequestMapping("/orbitrelated")
public class OrbitRelatedController {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OrbitRelatedController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IOrbitRelatedService orbitRelatedService;
	
	/**
	 * 查询航天器相关信息列表
	 * @param request
	 * @param satId
	 * @return
	 */
	@RequestMapping("findOrbitrelated")
	public @ResponseBody
	String findOrbitrelated(HttpServletRequest request, String satId,String key) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findOrbitrelated]开始执行");
		}
		key = key==null?"":key;
		List<OrbitRelatedBean> findOrbitrelated = orbitRelatedService
				.findOrbitrelatedList(satId,key);
		return gson.toJson(findOrbitrelated);
	}
	
	/**
	 * 根据卫星查询卫星相关信息列表
	 * @param request
	 * @param satId
	 * @return
	 */
	@RequestMapping("getRelatedBySatId")
	public @ResponseBody
	String getRelatedBySatId(HttpServletRequest request, String satId) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][getRelatedBySatId]开始执行");
		}
		//查询已经关联的卫星相关信息
		List<OrbitRelatedBean> findOrbitrelated = orbitRelatedService.findOrbitrelatedList(satId,"");
		return gson.toJson(findOrbitrelated);
	}
	@RequestMapping("findAllOrbitrelatedList")
	public @ResponseBody
	String findAllOrbitrelatedList(HttpServletRequest request) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findAllOrbitrelatedList]开始执行");
		}
		//查询所有的卫星相关信息
		List<Map<String, Object>> allRelate = orbitRelatedService.findAllOrbitrelatedList();
		Map<String,Object> rsMap = new HashMap<String, Object>();
		rsMap.put("Rows", allRelate);
		return gson.toJson(rsMap);
	}
	
	/**
	 * 新增卫星和相关信息关联关系
	 * @param request
	 * @param satId
	 * @param jsjgId
	 * @return
	 */
	@RequestMapping("addSatRelatedInfo")
	public @ResponseBody
	String addSatRelatedInfo(HttpServletRequest request, String satId, String jsjgId) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][addSatRelatedInfo]开始执行");
		}
		ResultBean bean = new ResultBean();
		//新增卫星和相关信息关联关系
		boolean flag = orbitRelatedService.addSatRelatedInfo(satId,jsjgId);
		if(flag){
			//创建表
			orbitRelatedService.createTabel(jsjgId, satId);
			enginUpdate(satId);
			bean.setSuccess("true");
		}else{
			bean.setSuccess("false");
		}
		return gson.toJson(bean);
	}
	
	/**
	 * 移除卫星和相关信息关联关系
	 * @param request
	 * @param satId
	 * @param jsjgId
	 * @return
	 */
	@RequestMapping("removeSatRelatedInfo")
	public @ResponseBody
	String removeSatRelatedInfo(HttpServletRequest request, String satId, String jsjgId) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][removeSatRelatedInfo]开始执行");
		}
		ResultBean bean = new ResultBean();
		//新增卫星和相关信息关联关系
		boolean flag = orbitRelatedService.removeSatRelatedInfo(satId,jsjgId);
		if(flag){
			bean.setSuccess("true");
		}else{
			bean.setSuccess("false");
		}
		return gson.toJson(bean);
	}
	
	/**
	 * 查询航天器相关信息字段列表数据
	 * @param request
	 * @param relateId
	 * @return
	 */
	@RequestMapping("findFieldList")
	public @ResponseBody
	String findFieldList(HttpServletRequest request, String relateId) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findField]开始执行");
		}
		List<OrbitRelatedFieldBean> fieldList = orbitRelatedService
				.findOrbitRelatedFieldList(relateId);
		return gson.toJson(fieldList);
	}
	
	@RequestMapping("judgeIsHaveField")
	public @ResponseBody
	String judgeIsHaveField(HttpServletRequest request, String relateId) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][judgeIsHaveField]开始执行");
		}
		List<OrbitRelatedFieldBean> fieldList = orbitRelatedService
				.findOrbitRelatedFieldList(relateId);
		ResultBean rb = new ResultBean();
		if(fieldList.size() > 0){
			rb.setSuccess("true");
		}else {
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 分页查询航天器相关信息
	 * @param request
	 * @param satId
	 * @return
	 */
	@RequestMapping(value="/queryOrbitRelatedByPage")
	public @ResponseBody
	String queryOrbitRelatedByPage(HttpServletRequest request, String satId,CommonBean bean) {
		Map<String, Object> result = orbitRelatedService.queryOrbitRelatedByPage(satId,bean);
		return gson.toJson(result);
	}
	/**
	 * 分页查询已启用的航天器相关信息
	 * @param request
	 * @param satId
	 * @return
	 */
	@RequestMapping(value="/queryStartOrbitRelatedByPage")
	public @ResponseBody
	String queryStartOrbitRelatedByPage(HttpServletRequest request, String satId,CommonBean bean) {
		Map<String, Object> result = orbitRelatedService.queryStartOrbitRelatedByPage(satId,bean);
		return gson.toJson(result);
	}
	
	/**
	 * 分页查询航天器相关信息字段信息
	 * @param request
	 * @param page
	 * @param jsjg_id
	 * @return
	 */
	@RequestMapping(value="/queryOrbitFieldByPage")
	public @ResponseBody
	String queryOrbitFieldByPage(HttpServletRequest request,
			String jsjg_id,CommonBean bean) {
		Map<String,Object> rsMap = orbitRelatedService.queryOrbitFieldByPage(bean,jsjg_id);
		return gson.toJson(rsMap);
	}
	
	/**
	 * ext查询航天器相关字段信息
	 * @param request
	 * @param page
	 * @param jsjg_id
	 * @return
	 */
	@SuppressWarnings( { "rawtypes" })
	@RequestMapping("findorbitrelatedfieldquerypage")
	public @ResponseBody
	String findOrbitRelatedFieldQueryPage(HttpServletRequest request,
			PageView page, String jsjg_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findOrbitRelatedFieldQueryPage]开始执行");
		}
		if (jsjg_id == null)
			return null;
		CommonBean cb = new CommonBean();
		cb.setPage(page.getPage());
		cb.setPagesize(page.getLimit());
		Map<String,Object> rsMap = orbitRelatedService.queryOrbitFieldByPage(cb,jsjg_id);
		page.setRecords((List)rsMap.get("Rows"));
		page.setRowCount(Long.parseLong(rsMap.get("Total").toString()));
		return gson.toJson(page);
	}
	
	@RequestMapping(value="/findorbitrelatedfieldlist")
	public @ResponseBody
	String findOrbitRelatedFieldList(HttpServletRequest request,
			PageView page, String jsjg_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findOrbitRelatedFieldList]开始执行");
		}
		String json = "";
		if (jsjg_id == null)
			return null;
		List<OrbitRelatedFieldBean> orbitRelatedFieldBeans = orbitRelatedService
				.findOrbitRelatedFieldList(jsjg_id);
		page.setRecords(orbitRelatedFieldBeans);
		json = JSONObject.toJSONString(page);
		return json;
	}
	
	/**
	 * 新增航天器相关信息
	 * @param request
	 * @param OrbitRelatedBean
	 * @return
	 */
	@RequestMapping(value="/addRelated")
	public @ResponseBody
	String addRelated(HttpServletRequest request,OrbitRelatedBean bean) {
		ResultBean rb = new ResultBean();
		
		//判断信息编号是否重复
		boolean exit = judgeCode(bean.getSys_resource_id(),bean.getJsjg_code());
		if(exit){
			rb.setSuccess("false");
			rb.setMessage("信息编号已经存在！");
			return gson.toJson(rb);
		}
		
		bean.setJsjg_id(getPkId());
		boolean flag = orbitRelatedService.addRelated(bean);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("保存成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("保存失败！");
		}
		return gson.toJson(rb);
	}
	
	public int getPkId() {
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo("select PK_SEQ.nextval pk_id from dual");
		if(dbr != null && dbr.getRows() > 0){
			return Integer.parseInt(dbr.get(0, "pk_id"));
		}
		return -1;
	}
	
	/**
	 * 查看航天器相关信息
	 * @param request
	 * @param jsjg_id
	 * @return
	 */
	@RequestMapping(value="/viewRelated")
	public @ResponseBody
	String viewRelated(HttpServletRequest request,String jsjg_id) {
		Map<String, Object> flag = orbitRelatedService.viewRelated(jsjg_id);
		return gson.toJson(flag);
	}
	/**
	 * 启用航天器相关信息
	 * @param request
	 * @param jsjgId
	 * @return
	 */
	@RequestMapping(value="/startRelated")
	public @ResponseBody
	String startRelated(HttpServletRequest request,String jsjgId) {
		ResultBean rb = new ResultBean();
		boolean flag = orbitRelatedService.startRelated(jsjgId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}

	/**
	 * 修改航天器相关信息
	 * @param request
	 * @param OrbitRelatedBean
	 * @return
	 */
	@RequestMapping(value="/updateRelated")
	public @ResponseBody
	String updateRelated(HttpServletRequest request,OrbitRelatedBean bean) {
		ResultBean rb = new ResultBean();
		
		//判断编号是否已经存在
		boolean flag = orbitRelatedService.judgeCodeIsExit(bean.getJsjg_id(),bean.getJsjg_code());
		if(flag){
			rb.setSuccess("false");
			rb.setMessage("信息编号已经存在！");
			return gson.toJson(rb);
		}
		flag = orbitRelatedService.updateRelated(bean);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("修改信息成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("修改信息成功！");
		}
		return gson.toJson(rb);
	}

	/**
	 * 修改航天器相关信息状态
	 * @param request
	 * @param jsjgIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/updateRelatedStatus")
	public @ResponseBody
	String updateRelatedStatus(HttpServletRequest request,String jsjgIds,String status) {
		ResultBean rb = new ResultBean();
		
		String[] jsjgArr = jsjgIds.split(",");
		
		boolean flag = orbitRelatedService.updateRelatedStatus(jsjgArr,status);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("修改信息成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("修改信息成功！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 新增航天器相关字段信息
	 * @param request
	 * @param OrbitRelatedFieldBean
	 * @return
	 */
	@RequestMapping(value="/addField")
	public @ResponseBody
	String addField(HttpServletRequest request,OrbitRelatedFieldBean bean) {
		ResultBean rb = new ResultBean();
		
		//判断字段名称是否重复
		boolean exit = orbitRelatedService.judgeFieldName(bean.getJsjg_id(),bean.getField_code(),"");
		if(exit){
			rb.setSuccess("false");
			rb.setMessage("字段英文编号已经存在！");
			return gson.toJson(rb);
		}
		
		boolean flag = orbitRelatedService.addField(bean);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("保存成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("保存失败！");
		}
		return gson.toJson(rb);
	}

	/**
	 * 查看航天器相关字段信息
	 * @param request
	 * @param field_id
	 * @return
	 */
	@RequestMapping(value="/viewField")
	public @ResponseBody
	String viewField(HttpServletRequest request,String field_id) {
		Map<String, Object> flag = orbitRelatedService.viewField(field_id);
		return gson.toJson(flag);
	}
	
	/**
	 * 修改航天器相关字段信息
	 * @param request
	 * @param OrbitRelatedBean
	 * @return
	 */
	@RequestMapping(value="/updateField")
	public @ResponseBody
	String updateField(HttpServletRequest request,OrbitRelatedFieldBean bean) {
		ResultBean rb = new ResultBean();
		
		//判断字段名称是否重复
		boolean exit = orbitRelatedService.judgeFieldName(bean.getJsjg_id(),bean.getField_code(),bean.getField_id());
		if(exit){
			rb.setSuccess("false");
			rb.setMessage("字段英文编号已经存在！");
			return gson.toJson(rb);
		}
		
		boolean flag = orbitRelatedService.updateField(bean);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("修改信息成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("修改信息成功！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 修改航天器相关字段信息状态
	 * @param request
	 * @param fieldIds
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/updateFieldStatus")
	public @ResponseBody
	String updateFieldStatus(HttpServletRequest request,String fieldIds,String status) {
		ResultBean rb = new ResultBean();
		
		String[] fieldIdArr = fieldIds.split(",");
		
		boolean flag = orbitRelatedService.updateFieldStatus(fieldIdArr,status);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("修改信息成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("修改信息成功！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 判断编号是否已经存在
	 * @param satId
	 * @param jsjg_code
	 * @return
	 */
	private boolean judgeCode(String satId,String jsjg_code) {
		Map<String, Object> map = orbitRelatedService.queryOrbitRelatedByCode(satId, jsjg_code);
		if(map != null && map.size() > 0 && !"1".equals(map.get("jsjg_status"))){
			//已经存在
			return true;
		}
		return false;
	}

	/**
	 * @Title: findRoleByType
	 * @Description: 根据类型获取操作内容
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping(value="/findrolebytype")
	public @ResponseBody
	String findRoleByType(HttpServletRequest request, String type) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrbitRelatedController][findRoleByType]开始执行");
		}
		if (type == null)
			return null;
		if (type.equals("3")) {
			return "[{role:'等于', value:'='},{role:'不等于', value:'!='},{role:'包含', value:'like'},{role:'不包含', value:'not like'}]";
		} else if (type.equals("1") || type.equals("0")) {
			return "[{role:'>', value:'>'},{role:'<', value:'<'},{role:'=', value:'='},{role:'>=', value:'>='},{role:'<=', value:'<='},{role:'!=', value:'!='}]";
		} else if (type.equals("2")) {
			return "[{role:'早于', value:'<'},{role:'晚于', value:'>'},{role:'等于', value:'='}]";
		}
		return null;
	}
	
	private void enginUpdate(String sat_id){
		IEngineRegistrationService engineRegistrationService = (IEngineRegistrationService) SpringBeanFactory
				.getBean("engineRegistrationService");

		List<EngineRegistrationBean> beans = engineRegistrationService
				.engineRegistrationQueryPage(null);
//		List<EngineRegistrationBean> beans = new ArrayList<EngineRegistrationBean>();
		for (EngineRegistrationBean b : beans) {
			String engineClass = b.getEngine_class();

			try {
				IEngine engine = (IEngine) Class.forName(engineClass)
						.newInstance();

				// 加载引擎到内存
				RegisterManager.getRegisterComponent().engineUpdate(sat_id);

				if (log.isInfoEnabled()) {
					log.info("引擎[" + engineClass + "]加载完成");
				}
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("引擎[" + engineClass + "]加载失败！", e);
				}
			}
		}
	}
}

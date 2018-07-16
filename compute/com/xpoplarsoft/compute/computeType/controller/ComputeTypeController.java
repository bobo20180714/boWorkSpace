package com.xpoplarsoft.compute.computeType.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.compute.computeType.bean.ComputeTypeBean;
import com.xpoplarsoft.compute.computeType.service.IComputeTypeService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;

@Controller
@RequestMapping("/ComputeType")
public class ComputeTypeController {

	private static Log log = LogFactory.getLog(ComputeTypeController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IComputeTypeService service;

	@RequestMapping("/add")
	public @ResponseBody String addComputeType(ComputeTypeBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][addComputeType]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.addComputeType(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][addComputeType]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 添加类型和控制计算关系
	 * @param typeId
	 * @param computeId
	 * @return
	 */
	@RequestMapping("/addRelation")
	public @ResponseBody String addRelation(String typeId,String computeId,
			int overTime,int computeCount,int isSaveResult,int isMulticast){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][addRelation]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.addRelation(typeId,computeId,
				overTime,computeCount,isSaveResult,isMulticast);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][addRelation]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 修改最大失败次数、超时时间
	 * @param typeId
	 * @param computeId
	 * @return
	 */
	@RequestMapping("/updateRelation")
	public @ResponseBody String updateRelation(String relateId,int overTime,int computeCount,int isMulticast){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][updateRelation]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		boolean flag = service.updateRelation(relateId,overTime,computeCount,isMulticast);
		if(flag){
			rb.setSuccess("true");

		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][updateRelation]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 删除类型和控制计算关系
	 * @param typeId
	 * @param computeId
	 * @return
	 */
	@RequestMapping("/deleteRelationByPkId")
	public @ResponseBody String deleteRelationByPkId(String pkId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteRelationByPkId]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteRelationByPkId(pkId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteRelationByPkId]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 删除类型和控制计算关系
	 * @param typeId
	 * @param computeId
	 * @return
	 */
	@RequestMapping("/deleteRelation")
	public @ResponseBody String deleteRelation(String typeId,String computeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteRelation]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteRelation(typeId,computeId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteRelation]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 查询已经有的计算功能
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/getRelatedByTypeId")
	public @ResponseBody String getRelatedByTypeId(String satId,String typeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][getRelatedByTypeId]开始执行！");
		}
		List<Map<String,Object>> flag = service.getRelatedByTypeId(satId,typeId);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][getRelatedByTypeId]执行完成！");
		}
		return gson.toJson(flag);
	}
	
	/**
	 * 分页查询已经有的计算功能
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/pageQueryFuncByTypeId")
	public @ResponseBody String pageQueryFuncByTypeId(String satId,String typeId,String computeName,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][pageQueryFuncByTypeId]开始执行！");
		}
		Map<String,Object> flag = service.pageQueryFuncByTypeId(satId,typeId,computeName,bean);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][pageQueryFuncByTypeId]执行完成！");
		}
		return gson.toJson(flag);
	}
	
	@RequestMapping("/update")
	public @ResponseBody String updateComputeType(ComputeTypeBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][updateComputeType]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.updateComputeType(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][updateComputeType]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("/list")
	public @ResponseBody String computeTypeList(String computeName,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][computeTypeList]开始执行！");
		}
		Map<String,Object> rsMap = service.computeTypeList(computeName,bean);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][computeTypeList]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	@RequestMapping("/queryComputeTypeTree")
	public @ResponseBody String queryComputeTypeTree(String ownerId,HttpSession session){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][queryComputeTypeTree]开始执行！");
		}
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		List<Map<String, Object>> rsMap = new ArrayList<Map<String, Object>>();
		if(managerAccount.equals(userAccount)){
			rsMap = service.queryAllComputeTypeTree(ownerId,loginUser.getUserId());
		}else{
			rsMap = service.queryComputeTypeTree(ownerId,loginUser.getUserId());
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][queryComputeTypeTree]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	@RequestMapping("/queryComputeTypeAndFuncTree")
	public @ResponseBody String queryComputeTypeAndFuncTree(String ownerId,HttpSession session){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][queryComputeTypeAndFuncTree]开始执行！");
		}
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		List<Map<String, Object>> rsMap = new ArrayList<Map<String, Object>>();
		if(managerAccount.equals(userAccount)){
			rsMap = service.queryAllComputeTypeAndFuncTree(ownerId,loginUser.getUserId());
		}else{
			rsMap = service.queryComputeTypeAndFuncTree(ownerId,loginUser.getUserId());
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][queryComputeTypeAndFuncTree]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/view")
	public @ResponseBody String view(String computeTypeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][view]开始执行！");
		}
		ComputeTypeBean rsMap = service.view(computeTypeId);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][view]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String deleteComputeType(String computeTypeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteComputeType]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteComputeType(computeTypeId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][deleteComputeType]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("/getRelateTypeAndFunc")
	public @ResponseBody String getRelateTypeAndFunc(String computeTypeId,String computeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][getRelateTypeAndFunc]开始执行！");
		}
		Map<String,Object> flag = service.getRelateTypeAndFunc(computeTypeId,computeId);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][getRelateTypeAndFunc]执行完成！");
		}
		return gson.toJson(flag);
	}
	
	/**
	 * 关联所有计算模块
	 * @param typeId
	 * @param funcListStr
	 * @return
	 */
	@RequestMapping("/relateAllFunc")
	public @ResponseBody String relateAllFunc(String typeId,String funcListStr){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][relateAllFunc]开始执行！");
		}
		ResultBean rb = new ResultBean();
		JSONArray array = JSONObject.parseArray(funcListStr);
		List<Map<String,String>> funcList = new ArrayList<Map<String,String>>();
		JSONObject jsonObj = null;
		Map<String,String> rsMap = null;
		for (Object object : array) {
			rsMap = new HashMap<String, String>();
			jsonObj = (JSONObject)object;
			rsMap.put("computeId", jsonObj.get("computeId").toString());
			rsMap.put("overTime", jsonObj.get("overTime").toString());
			rsMap.put("computeCount", jsonObj.get("computeCount").toString());
			rsMap.put("isSaveResult", jsonObj.get("isSaveResult").toString());
			rsMap.put("isMulticast", jsonObj.get("isMulticast").toString());
			
			
			
			funcList.add(rsMap);
		}
		
		boolean flag = service.relateAllFunc(typeId,funcList);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][relateAllFunc]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 取消关联所有计算模块
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/remoceRelateAllFunc")
	public @ResponseBody String remoceRelateAllFunc(String typeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][remoceRelateAllFunc]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.remoceRelateAllFunc(typeId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeTypeController][remoceRelateAllFunc]执行完成！");
		}
		return gson.toJson(rb);
	}
	
}

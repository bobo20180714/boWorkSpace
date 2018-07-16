package com.xpoplarsoft.compute.computeFunc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.compute.computeFunc.bean.ComputeFuncBean;
import com.xpoplarsoft.compute.computeFunc.service.IComputeFuncService;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

@Controller
@RequestMapping("/ComputeFunc")
public class ComputeFuncController {

	private static Log log = LogFactory.getLog(ComputeFuncController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IComputeFuncService service;

	@RequestMapping("/add")
	public @ResponseBody String addCompute(ComputeFuncBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][addCompute]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.addCompute(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][addCompute]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("/update")
	public @ResponseBody String updateCompute(ComputeFuncBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][updateCompute]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.updateCompute(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][updateCompute]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("/list")
	public @ResponseBody String computeList(String computeName,String className,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][computeList]开始执行！");
		}
		Map<String,Object> rsMap = service.computeList(computeName,className,bean);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][computeList]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/queryAllComputeList")
	public @ResponseBody String queryAllComputeList(String computeName){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][queryAllComputeList]开始执行！");
		}
		List<Map<String,Object>> rsList = service.queryAllComputeList(computeName);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][queryAllComputeList]执行完成！");
		}
		Map<String,Object> rsMap = new HashMap<String, Object>();
		rsMap.put("Rows", rsList);
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/view")
	public @ResponseBody String view(String computeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][view]开始执行！");
		}
		ComputeFuncBean rsMap = service.view(computeId);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][view]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String deleteCompute(String computeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][deleteCompute]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteCompute(computeId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][deleteCompute]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	
	/**
	 * 根据控制计算id获取参数信息
	 * @param computeName
	 * @param bean
	 * @return
	 */
	@RequestMapping("/getFunctionInfo")
	public @ResponseBody String getFunctionInfo(String computeId){
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][getFunctionInfo]开始执行！");
		}
		FunctionBean bean = service.getFunctionInfo(computeId);
		if(log.isInfoEnabled()){
			log.info("组件[ComputeFuncController][getFunctionInfo]执行完成！");
		}
		return gson.toJson(bean);
	}
	
}

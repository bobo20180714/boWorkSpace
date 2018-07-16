package com.xpoplarsoft.compute.functionManage.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.compute.functionManage.service.IFieldService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

@Controller
@RequestMapping("/field")
public class FieldController {

	private static Log log = LogFactory.getLog(FieldController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IFieldService service;

	@RequestMapping("/add")
	public @ResponseBody String addField(FieldBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][addField]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.addField(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][addField]执行完成！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("/list")
	public @ResponseBody String fieldList(String fid,CommonBean bean){
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][fieldList]开始执行！");
		}
		Map<String,Object> rsMap = service.fieldList(fid,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][fieldList]执行完成！");
		}
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String deleteField(String fieldId){
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][deleteField]开始执行！");
		}
		ResultBean rb = new ResultBean();
		boolean flag = service.deleteField(fieldId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isInfoEnabled()){
			log.info("组件[FieldController][deleteField]执行完成！");
		}
		return gson.toJson(rb);
	}
}

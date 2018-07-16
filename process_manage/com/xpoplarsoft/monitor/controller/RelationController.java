package com.xpoplarsoft.monitor.controller;

import java.util.ArrayList;
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
import com.xpoplarsoft.monitor.bean.ColumnBean;
import com.xpoplarsoft.monitor.service.IRelationService;

/**
 * 进程相关信息 日志、运行参数、输入结果
 * @author mengxiangchao
 *
 */
@Controller
@RequestMapping("/relationInfo")
public class RelationController {


	private static Log log = LogFactory.getLog(RelationController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IRelationService relationService;
	
	/**
	 * 查询日志表格列属性集合
	 * @param processCode
	 * @return
	 */
	@RequestMapping("queryLogGridColumns")
	public @ResponseBody String queryLogGridColumns(String processCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryLogGridColumns]开始执行！");
		}
		
		List<ColumnBean> columnList = new ArrayList<ColumnBean>();
		
		if(processCode == null){
			if(log.isErrorEnabled()){
				log.error("传入参数为空！processCode=["+processCode+"]");
			}
		}else{
			columnList = relationService.queryLogGridColumns(processCode);
		}
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryLogGridColumns]执行结束！");
		}
		return gson.toJson(columnList);
	}
	
	/**
	 * 进程日志列表查询
	 * @param processCode 进程唯一标识
	 */
	@RequestMapping("processLogList")
	public @ResponseBody String processLogList(String processCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][processLogList]开始执行！");
		}
		
		Map<String,Object> rsMap = new HashMap<String,Object>();
		List<Map<String,String>> dataList = relationService.processLogList(processCode);
		rsMap.put("Rows", dataList);
		if(log.isInfoEnabled()){
			log.info("组件[MonitorController][processLogList]执行结束！");
		}
		return gson.toJson(rsMap);
	}
	
	/**
	 * 查询运行参数表格列属性及值
	 * @param processCode
	 * @return
	 */
	@RequestMapping("queryRunParam")
	public @ResponseBody String queryRunParam(String processCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryRunParam]开始执行！");
		}
		
		List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
		
		if(processCode == null){
			if(log.isErrorEnabled()){
				log.error("传入参数为空！processCode=["+processCode+"]");
			}
		}else{
			rsList = relationService.queryRunParam(processCode);
		}
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryRunParam]执行结束！");
		}
		return gson.toJson(rsList);
	}
	
	/**
	 * 查询输出结果表格列属性及值
	 * @param processCode
	 * @return
	 */
	@RequestMapping("queryResult")
	public @ResponseBody String queryResult(String processCode){
		
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryResult]开始执行！");
		}
		
		List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
		
		if(processCode == null){
			if(log.isErrorEnabled()){
				log.error("传入参数为空！processCode=["+processCode+"]");
			}
		}else{
			rsList = relationService.queryResult(processCode);
		}
		if(log.isInfoEnabled()){
			log.info("组件[RelationController][queryResult]执行结束！");
		}
		return gson.toJson(rsList);
	}
	
}

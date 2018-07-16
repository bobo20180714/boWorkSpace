package com.xpoplarsoft.compute.orderManage.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.compute.orderManage.bean.OrderBean;
import com.xpoplarsoft.compute.orderManage.service.IOrderManagerService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

@RequestMapping("/orderManager")
@Controller
public class OrderManagerController {

	private static Log log = LogFactory.getLog(OrderManagerController.class);
	
	private static Gson gson = new Gson();
	
	@Autowired
	private IOrderManagerService service;
	
	/**
	 * 订单列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody String list(CommonBean bean,String satId,String computeId, String typeId,String orderName,String timeStart,String timeEnd){
		if(satId == null || "".equals(satId)){
			satId = "-1";
		}
		Map<String,Object> rsMap = service.list(bean,satId,computeId,typeId,orderName,timeStart,timeEnd);
		return gson.toJson(rsMap);
	}
	
	/**
	 * 订单新增
	 * @param bean
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody String add(OrderBean bean){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][add]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.add(bean);
		
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][add]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 订单编辑
	 * @param bean
	 * @return
	 */
	@RequestMapping("/update")
	public @ResponseBody String update(OrderBean bean){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][update]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.update(bean);
		
//		flag = service.updateSate(bean.getOrderId(),1);
		
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][update]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 订单发布
	 * @param orderId
	 * @param satMid
	 * @param orderName
	 * @return
	 */
	@RequestMapping("/publish")
	public @ResponseBody String publish(String orderId,String satMid,String orderName){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][publish]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.publish(orderId,satMid,orderName);
		
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][publish]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	/**
	 * 订单删除
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/deleteOrder")
	public @ResponseBody String deleteOrder(String orderId){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][deleteOrder]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.updateSate(orderId,9);
		
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][deleteOrder]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 待处理操作
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/start")
	public @ResponseBody String start(String orderId){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][start]开始执行！");
		}
		
		ResultBean rb = new ResultBean();
		
		boolean flag = service.updateSate(orderId,2);
		
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][start]执行结束！");
		}
		
		return gson.toJson(rb);
	}
	
	/**
	 * 订单详情
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/view")
	public @ResponseBody String view(String orderId){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][view]开始执行！");
		}
		
		Map<String,Object> rsMap = service.view(orderId);
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][view]执行结束！");
		}
		
		return gson.toJson(rsMap);
	}
	
	/**
	 * 订单详情
	 * @param pkId
	 * @return
	 */
	@RequestMapping("/viewByPkId")
	public @ResponseBody String viewByPkId(String pkId){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][view]开始执行！");
		}
		
		OrderBean rsMap = service.viewByPkId(pkId);
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][view]执行结束！");
		}
		
		return gson.toJson(rsMap);
	}
	
	/**
	 * 订单详情为修改界面使用
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getOrderDetail")
	public @ResponseBody String getOrderDetail(String orderId){
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][getOrderDetail]开始执行！");
		}
		
		OrderBean rsMap = service.getOrderDetail(orderId);
		
		if(log.isInfoEnabled()){
			log.info("组件[OrderManagerController][getOrderDetail]执行结束！");
		}
		
		return gson.toJson(rsMap);
	}
	
}

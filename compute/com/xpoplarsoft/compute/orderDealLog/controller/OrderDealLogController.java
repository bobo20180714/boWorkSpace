package com.xpoplarsoft.compute.orderDealLog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.compute.orderDealLog.bean.OrderDealLog;
import com.xpoplarsoft.compute.orderDealLog.service.IOrderDealService;
import com.xpoplarsoft.framework.common.bean.CommonBean;

@RequestMapping("/orderDealLog")
@Controller
public class OrderDealLogController {

	private static Gson gson = new Gson();
	
	@Autowired
	private IOrderDealService service;
	
	/**
	 * 监视界面日志列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/queryMonitorLog")
	public @ResponseBody String queryMonitorLog(){
		List<OrderDealLog> rsMap = service.queryMonitorLog();
		return gson.toJson(rsMap);
	}
	
	/**
	 * 查询日志列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody String list(CommonBean bean,String orderId,String startTime,String endTime){
		Map<String, Object> rsMap = service.list(bean,orderId,startTime,endTime);
		return gson.toJson(rsMap);
	}
	
	
}

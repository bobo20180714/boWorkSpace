package com.xpoplarsoft.compute.orderDealLog.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.orderDealLog.bean.OrderDealLog;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IOrderDealService {

	List<OrderDealLog> queryMonitorLog();

	Map<String, Object> list(CommonBean bean, String orderId,String startTime,
			String endTime);

}

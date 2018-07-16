package com.xpoplarsoft.compute.orderManage.service;

import java.util.Map;

import com.xpoplarsoft.compute.orderManage.bean.OrderBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IOrderManagerService {

	/**
	 * 订单列表
	 * @param bean
	 * @param timeEnd 
	 * @param timeStart 
	 * @param orderName 
	 * @return
	 */
	Map<String, Object> list(CommonBean bean,String satId,String computeId, String typeId, String orderName, String timeStart, String timeEnd);

	/**
	 * 订单新增
	 * @param bean
	 * @return
	 */
	boolean add(OrderBean bean);

	/**
	 * 订单发布
	 * @param orderId
	 * @param satMid
	 * @param orderName
	 * @return
	 */
	boolean publish(String orderId,String satMid, String orderName);

	/**
	 * 修改订单状态
	 * @param orderId
	 * @param 目标状态
	 * @return
	 */
	boolean updateSate(String orderId, int i);

	/**
	 * 订单详情 --获取查看界面数据
	 * @param orderId
	 * @return
	 */
	Map<String, Object> view(String orderId);

	/**
	 * 获取订单详细信息 --获取修改界面数据
	 * @param orderId
	 * @return
	 */
	OrderBean getOrderDetail(String orderId);

	/**
	 * 订单编辑
	 * @param bean
	 * @return
	 */
	boolean update(OrderBean bean);

	OrderBean viewByPkId(String pkId);

}

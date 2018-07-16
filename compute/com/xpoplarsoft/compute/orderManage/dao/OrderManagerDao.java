package com.xpoplarsoft.compute.orderManage.dao;

import java.util.Calendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import com.xpoplarsoft.compute.orderManage.bean.OrderBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class OrderManagerDao implements IOrderManagerDao {

	@Override
	public boolean add(OrderBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("order_id", bean.getOrderId());
		param.setObject("order_name", bean.getOrderName());

		param.setObject("order_class", bean.getOrder_class());
		param.setObject("loop_space", bean.getLoop_space());
		param.setObject("loop_maxnum", bean.getLoop_maxnum());
		param.setObject("loop_endtime", bean.getLoop_endtime());
		
		param.setObject("time", bean.getTime());
		param.setObject("over_time", bean.getOverTime());
		param.setObject("compute_count", bean.getComputeCount());
		param.setObject("sat_id", bean.getSatId());
		param.setObject("order_content", bean.getOrderContent());
		param.setObject("comput_id", bean.getComputId());
		param.setObject("comput_type_id", bean.getComputTypeId());
		param.setObject("comput_param", bean.getComputParam());
		return SQLFactory.getSqlComponent().updateInfo("orderManager", "addOrder", param);
	}

	@Override
	public DBResult list(CommonBean bean,String satId, String computeId,  String typeId, 
			String orderName, String timeStart,
			String timeEnd) {
		DBParameter param = new DBParameter();
		param.setObject("satId", Integer.parseInt(satId));
		param.setObject("computeId", (computeId == null || "".equals(computeId))?-1:Integer.parseInt(computeId));
		param.setObject("typeId", (typeId == null || "".equals(typeId))?-1:Integer.parseInt(typeId));
		param.setObject("order_name", orderName);
		param.setObject("time_start", (timeStart == null || "".equals(timeStart))?"1970-01-01":timeStart);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR,10);
		String nowTime = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd HH:mm");
		param.setObject("time_end", (timeEnd == null || "".equals(timeEnd))?nowTime:timeEnd);
		return SQLFactory.getSqlComponent().pagingQueryInfo("orderManager", "orderList", param, bean.getPage(), bean.getPagesize());
	}
	
	@Override
	public boolean updateSate(String orderId,int orderState) {
		DBParameter param = new DBParameter();
		param.setObject("order_id", orderId);
		param.setObject("order_state", orderState);
		return SQLFactory.getSqlComponent().updateInfo("orderManager", "updateSate", param);
	}

	@Override
	public DBResult view(String orderId) {
		DBParameter param = new DBParameter();
		param.setObject("order_id", orderId);
		return SQLFactory.getSqlComponent().queryInfo("orderManager", "viewOrder", param);
	}

	@Override
	public boolean update(OrderBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("pk_id", bean.getPkId());
		param.setObject("order_name", bean.getOrderName());
		param.setObject("order_content", bean.getOrderContent());
		param.setObject("comput_param", bean.getComputParam());
		param.setObject("time", bean.getTime());
		param.setObject("over_time", bean.getOverTime());
		param.setObject("compute_count", bean.getComputeCount());
		

		param.setObject("order_class", bean.getOrder_class());
		param.setObject("loop_space", bean.getLoop_space());
		param.setObject("loop_maxnum", bean.getLoop_maxnum());
		param.setObject("loop_endtime", bean.getLoop_endtime());
		
		return SQLFactory.getSqlComponent().updateInfo("orderManager", "updateOrder", param);
	}

	@Override
	public DBResult viewByPkId(String pkId) {
		DBParameter param = new DBParameter();
		param.setObject("pkId", pkId);
		return SQLFactory.getSqlComponent().queryInfo("orderManager", "viewByPkId", param);
	}

}

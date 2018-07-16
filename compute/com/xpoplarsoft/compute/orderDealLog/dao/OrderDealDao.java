package com.xpoplarsoft.compute.orderDealLog.dao;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class OrderDealDao implements IOrderDealDao {

	@Override
	public DBResult list(CommonBean bean, String orderId, String startTime,
			String endTime) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("orderId", orderId);
		dbp.setObject("startTime", startTime);
		dbp.setObject("endTime", endTime);
		return SQLFactory.getSqlComponent().pagingQueryInfo("orderLog", "list", dbp, bean.getPage(), bean.getPagesize());
	}

}

package com.xpoplarsoft.compute.orderDealLog.dao;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IOrderDealDao {

	DBResult list(CommonBean bean, String orderId, String startTime,
			String endTime);

}

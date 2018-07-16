package com.xpoplarsoft.compute.orderManage.dao;

import com.xpoplarsoft.compute.orderManage.bean.OrderBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IOrderManagerDao {

	boolean add(OrderBean bean);

	DBResult list(CommonBean bean,String satId, String computeId,  String typeId,String orderName, String timeStart, String timeEnd);

	boolean updateSate(String orderId,int orderState);

	DBResult view(String orderId);

	boolean update(OrderBean bean);

	DBResult viewByPkId(String pkId);

}

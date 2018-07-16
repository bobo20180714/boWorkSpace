package com.xpoplarsoft.compute.orderDealLog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.compute.orderDealLog.bean.OrderDealLog;
import com.xpoplarsoft.compute.orderDealLog.cache.OrderDealLogCache;
import com.xpoplarsoft.compute.orderDealLog.dao.IOrderDealDao;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.tool.DBResultUtil;

@Service
public class OrderDealService implements IOrderDealService {

	@Autowired
	private IOrderDealDao dao;
	
	@Override
	public List<OrderDealLog> queryMonitorLog() {
		 List<OrderDealLog> rslist = OrderDealLogCache.getInstance().getLogDataList();
		 if(rslist != null && rslist.size() > 10){
			 rslist = rslist.subList(0, 10);
		 }
		 return rslist;
	}

	@Override
	public Map<String, Object> list(CommonBean bean,String orderId, String startTime,
			String endTime) {
		DBResult rs = dao.list(bean,orderId,startTime,endTime);
		return dbResultToPageData(rs);
	}
	

	/**
	 * 查询结果数据转为列表分页数据
	 * @author 孟祥超
	 * @param dbr
	 * @return
	 */
	public static Map<String,Object> dbResultToPageData(DBResult dbResult) {
		Map<String,Object> pageData = new HashMap<String, Object>();
		
		//列表数据
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if (dbResult == null || dbResult.getRows() <= 0) {
			pageData.put("Total",0);
		} else {
			//获取总条数
			int rows = dbResult.getRows();
			pageData.put("Total",dbResult.getTotal());
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,Object> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,Object>();
				for (int j = 0; j < columnName.length; j++) {
					Object obj = dbResult.getObject(i, columnName[j]);
					String columnNameTemp = columnName[j].toLowerCase();
					if(obj == null){
						cellMap.put(columnName[j].toLowerCase(), "");
					}else{
						if("log_msg".equals(columnNameTemp) || "err_msg".equals(columnNameTemp)){
							cellMap.put(columnNameTemp, DBResultUtil.ClobToString((CLOB)obj));
						}else{
							cellMap.put(columnNameTemp, obj);
						}
					}
				}
				infoList.add(cellMap);
			}
		}
		pageData.put("Rows",infoList);
		
		return pageData;
	}

}

package com.xpoplarsoft.compute.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.xpoplarsoft.compute.bean.OrderBean;
import com.xpoplarsoft.compute.db.DBParameter;
import com.xpoplarsoft.compute.db.DBResult;
import com.xpoplarsoft.compute.db.DBTools;

/**
 * 特需计算数据库访问类 根据订单获取计算组件定义 将计算结果存储数据库
 * 
 * @author zhouxignlu 2017年3月13日
 */
public class ComputeDao {

	private String dbSource = "compute";
	
	/**
	 * 获取待处理的订单
	 * @return
	 */
	public List<OrderBean> get2Order() {
		List<OrderBean> rs = new ArrayList<OrderBean>();
		String sql = "select o.*,ff.fct_all_path_namej comput_class_name  from COMPUT_ORDER_INFO o "+ 
		" left join compute_func func on func.pk_id = o.comput_id "+ 
		" left join FCT ff on ff.fct_id = func.fct_id  where ORDER_STATE='2'"
		+ " and to_date(o.time,'yyyy-MM-dd hh24:mi:ss') <= sysdate ";
		DBParameter para = new DBParameter();
		DBResult dbr = DBTools.queryInfo(dbSource, sql, para);
		OrderBean order = null;
		if (dbr != null && dbr.getRows() > 0) {
			for(int i=0;i<dbr.getRows();i++){
				order = new OrderBean();
				rs.add((OrderBean) mapToBean(dbResultToMap(dbr,i), order));
			}
		}
		return rs;
	}
	
	/**
	 * 从数据库中获取订单信息
	 * 
	 * @param order_id
	 * @return
	 */
	public OrderBean getOrderById(String order_id) {
		
		String sql = "select o.*,f.fct_all_path_namej get_data_class_name,ff.fct_all_path_namej comput_class_name,"+ 
		"fff.fct_all_path_namej result_class_name "+ 
		" from COMPUT_ORDER_INFO o "+ 
		" left join FCT f on f.fct_id = o.get_data_id "+ 
		" left join FCT ff on ff.fct_id = o.comput_id "+ 
		" left join FCT fff on fff.fct_id = o.result_id "+ 
		" where ORDER_ID='"+ order_id + "'";
//		String sql = "select * from COMPUT_ORDER_INFO where ORDER_ID='"
//				+ order_id + "'";
		DBParameter para = new DBParameter();
		DBResult dbr = DBTools.queryInfo(dbSource, sql, para);
		OrderBean order = null;
		if (dbr != null && dbr.getRows() > 0) {
			order = new OrderBean();
			mapToBean(dbResultToMap(dbr,0), order);
		}
		return order;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object mapToBean(Map map, Object obj) {
		try {
			Map returnMap = new HashMap();
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = map.get(key);
				if (value instanceof Map) {
					returnMap.remove(key);
					Class c = Class.forName(obj.getClass().getName());
					Field field = c.getDeclaredField(key);
					Object properObject = field.getType().newInstance();
					String setMethodName = "set"
							+ key.substring(0, 1).toUpperCase()
							+ key.substring(1);
					Method method = c.getMethod(setMethodName, properObject
							.getClass());
					Map properMap = (Map) value;
					method.invoke(obj, mapToBean(properMap, properObject));
				} else {
					returnMap.put(key, value);
				}
			}
			BeanUtils.populate(obj, returnMap);
		} catch (Exception e) {
			return null;
		}
		return obj;
	}
	
	/**
	 * 根据订单编号修改库中订单的状态为正在处理、处理完成、处理失败
	 * 
	 * @param state
	 * @return
	 */
	public boolean setOrderState(String order_id, String state, String err) {
		if("".equals(err) || err == null){
			err = "";
		}else if(err.length() > 3000){
			err = err.substring(0, 3000);
		}
		String sql = "update COMPUT_ORDER_INFO set ORDER_STATE='" + state
				+ "', ORDER_ERR='" + err
				+ "'  where ORDER_ID='" + order_id + "'";
		DBParameter para = new DBParameter();
		return DBTools.update(dbSource, sql, para);
	}
	
	/**
	 * 根据主进程编号查询主进程信息
	 * 
	 * @param processCode
	 * @return
	 */
	public Map<String, Object> getProcessInfoByCode(String processCode) {
		String sql = "select * from PROCESS_INFO where process_code = '"+processCode+"' and PROCESS_INFO_STATE = 2";
		DBParameter para = new DBParameter();
		DBResult rst = DBTools.queryInfo(dbSource, sql, para);
		return dbResultToMap(rst, 0);
	}

	private Map<String, Object> dbResultToMap(DBResult rst,int index) {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		if (rst != null && rst.getRows() > 0) {
			String[] colName = rst.getColName();
			for (String col : colName) {
				beanMap.put(col.toLowerCase(), rst.getObject(index, col));
			}
			return beanMap;
		}
		return beanMap;
	}
}

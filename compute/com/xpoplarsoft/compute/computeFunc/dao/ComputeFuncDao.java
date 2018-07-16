package com.xpoplarsoft.compute.computeFunc.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.compute.common.CommonUtil;
import com.xpoplarsoft.compute.computeFunc.bean.ComputeFuncBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class ComputeFuncDao implements IComputeFuncDao {

	@Override
	public boolean addCompute(ComputeFuncBean bean) {
		bean.setFctId(String.valueOf(CommonUtil.getComputePkId()));
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = new DBParameter();
		dbp.setObject("fct_id", bean.getFctId());
		dbp.setObject("fct_class_name", bean.getFctClassName());
		dbp.setObject("fct_all_path_namej", bean.getFctPckName()+"."+bean.getFctClassName());
		dbp.setObject("fct_pck_name", bean.getFctPckName());
		dbp.setObject("fct_type", bean.getFctType());
		dbpList.add(dbp);
		map.put("addFct", dbpList);
		
		dbpList = new ArrayList<DBParameter>();
		dbp = new DBParameter();
		dbp.setObject("compute_name", bean.getComputeName());
		dbp.setObject("compute_desc", bean.getComputeDesc());
		dbp.setObject("fct_id",bean.getFctId());
		dbp.setObject("version", bean.getVersion());
		dbp.setObject("is_user_defined", bean.getIsUserDefined());
		dbp.setObject("user_page_path", bean.getUserPagePath());
		dbp.setObject("over_time", bean.getOverTime());
		dbp.setObject("compute_count", bean.getComputeCount());
		dbp.setObject("is_save_result", bean.getIsSaveResult());
		dbp.setObject("is_multicast", bean.getIsMulticast());
		dbp.setObject("input_param",JSONObject.toJSONString(bean.getFieldList()));
		dbpList.add(dbp);
		map.put("addComputeFunc", dbpList);
		return SQLFactory.getSqlComponent().batchUpdate("computeFunc", map);
	}
	
	@Override
	public DBResult computeList(String computeName,String className, CommonBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_name", computeName);
		dbp.setObject("fct_class_name", className);
		return SQLFactory.getSqlComponent().pagingQueryInfo("computeFunc", "computeList",dbp,
				bean.getPage(),bean.getPagesize());
	}

	@Override
	public boolean deleteCompute(String computeId) {
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = new DBParameter();
		dbp.setObject("computeId", computeId);
		dbpList.add(dbp);
		map.put("deleteFct", dbpList);
		map.put("deleteComputeFunc", dbpList);
		return SQLFactory.getSqlComponent().batchUpdate("computeFunc", map);
	}

	@Override
	public boolean updateCompute(ComputeFuncBean bean) {
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = new DBParameter();
		dbp.setObject("pk_id", bean.getPkId());
		dbp.setObject("fct_class_name", bean.getFctClassName());
		dbp.setObject("fct_all_path_namej", bean.getFctPckName()+"."+bean.getFctClassName());
		dbp.setObject("fct_pck_name", bean.getFctPckName());
		dbpList.add(dbp);
		map.put("updateFct", dbpList);
		
		dbpList = new ArrayList<DBParameter>();
		dbp = new DBParameter();
		dbp.setObject("pk_id", bean.getPkId());
		dbp.setObject("compute_name", bean.getComputeName());
		dbp.setObject("compute_desc", bean.getComputeDesc());
		dbp.setObject("version", bean.getVersion());
		dbp.setObject("is_user_defined", bean.getIsUserDefined());
		dbp.setObject("user_page_path", bean.getUserPagePath());
		dbp.setObject("over_time", bean.getOverTime());
		dbp.setObject("compute_count", bean.getComputeCount());
		dbp.setObject("is_save_result", bean.getIsSaveResult());
		dbp.setObject("is_multicast", bean.getIsMulticast());
		dbp.setObject("input_param",JSONObject.toJSONString(bean.getFieldList()));
		dbpList.add(dbp);
		map.put("updateComputeFunc", dbpList);
		return SQLFactory.getSqlComponent().batchUpdate("computeFunc", map);
	
	}

	@Override
	public DBResult view(String computeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_id", computeId);
		return SQLFactory.getSqlComponent().queryInfo("computeFunc", "view", dbp);
		}

	@Override
	public DBResult queryAllComputeList(String computeName) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_name", computeName);
		dbp.setObject("fct_class_name", "");
		return SQLFactory.getSqlComponent().queryInfo("computeFunc", "computeList",dbp);
	}

	@Override
	public DBResult getFunctionInfo(String computeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_id", computeId);
		return SQLFactory.getSqlComponent().queryInfo("computeFunc", "getFunctionInfo", dbp);
	}

}

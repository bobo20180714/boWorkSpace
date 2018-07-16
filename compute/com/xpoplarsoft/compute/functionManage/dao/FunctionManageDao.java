package com.xpoplarsoft.compute.functionManage.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionParam;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class FunctionManageDao implements IFunctionManageDao {

	@Override
	public DBResult functionList(String functionName, String functionCode,
			String className, CommonBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("functionName", functionName);
		dbParam.setObject("functionCode", functionCode);
		dbParam.setObject("className", className);
		return SQLFactory.getSqlComponent().pagingQueryInfo("functionManage", "functionList", dbParam, bean.getPage(), bean.getPagesize());
	}

	@Override
	public DBResult paramList(String functionId, CommonBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("functionId", functionId);
		return SQLFactory.getSqlComponent().pagingQueryInfo("functionManage", "paramList", dbParam, bean.getPage(), bean.getPagesize());
	}

	@Override
	public boolean deleteFunction(String functionId) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("functionId", functionId);
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "deleteFunction",dbParam);
	}

	@Override
	public boolean addFunction(FunctionBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fct_id", bean.getFctId());
		dbParam.setObject("fct_code", bean.getFctCode());
		dbParam.setObject("fct_name", bean.getFctName());
		dbParam.setObject("table_name", bean.getTableName());
		dbParam.setObject("fct_content", bean.getFctContent());
		dbParam.setObject("return_type", bean.getReturnType());
		dbParam.setObject("param_num", bean.getParamNum());
		dbParam.setObject("fct_class_name", bean.getFctClassName());
		dbParam.setObject("fct_all_path_namej",  bean.getFctPckName()+"."+ bean.getFctClassName());
		dbParam.setObject("fct_pck_name", bean.getFctPckName());
		dbParam.setObject("fct_type", bean.getFctType());
		dbParam.setObject("is_save_result", bean.getIsSaveResult());
		dbParam.setObject("fct_param", JSONObject.toJSONString(bean.getParamList()));
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "addFunction",dbParam);
	}

	@Override
	public boolean addParam(List<FunctionParam> beanList) {
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbParam = null;
		for (FunctionParam bean : beanList) {
			dbParam = new DBParameter(); 
			dbParam.setObject("param_name", bean.getParamName());
			dbParam.setObject("param_type", bean.getParamType());
			dbParam.setObject("param_content", bean.getParamContent());
			dbParam.setObject("fid", bean.getFid());
			dbParam.setObject("param_order", bean.getParamOrder());
			dbpList.add(dbParam);
		}
		return SQLFactory.getSqlComponent().batchUpdate("functionManage", "addParam",dbpList);
	}
	
	@Override
	public boolean updateParam(FunctionParam bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("field_id", bean.getFieldId());
		dbParam.setObject("param_name", bean.getParamName());
		dbParam.setObject("param_type", bean.getParamType());
		dbParam.setObject("param_content", bean.getParamContent());
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "updateParam",dbParam);
	}

	@Override
	public DBResult getFunctionParam(String fieldId) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("field_id", fieldId);
		return SQLFactory.getSqlComponent().queryInfo("functionManage", "getFunctionParam",dbParam);
	}

	@Override
	public boolean updateParamOrder(int fieldId, int paramOrder) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("field_id", fieldId);
		dbParam.setObject("param_order", paramOrder);
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "updateParamOrder",dbParam);
	}

	@Override
	public DBResult queryParamByOrder(int fid, int paramOrder) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fid", fid);
		dbParam.setObject("param_order", paramOrder);
		return SQLFactory.getSqlComponent().queryInfo("functionManage", "queryParamByOrder",dbParam);
	}

	@Override
	public DBResult getPkId() {
		return SQLFactory.getSqlComponent().queryInfo("functionManage", "getPkId",new DBParameter());
	}

	@Override
	public boolean deleteParamByFid(String functionId) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fid", functionId);
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "deleteParamByFid",dbParam);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean updateSql(String sql) {
		return SQLFactory.getSqlComponent().updateInfo(sql);
	}

	@Override
	public DBResult queryFunction(String fid) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fid", fid);
		return SQLFactory.getSqlComponent().queryInfo("functionManage", "queryFunction", dbParam);
	}

	@Override
	public boolean updateFunction(FunctionBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fct_id", bean.getFctId());
		dbParam.setObject("fct_code", bean.getFctCode());
		dbParam.setObject("fct_name", bean.getFctName());
		dbParam.setObject("fct_content", bean.getFctContent());
		dbParam.setObject("fct_class_name", bean.getFctClassName());
		dbParam.setObject("fct_all_path_namej",  bean.getFctPckName()+"."+ bean.getFctClassName());
		dbParam.setObject("fct_pck_name", bean.getFctPckName());
		dbParam.setObject("fct_type", bean.getFctType());
		dbParam.setObject("fct_param", JSONObject.toJSONString(bean.getParamList()));
		return SQLFactory.getSqlComponent().updateInfo("functionManage", "updateFunction",dbParam);
	}

}

package com.xpoplarsoft.compute.functionManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionParam;
import com.xpoplarsoft.compute.functionManage.dao.IFieldDao;
import com.xpoplarsoft.compute.functionManage.dao.IFunctionManageDao;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.tool.DBResultUtil;

@Service
public class FunctionManageService implements IFunctionManageService{

	private static Log log = LogFactory.getLog(FunctionManageService.class);
	
	@Autowired
	private IFunctionManageDao dao;
	
	@Autowired
	private IFieldDao fieldDao;
	
	@Override
	public Map<String, Object> functionList(String functionName,
			String functionCode, String className, CommonBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][functionList]开始执行！");
		}
		DBResult dbr = dao.functionList(functionName,functionCode,className,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][functionList]开始执行！");
		}
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public Map<String, Object> paramList(String functionId, CommonBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][paramList]开始执行！");
		}
		DBResult dbr = dao.paramList(functionId,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][paramList]开始执行！");
		}
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean deleteFunction(String functionId) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][deleteFunction]开始执行！");
		}
		boolean dbr = dao.deleteFunction(functionId);
//		dbr = dao.deleteParamByFid(functionId);
		dbr = fieldDao.deleteFieldByFid(functionId);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][deleteFunction]开始执行！");
		}
		return dbr;
	}

	@Override
	public int addFunction(FunctionBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][addFunction]开始执行！");
		}
		
		int fid = getPkId();
		bean.setFctId(fid);
		dao.addFunction(bean);

		/*//创建参数
		int paramNum = Integer.parseInt(bean.getParamNum());
		List<FunctionParam> paramList = new ArrayList<FunctionParam>();
		FunctionParam param = null;
		for(int i = 1; i <= paramNum; i++ ){
			param = new FunctionParam();
			param.setFid(fid);
			param.setParamOrder(i);
			param.setParamName("param" + i);
			param.setParamType("12");
			param.setParamContent("参数"+i);
			paramList.add(param);
		}
		dao.addParam(paramList);*/
		
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][addFunction]开始执行！");
		}
		return fid;
	}

	@Override
	public int getPkId() {
		DBResult dbr = dao.getPkId();
		if(dbr != null && dbr.getRows() > 0){
			return Integer.parseInt(dbr.get(0, "pk_id"));
		}
		return -1;
	}

	@Override
	public boolean updateParam(FunctionParam bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][updateParam]开始执行！");
		}
		boolean dbr = dao.updateParam(bean);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][updateParam]开始执行！");
		}
		return dbr;
	}

	@Override
	public FunctionParam getFunctionParam(String fieldId) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][getFunctionParam]开始执行！");
		}
		FunctionParam param = new FunctionParam();
		DBResult dbr = dao.getFunctionParam(fieldId);
		if(dbr != null && dbr.getRows() > 0){
			param.setFieldId(Integer.parseInt(dbr.get(0, "FIELD_ID")));
			param.setFid(Integer.parseInt(dbr.get(0, "FID")));
			param.setParamOrder(Integer.parseInt(dbr.get(0, "PARAM_ORDER")));
			param.setParamName(dbr.get(0, "param_name"));
			param.setParamType(dbr.get(0, "param_type"));
			param.setParamContent(dbr.get(0, "param_content"));
		}
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][getFunctionParam]开始执行！");
		}
		return param;
	}

	@Override
	public boolean updateParamOrder(int fieldId, int paramOrder) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][updateParamOrder]开始执行！");
		}
		boolean dbr = dao.updateParamOrder(fieldId,paramOrder);
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][updateParamOrder]开始执行！");
		}
		return dbr;
	}

	@Override
	public FunctionParam queryParamByOrder(int fid, int paramOrder) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][queryParamByOrder]开始执行！");
		}
		DBResult dbr = dao.queryParamByOrder(fid,paramOrder);
		FunctionParam param = new FunctionParam();
		if(dbr != null && dbr.getRows() > 0){
			param.setFieldId(Integer.parseInt(dbr.get(0, "FIELD_ID")));
			param.setFid(Integer.parseInt(dbr.get(0, "FID")));
			param.setParamOrder(Integer.parseInt(dbr.get(0, "PARAM_ORDER")));
			param.setParamName(dbr.get(0, "param_name"));
			param.setParamType(dbr.get(0, "param_type"));
			param.setParamContent(dbr.get(0, "param_content"));
		}
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][queryParamByOrder]开始执行！");
		}
		return param;
	}

	@Override
	public boolean createTable(String tableName, List<FieldBean> fieldBeanlist) {
		StringBuilder sql = new StringBuilder("create table "+tableName+"(");
		FieldBean fieldBean = null;
		for (int i = 0; i < fieldBeanlist.size(); i++) {
			fieldBean = fieldBeanlist.get(i);
			sql.append(fieldBean.getFieldName()).append("   ");
			if(fieldBean.getFieldType() == 1){
				sql.append("INTEGER");
			}else if(fieldBean.getFieldType() == 2){
				sql.append("NUMBER").append("("+fieldBean.getFieldLength()).append(","+fieldBean.getFieldScale()+")");
			}else if(fieldBean.getFieldType() == 3){
				sql.append("VARCHAR2").append("("+fieldBean.getFieldLength()+")");
			}else if(fieldBean.getFieldType() == 4){
				sql.append("TIMESTAMP");
			}
			if(i < fieldBeanlist.size()-1){
				sql.append(",");
			}
		}
		sql.append(")");
		if(log.isDebugEnabled()){
			log.debug("创建表语句：["+sql.toString()+"]");
		}
		return dao.updateSql(sql.toString());
	}

	@Override
	public FunctionBean queryFunction(String fid) {
		FunctionBean fcBean = new FunctionBean();
		DBResult dbr = dao.queryFunction(fid);
		if(dbr != null && dbr.getRows() > 0){
			int i = 0;
			fcBean.setFctId(Integer.parseInt(dbr.get(i, "fct_id")));
			fcBean.setFctCode(dbr.get(i, "fct_code"));
			fcBean.setFctName(dbr.get(i, "fct_name"));
			fcBean.setFctContent(dbr.get(i, "fct_content"));
			fcBean.setFctClassName(dbr.get(i, "fct_class_name"));
			fcBean.setFctAllPathNamej(dbr.get(i, "fct_all_path_namej"));
			fcBean.setFctPckName(dbr.get(i, "fct_pck_name"));
			String fctParam = dbr.get(i, "fct_param");
			List<Map<String,String>> paramList = new ArrayList<Map<String,String>>();
			Map<String,String> paramMap = null;
			JSONArray jArr = JSONArray.parseArray(fctParam);
			for (int j = 0;jArr != null && j < jArr.size(); j++) {
				paramMap = new HashMap<String, String>();
				JSONObject jo = jArr.getJSONObject(j);
				paramMap.put("param_name", jo.getString("param_name"));
				paramMap.put("param_code", jo.getString("param_code"));
				paramMap.put("param_type", jo.getString("param_type"));
				paramList.add(paramMap);
			}
			fcBean.setParamList(paramList);
		}
		return fcBean;
	}

	@Override
	public boolean updateFunction(FunctionBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FunctionManageService][updateFunction]开始执行！");
		}
		return dao.updateFunction(bean);
	}

}

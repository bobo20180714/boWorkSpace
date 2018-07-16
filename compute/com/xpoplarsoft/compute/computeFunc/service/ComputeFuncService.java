package com.xpoplarsoft.compute.computeFunc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.compute.computeFunc.bean.ComputeFuncBean;
import com.xpoplarsoft.compute.computeFunc.dao.IComputeFuncDao;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class ComputeFuncService implements IComputeFuncService {

	@Autowired
	private IComputeFuncDao dao;
	
	@Override
	public boolean addCompute(ComputeFuncBean bean) {
		return dao.addCompute(bean);
	}

	@Override
	public boolean updateCompute(ComputeFuncBean bean) {
		return dao.updateCompute(bean);
	}

	@Override
	public Map<String, Object> computeList(String computeName,String className,
			CommonBean bean) {
		DBResult dbr = dao.computeList(computeName,className,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean deleteCompute(String computeId) {
		return dao.deleteCompute(computeId);
	}

	@Override
	public ComputeFuncBean view(String computeId) {
		DBResult dbr = dao.view(computeId);
		ComputeFuncBean beanMap = new ComputeFuncBean();
		if (dbr != null && dbr.getRows() > 0) {
			beanMap.setPkId(dbr.get(0, "compute_desc"));
			beanMap.setComputeDesc(dbr.get(0, "compute_desc"));
			beanMap.setComputeName(dbr.get(0, "compute_name"));
			beanMap.setFctId(dbr.get(0, "fct_id"));
			beanMap.setVersion(dbr.get(0, "version"));
			beanMap.setIsUserDefined(Integer.parseInt(dbr.get(0, "is_user_defined")));
			beanMap.setUserPagePath(dbr.get(0, "user_page_path"));
			beanMap.setOverTime(Integer.parseInt(dbr.get(0, "over_time")));
			beanMap.setComputeCount(Integer.parseInt(dbr.get(0, "compute_count")));
			beanMap.setIsSaveResult(Integer.parseInt(dbr.get(0, "is_save_result")));
			beanMap.setIsMulticast(Integer.parseInt(dbr.get(0, "is_multicast")));
			beanMap.setFctClassName(dbr.get(0, "fct_class_name"));
			beanMap.setFctPckName(dbr.get(0, "fct_pck_name"));
			beanMap.setFctAllPathNamej(dbr.get(0, "fct_all_path_namej"));
			beanMap.setInputParam(dbr.get(0, "input_param"));
			String fctParam = dbr.get(0, "input_param");
			List<Map<String,String>> fieldList = new ArrayList<Map<String,String>>();
			Map<String,String> paramMap = null;
			JSONArray jArr = JSONArray.parseArray(fctParam);
			for (int j = 0;jArr != null && j < jArr.size(); j++) {
				paramMap = new HashMap<String, String>();
				JSONObject jo = jArr.getJSONObject(j);
				paramMap.put("param_name", jo.getString("param_name"));
				paramMap.put("param_code", jo.getString("param_code"));
				paramMap.put("param_type", jo.getString("param_type"));
				paramMap.put("param_value", jo.getString("param_value"));
				paramMap.put("param_text", jo.getString("param_text"));
				paramMap.put("is_valid", jo.getString("is_valid"));
				paramMap.put("max_value", jo.getString("max_value"));
				paramMap.put("min_value", jo.getString("min_value"));
				paramMap.put("param_show_type", jo.getString("param_show_type"));
				fieldList.add(paramMap);
			}
			beanMap.setFieldList(fieldList);
		}
		return beanMap;
	}

	@Override
	public List<Map<String, Object>> queryAllComputeList(String computeName) {
		DBResult dbr = dao.queryAllComputeList(computeName);
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public FunctionBean getFunctionInfo(String computeId) {
		FunctionBean fcBean = new FunctionBean();
		DBResult dbr = dao.getFunctionInfo(computeId);
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
			for (int j = 0; jArr != null && j < jArr.size(); j++) {
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

}

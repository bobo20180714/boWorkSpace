package com.xpoplarsoft.baseInfo.orbitrelated.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedFieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class OrbitRelatedDao implements IOrbitRelatedDao {

	@Override
	public DBResult findOrbitRelatedFieldList(String jsjg_id) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjg_id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orbitRelated", "findOrbitRelatedFieldList", param);
		return result;
	}

	@Override
	public DBResult queryOrbitFieldByPage(CommonBean bean, String jsjg_id){
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjg_id);
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				"orbitRelated", "queryOrbitFieldByPage", param,bean.getPage(), bean.getPagesize());
		return result;
	}

	@Override
	public DBResult queryOrbitRelatedByPage(String satId, CommonBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("satId", satId);
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				"orbitRelated", "queryOrbitRelatedByPage", param,bean.getPage(), bean.getPagesize());
		return result;
	}
	
	@Override
	public DBResult queryStartOrbitRelatedByPage(String satId,CommonBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("satId", satId);
		param.setObject("key", "");
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				"orbitRelated", "queryStartOrbitRelatedByPage", param,bean.getPage(), bean.getPagesize());
		return result;
	}

	@Override
	public DBResult queryOrbitRelatedByCode(String satId, String jsjg_code) {
		DBParameter param = new DBParameter();
		param.setObject("satId", satId);
		param.setObject("jsjg_code", jsjg_code);
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orbitRelated", "queryOrbitRelatedByCode", param);
		return result;
	}

	@Override
	public boolean addRelated(OrbitRelatedBean bean) {
		LinkedHashMap<String, List<DBParameter>> sqlMap = new LinkedHashMap<String, List<DBParameter>>();
		
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", bean.getJsjg_id());
		param.setObject("jsjg_name", bean.getJsjg_name());
		param.setObject("jsjg_code", bean.getJsjg_code());
		param.setObject("is_time_range", bean.getIs_time_range());
		param.setObject("jsjg_desc", bean.getJsjg_desc());
		param.setObject("start_time", bean.getStart_time()==null?"":bean.getStart_time());
		param.setObject("end_time", bean.getEnd_time()==null?"":bean.getEnd_time());
		dbpList.add(param);
		sqlMap.put("addRelated", dbpList);
		
		/*dbpList = new ArrayList<DBParameter>();
		param = new DBParameter();
		param.setObject("jsjg_id", bean.getJsjg_id());
		param.setObject("sat_id", bean.getSys_resource_id());
		dbpList.add(param);
		sqlMap.put("addRelatedSat", dbpList);*/
		
		return SQLFactory.getSqlComponent().batchUpdate("orbitRelated",sqlMap);
	}

	@Override
	public boolean updateRelatedStatus(String jsjg_id, String status) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjg_id);
		param.setObject("jsjg_status", status);
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated", "updateRelatedStatus", param);
	}

	@Override
	public DBResult viewRelated(String jsjg_id) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjg_id);
		return SQLFactory.getSqlComponent().queryInfo("orbitRelated", "viewRelated", param);
	}

	@Override
	public boolean updateRelated(OrbitRelatedBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", bean.getJsjg_id());
		param.setObject("jsjg_name", bean.getJsjg_name());
		param.setObject("jsjg_code", bean.getJsjg_code());
//		param.setObject("is_time_range", bean.getIs_time_range());
//		param.setObject("start_time", bean.getStart_time());
//		param.setObject("end_time", bean.getEnd_time());
		param.setObject("jsjg_desc", bean.getJsjg_desc());
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated", "updateRelated", param);
	}

	/**
	 * 新增字段信息
	 */
	@Override
	public boolean addField(OrbitRelatedFieldBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", bean.getJsjg_id());
		param.setObject("field_name", bean.getField_name());
		param.setObject("field_code", bean.getField_code());
		param.setObject("field_length", bean.getField_length());
		param.setObject("fiel_dscale", bean.getFiel_dscale());
		param.setObject("field_type", bean.getField_type());
		param.setObject("field_order", bean.getField_order());
		param.setObject("field_comment", bean.getField_comment());
		param.setObject("is_display_flag", bean.getIs_display_flag());
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated", "addField", param);
	}

	@Override
	public boolean updateFieldStatus(String fieldId, String status) {
		DBParameter param = new DBParameter();
		param.setObject("field_id", fieldId);
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated", "updateFieldStatus", param);
	}

	@Override
	public boolean updateField(OrbitRelatedFieldBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("field_id", bean.getField_id());
		param.setObject("field_code", bean.getField_code());
		param.setObject("field_name", bean.getField_name());
		param.setObject("field_type", bean.getField_type());
		param.setObject("field_length", bean.getField_length());
		param.setObject("fiel_dscale", bean.getFiel_dscale());
		param.setObject("field_order", bean.getField_order());
		param.setObject("is_display_flag", bean.getIs_display_flag());
		param.setObject("field_comment", bean.getField_comment());
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated", "updateField", param);
	}

	@Override
	public DBResult viewField(String field_id) {
		DBParameter param = new DBParameter();
		param.setObject("field_id", field_id);
		return SQLFactory.getSqlComponent().queryInfo("orbitRelated", "viewField", param);
	}

	@Override
	public DBResult findOrbitrelatedList(String satId,String key) {
		DBParameter param = new DBParameter();
		param.setObject("satId", satId);
		param.setObject("key", key);
		return SQLFactory.getSqlComponent().queryInfo("orbitRelated", "queryStartOrbitRelatedByPage", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryRelateByIdAndCode(int jsjg_id, String jsjg_code) {
		String sql = "select * from ORBIT_RELATED where jsjg_id != "+jsjg_id + " and jsjg_code='"+jsjg_code+"'";
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryFieldByIdAndCode(int jsjg_id, String field_code,
			String field_id) {
		String sql = "select * from ORBIT_RELATED_FIELDS where jsjg_id = "+jsjg_id+" and field_code='"+field_code+"'";
		if(field_id != null && !"".equals(field_id)){
			sql = sql + " and field_id != '"+field_id+"'";
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@Override
	public DBResult findAllOrbitrelatedList() {
		return SQLFactory.getSqlComponent().queryInfo(
				"orbitRelated", "findAllOrbitrelatedList", new DBParameter());
	}

	@Override
	public boolean addSatRelatedInfo(String satId, String jsjgId) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjgId);
		param.setObject("sat_id", satId);
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated","addRelatedSat",param);
	}

	@Override
	public boolean removeSatRelatedInfo(String satId, String jsjgId) {
		DBParameter param = new DBParameter();
		param.setObject("jsjg_id", jsjgId);
		param.setObject("sat_id", satId);
		return SQLFactory.getSqlComponent().updateInfo("orbitRelated","deleteRelatedSat",param);
	}

}

package com.xpoplarsoft.compute.functionManage.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class FieldDao implements IFieldDao {

	@Override
	public boolean addField(FieldBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fct_id", bean.getFid());
		dbParam.setObject("field_name", bean.getFieldName());
		dbParam.setObject("field_type", bean.getFieldType());
		dbParam.setObject("field_comment", bean.getFieldComment());
		dbParam.setObject("field_length", bean.getFieldLength());
		dbParam.setObject("fiel_dscale", bean.getFieldScale());
		return SQLFactory.getSqlComponent().updateInfo("field", "addField",dbParam);
	}

	@Override
	public DBResult fieldList(String fid, CommonBean bean) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fid", fid);
		return SQLFactory.getSqlComponent().pagingQueryInfo("field", "fieldList", dbParam, bean.getPage(), bean.getPagesize());
	}

	@Override
	public boolean deleteField(String fieldId) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("field_id", fieldId);
		return SQLFactory.getSqlComponent().updateInfo("field", "deleteField",dbParam);
	}

	@Override
	public boolean deleteFieldByFid(String functionId) {
		DBParameter dbParam = new DBParameter(); 
		dbParam.setObject("fid", functionId);
		return SQLFactory.getSqlComponent().updateInfo("field", "deleteFieldByFid",dbParam);
	}

	@Override
	public boolean addFieldList(List<FieldBean> fieldBeanlist) {
		List<DBParameter> paramList = new ArrayList<DBParameter>();
		DBParameter dbParam = null; 
		for (FieldBean fieldBean : fieldBeanlist) {
			dbParam = new DBParameter(); 
			dbParam.setObject("fct_id", fieldBean.getFid());
			dbParam.setObject("field_name", fieldBean.getFieldName());
			dbParam.setObject("field_type", fieldBean.getFieldType());
			dbParam.setObject("field_comment", fieldBean.getFieldComment());
			dbParam.setObject("field_length", fieldBean.getFieldLength());
			dbParam.setObject("fiel_dscale", fieldBean.getFieldScale());
			paramList.add(dbParam);
		}
		return SQLFactory.getSqlComponent().batchUpdate("field", "addField",paramList);
	}

}

package com.xpoplarsoft.compute.functionManage.dao;

import java.util.List;

import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IFieldDao {

	boolean addField(FieldBean bean);

	DBResult fieldList(String fid, CommonBean bean);

	boolean deleteField(String fieldId);

	boolean deleteFieldByFid(String functionId);

	boolean addFieldList(List<FieldBean> fieldBeanlist);

}

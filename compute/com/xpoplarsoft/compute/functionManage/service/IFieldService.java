package com.xpoplarsoft.compute.functionManage.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 表字段服务层
 * @author mengxiangchao
 *
 */
public interface IFieldService {

	boolean addField(FieldBean bean);

	Map<String, Object> fieldList(String fid, CommonBean bean);

	boolean deleteField(String fieldId);

	boolean addFieldList(List<FieldBean> fieldBeanlist);

}

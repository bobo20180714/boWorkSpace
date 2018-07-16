package com.xpoplarsoft.compute.functionManage.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.compute.functionManage.dao.IFieldDao;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.tool.DBResultUtil;

@Service
public class FieldService implements IFieldService {

	private static Log log = LogFactory.getLog(FieldService.class);

	@Autowired
	private IFieldDao dao;
	
	@Override
	public boolean addField(FieldBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][addField]开始执行！");
		}
		
		boolean flag = dao.addField(bean);
		
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][addField]开始执行！");
		}
		return flag;
	}

	@Override
	public Map<String, Object> fieldList(String fid, CommonBean bean) {
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][fieldList]开始执行！");
		}
		DBResult dbr = dao.fieldList(fid,bean);
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][fieldList]开始执行！");
		}
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean deleteField(String fieldId) {
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][deleteField]开始执行！");
		}
		
		boolean flag = dao.deleteField(fieldId);
		
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][deleteField]开始执行！");
		}
		return flag;
	}

	@Override
	public boolean addFieldList(List<FieldBean> fieldBeanlist) {
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][addFieldList]开始执行！");
		}
		
		boolean flag = dao.addFieldList(fieldBeanlist);
		
		if(log.isInfoEnabled()){
			log.info("组件[FieldService][addFieldList]开始执行！");
		}
		return flag;
	}
}

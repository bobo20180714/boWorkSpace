package com.xpoplarsoft.compute.computeType.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.compute.computeType.bean.ComputeTypeBean;
import com.xpoplarsoft.compute.computeType.dao.IComputeTypeDao;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class ComputeTypeService implements IComputeTypeService {

	@Autowired
	private IComputeTypeDao dao;
	
	@Override
	public boolean addComputeType(ComputeTypeBean bean) {
		return dao.addComputeType(bean);
	}

	@Override
	public boolean updateComputeType(ComputeTypeBean bean) {
		return dao.updateComputeType(bean);
	}

	@Override
	public Map<String, Object> computeTypeList(String computeTypeName,
			CommonBean bean) {
		DBResult dbr = dao.computeTypeList(computeTypeName,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public List<Map<String, Object>> queryComputeTypeTree(String ownerId,String userId) {
		DBResult dbr = dao.queryComputeTree(ownerId,userId);
		return DBResultUtil.resultToList(dbr);
	}
	
	@Override
	public List<Map<String, Object>> queryAllComputeTypeTree(String ownerId,String userId) {
		DBResult dbr = dao.queryAllComputeTree(ownerId,userId);
		return DBResultUtil.resultToList(dbr);
	}
	
	@Override
	public List<Map<String, Object>> queryComputeTypeAndFuncTree(String ownerId,String userId) {
		DBResult dbr = dao.queryComputeTypeAndFuncTree(ownerId,userId);
		return DBResultUtil.resultToList(dbr);
	}
	@Override
	public List<Map<String, Object>> queryAllComputeTypeAndFuncTree(String ownerId,String userId) {
		DBResult dbr = dao.queryAllComputeTypeAndFuncTree(ownerId,userId);
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public boolean deleteComputeType(String computeTypeId) {
		return dao.deleteComputeType(computeTypeId);
	}

	@Override
	public ComputeTypeBean view(String computeTypeId) {
		DBResult dbr = dao.view(computeTypeId);
		ComputeTypeBean beanMap = new ComputeTypeBean();
		if (dbr != null && dbr.getRows() > 0) {
			beanMap.setTypeDesc(dbr.get(0, "compute_type_desc"));
			beanMap.setTypeName(dbr.get(0, "compute_type_name"));
			beanMap.setFatherId(dbr.get(0, "father_id"));
			beanMap.setIsLeaf(dbr.get(0, "is_leaf"));
			beanMap.setSatId(dbr.get(0, "sat_id"));
		}
		return beanMap;
	}

	@Override
	public boolean addRelation(String typeId, String computeId,
			int overTime,int computeCount,int isSaveResult,int isMulticast) {
		return dao.addRelation(typeId,computeId,
				overTime,computeCount,isSaveResult,isMulticast);
	}
	
	@Override
	public boolean deleteRelation(String typeId, String computeId) {
		return dao.deleteRelation(typeId,computeId);
	}

	@Override
	public List<Map<String, Object>> getRelatedByTypeId(String satId,String typeId) {
		DBResult dbr = dao.getRelatedByTypeId(satId,typeId);
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public Map<String, Object> pageQueryFuncByTypeId(String satId,String typeId,String computeName,CommonBean bean) {
		DBResult dbr = dao.pageQueryFuncByTypeId(satId,typeId,computeName,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean deleteRelationByPkId(String pkId) {
		return dao.deleteRelationByPkId(pkId);
	}

	@Override
	public Map<String, Object> getRelateTypeAndFunc(String computeTypeId,
			String computeId) {
		DBResult dbr = dao.getRelateTypeAndFunc(computeTypeId,computeId);
		return DBResultUtil.dbResultToMap(dbr);
	}
//修改关联关系表
	@Override
	public boolean updateRelation(String relateId, int overTime,
			int computeCount,int isMulticast) {

		return dao.updateRelation(relateId,overTime,computeCount,isMulticast);
		}

	@Override
	public boolean relateAllFunc(String typeId,
			List<Map<String, String>> funcList) {
		return dao.relateAllFunc(typeId,funcList);
	}

	@Override
	public boolean remoceRelateAllFunc(String typeId) {
		return dao.remoceRelateAllFunc(typeId);
	}

}

package com.xpoplarsoft.compute.computeType.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.compute.computeType.bean.ComputeTypeBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class ComputeTypeDao implements IComputeTypeDao {

	@Override
	public DBResult queryAllComputeTree(String ownerId,String userId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("user_id", userId);
		dbp.setObject("pid", ownerId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "queryAllComputeTypeTree", dbp);
	}
	@Override
	public DBResult queryComputeTree(String ownerId,String userId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("user_id", userId);
		dbp.setObject("pid", ownerId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "queryComputeTypeTree", dbp);
	}
	@Override
	public DBResult queryComputeTypeAndFuncTree(String ownerId,String userId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("user_id", userId);
		dbp.setObject("pid", ownerId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "queryComputeTypeAndFuncTree", dbp);
	}
	@Override
	public DBResult queryAllComputeTypeAndFuncTree(String ownerId,String userId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("user_id", userId);
		dbp.setObject("pid", ownerId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "queryAllComputeTypeAndFuncTree", dbp);
	}

	@Override
	public boolean addComputeType(ComputeTypeBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_type_name", bean.getTypeName());
		dbp.setObject("compute_type_desc", bean.getTypeDesc());
		dbp.setObject("father_id", (bean.getFatherId()==null || "".equals( bean.getFatherId()))?-1:bean.getFatherId());
		dbp.setObject("is_leaf", bean.getIsLeaf());
		dbp.setObject("sat_id",(bean.getSatId()==null || "".equals( bean.getSatId()))?-1:bean.getSatId());
		return SQLFactory.getSqlComponent().updateInfo("computeType", "addComputeType", dbp);
	}

	@Override
	public boolean updateComputeType(ComputeTypeBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("pk_id", bean.getPkId());
		dbp.setObject("compute_type_name", bean.getTypeName());
		dbp.setObject("compute_type_desc", bean.getTypeDesc());
		return SQLFactory.getSqlComponent().updateInfo("computeType", "updateComputeType", dbp);
	}

	@Override
	public DBResult computeTypeList(String computeTypeName, CommonBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("compute_type_name", computeTypeName);
		return SQLFactory.getSqlComponent().pagingQueryInfo("computeType", "computeTypeList",dbp,
				bean.getPage(),bean.getPagesize());
	}

	@Override
	public boolean deleteComputeType(String computeTypeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("pk_id", computeTypeId);
		return SQLFactory.getSqlComponent().updateInfo("computeType", "deleteComputeType", dbp);
	}

	@Override
	public DBResult view(String computeTypeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("pk_id", computeTypeId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "view", dbp);
	}

	@Override
	public boolean addRelation(String typeId, String computeId,
			int overTime,int computeCount,int isSaveResult,int isMulticast) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("typeId", typeId);
		dbp.setObject("computeId", computeId);
		dbp.setObject("overTime", overTime);
		dbp.setObject("computeCount", computeCount);
		dbp.setObject("isSaveResult", isSaveResult);
		dbp.setObject("isMulticast", isMulticast);
		return SQLFactory.getSqlComponent().updateInfo("computeType", "addRelation", dbp);
	}
	
	@Override
	public boolean deleteRelation(String typeId, String computeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("typeId", typeId);
		dbp.setObject("computeId", computeId);
		return SQLFactory.getSqlComponent().updateInfo("computeType", "deleteRelation", dbp);
	}

	@Override
	public DBResult getRelatedByTypeId(String satId,String typeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("satId", ("".equals(satId) || satId == null)?-1:Integer.parseInt(satId));
		dbp.setObject("typeId", ("".equals(typeId) || typeId == null)?-1:Integer.parseInt(typeId));
		return SQLFactory.getSqlComponent().queryInfo("computeType", "getRelatedByTypeId", dbp);
	}

	@Override
	public DBResult pageQueryFuncByTypeId(String satId,String typeId,String computeName,  CommonBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("satId", ("".equals(satId) || satId == null)?-1:Integer.parseInt(satId));
		dbp.setObject("typeId", ("".equals(typeId) || typeId == null)?-1:Integer.parseInt(typeId));
		dbp.setObject("compute_name", computeName);
		return SQLFactory.getSqlComponent().pagingQueryInfo("computeType", "getRelatedByTypeId",
				dbp, bean.getPage(), bean.getPagesize());
	}

	@Override
	public boolean deleteRelationByPkId(String pkId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("pk_id", pkId);
		return SQLFactory.getSqlComponent().updateInfo("computeType", "deleteRelationByPkId", dbp);
	}
	@Override
	public DBResult getRelateTypeAndFunc(String computeTypeId, String computeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("computeTypeId",computeTypeId);
		dbp.setObject("computeId",computeId);
		return SQLFactory.getSqlComponent().queryInfo("computeType", "viewTypeRelateCompute", dbp);
	}
	@Override
	public boolean updateRelation(String relateId, int overTime,
			int computeCount,int isMulticast) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("relateId", relateId);
		dbp.setObject("overTime", overTime);
		dbp.setObject("computeCount", computeCount);
		dbp.setObject("isMulticast", isMulticast);

		return SQLFactory.getSqlComponent().updateInfo("computeType", "updateRelation", dbp);
	}
	@Override
	public boolean relateAllFunc(String typeId,
			List<Map<String, String>> funcList) {
		List<DBParameter> list = new ArrayList<DBParameter>();
		DBParameter dbp = null;
		for (Map<String, String> rsMap : funcList) {
			dbp = new DBParameter();
			dbp.setObject("typeId", typeId);
			dbp.setObject("computeId", rsMap.get("computeId"));
			dbp.setObject("overTime", rsMap.get("overTime"));
			dbp.setObject("computeCount", rsMap.get("computeCount"));
			dbp.setObject("isSaveResult", rsMap.get("isSaveResult"));
			dbp.setObject("isMulticast", rsMap.get("isMulticast"));
			
			list.add(dbp);
		}
		return SQLFactory.getSqlComponent().batchUpdate("computeType", "addRelation", list);
	}
	@Override
	public boolean remoceRelateAllFunc(String typeId) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("typeId", typeId);
		return SQLFactory.getSqlComponent().updateInfo("computeType", "remoceRelateAllFunc", dbp);
	}

}

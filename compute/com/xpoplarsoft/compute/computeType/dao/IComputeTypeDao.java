package com.xpoplarsoft.compute.computeType.dao;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.computeType.bean.ComputeTypeBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IComputeTypeDao {

	DBResult queryAllComputeTree(String ownerId,String userId);
	DBResult queryComputeTree(String ownerId,String userId);
	DBResult queryComputeTypeAndFuncTree(String ownerId,String userId);
	DBResult queryAllComputeTypeAndFuncTree(String ownerId,String userId);

	boolean addComputeType(ComputeTypeBean bean);

	boolean updateComputeType(ComputeTypeBean bean);

	DBResult computeTypeList(String computeTypeName, CommonBean bean);

	boolean deleteComputeType(String computeTypeId);

	DBResult view(String computeTypeId);

	boolean addRelation(String typeId, String computeId,
			int overTime,int computeCount,int isSaveResult,int isMulticast);
	
	boolean deleteRelation(String typeId, String computeId);

	DBResult getRelatedByTypeId(String satId,String typeId);

	DBResult pageQueryFuncByTypeId(String satId,String typeId,String computeName, CommonBean bean);

	boolean deleteRelationByPkId(String pkId);
	DBResult getRelateTypeAndFunc(String computeTypeId, String computeId);
	boolean updateRelation(String relateId, int overTime, int computeCount,int isMulticast);
	boolean relateAllFunc(String typeId, List<Map<String, String>> funcList);
	boolean remoceRelateAllFunc(String typeId);

}

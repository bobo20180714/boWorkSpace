package com.xpoplarsoft.compute.computeType.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.computeType.bean.ComputeTypeBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IComputeTypeService {

	boolean addComputeType(ComputeTypeBean bean);

	boolean updateComputeType(ComputeTypeBean bean);

	Map<String, Object> computeTypeList(String computeName, CommonBean bean);
	
	/**
	 * 查询所有卫星的计算类型，有权限限制
	 * @param ownerId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> queryComputeTypeTree(String ownerId,String userId);
	
	/**
	 * 查询所有卫星的计算类型，没有权限限制
	 * @param ownerId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> queryAllComputeTypeTree(String ownerId,String userId);
	
	/**
	 * 查询卫星的计算类型及计算模块，有权限限制
	 * @param ownerId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> queryComputeTypeAndFuncTree(String ownerId,String userId);
	
	/**
	 * 查询所有卫星的计算类型及计算模块，没有权限限制
	 * @param ownerId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> queryAllComputeTypeAndFuncTree(String ownerId,String userId);

	boolean deleteComputeType(String computeId);

	ComputeTypeBean view(String computeId);

	boolean addRelation(String typeId, String computeId,
			int overTime,int computeCount,int isSaveResult,int isMulticast);

	boolean deleteRelation(String typeId, String computeId);

	List<Map<String,Object>> getRelatedByTypeId(String satId,String typeId);

	Map<String, Object> pageQueryFuncByTypeId(String satId,String typeId,String computeName,CommonBean bean);

	boolean deleteRelationByPkId(String pkId);

	Map<String, Object> getRelateTypeAndFunc(String computeTypeId,
			String computeId);

	boolean updateRelation(String relateId, int overTime, int computeCount,int isMulticast);

	/**
	 * 关联所有计算模块
	 * @param typeId
	 * @param funcList
	 * @return
	 */
	boolean relateAllFunc(String typeId, List<Map<String, String>> funcList);

	/**
	 * 取消关联所有计算模块
	 * @param typeId
	 * @return
	 */
	boolean remoceRelateAllFunc(String typeId);

}

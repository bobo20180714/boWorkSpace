package com.xpoplarsoft.baseInfo.orbitrelated.dao;


import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedFieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;


public interface IOrbitRelatedDao{


	DBResult queryOrbitRelatedByPage(String satId, CommonBean bean);

	/**
	 * 查询航天器相关信息的字段信息
	 * @param jsjg_id
	 * @return
	 */
	DBResult findOrbitRelatedFieldList(String jsjg_id);

	/**
	 * 分页查询航天器相关信息字段信息
	 * @param CommonBean
	 * @param jsjg_id
	 * @return
	 */
	DBResult queryOrbitFieldByPage(CommonBean bean, String jsjg_id);

	/**
	 * 根据卫星和信息编号查询相关信息
	 * @param satId
	 * @param jsjg_code
	 * @return
	 */
	DBResult queryOrbitRelatedByCode(String satId, String jsjg_code);

	/**
	 * 新增航天器在轨相关信息
	 * @param bean
	 * @return
	 */
	boolean addRelated(OrbitRelatedBean bean);

	/**
	 * 修改航天器相关信息状态
	 * @param jsjg_id
	 * @param status
	 * @return
	 */
	boolean updateRelatedStatus(String jsjg_id, String status);

	/**
	 * 查看航天器相关信息
	 * @param jsjg_id
	 * @return
	 */
	DBResult viewRelated(String jsjg_id);

	/**
	 * 修改航天器相关信息
	 * @param bean
	 * @return
	 */
	boolean updateRelated(OrbitRelatedBean bean);

	/**
	 * 新增字段信息
	 * @param bean
	 * @return
	 */
	boolean addField(OrbitRelatedFieldBean bean);

	/**
	 * 修改字段信息状态
	 * @param fieldId
	 * @param status
	 * @return
	 */
	boolean updateFieldStatus(String fieldId, String status);

	/**
	 * 修改字段信息
	 * @param OrbitRelatedFieldBean
	 * @return
	 */
	boolean updateField(OrbitRelatedFieldBean bean);

	/**
	 * 查看字段信息
	 * @param field_id
	 * @return
	 */
	DBResult viewField(String field_id);

	/**
	 * 查询航天器相关信息列表
	 * @param satId
	 * @return
	 */
	DBResult findOrbitrelatedList(String satId,String key);

	/**
	 * 分页查询启用的相关信息
	 * @param satId
	 * @param bean
	 * @return
	 */
	DBResult queryStartOrbitRelatedByPage(String satId,CommonBean bean);

	/**
	 * 查询除了jsjg_id对应的信息，其他相关信息是否存在该编号
	 * @param jsjg_id
	 * @param jsjg_code
	 * @return
	 */
	DBResult queryRelateByIdAndCode(int jsjg_id, String jsjg_code);

	/**
	 * 查询除了field_id对应的字段信息，其他字段信息是否存在该编号
	 * @param jsjg_id
	 * @param jsjg_code
	 * @return
	 */
	DBResult queryFieldByIdAndCode(int jsjg_id, String field_code,
			String field_id);

	/**
	 * 查询所有的卫星相关信息
	 * @return
	 */
	DBResult findAllOrbitrelatedList();

	/**
	 * 新增关联关系
	 * @param satId
	 * @param jsjgId
	 * @return
	 */
	boolean addSatRelatedInfo(String satId, String jsjgId);

	boolean removeSatRelatedInfo(String satId, String jsjgId);

}
package com.xpoplarsoft.baseInfo.orbitrelated.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedFieldBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IOrbitRelatedService {

	/**
	 * 分页查询航天器相关信息
	 * @param satId
	 * @param bean
	 * @return
	 */
	Map<String, Object> queryOrbitRelatedByPage(String satId,
			CommonBean bean);
	
	/**
	 * 查询字段集合
	 * @param jsjg_id
	 */
	List<OrbitRelatedFieldBean> findOrbitRelatedFieldList(String jsjg_id);
	
	/**
	 * 分页查询航天器相关信息字段信息
	 * @param bean
	 * @param jsjg_id
	 * @return
	 */
	Map<String, Object> queryOrbitFieldByPage(CommonBean bean, String jsjg_id);

	/**
	 * 根据卫星id和信息编号查询相关信息
	 * @param satId
	 * @param jsjg_code
	 * @return
	 */
	Map<String, Object> queryOrbitRelatedByCode(String satId, String jsjg_code);

	/**
	 * 新增航天器相关信息
	 * @param bean
	 * @return
	 */
	boolean addRelated(OrbitRelatedBean bean);

	/**
	 * 修改航天器相关信息状态
	 * @param jsjgArr
	 * @param status
	 * @return
	 */
	boolean updateRelatedStatus(String[] jsjgArr, String status);

	/**
	 * 查看航天器相关信息
	 * @param jsjg_id
	 * @return
	 */
	Map<String, Object> viewRelated(String jsjg_id);

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
	 * 修改航天器相关字段信息状态
	 * @param fieldIdArr
	 * @param status
	 * @return
	 */
	boolean updateFieldStatus(String[] fieldIdArr, String status);

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
	Map<String, Object> viewField(String field_id);

	/**
	 * 查询航天器相关信息列表
	 * @param satId
	 * @return
	 */
	List<OrbitRelatedBean> findOrbitrelatedList(String satId,String key);

	boolean startRelated(String jsjgId);

	/**
	 * 分页查询已启用的相关信息
	 * @param satId
	 * @param bean
	 * @return
	 */
	Map<String, Object> queryStartOrbitRelatedByPage(String satId,
			CommonBean bean);

	/**
	 * 判断编号是否已经存在
	 * @param jsjg_id
	 * @param jsjg_code
	 * @return
	 */
	boolean judgeCodeIsExit(int jsjg_id, String jsjg_code);

	/**
	 * 判断字段名称是否重复
	 * @param jsjg_id
	 * @param field_code
	 * @param field_id
	 * @return
	 */
	boolean judgeFieldName(int jsjg_id, String field_code, String field_id);

	/**
	 * 查询所有的卫星相关信息
	 * @return
	 */
	List<Map<String, Object>> findAllOrbitrelatedList();

	/**
	 * 添加关联关系
	 * @param satId
	 * @param jsjgId
	 * @return
	 */
	boolean addSatRelatedInfo(String satId, String jsjgId);

	/**
	 * 创建表结构
	 * @param jsjgId
	 * @param satId
	 * @return
	 */
	boolean createTabel(String jsjgId, String satId);

	/**
	 * 移除关联关系
	 * @param satId
	 * @param jsjgId
	 * @return
	 */
	boolean removeSatRelatedInfo(String satId, String jsjgId);

}

package com.xpoplarsoft.limits.strcture;

import java.util.List;
import java.util.Map;

public interface IOrganizationService {

	/**
	 * 获取组织机构集合
	 * 
	 * @param
	 * @return 组织机构集合
	 */
	List<Map<String, Object>> getStrctureList();

	/**
	 * 根据机构名称获取组织机构集合
	 * 
	 * @param org_name
	 * @return
	 */
	List<Map<String, Object>> getStrctureByName(String org_name);

	/**
	 * 根据机构编号名称获取组织机构集合
	 * 
	 * @param org_code
	 * @return
	 */
	List<Map<String, Object>> getStrctureByCode(String org_code);
	
	
	/**
	 * 根据机构ID获取组织机构集合
	 * 
	 * @param org_id
	 * @return
	 */
	List<Map<String, Object>> getStrctureByID(String org_id);
}

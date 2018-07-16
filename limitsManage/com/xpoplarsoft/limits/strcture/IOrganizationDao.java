package com.xpoplarsoft.limits.strcture;

import java.util.List;

import com.xpoplarsoft.framework.db.DBResult;


/**
 * 组织机构获取数据dao层接口
 * 
 * @author 王晓东
 * @date 2015-01-08
 */
public interface IOrganizationDao {

	/**
	 * 获取组织机构的集合
	 * 
	 * @param
	 * @return
	 */
	List<DBResult> getStrctureList();

	/**
	 * 根据机构名称获取组织机构集合
	 * 
	 * @param org_name
	 * @return
	 */
	List<DBResult> getStrctureByName(String org_name);

	/**
	 * 根据机构编号名称获取组织机构集合
	 * 
	 * @param org_code
	 * @return
	 */
	List<DBResult> getStrctureByCode(String org_code);
	
	/**
	 * 根据机构ID获取组织机构集合
	 * 
	 * @param org_id
	 * @return
	 */
	List<DBResult> getStrctureByID(String org_id);

}

package com.xpoplarsoft.limits.strcture.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.strcture.IOrganizationDao;
import com.xpoplarsoft.limits.strcture.bean.OrgStructure;

/**
 * 组织机构的数据dao层接口
 * 
 * @author 王晓东
 * @date 2015-01-08
 */
public interface IOrgStrctureDao extends IOrganizationDao {

	/**
	 * 增加组织机构信息
	 * 
	 * @param org
	 *            由编辑页面的元素组成的org
	 * @return 增加用户是否成功
	 */
	public boolean addStructure(OrgStructure org);

	/**
	 * 修改组织机构信息
	 * 
	 * @param org
	 *            由编辑页面的元素组成的org
	 * @return 修改用户是否成功
	 */
	public boolean updateStructure(OrgStructure org);

	/**
	 * 删除组织机构信息(逻辑删除0删除1正常）
	 * 
	 * @param pkId
	 *            机构id
	 * @param company_id
	 *            机构所属公司id
	 * @return 删除用户是否成功
	 */
	public boolean deleteStrcture(String pkId, String company_id);

	/**
	 * 根据机构编号获取机构
	 * 
	 * @param orgCode
	 * @return
	 */
	public DBResult getOrg(String orgCode);

	/**
	 * 查询组织机构信息
	 * 
	 * @param id
	 *            查询机构id
	 * @return
	 */
	public DBResult viewStructure(String id);

	

}

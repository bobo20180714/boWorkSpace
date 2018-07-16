package com.xpoplarsoft.limits.strcture.service;


import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.strcture.IOrganizationService;
import com.xpoplarsoft.limits.strcture.bean.OrgStructure;

/**
 * 组织机构的业务层接口
 * @author 王晓东
 * @date 2015-01-06
 */
public interface IOrgStrctureService extends IOrganizationService{
	
	/**
	 * 添加组织机构信息
	 * @param org 组织对象
	 * @return
	 */
	public ResultBean addStructure(OrgStructure org);
	
	/**
	 * 更新组织机构信息
	 * @param org 组织对象
	 * @return
	 */
	public ResultBean updateStructure(OrgStructure org);
	
	/**
	 * 删除组织
	 * @param pkId 删除组织的id
	 * @param company_id 所属 公司id
	 * @return
	 */
	public ResultBean deleteStrcture(String pkId,String company_id);
	
	/**
	 * 检查组织机构编号重复
	 * @param orgCode 组织机构编号
	 * @return
	 */
	public ResultBean checkCode(String orgCode);
	
	/**
	 * 查看组织机构信息
	 * @param id 查看组织机构id
	 * @return
	 */
	public String viewStructure(String id);
	
	
	
}

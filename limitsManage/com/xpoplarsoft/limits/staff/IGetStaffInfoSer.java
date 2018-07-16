package com.xpoplarsoft.limits.staff;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.staff.bean.StaffWhere;

/**
 * @author 王晓东 2014-01-13
 */
public interface IGetStaffInfoSer {
	/**
	 * 根据组织机构id查询该机构下的所有员工
	 * @param org_id 组织机构id
	 * @return
	 */
	String getStaffByOrgID(String org_id);
	
	/**
	 * 根据组织机构名称查询该机构下的所有员工
	 * @param org_name 组织机构名称
	 * @return
	 */
	String getStaffByOrgName(String org_name);
	
	/**
	 * 根据组织机构编号查询该机构下的所有员工
	 * @param org_code 组织机构编号
	 * @return
	 */
	String getStaffByOrgCode(String org_code);
	
	/**
	 * 根据员工性别查询员工
	 * @param sex 性别
	 * @return
	 */
	String getStaffBySex(String sex);
	
	/**
	 * 根据员工姓名查询员工
	 * @param name  员工姓名
	 * @return
	 */
	String getStaffByName(String name);
	
	/**
	 * 根据年龄区间查询员工
	 * @param start
	 * @param end 年龄区间
	 * @return
	 */
	String getStaffByAge(String start,String end);
	
	/**
	 * 根据岗位查询员工
	 * @param job_name 岗位
	 * @return
	 */
	String getStaffByJobCode(String job_name);
	
	/**
	 * 获取所有用户信息
	 * 
	 * @param
	 * @return ResultBean
	 */
	public ResultBean staffList(StaffWhere where);
	
	/**
	 * 选择员工
	 * @param pk_ids 一个或多个员工的id
	 * @param org_id 组织机构的id
	 * @return
	 */
	public ResultBean selectStaff(String pk_ids,String org_id);
	
	/**
	 * 移除员工
	 * @param pk_ids 一个或多个员工的id
	 * @param company_id 所属公司id
	 * @return
	 */
	public ResultBean removeStaff(String pk_ids,String company_id);
	
}

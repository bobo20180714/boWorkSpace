package com.xpoplarsoft.limits.staff;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.staff.bean.StaffWhere;

public interface IGetStaffInfoDao {
	/**
	 * 通过组织机构id查询员工信息
	 * @param org_id
	 * @return
	 */
	DBResult getStaffByOrgID(String org_id);
	
	/**
	 * 通过组织机构名称查询员工信息
	 * @param org_name
	 * @return
	 */
	DBResult getStaffByOrgName(String org_name);
	
	/**
	 * 通过组织机构编号查询员工信息
	 * @param org_code
	 * @return
	 */
	DBResult getStaffByOrgCode(String org_code);
	
	/**
	 * 通过员工性别查询员工信息
	 * @param sex
	 * @return
	 */
	DBResult getStaffBySex(String sex);
	
	/**
	 * 通过员工姓名查询员工姓名
	 * @param name
	 * @return
	 */
	DBResult getStaffByName(String name);
	
	/**
	 * 通过年龄区间查询员工信息
	 * @param start
	 * @param end
	 * @return
	 */
	DBResult getStaffByAge(String start,String end);
	
	/**
	 * 通过岗位名称查询员工信息
	 * @param job_name
	 * @return
	 */
	DBResult getStaffByJobCode(String job_name);
	
	/**
	 * 获取所有用户信息
	 * 
	 * @param
	 * @return DBResult
	 */
	DBResult staffList(StaffWhere where);
	
	/**
	 * 选择员工
	 * 
	 * @param pk_ids
	 *            一个或多个员工的id
	 * @param org_id
	 *            选择组织机构的id
	 * @return
	 */
	public boolean selectStaff(String pk_ids, String org_id);

	/**
	 * 移除员工
	 * 
	 * @param pk_ids
	 *            一个或多个员工的id
	 * @param company_id
	 *            选择组织机构的id
	 * @return
	 */
	public boolean removeStaff(String pk_ids, String company_id);
	
}

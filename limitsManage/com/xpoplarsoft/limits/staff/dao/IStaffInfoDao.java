package com.xpoplarsoft.limits.staff.dao;


import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.staff.IGetStaffInfoDao;
import com.xpoplarsoft.limits.staff.bean.StaffInfo;

public interface IStaffInfoDao extends IGetStaffInfoDao {

	/**
	 * 获取员工信息
	 * 
	 * @param staffCode
	 *            要查询的用户code
	 * @return DBResult
	 */
	DBResult getStaffByCode(String staffCode);

	/**
	 * 添加员工信息
	 * 
	 * @param beanMap
	 * @return
	 */
	boolean addStaff(StaffInfo staff);

	/**
	 * 修改员工信息
	 * 
	 * @param bean
	 *            修改的信息
	 * @return Boolean
	 */
	boolean updateStaffByCode(StaffInfo staff);

	/**
	 * 删除员工信息
	 * 
	 * @param staffCode
	 *            要删除的用户code
	 * @return DBResult
	 */
	boolean deleteStaffByCode(String staffCodes);
	
	/**
	 * 员工关联用户
	 * @param staff_id
	 * @param userId
	 * @return
	 */
	boolean linkStaff(String staff_id, String userId);
	
}

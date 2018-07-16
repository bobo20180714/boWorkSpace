package com.xpoplarsoft.limits.staff.service;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.staff.IGetStaffInfoSer;
import com.xpoplarsoft.limits.staff.bean.StaffInfo;

/**
 * @author 王晓东 修改于2014年12月8日
 */
public interface IStaffInfoService extends IGetStaffInfoSer {

	/**
	 * 获取部门编码
	 * 
	 * @param staffCode
	 *            要查询的员工code
	 * @return ResultBean
	 */
	String getStaffByCode(String staffCode);

	/**
	 * 添加员工信息
	 * 
	 * @param bean
	 *            要添加的员工信息
	 * @return
	 */
	ResultBean addStaff(StaffInfo staff);

	/**
	 * 修改员工信息
	 * 
	 * @param bean
	 *            要修改的数据
	 * @return ResultBean
	 */
	ResultBean updateStaffByCode(StaffInfo staff);

	/**
	 * 删除员工信息
	 * 
	 * @param staffCodes
	 * @return ResultBean
	 */
	ResultBean deleteStaffByCode(String staffCodes);
	
	/**
	 * 关联用户
	 * @param staff_id
	 * @param userId
	 * @return
	 */
	ResultBean linkStaff(String staff_id, String userId);
	
	
}

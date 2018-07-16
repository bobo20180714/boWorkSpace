package com.xpoplarsoft.limits.staff.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.staff.IGetStaffInfoDao;
import com.xpoplarsoft.limits.staff.action.StaffInfoAction;
import com.xpoplarsoft.limits.staff.bean.StaffInfo;
import com.xpoplarsoft.limits.staff.bean.StaffWhere;
import com.xpoplarsoft.limits.staff.dao.IStaffInfoDao;
import com.xpoplarsoft.limits.strcture.dao.IOrgStrctureDao;

/**
 * 员工信息管理的业务处理
 * 
 * @author 王晓东
 * @date 2014-12-31
 * 
 */
@Service
public class StaffInfoServiceImpl implements IStaffInfoService {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(StaffInfoAction.class);
	@Autowired
	public IOrgStrctureDao orgDao;
	@Autowired
	public IStaffInfoDao dao;
	@Autowired
	public IGetStaffInfoDao getDao;

	@Override
	public ResultBean addStaff(StaffInfo staff) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][addStaff]开始执行");
		}
		ResultBean rb = new ResultBean();
		if (dao.addStaff(staff)) {
			rb.setSuccess("true");
			rb.setMessage("增加员工信息成功！");
		} else {
			rb.setSuccess("false");
			rb.setMessage("增加员工信息失败！");
		}
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][addStaff]执行结束");
		}
		return rb;
	}

	/**
	 * 根据员工CODE获取员工信息
	 * 
	 * @param staffCode
	 *            要查询的用户code
	 * @return ResultBean
	 */
	@Override
	public String getStaffByCode(String staffCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][getStaffByCode]开始执行");
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		Gson json = new Gson();
		DBResult dbResult = dao.getStaffByCode(staffCode);
		if (dbResult != null && dbResult.getRows() > 0) {
			String[] resName = dbResult.getColName();

			for (int j = 0; j < resName.length; j++) {
				// 查询列对应的值
				String resValue = dbResult.getValue(0, resName[j]);
				// 放入map中
				cellMap.put(resName[j].toLowerCase(), resValue);
			}
			cellMap.put("success", "true");
		}
		String retJson = json.toJson(cellMap);
		if (log.isDebugEnabled()) {
			log.debug("返回json数据为[" + retJson + "]");
		}
		return retJson;
	}

	/**
	 * 根据用户ID得到员工的信息
	 * 
	 * @param staffInfo
	 *            要修改的员工
	 * @return ResultBean
	 */
	@Override
	public ResultBean updateStaffByCode(StaffInfo staff) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][updateStaffByCode]开始执行");
		}
		ResultBean rb = new ResultBean();

		Boolean flag = dao.updateStaffByCode(staff);
		if (flag) {
			rb.setSuccess("true");
			rb.setMessage("修改成功！");
		} else {
			rb.setSuccess("false");
			rb.setMessage("修改失败！");
		}
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][updateStaffByCode]执行结束");
		}
		return rb;
	}

	/**
	 * 根据员工CODE删除员工信息
	 * 
	 * @param staffCode
	 *            要删除的用户员工编号
	 * @return ResultBean
	 */
	@Override
	public ResultBean deleteStaffByCode(String staffCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][deleteStaffByCode]开始执行");
		}
		Boolean flage = dao.deleteStaffByCode(staffCodes);
		ResultBean rb = new ResultBean();
		if (flage) {
			rb.setSuccess("true");
			rb.setMessage("删除成功！");
		} else {
			rb.setSuccess("false");
			rb.setMessage("删除失败！");
		}
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][deleteStaffByCode]执行结束");
		}
		return rb;
	}

	@Override
	public ResultBean staffList(StaffWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][queryUsers]开始执行");
		}

		// 定义返回结果
		ResultBean resultBean = new ResultBean();

		
		// 调用dao层，查询用户
		DBResult result = getDao.staffList(where);

		// 页面数据
		Map<String, Object> pageInfo = new HashMap<String, Object>();

		// 一行数据
		Map<String, Object> cellMap = null;

		// 每行的数据集合
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();

		// 获得项目的行数
		int totalResult = result.getTotal();
		if (result == null || result.getRows() <= 0) {
			pageInfo.put("Total", 0);
		} else {
			pageInfo.put("Total", totalResult);
		}

		if (result != null && result.getRows() > 0) {
			// 循环添加数据
			for (int i = 0; i < result.getRows(); i++) {
				cellMap = new HashMap<String, Object>();
				cellMap.put("pk_id", result.getObject(i, "pk_id"));
				cellMap.put("user_id", result.getObject(i, "user_id"));
				cellMap.put("staff_code", result.getObject(i, "staff_code"));
				cellMap.put("staff_name", result.getObject(i, "staff_name"));
				cellMap.put("state", result.getObject(i, "state"));
				cellMap.put("create_time", result.getObject(i, "create_time"));
				cellMap.put("id_card", result.getObject(i, "id_card"));
				cellMap.put("staff_photo", result.getObject(i, "staff_photo"));
				cellMap.put("birthday", result.getObject(i, "birthday"));
				cellMap.put("org_id", result.getObject(i, "org_id"));
				cellMap.put("skill_level", result.getObject(i, "skill_level"));
				cellMap.put("email", result.getObject(i, "email"));
				cellMap.put("family_adress",
						result.getObject(i, "family_adress"));
				cellMap.put("remark", result.getObject(i, "remark"));
				cellMap.put("staff_code", result.getObject(i, "staff_code"));
				cellMap.put("staff_name", result.getObject(i, "staff_name"));
				cellMap.put("sex", result.getObject(i, "sex"));
				cellMap.put("arrive_date", result.getObject(i, "arrive_date"));
				cellMap.put("staff_age", result.getObject(i, "staff_age"));
				cellMap.put("mobel_no", result.getObject(i, "mobel_no"));
				cellMap.put("user_name", result.getObject(i, "user_name"));
				cellMap.put("job_code", result.getObject(i, "job_code"));
				cellMap.put("job_name", result.getObject(i, "job_name"));
				cellMap.put("sex_name", result.getObject(i, "sex_name"));
				cellMap.put("org_name", result.getObject(i, "org_name"));
				infoList.add(cellMap);
			}
		}

		// 封装返回结果
		pageInfo.put("Rows", infoList);
		resultBean.setData(pageInfo);
		resultBean.setSuccess("true");
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + resultBean + "]");
		}

		return resultBean;
	}

	@Override
	public String getStaffByOrgID(String org_id) {
		Gson json = new Gson();
		DBResult result = getDao.getStaffByOrgID(org_id);
		// 返回json
		String retJson = null;

		Map pageInfo = new HashMap();

		Map cellMap = null;

		List infoList = new ArrayList();

		if (result == null || result.getRows() <= 0) {
			pageInfo.put("Total", 0);

		} else {
			// 总条数
			int total = result.getTotal();
			pageInfo.put("Total", total);
			int rows = result.getRows();
			String[] resName = result.getColName();
			for (int i = 0; i < rows; i++) {
				// 单元格数据
				cellMap = new HashMap<String, String>();
				for (int j = 0; j < resName.length; j++) {
					// 查询列对应的值
					String resValue = result.getValue(i, resName[j]);
					// 放入map中
					cellMap.put(resName[j].toLowerCase(), resValue);
				}

				// 放入每行数据list中
				infoList.add(cellMap);
			}

			pageInfo.put("Rows", infoList);

		}
		retJson = json.toJson(pageInfo);

		if (log.isDebugEnabled()) {
			log.debug("返回json数据为[" + retJson + "]");
		}
		return retJson;
	}
	
	
	
	
	/**
	 * 关联员工和用户信息
	 * 
	 * @param staffCode :staffCode 员工编号
	 * @param userId : userID 用户编号
	 * @return ResultBean
	 */
	@Override
	public ResultBean linkStaff(String staff_id, String userIds) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][linkStaff]开始执行");
		}
		Boolean flage = dao.linkStaff(staff_id,userIds);
		ResultBean rb = new ResultBean();
		if(flage){
			rb.setSuccess("true");
			rb.setMessage("关联成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("关联失败！");
		}
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoService][linkStaff]执行结束");
		}
		return rb;
	}

	@Override
	public String getStaffByOrgName(String org_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStaffByOrgCode(String org_code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStaffBySex(String sex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStaffByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStaffByAge(String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStaffByJobCode(String job_name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResultBean selectStaff(String pk_ids, String org_id) {

		ResultBean result = new ResultBean();

		boolean status = dao.selectStaff(pk_ids, org_id);
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改数据成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("修改数据失败！");
		}

		return result;
	}

	@Override
	public ResultBean removeStaff(String pk_ids, String company_id) {

		ResultBean result = new ResultBean();

		boolean status = dao.removeStaff(pk_ids, company_id);
		if (status) {
			result.setSuccess("true");
			result.setMessage("移除员工成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("移除员工失败！");
		}

		return result;
	}


}

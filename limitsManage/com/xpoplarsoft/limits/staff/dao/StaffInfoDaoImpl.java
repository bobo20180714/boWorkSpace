package com.xpoplarsoft.limits.staff.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.limits.staff.bean.StaffInfo;
import com.xpoplarsoft.limits.staff.bean.StaffWhere;

/**
 * 员工信息管理的数据交互类
 * 
 * @author 王晓东
 * @date 2014-12-30
 * 
 */
@Repository
public class StaffInfoDaoImpl implements IStaffInfoDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(StaffInfoDaoImpl.class);


	/**
	 * 根据员工CODE获取员工信息
	 * 
	 * @param staffCode
	 *            要查询的用户code
	 * @return DBResult
	 */
	@Override
	public DBResult getStaffByCode(String staffCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][getStaffByCode]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("staff_code", staffCode);
		DBResult db = SQLFactory.getSqlComponent().queryInfo("staffInfo","view_staff_info", para);
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][getStaffByCode]执行结束");
		}
		return db;
	}

	/**
	 * 添加员工信息
	 * 
	 * @param staffInfo
	 *            要添加的员工信息
	 * @return boolean
	 */
	@Override
	public boolean addStaff(StaffInfo staff) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][addStaff]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("pk_id", staff.getPkId());
		para.setObject("staff_code", staff.getStaffCode());
		para.setObject("staff_name", staff.getStaffName());
		para.setObject("sex", staff.getSex());
		para.setObject("staff_qq", staff.getStaffQQ());
		para.setObject("mobel_no", staff.getMobelNo());
		para.setObject("id_card", staff.getIdCard());
		para.setObject("birthday", staff.getBirthday());
		para.setObject("staff_photo", staff.getStaffPhoto());
		para.setObject("job_code", staff.getJobCode());
		para.setObject("org_id", staff.getOrgId());
		para.setObject("skill_level", staff.getSkillLevel());
		para.setObject("arrive_date", staff.getArriveDate());
		para.setObject("email", staff.getEmail());
		para.setObject("family_adress", staff.getFamilyAdress());
		para.setObject("remark", staff.getRemark());
		para.setObject("state", staff.getState());
		para.setObject("update_user_code", staff.getUpdateUserCode());
		para.setObject("update_time", staff.getUpdateTime());
		boolean istrue = SQLFactory.getSqlComponent().updateInfo("staffInfo","add_staff_info", para);
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][addStaff]执行结束");
		}
		return istrue;
	}

	/**
	 * 根据员工CODE修改员工信息
	 * 
	 * @param staffCode
	 *            要修改的用户code
	 * @param staffInfo
	 *            修改的信息
	 * @return Boolean
	 */
	@Override
	public boolean updateStaffByCode(StaffInfo staff) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][updateStaffByCode]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("pk_id", staff.getPkId());
		para.setObject("staff_code", staff.getStaffCode());
		para.setObject("staff_name", staff.getStaffName());
		para.setObject("sex", staff.getSex());
		para.setObject("staff_qq", staff.getStaffQQ());
		para.setObject("mobel_no", staff.getMobelNo());
		para.setObject("id_card", staff.getIdCard());
		para.setObject("birthday", staff.getBirthday());
		para.setObject("staff_photo", staff.getStaffPhoto());
		para.setObject("job_code", staff.getJobCode());
		para.setObject("org_id", staff.getOrgId());
		para.setObject("skill_level", staff.getSkillLevel());
		para.setObject("arrive_date", staff.getArriveDate());
		para.setObject("email", staff.getEmail());
		para.setObject("family_adress", staff.getFamilyAdress());
		para.setObject("remark", staff.getRemark());
		para.setObject("update_user_code", staff.getUpdateUserCode());
		para.setObject("update_time", staff.getUpdateTime());
		boolean flag = SQLFactory.getSqlComponent().updateInfo("staffInfo","alert_staff_info", para);
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][updateStaffByCode]执行结束");
		}

		return flag;
	}

	/**
	 * 根据员工CODE删除员工信息
	 * 
	 * @param staffCode
	 *            要删除的用户员工编号code1,code2......
	 * @return DBResult
	 */
	public boolean deleteStaffByCode(String staffCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][deleteStaffByCode]开始执行");
		}
		String[] strlist = staffCodes.split(";");
		List<DBParameter> list = new ArrayList<DBParameter>();
		for (int i = 0; i < strlist.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("staff_code", strlist[i]);
			list.add(para);
		}
		boolean db = SQLFactory.getSqlComponent().batchUpdate("staffInfo", "delete_staff_info", list);
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][deleteStaffByCode]执行结束");
		}
		return db;
	}

	/**
	 * 查询员工
	 * 
	 * @param where 查询条件
	 * @author 王晓东 2014-01-07
	 * @return
	 */
	@Override
	public DBResult staffList(StaffWhere where) {

		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][queryUsers]开始执行");
		}
		if(where.getStaffCode() == null){
			where.setStaffCode("");
		}
		if(where.getStaffName() == null){
			where.setStaffName("");
		}
		DBParameter para = new DBParameter();
		para.setObject("staff_code", where.getStaffCode()==""?null:"%"+where.getStaffCode()+"%");
		para.setObject("staff_name", where.getStaffName()==""?null:"%"+where.getStaffName()+"%");
		// 查询
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo("staffInfo","query_staffs_list", para,where.getPage(),where.getPagesize());

		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	@Override
	public DBResult getStaffByOrgID(String org_id) {
		DBParameter para = new DBParameter();
		para.setObject("org_id", org_id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("staffInfo","idbystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffByOrgName(String org_name) {
		DBParameter para = new DBParameter();
		para.setObject("org_id", org_name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("org_namebystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffByOrgCode(String org_code) {
		DBParameter para = new DBParameter();
		para.setObject("org_id", org_code);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("codebystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffBySex(String sex) {
		DBParameter para = new DBParameter();
		para.setObject("org_id", sex);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("sexbystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffByName(String name) {
		DBParameter para = new DBParameter();
		para.setObject("org_id", name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("namebystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffByAge(String start, String end) {
		DBParameter para = new DBParameter();
		para.setObject("start", start);
		para.setObject("end", end);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("agebystaff_info", para);

		return result;
	}

	@Override
	public DBResult getStaffByJobCode(String job_name) {
		DBParameter para = new DBParameter();
		para.setObject("job_name", job_name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("job_namebystaff_info", para);

		return result;
	}
	
	/**
	 * 关联员工和用户信息
	 * 
	 * @param staffCode :staffCode 员工编号
	 * @param userId : userID 用户编号
	 * @return Boolean
	 */
	@Override
	public boolean linkStaff(String staff_id, String userIds) {
		if (log.isInfoEnabled()) {
			log.info("组件[StaffInfoDao][linkStaff]开始执行");
		}
		
		if (userIds == "") {
			DBParameter para = new DBParameter();
			para.setObject("staff_id", staff_id);
			boolean flag = SQLFactory.getSqlComponent().updateInfo("staffAndUser", "delete_staff_user", para);
			if (log.isInfoEnabled()) {
				log.info("组件[StaffInfoDao][linkStaff--delete]执行结束");
			}
			return flag;
		} else {
			LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
			
			List<DBParameter> staffs = new ArrayList<DBParameter>();
			DBParameter para = new DBParameter();
			para.setObject("staff_id", staff_id);
			staffs.add(para);
			map.put("delete_staff_user", staffs);
			
			String[] pk_ids = userIds.split(";");
			List<DBParameter> list = new ArrayList<DBParameter>();
			for (int i = 0; i < pk_ids.length; i++) {
				DBParameter par = new DBParameter();
				par.setObject("pk_id", pk_ids[i]);
				par.setObject("staff_id", staff_id);
				list.add(par);
			}
			map.put("staff_link_user", list);
			boolean db = SQLFactory.getSqlComponent().batchUpdate("staffAndUser", map);
			if (log.isInfoEnabled()) {
				log.info("组件[StaffInfoDao][linkStaff]执行结束");
			}
			return db;
		}
	}
	
	

	@Override
	public boolean selectStaff(String pk_ids, String org_id) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][selectStaff]开始执行");
		}

		List<DBParameter> list = new ArrayList<DBParameter>();
		String[] str = pk_ids.split(";");
		for (int i = 0; i < str.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("org_id", org_id);
			para.setObject("pk_id", str[i]);
			list.add(para);
		}
		boolean result = SQLFactory.getSqlComponent().batchUpdate("StaffInfo", "select_staff_info", list);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("选择员工成功！");
			}
			return true;
		} else {
			if (log.isWarnEnabled()) {
				log.warn("选择员工失败！");
			}
			return false;
		}
	}

	@Override
	public boolean removeStaff(String pk_ids, String company_id) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][removeStaff]开始执行");
		}

		List<DBParameter> list = new ArrayList<DBParameter>();
		String[] str = pk_ids.split(";");
		for (int i = 0; i < str.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("company_id", company_id);
			para.setObject("pk_id", str[i]);
			list.add(para);
		}
		boolean result = SQLFactory.getSqlComponent().batchUpdate("staffInfo","remove_staff_info",list);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("移除员工成功！");
			}
			return true;
		} else {
			if (log.isWarnEnabled()) {
				log.warn("移除员工失败！");
			}
			return false;
		}
	}


}

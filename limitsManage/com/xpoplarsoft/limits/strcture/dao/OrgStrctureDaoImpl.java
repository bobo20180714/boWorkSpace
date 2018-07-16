package com.xpoplarsoft.limits.strcture.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.db.impl.DBTools;
import com.xpoplarsoft.limits.strcture.bean.OrgStructure;

/**
 * 组织机构管理的 接口
 * 
 * @author 王晓东
 * @date 2015-01-06
 */
@Repository
public class OrgStrctureDaoImpl implements IOrgStrctureDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(OrgStructure.class);

	/**
	 * 新增组织机构
	 * 
	 * @param OrgStrcture
	 * @return boolean
	 */
	@Override
	public boolean addStructure(OrgStructure org) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][addStructure]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("pk_id", org.getPkId());
		para.setObject("org_code", org.getOrgCode());
		para.setObject("org_name", org.getOrgName());
		para.setObject("regist_corporation", org.getRegistCorporation());
		para.setObject("org_adress", org.getOrgAdress());
		para.setObject("org_link_no", org.getOrgLinkNo());
		para.setObject("parent_id", org.getParentId());
		para.setObject("company_id", org.getCompanyId());
		para.setObject("state", org.getState());
		para.setObject("update_user_id", org.getUpdateUserID());
		para.setObject("update_time", org.getUpdateTime());
		boolean result = SQLFactory.getSqlComponent().updateInfo("orgInfo","add_org_info", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("新增组织机构信息成功！");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("新增组织机构信息失败！");
			}
			return false;
		}
	}

	/**
	 * 修改组织机构信息
	 * 
	 * @param OrgStrcture
	 * @return boolean
	 */
	@Override
	public boolean updateStructure(OrgStructure org) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][updateStructure]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("org_code", org.getOrgCode());
		para.setObject("org_name", org.getOrgName());
		para.setObject("regist_corporation", org.getRegistCorporation());
		para.setObject("org_adress", org.getOrgAdress());
		para.setObject("org_link_no", org.getOrgLinkNo());
		para.setObject("update_user_id", org.getUpdateUserID());
		para.setObject("update_time", org.getUpdateTime());
		para.setObject("pk_id", org.getPkId());
		boolean result = SQLFactory.getSqlComponent().updateInfo("orgInfo","update_org_info", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("修改数据成功！");
			}
			return true;
		} else {
			if (log.isWarnEnabled()) {
				log.warn("修改数据失败！");
			}
			return false;
		}
	}


	/**
	 * 删除组织机构信息
	 * 
	 * @param orgCode
	 * @return boolean
	 */
	@Override
	public boolean deleteStrcture(String pk_id, String company_id) {

		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][deleteStrcture]开始执行");
		}
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		boolean result = false;
		List<DBParameter> list = new ArrayList<DBParameter>();
		DBParameter para = new DBParameter();
		para.setObject("pk_id", pk_id);
		list.add(para);
		map.put("delete_org_info", list);
		
		List<DBParameter> childlist = new ArrayList<DBParameter>();
		DBParameter par = new DBParameter();
		par.setObject("parent_id", pk_id);
		childlist.add(par);
		map.put("delete_child", childlist);
		
		List<DBParameter> stafflist = new ArrayList<DBParameter>();
		DBParameter pa = new DBParameter();
		pa.setObject("company_id", company_id);
		pa.setObject("pk_id", pk_id);
		stafflist.add(pa);
		map.put("delete_staff", stafflist);

		result = SQLFactory.getSqlComponent().batchUpdate("orgInfo", map);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("删除数据成功！");
			}
			return true;
		} else {

			if (log.isWarnEnabled()) {
				log.warn("删除数据失败！");
			}
			return false;
		}
	}

	

	@Override
	public DBResult getOrg(String orgCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrgStrctureDao][getOrg]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("org_code", orgCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("orgInfo","check_org_code", para);
		return result;
	}

	@Override
	public DBResult viewStructure(String id) {
		DBParameter para = new DBParameter();
		para.setObject("pk_id", id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("orgInfo","view_org_info", para);
		return result;
	}

	
	@Override
	public List<DBResult> getStrctureList() {

		if (log.isInfoEnabled()) {
			log.info("组件[IOrganizationDao][getStrctureList]开始执行");
		}

		List<DBResult> list = new ArrayList<DBResult>();
		String one = "select * from company_info where state =1";
		String two = "select * from org_info where state = 1";
		DBResult result1 = SQLFactory.getSqlComponent().queryInfo(one);
		DBResult result2 = SQLFactory.getSqlComponent().queryInfo(two);
		list.add(result1);
		list.add(result2);
		return list;
	}
	
	@Override
	public List<DBResult> getStrctureByName(String org_name) {
		if (log.isInfoEnabled()) {
			log.info("组件[IOrganizationDao][getStrctureByName]开始执行");
		}

		List<DBResult> list = new ArrayList<DBResult>();
		String one = "select * from org_info where state = 1 and org_name = '"+org_name+"'";
		String two = "select * from org_info where state =1 and parent_id in (select pk_id from org_info where state = 1 and org_name = '"+org_name+"')";
		DBResult result1 = SQLFactory.getSqlComponent().queryInfo(one);
		DBResult result2 = SQLFactory.getSqlComponent().queryInfo(two);
		list.add(result1);
		list.add(result2);
		return list;
	}

	@Override
	public List<DBResult> getStrctureByCode(String org_code) {
		if (log.isInfoEnabled()) {
			log.info("组件[IOrganizationDao][getStrctureByCode]开始执行");
		}

		List<DBResult> list = new ArrayList<DBResult>();
		String one = "select * from org_info where state = 1 and org_code = '"+org_code+"'";
		String two = "select * from org_info where state =1 and parent_id in (select pk_id from org_info where state = 1 and org_code = '"+org_code+"')";
		DBResult result1 = SQLFactory.getSqlComponent().queryInfo(one);
		DBResult result2 = SQLFactory.getSqlComponent().queryInfo(two);
		list.add(result1);
		list.add(result2);
		return list;
	}

	@Override
	public List<DBResult> getStrctureByID(String org_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[IOrganizationDao][getStrctureByID]开始执行");
		}

		List<DBResult> list = new ArrayList<DBResult>();
		String one = "select * from org_info where state = 1 and pk_id = '"+org_id+"'";
		String two = "select * from org_info where state =1 and parent_id = '"+org_id+"'";
		DBResult result1 = SQLFactory.getSqlComponent().queryInfo(one);
		DBResult result2 = SQLFactory.getSqlComponent().queryInfo(two);
		list.add(result1);
		list.add(result2);
		return list;
	}
}

package com.xpoplarsoft.limits.newrole.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.limits.newrole.bean.Role;


@Repository
public class NewRoleDao implements INewRoleDao{

	public List<Role> findRoleQueryPage(Map condition) {
		return null;
	}

	public DBResult findNoRole(DBParameter param) {
		DBResult res = SQLFactory.getSqlComponent().queryInfo(
				"rolemapper", "findNoRole", param);
		return res;
	}

	public DBResult findHasRole(DBParameter param) {
		DBResult res = SQLFactory.getSqlComponent().queryInfo(
				"rolemapper", "findHasRole", param);
		return res;
	}

	public Boolean userGroupRoleDelete(List<DBParameter> param) {
//		DBParameter param= new DBParameter();
//		param.setObject("org_id", org_id);
		Boolean flag =  SQLFactory.getSqlComponent().batchUpdate(
				"rolemapper", "userGroupRoleDelete", param);
		return flag;
	}

	public Boolean userGroupRoleAdd(List<DBParameter> param) {
		Boolean flag =  SQLFactory.getSqlComponent().batchUpdate(
				"rolemapper", "userGroupRoleAdd", param);
		return flag;
	}

	public int roleInfoAdd(Role role) {
		return 0;
	}

	public int roleInfoUpdate(Role role) {
		return 0;
	}

	public int roleInfoDelete(String ids) {
		return 0;
	}

	public int roleMenuDelete(String role_id) {
		return 0;
	}

	public int roleMenuAdd(Map map) {
		return 0;
	}
	
	public List<Map<String, String>> findRoleByRoleName(String roleName) {
		return null;
	}
	
	public Role getRoleByRoleId(String role_id) {
		return null;
	}

	@Override
	public String getFindNoRoleCount(DBParameter param) {
		String res = SQLFactory.getSqlComponent().queryInfo(
				"rolemapper", "getFindNoRoleCount", param).get(0, 0);
		return res;
	}
		
	@Override
	public String getFindHasRoleCount(DBParameter param) {
		String res = SQLFactory.getSqlComponent().queryInfo(
				"rolemapper", "getFindHasRoleCount", param).get(0, 0);
		return res;
	}
}


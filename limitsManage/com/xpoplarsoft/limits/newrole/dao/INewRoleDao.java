package com.xpoplarsoft.limits.newrole.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.newrole.bean.Role;

public interface INewRoleDao {

//	@SuppressWarnings("rawtypes")
	public List<Role> findRoleQueryPage(Map condition);

	public DBResult findNoRole(DBParameter param);

	public DBResult findHasRole(DBParameter param);

	public Boolean userGroupRoleDelete(List<DBParameter> parList);

//	@SuppressWarnings("rawtypes")
	public Boolean userGroupRoleAdd(List<DBParameter> parList);

	public int roleInfoAdd(Role role);

	public int roleInfoUpdate(Role role);

	public int roleInfoDelete(String ids);

	public int roleMenuDelete(String role_id);

//	@SuppressWarnings("rawtypes")
	public int roleMenuAdd(Map map);
	
	public List<Map<String, String>> findRoleByRoleName(String roleName);
	
	public Role getRoleByRoleId(String role_id);

	public String getFindNoRoleCount(DBParameter param);

	public String getFindHasRoleCount(DBParameter param);
}

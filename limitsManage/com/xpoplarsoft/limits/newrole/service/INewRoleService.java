package com.xpoplarsoft.limits.newrole.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.limits.newrole.bean.Role;

public interface INewRoleService {

//	@SuppressWarnings("rawtypes")
	public List<Role> findRoleQueryPage(Map condition);

	public  List<Role> findNoRole(DBParameter param);

	public List<Role> findHasRole(DBParameter param);

	public boolean userGroupRoleUpdate(String org_id, String ids);

	public int roleInfoAdd(Role role);

	public int roleInfoUpdate(Role role);

	public int roleInfoDelete(String ids);

	public boolean roleMenuUpdate(String role_id, String ids);
	
	public List<Map<String, String>> findRoleByRoleName(String roleName);

	public String getFindNoRoleCount(DBParameter param);

	public String getFindHasRoleCount(DBParameter param);

	public boolean userGroupRoleDelete(String org_id, String ids);

}

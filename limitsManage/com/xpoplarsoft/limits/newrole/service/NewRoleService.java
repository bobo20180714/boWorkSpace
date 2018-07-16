package com.xpoplarsoft.limits.newrole.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.framework.db.DBParameter;


import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.newrole.bean.Role;
import com.xpoplarsoft.limits.newrole.dao.INewRoleDao;

@Service
public class NewRoleService implements INewRoleService{
	@Autowired
	private INewRoleDao newRoleDao;
	
//	@Autowired
//	private ILimitDao limitDao;
	
//	@Autowired
//	private IOperateLogService operateLogService;
	
	private static final String OPERATE = "Role";
	
//	@SuppressWarnings("rawtypes")
	@Override
	public List<Role> findRoleQueryPage(Map condition) {
		return newRoleDao.findRoleQueryPage(condition);
	}

	@Override
	public List<Role> findNoRole(DBParameter param) {
		DBResult res = newRoleDao.findNoRole(param);
		List<Role> roles = new ArrayList<Role>();
		for(int i=0;i<res.getRows();i++){
			Role role = new Role();
			role.setRole_id(res.get(i, "role_id"));
			role.setRole_name(res.get(i, "role_name"));
			roles.add(role);
		}
		return roles;
	}

	@Override
	public List<Role> findHasRole(DBParameter param) {
		DBResult res = newRoleDao.findHasRole(param);
		List<Role> roles = new ArrayList<Role>();
		for(int i=0;i<res.getRows();i++){
			Role role = new Role();
			role.setRole_id(res.get(i, "role_id"));
			role.setRole_name(res.get(i, "role_name"));
			roles.add(role);
		}
		return roles;
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean userGroupRoleUpdate(String org_id, String ids) {
		List <DBParameter> parList = new ArrayList<DBParameter>();
		String []str = ids.split(",");
		for(int i = 0; i< str.length; i++){
			DBParameter param = new DBParameter();
			param.setObject("id", str[i]);
			param.setObject("org_id", org_id);
			parList.add(param);
		}
		Boolean addRow = newRoleDao.userGroupRoleAdd(parList);
/*		String [] roleIds=ids.split(",");
		Boolean addRow = false;
		DBParameter param = new DBParameter();
		for(int i=0;i<roleIds.length;i++){
			param.setObject("role_id", roleIds[i]);
			param.setObject("org_id", org_id);
			addRow = newRoleDao.userGroupRoleAdd(param);
			if(!addRow){
				addRow = false;
				return addRow;
			}
		}*/
		return addRow;
	}
		
		@Override
		public boolean userGroupRoleDelete(String org_id, String ids){
			List <DBParameter> parList = new ArrayList<DBParameter>();
			String []str = ids.split(",");
			for(int i = 0; i< str.length; i++){
				DBParameter param = new DBParameter();
				param.setObject("id", str[i]);
				param.setObject("org_id", org_id);
				parList.add(param);
			}
			Boolean deleteRow=newRoleDao.userGroupRoleDelete(parList);
				if(!deleteRow){
					deleteRow = false;
					return deleteRow;
				}
		return deleteRow;
	}

	@Override
	public int roleInfoAdd(Role role) {
		return newRoleDao.roleInfoAdd(role);

	}

	@Override
	public int roleInfoUpdate(Role role) {

		return newRoleDao.roleInfoUpdate(role);
	}

	@Override
	public int roleInfoDelete(String ids) {
		String [] roleIds=ids.split(",");
		int rs = 0;
		for(String role_id : roleIds){
			Role role = newRoleDao.getRoleByRoleId(role_id.replace('\'', ' ').trim());

			rs = newRoleDao.roleInfoDelete(ids);
			
		}
		return rs;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean roleMenuUpdate(String role_id, String ids) {
		Map map=new HashMap();
		Role role = newRoleDao.getRoleByRoleId(role_id);
	
		
		int deleteRow=newRoleDao.roleMenuDelete(role_id);
		if(ids.length()==0&&deleteRow>=0) return true;
		String [] menuIds=ids.split(",");
		
		map.put("role_id", role_id);
		map.put("menuIds", Arrays.asList(menuIds));
		int addRow=newRoleDao.roleMenuAdd(map);
		
		if(deleteRow>=0&&addRow!=0) return true;
		return false;
	}

	@Override
	public List<Map<String, String>> findRoleByRoleName(String roleName) {
		return newRoleDao.findRoleByRoleName(roleName);
	}

	@Override
	public String getFindNoRoleCount(DBParameter param) {
		return newRoleDao.getFindNoRoleCount(param);
	}

	@Override
	public String getFindHasRoleCount(DBParameter param) {
		return newRoleDao.getFindHasRoleCount(param);
	}

}

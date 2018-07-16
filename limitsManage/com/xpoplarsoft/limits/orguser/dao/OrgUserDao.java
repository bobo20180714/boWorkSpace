package com.xpoplarsoft.limits.orguser.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.limits.orguser.bean.OrgView;
import com.xpoplarsoft.limits.orguser.bean.UserInfoView;

@Repository
public class OrgUserDao implements IOrgUserDao{

	@Override
	public DBResult findOrgTree(DBParameter param) {
		DBResult result = SQLFactory.getSqlComponent().queryInfo("orgusermapper", "findOrgTree", param);
		return result;
	}

	@Override
	public DBResult findUserQueryPage(DBParameter param) {
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "findUserQueryPage", param);
		return result;
	}

	@Override
	public DBResult findUserByLoginName(DBParameter param) {
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "findUserByLoginName", param);
		return result;
	}

	@Override
	public OrgView getOrgById(String org_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean orgAdd(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "orgAdd", param);
		return flag;
	}

	@Override
	public Boolean orgUpdate(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "orgUpdate", param);
		return flag;
	}

	@Override
	public Boolean orgDelete(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "orgDelete", param);
		return flag;
	}

	@Override
	public int getCountOfUsers(DBParameter param) {
	    String cou = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "getCountOfUsers", param).get(0, 0);
	    return Integer.parseInt(cou);
	}

	@Override
	public int getCountOfSubOrg(DBParameter param) {
    String cou = SQLFactory.getSqlComponent().queryInfo(
			"orgusermapper", "getCountOfSubOrg", param).get(0, 0);
    return Integer.parseInt(cou);
	}

	@Override
	public int getCountOfRoles(DBParameter param) {
	    String cou = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "getCountOfRoles", param).get(0, 0);
	    return Integer.parseInt(cou);
	}

	@Override
	public int getCountOfLimits(String org_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DBResult getUserById(String user_id) {
		DBParameter param = new DBParameter();
		param.setObject("user_id", user_id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("orgusermapper", "getUserById", param);
		return result;
	}

	@Override
	public Boolean userGroupAdd(DBParameter par) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo("orgusermapper", "userGroupAdd", par);
		return flag;

	}

	@Override
	public Boolean userInfoAdd(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "userInfoAdd", param);
		return flag;
	}

	@Override
	public Boolean userInfoUpdate(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "userInfoUpdate", param);
		return flag;
	}

	@Override
	public Boolean userInfoDelete(List<DBParameter> param) {
		Boolean flag = SQLFactory.getSqlComponent().batchUpdate("orgusermapper", "userInfoDelete", param);
//		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
//				"orgusermapper", "userInfoDelete", param);
		return flag;
	}

	@Override
	public Boolean setPassword(DBParameter param) {
		Boolean flag = SQLFactory.getSqlComponent().updateInfo(
				"orgusermapper", "setPassword", param);
		return flag;
	}

	@Override
	public Boolean userOrgchange(List<DBParameter> param) {
		Boolean flag = SQLFactory.getSqlComponent().batchUpdate(
				"orgusermapper", "userOrgchange", param);
		return flag;
	}

	@Override
	public List<UserInfoView> selectLoginName(String login_name,String org_id) {
		DBParameter param = new DBParameter();
		param.setObject("login_name", login_name);
		param.setObject("org_id", org_id);
		List<UserInfoView> userList = new ArrayList<UserInfoView>();
		DBResult result = SQLFactory.getSqlComponent().queryInfo("orgusermapper", "selectLoginName", param);
		if(result.getRows()>0){
			for(int i=0;i<result.getRows();i++){
			UserInfoView user = new UserInfoView();
			user.setLogin_name(result.getValue(i, "login_name"));
			userList.add(user);
			}
		}
		return userList;
	}

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfoView> findUserQuery(DBParameter param) {
		DBResult res = SQLFactory.getSqlComponent().queryInfo("orgusermapper", "findUserQuery", param);
		List<UserInfoView> users = new ArrayList<UserInfoView>();
		for(int i=0;i<res.getRows();i++){
			UserInfoView alone = new UserInfoView();
			alone.setLogin_name(res.get(i, "user_account"));
			alone.setUser_name(res.get(i, "user_name"));
			alone.setZw(res.get(i, "zw"));
			alone.setTelephone(res.get(i, "telephone"));
			alone.setDanwei(res.get(i, "danwei"));
			alone.setBumen(res.get(i, "bumen"));
			alone.setEnd_time(res.get(i, "end_time"));
			alone.setOrg_name(res.get(i, "org_name"));
			alone.setCreate_time(res.get(i, "create_time"));
			alone.setCreate_user_name(res.get(i, "create_user_code"));
			users.add(alone);
		}
		return users;
	}

	@Override
	public String getFindOrgCount(DBParameter param) {
		String result = SQLFactory.getSqlComponent().queryInfo("orgusermapper", "getFindOrgCount", param).get(0, 0);
		return result;
	}

	@Override
	public String getFindUserCount(DBParameter param) {
		String result = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "getFindUserCount", param).get(0, 0);
		return result;
	}

	@Override
	public DBResult findOrgById(DBParameter param) {
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "findOrgById", param);
		return result;
	}

	@Override
	public String getUserId(DBParameter par) {
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"orgusermapper", "getUserId", par);
		return result.get(0, "user_id");	
	}

	@Override
	public DBResult getUserInfoByAccount(String login_name) {
		DBParameter param = new DBParameter();
		param.setObject("user_account", login_name);
		return SQLFactory.getSqlComponent().queryInfo("orgusermapper", "getUserInfoByAccount", param);
	}
		
}


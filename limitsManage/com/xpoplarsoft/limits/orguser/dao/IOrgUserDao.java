package com.xpoplarsoft.limits.orguser.dao;

import java.util.List;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.orguser.bean.OrgView;
import com.xpoplarsoft.limits.orguser.bean.UserInfoView;

public interface IOrgUserDao {

	public DBResult findOrgTree(DBParameter param);

	public DBResult findUserQueryPage(DBParameter param);
	
	public DBResult findUserByLoginName(DBParameter param);
	
	public OrgView getOrgById(String org_id);
	
	public Boolean orgAdd(DBParameter param);

	public Boolean orgUpdate(DBParameter param);

	public Boolean orgDelete(DBParameter param);
	
	/**
	 * 获取机构中正在使用的用户账户个数
	 * @param org_id
	 * @return
	 */
	public int getCountOfUsers(DBParameter param);
	/**
	 * 获取机构的下级机构个数
	 * @param org_id
	 * @return
	 */
	public int getCountOfSubOrg(DBParameter param);
	/**
	 * 获取机构已分配角色个数
	 * @param param
	 * @return
	 */
	public int getCountOfRoles(DBParameter param);
	/**
	 * 获取机构已分配权限个数
	 * @param org_id
	 * @return
	 */
	public int getCountOfLimits(String org_id);
	
	public DBResult getUserById(String user_id);

//	@SuppressWarnings("rawtypes")
	public Boolean userGroupAdd(DBParameter par);

	public Boolean userInfoAdd(DBParameter param);

	public Boolean userInfoUpdate(DBParameter param);

	public Boolean userInfoDelete(List<DBParameter> paramList);

	public Boolean setPassword(DBParameter param);

	public Boolean userOrgchange(List<DBParameter> parList);

	public List<UserInfoView> selectLoginName(String login_name, String org_id);

//	@Select("select sys_guid() from dual")
	public String getUUID();
	
//	@SuppressWarnings("rawtypes")
	public List<UserInfoView> findUserQuery(DBParameter param);

	public String getFindOrgCount(DBParameter param);

	public String getFindUserCount(DBParameter param);

	public DBResult findOrgById(DBParameter param);

	public String getUserId(DBParameter par);

	/**
	 * 根据登录账号查询用户信息
	 * @param login_name
	 * @return
	 */
	public DBResult getUserInfoByAccount(String login_name);

}

package com.xpoplarsoft.limits.orguser.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.limits.orguser.bean.OrgView;
import com.xpoplarsoft.limits.orguser.bean.UserInfoView;

public interface IOrgUserService {

	public List<OrgView> findOrgTree(DBParameter param);

//	@SuppressWarnings("rawtypes")
	public List<UserInfoView> findUserQueryPage(DBParameter param);
	
//	@SuppressWarnings("rawtypes")
	public String findUserByLoginName(DBParameter param);

	public Boolean orgAdd(DBParameter param);

	public Boolean orgUpdate(DBParameter param);

	public String orgDelete(DBParameter param);

	public boolean userInfoAdd(DBParameter param);

	public Boolean userInfoUpdate(UserInfoView userInfoView);

	public Boolean userInfoDelete(String ids);

	public Boolean setPassword(DBParameter param);

	public Boolean userOrgchange(String org_id, String ids);

	public String userDaoru(List<String> records, UserInfoView userInfoView);
	
	public String getUserExportFile(UserInfoView userInfoView,String path);
	
//	@SuppressWarnings("rawtypes")
	public List<UserInfoView> findUserQuery(UserInfoView userInfoView);

	public String getFindUserCount(DBParameter param);

	public OrgView findOrgById(DBParameter param);

	public String findOrgUserByLoginName(DBParameter param);

	public String getUserId(DBParameter par);

	/**
	 * 根据用户ID查询用户信息
	 * @param user_id
	 * @return
	 */
	public Map<String, Object> getUserById(String user_id);
	
	/**
	 * 判断登录账号是否已经存在
	 * @param login_name
	 * @return
	 */
	public boolean judgeLoginAccountExit(String login_name);
}

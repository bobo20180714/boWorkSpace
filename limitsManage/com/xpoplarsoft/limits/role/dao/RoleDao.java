package com.xpoplarsoft.limits.role.dao;

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
import com.xpoplarsoft.limits.role.bean.PoplarRole;
import com.xpoplarsoft.limits.role.bean.PoplarRoleRes;
import com.xpoplarsoft.limits.role.bean.RoleWhere;

/**
 * 类功能: 角色dao实现类
 * 
 * @author 王晓东
 * @date 2015-01-20
 */
@Repository
public class RoleDao implements IRoleDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(RoleDao.class);

	/**
	 * 新增
	 * 
	 * @param role
	 * @return
	 */
	public boolean add(PoplarRole bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][add]开始执行");
		}


		DBParameter para = new DBParameter();
		para.setObject("pk_id", bean.getPkId());
		para.setObject("role_name", bean.getRoleName());
		para.setObject("update_user_code", bean.getUpdateUserCode());
		para.setObject("update_time", bean.getUpdateTime());
		para.setObject("role_code", bean.getRoleCode());
		para.setObject("state", bean.getState());
		para.setObject("role_desc", bean.getRoleDesc());

		boolean result = SQLFactory.getSqlComponent().updateInfo("roleInfo","add_role", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("新增角色成功");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("新增角色失败");
			}
			return false;
		}
	}

	/**
	 * 修改
	 * 
	 * @param role
	 * @return
	 */
	public boolean update(PoplarRole role) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][update]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("role_code", role.getRoleCode());
		para.setObject("role_name", role.getRoleName());
		para.setObject("update_user_code", role.getUpdateUserCode());
		para.setObject("update_time", role.getUpdateTime());
		para.setObject("role_desc", role.getRoleDesc());
		boolean result = SQLFactory.getSqlComponent().updateInfo("roleInfo","alter_role", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("修改角色成功");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("修改角色失败");
			}
			return false;
		}

	}

	/**
	 * 根据角色名称查询数据库
	 * 
	 * @param roleName
	 * @return boolean
	 */
	@Override
	public boolean roleNameExist(String roleName) {

		if (roleName == null) {
			return false;
		}
		String where = " where role_name='" + roleName + "'";

		DBParameter para = new DBParameter();
		para.setObject("where", where);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("query_poplar_role_list", para);
		if (result == null || result.getTotal() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param roleCodes
	 * @return
	 */
	public boolean delete(String[] roleList) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][delete]开始执行");
		}
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> roleReslist = new ArrayList<DBParameter>();
		List<DBParameter> roleUserList = new ArrayList<DBParameter>();
		List<DBParameter> list = new ArrayList<DBParameter>();
		for (int i = 0; i < roleList.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("role_code", roleList[i]);
			roleReslist.add(para);
			roleUserList.add(para);
			list.add(para);
		}
		// 删除角色和资源对应关系信息
		map.put("delete_role_res", roleReslist);
		// 删除角色和用户对应关系信息
		map.put("delete_role_user", roleUserList);
		// 删除角色信息
		map.put("delete_role", list);
		boolean result = SQLFactory.getSqlComponent().batchUpdate("roleInfo", map);
		return result;
	}

	/**
	 * 更新角色状态
	 * 
	 * @param roleList
	 * @param state
	 * 
	 * @return 成功标志
	 */
	public boolean updateState(String[] roleList, String state) {
		List<DBParameter> list = new ArrayList<DBParameter>(); 
		for (int i = 0; i < roleList.length; i++) {
			String roleCode = roleList[i];
			DBParameter para = new DBParameter();
			para.setObject("role_code", roleCode);
			para.setObject("state", state);
			list.add(para);
		}
		boolean result = SQLFactory.getSqlComponent().batchUpdate("roleInfo","update_role_state", list);
		return result;
	}

	/**
	 * 根据表格条件查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return
	 */
	public DBResult getRoleList(RoleWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][getRoleList]开始执行");
		}
		if(where.getName() == null){
			where.setName("");
		}
		DBParameter para = new DBParameter();
		para.setObject("role_name", where.getName()==""?null:"%"+where.getName()+"%");
		DBResult result = null;
		if (where.getState() != "" && where.getState() != null) {
			para.setObject("state", where.getState());
			result = SQLFactory.getSqlComponent().queryInfo("roleInfo", "query_state_list", para);
		} else if (where.getPage() == -1 || where.getPagesize() == -1) {
			result = SQLFactory.getSqlComponent().queryInfo("roleInfo", "query_role_list", para);
		} else {
			result = SQLFactory.getSqlComponent().pagingQueryInfo("roleInfo", "query_role_list", para,where.getPage(),where.getPagesize());
		}
		return result;
	}

	/**
	 * 根据角色编码获取资源树
	 * 
	 * @param 
	 * @return
	 */
	public DBResult getResTreeByRoleCode(String roleCode){
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][getResTreeByRoleCode]开始执行");
		}
		
		DBParameter para = new DBParameter();
		para.setObject("role_id", roleCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("roleAndRes","query_restree_role", para);
		return result;
	}

	@Override
	public DBResult getRoleByRoleCode(String roleCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][getRoleByRoleCode]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("role_code", roleCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("roleInfo","view_role", para);
		return result;
	}

	@Override
	public boolean alterUserRole(String userAccount, String roleCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][alterUserRole]开始执行");
		}
		
		if (roleCodes == "") {
			DBParameter para = new DBParameter();
			para.setObject("user_id", userAccount);
			boolean result = SQLFactory.getSqlComponent().updateInfo("userAndRole", "delete_user_role", para);
			return result;
		}
		String[] roles = roleCodes.split(",");
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		
		List<DBParameter> list = new ArrayList<DBParameter>();
		DBParameter para = new DBParameter();
		para.setObject("user_id", userAccount);
		list.add(para);
		map.put("delete_user_role", list);
		
		List<DBParameter> rolelist = new ArrayList<DBParameter>();
		for (int i = 0; i < roles.length; i++) {
			DBParameter par = new DBParameter();
			par.setObject("pk_id", FlowNoFactory.getFlowNoComponent().getFlowNo());
			par.setObject("user_id", userAccount);
			par.setObject("role_id", roles[i]);
			rolelist.add(par);
		}
		map.put("add_user_role", rolelist);
		boolean result = SQLFactory.getSqlComponent().batchUpdate("userAndRole", map);
		return result;
	}

	@Override
	public boolean updateRoleRes(PoplarRoleRes bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][updateRoleRes]开始执行");
		}
		String[] resCodes = bean.getResCodes().split(",");
		String roleId = bean.getRoleCode();
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		
		List<DBParameter> idlist = new ArrayList<DBParameter>();
		DBParameter par = new DBParameter();
		par.setObject("role_id", roleId);
		idlist.add(par);
		map.put("delete_role_res", idlist);
		
		List<DBParameter> list = new ArrayList<DBParameter>();
		for (int i = 0; i < resCodes.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("role_id", roleId);
			para.setObject("pk_id", FlowNoFactory.getFlowNoComponent().getFlowNo());
			para.setObject("res_id", resCodes[i]);
			list.add(para);
		}
		
		map.put("add_role_res", list);
		boolean result = SQLFactory.getSqlComponent().batchUpdate("roleAndRes", map);
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgeRoleNameExit(String roleName, String roleId) {
		String sql = "select * from role where STATUS = 0 and ROLE_NAME = '"+roleName+"' ";
		if(roleId != null || !"".equals(roleId)){
			sql = sql + " and ROLE_CODE != '"+roleId+"'";
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}
}

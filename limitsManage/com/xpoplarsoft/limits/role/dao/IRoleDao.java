package com.xpoplarsoft.limits.role.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.role.bean.PoplarRole;
import com.xpoplarsoft.limits.role.bean.PoplarRoleRes;
import com.xpoplarsoft.limits.role.bean.RoleWhere;

/**
 * 类功能: 角色dao接口类
 * 
 * @author chen.jie
 * @date 2014-3-14
 */
public interface IRoleDao {

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean add(PoplarRole bean);

	/**
	 * 修改角色信息
	 * 
	 * @param role
	 * @return
	 */
	public boolean update(PoplarRole role);

	/**
	 * 删除角色
	 * 
	 * @param roleCode
	 * @return
	 */
	public boolean delete(String[] roleCodes);
	
	/**
	 * 更新角色状态
	 * 
	 * @param roleList
	 * @param state
	 *            
	 * @return 成功标志
	 */
	public boolean updateState(String[] roleList,String state);

	/**
	 * 根据表格条件查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return 数据库执行语句结果
	 */
	public DBResult getRoleList(RoleWhere where);
	
	/**
	 * 根据角色编码查询
	 * @param roleCode
	 * @return
	 */
	public DBResult getRoleByRoleCode(String roleCode);

	/**
	 * 判断角色名称是否存在
	 * 
	 * @param roleName
	 * @return
	 */
	public boolean roleNameExist(String roleName);
	
	/**
	 * 根据角色编码获取资源树
	 * 
	 * @param 
	 * @return
	 */
	public DBResult getResTreeByRoleCode(String roleCode);
	
	/**
	 * 给用户分配角色
	 * @param userAccount
	 * @param roleCodes
	 * @return
	 */
	boolean alterUserRole(String userAccount, String roleCodes);
	
	boolean updateRoleRes(PoplarRoleRes bean);

	public DBResult judgeRoleNameExit(String roleName, String roleId);

}

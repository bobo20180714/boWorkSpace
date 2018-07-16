package com.xpoplarsoft.limits.role.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.role.bean.PoplarRole;
import com.xpoplarsoft.limits.role.bean.PoplarRoleRes;
import com.xpoplarsoft.limits.role.bean.RoleWhere;

/**
 * 类功能: 角色服务接口类
 * 
 * @author chen.jie
 * @date 2014-3-14
 */
public interface IRoleService {

	/**
	 * 新增
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean add(PoplarRole bean);

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean alter(PoplarRole bean);

	/**
	 * 删除
	 * 
	 * @param roleCode
	 * @return
	 */
	public ResultBean delete(String[] roleCode);

	/**
	 * 启用角色
	 * 
	 * @param roleList
	 *            角色编码集合
	 * @return 是否成功
	 */
	public ResultBean start(String[] roleList);

	/**
	 * 禁用角色
	 * 
	 * @param roleList
	 *            角色编码集合
	 * @return 是否成功
	 */
	public ResultBean stop(String[] roleList);

	/**
	 * 获取角色列表
	 * 
	 * @return
	 */
	public Map getRoleList(RoleWhere where);

	/**
	 * 根据角色编码获取单条角色信息
	 * 
	 * @param roleCode
	 * @return
	 */
	public String getRoleListByRole(String roleCode);

	/**
	 * 根据角色编码获取资源树
	 * 
	 * @param roleCode
	 * @return
	 */
	public ResultBean getResTreeByRoleCode(String roleCode);

	/**
	 * 更新角色权限
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean updateRoleRes(PoplarRoleRes bean);

	/**
	 * 根据用户编码查询角色列表
	 * 
	 * @param userCode
	 *            用户编码
	 * @return 角色列表
	 */
	public String getRoleListByUserCode(String userCode);

	/**
	 * 修改角色和用户的对应关系
	 * 
	 * @param userAccount
	 * @param roleCodes
	 * @return
	 */
	public ResultBean alterUserRole(String userAccount, String roleCodes);
	
	/**
	 * 根据用户编码获取权限资源列表
	 * 
	 * @param userCode
	 * @return list<Resources>
	 */
	public List<Resources> getResTreeByUserCode(String userCode);

	public boolean judgeRoleNameExit(String roleName, String roleId);

}

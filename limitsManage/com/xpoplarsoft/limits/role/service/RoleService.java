package com.xpoplarsoft.limits.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.resources.dao.IResourcesDao;
import com.xpoplarsoft.limits.role.bean.PoplarRole;
import com.xpoplarsoft.limits.role.bean.PoplarRoleRes;
import com.xpoplarsoft.limits.role.bean.RoleWhere;
import com.xpoplarsoft.limits.role.dao.IRoleDao;
import com.xpoplarsoft.limits.user.dao.IUserDao;

/**
 * 类功能: 角色服务实现类
 * 
 * @author chen.jie
 * @date 2014-3-14
 */
@Service
public class RoleService implements IRoleService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(RoleService.class);

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IResourcesDao resDao;

	/**
	 * 新增角色
	 * 
	 * @param bean
	 * @return ResultBean
	 */
	@Override
	public ResultBean add(PoplarRole bean) {
		ResultBean result = new ResultBean();

		boolean status = roleDao.add(bean);
		if (status){
			result.setSuccess("true");
			result.setMessage("新增角色成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("新增角色失败！");
		}
		return result;
	}

	/**
	 * 修改角色
	 * 
	 * @param bean
	 * @return ResultBean
	 */
	@Override
	public ResultBean alter(PoplarRole bean) {
		ResultBean result = new ResultBean();
		boolean status = roleDao.update(bean);
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改数据成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("修改数据失败！");
		}
		return result;
	}

	/**
	 * 删除角色
	 * 
	 * @param roleCode
	 * @return ResultBean
	 */
	@Override
	public ResultBean delete(String[] roleCodes) {

		ResultBean result = new ResultBean();
		boolean delStatus = roleDao.delete(roleCodes);
		if (delStatus) {
			result.setSuccess("true");
			result.setMessage("删除角色成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("删除角色失败！");
		}
		return result;
	}

	/**
	 * 启用角色
	 * 
	 * @param roleList
	 *            角色编码集合
	 * @return 是否成功
	 */
	public ResultBean start(String[] roleList) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][start]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = roleDao.updateState(roleList, "1");
		if (status) {
			result.setSuccess("true");
			result.setMessage("启用角色成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("启用角色失败！");
		}

		return result;
	}

	/**
	 * 禁用角色
	 * 
	 * @param roleList
	 *            角色编码集合
	 * @return 是否成功
	 */
	public ResultBean stop(String[] roleList) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][stop]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = roleDao.updateState(roleList, "2");
		if (status) {
			result.setSuccess("true");
			result.setMessage("禁用角色成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("禁用角色失败！");
		}

		return result;
	}

	/**
	 * 根据角色编码获取单条角色信息
	 * 
	 * @param roleCode
	 * @return
	 */
	public String getRoleListByRole(String roleCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][getRoleListByRole]开始执行");
		}
		DBResult dbResult = roleDao.getRoleByRoleCode(roleCode);
		Map<String, String> cellMap = new HashMap<String, String>();
		Gson json = new Gson();
		String result = "";
		if (dbResult != null && dbResult.getRows() > 0) {
			String[] resName = dbResult.getColName();

			for (int i = 0; i < resName.length; i++) {
				// 查询列对应的值
				String resValue = dbResult.getValue(0, resName[i]);
				// 放入map中
				cellMap.put(resName[i].toLowerCase(), resValue);
			}
			result = json.toJson(cellMap);
		}
		return result;
	}

	/**
	 * 获取角色列表
	 * 
	 * @return List<AutRole>
	 */
	@SuppressWarnings("rawtypes")
	public Map getRoleList(RoleWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getUserList]开始执行");
		}

		
		DBResult result = roleDao.getRoleList(where);
		// 页面数据
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		// 一行数据
		Map<String, String> cellMap = null;

		List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				cellMap = new HashMap<String, String>();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("role_code", result.getValue(i, "role_code"));
				cellMap.put("role_name", result.getValue(i, "role_name"));
				cellMap.put("state", result.getValue(i, "state"));
				cellMap.put("update_time", result.getValue(i, "update_time"));
				cellMap.put("role_desc", result.getValue(i, "role_desc"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);

		return pageInfo;
	}

	/**
	 * 根据角色编码获取资源树
	 * 
	 * @param
	 * @return
	 */
	public ResultBean getResTreeByRoleCode(String roleCode) {

		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][getResTreeByRoleCode]开始执行");
		}

		ResultBean result = new ResultBean();
		ResWhere where = new ResWhere();
		where.setState("1");
		DBResult dbResult = resDao.getList(where);
		DBResult dbResult2 = roleDao.getResTreeByRoleCode(roleCode);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; dbResult != null && i < dbResult.getRows(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; dbResult2 != null && j < dbResult2.getRows(); j++) {
				// 将角色拥有的权限在页面显示为已选择
				if (dbResult.getValue(i, "pk_id").equals(
						dbResult2.getValue(j, "pk_id"))) {
					map.put("ischecked", "true");
					break;
				}
			}
			map.put("pk_id", dbResult.getValue(i, "pk_id"));
			map.put("res_code", dbResult.getValue(i, "res_code"));
			map.put("res_name", dbResult.getValue(i, "res_name"));
			map.put("res_type", dbResult.getValue(i, "res_type"));
			map.put("res_father", dbResult.getValue(i, "res_father"));
			list.add(map);
		}

		result.setData(list);

		return result;
	}

	/**
	 * 根据用户编码获取权限资源列表
	 * 
	 * @param userCode
	 * @return list<Map>
	 */
	@Override
	public List<Resources> getResTreeByUserCode(String userCode) {
		DBResult result = resDao.getResTreeByUserCode(userCode);
		List<Resources> list = new ArrayList<Resources>();
		if (result != null) {
			for (int i = 0; i < result.getRows(); i++) {
				Resources res = new Resources();
				res.setPkId(result.getValue(i, "PK_ID"));
				res.setResName(result.getValue(i, "RES_NAME"));
				res.setResCode(result.getValue(i, "RES_CODE"));
				res.setResFather(result.getValue(i, "RES_FATHER"));
				res.setResValue(result.getValue(i, "RES_VALUE"));
				res.setResType(result.getValue(i, "res_type"));
				res.setShowType(result.getValue(i, "show_type"));
				list.add(res);
			}
		}
		return list;
	}

	/**
	 * 更新角色权限
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean updateRoleRes(PoplarRoleRes bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][updateRoleRes]开始执行");
		}

		boolean status = roleDao.updateRoleRes(bean);
		ResultBean retBean = new ResultBean();
		if (status) {
			if (log.isDebugEnabled()) {
				log.debug("更新角色权限信息成功！");
			}
			retBean.setSuccess("true");
			retBean.setMessage("更新角色权限信息成功！");
		} else {
			if (log.isWarnEnabled()) {
				log.warn("更新角色权限信息失败！");
			}
			retBean.setSuccess("false");
			retBean.setMessage("更新角色权限信息失败！");
		}

		return retBean;
	}

	/**
	 * 根据用户编码查询角色列表
	 * 
	 * @param userCode
	 *            用户编码
	 * @return 角色列表
	 */
	@Override
	public String getRoleListByUserCode(String userCode) {

		if (log.isInfoEnabled()) {
			log.info("组件[UserService][getRoleListByUser]开始执行");
		}
		DBResult result = userDao.getRoleListByUserCode(userCode);
		// 返回到前台的数据
		String retJson = null;
		// 页面数据
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		// 一行数据
		Map<String, String> cellMap = null;

		List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				cellMap = new HashMap<String, String>();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("role_code", result.getValue(i, "role_code"));
				cellMap.put("role_name", result.getValue(i, "role_name"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);
		Gson json = new Gson();
		retJson = json.toJson(pageInfo);
		return retJson;
	}

	/**
	 * 修改角色和用户的对应关系
	 * 
	 * @param userAccount
	 * @param roleCodes
	 * @return
	 */
	public ResultBean alterUserRole(String userAccount, String roleCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[RoleService][alterUserRole]开始执行");
		}

		ResultBean retBean = new ResultBean();
		
		boolean status = roleDao.alterUserRole(userAccount, roleCodes);

		if (status) {
			if (log.isDebugEnabled()) {
				log.debug("更新用户授权信息成功！");
			}
			retBean.setSuccess("true");
			retBean.setMessage("更新用户授权信息成功！");
		} else {
			if (log.isWarnEnabled()) {
				log.warn("更新用户授权信息失败！");
			}
			retBean.setSuccess("false");
			retBean.setMessage("更新用户授权信息失败！");
		}

		return retBean;
	}

	@Override
	public boolean judgeRoleNameExit(String roleName, String roleId) {
		DBResult dbr = roleDao.judgeRoleNameExit(roleName, roleId);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

}

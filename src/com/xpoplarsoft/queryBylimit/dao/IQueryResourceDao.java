package com.xpoplarsoft.queryBylimit.dao;

import com.xpoplarsoft.framework.db.DBResult;

public interface IQueryResourceDao {

	/**
	 * 根据关键字查询所有卫星数据
	 * @param userId
	 * @param key
	 * @return
	 */
	DBResult querySatList(String userId, String key);

	/**
	 * 根据关键字查询所有卫星数据
	 * @param userId
	 * @param key
	 * @param page
	 * @param pageSize
	 * @return
	 */
	DBResult querySatByPage(String userId, String key, int page,
			int pageSize);

	/**
	 * 获取卫星树结构数据
	 * @param userId
	 * @param key
	 * @return
	 */
	DBResult findSatTree(String userId, String key);

	/**
	 * 适用于动态查询树节点，根据父节点ID查询子节点数据，
	 *      sys_resource_id = -1        返回卫星
	 *      sys_resource_id = 卫星ID  返回该卫星下的分系统
	 *      sys_resource_id = 分系统ID   返回该分系统下的单机
	 * @param userId 用户唯一ID
	 * @param sys_resource_id 父节点ID
	 * @param key 关键字（卫星名称或卫星编号）模糊查询
	 * @return
	 */
	DBResult findGrantUserGroupEquipmentTree(String userId,
			String sys_resource_id, String key);

	/**
	 * 根据登录账号判断是否有该卫星的权限
	 * @param userAccount
	 * @param satMid
	 * @return 存在返回true
	 */
	boolean judgeHaveSatLimit(String userAccount, String satMid);

}

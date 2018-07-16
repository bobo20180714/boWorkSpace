package com.xpoplarsoft.queryBylimit.service;

import java.util.List;

import com.xpoplarsoft.queryBylimit.bean.SatBean;
import com.xpoplarsoft.queryBylimit.bean.SatSubAloneBean;

/**
 * 根据权限获取资源接口类
 * 
 * @author mxc
 * @date 2017-05-22
 *
 */
public interface IQueryResourceService {

	/**
	 * 获取卫星列表
	 * @param userId 用户唯一ID
	 * @param key  关键字（卫星名称或卫星编号）模糊查询
	 * @return
	 */
	public List<SatBean> querySatList(String userId, String key);

	/**
	 * 获取卫星总条数
	 * @param userId 用户唯一ID
	 * @param key  关键字（卫星名称或卫星编号）模糊查询
	 * @return
	 */
	public int querySatTotal(String userId,String key);
	
	/**
	 * 根据关键字（卫星名称或卫星代号） 分页查询卫星列表
	 * @param userId 用户唯一ID
	 * @param key  关键字（卫星名称或卫星编号）模糊查询
	 * @param page  当前页数
	 * @param pageSize  每页条数
	 * @return
	 */
	public List<SatBean> querySatByPage(String userId,String key,int page,int pageSize);
	
	/**
	 * 获取卫星树结构数据，包含卫星、分系统、单机数据
	 *    
	 * @param userId 用户唯一ID
	 * @param key  关键字（卫星名称或卫星编号）模糊查询
	 * @return
	 */
	public List<SatSubAloneBean> findSatTree(String userId, String key);

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
	public List<SatSubAloneBean> findGrantUserGroupEquipmentTree(String userId,
			String sys_resource_id, String key);
	
	/**
	 * 根据登录账号判断是否有该卫星的权限
	 * @param userAccount
	 * @param satMid
	 * @return
	 */
	public boolean judgeHaveSatLimit(String userAccount,String satMid);
}

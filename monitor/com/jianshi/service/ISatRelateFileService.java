package com.jianshi.service;

import java.util.List;
import java.util.Map;

import com.jianshi.model.PagTreeConfBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 综合显示挂接功能
 * @author mengxiangchao
 *
 */
public interface ISatRelateFileService {

	/**
	 * 查询树型结构
	 * @param ownerId
	 * @param userId
	 * @param isAdmin  0:超级管理员，1：否
	 * @return
	 */
	List<Map<String, String>> queryMonitorTree(String ownerId,String userId,int isAdmin);

	/**
	 * 新增关联关系
	 * @param bean
	 * @return
	 */
	boolean addSatFileRelate(List<PagTreeConfBean> beanList);

	/**
	 * 分页查询查询已挂接的文件
	 * @param ownerId
	 * @param ownerType
	 * @param bean
	 * @return
	 */
	Map<String, Object> queryRelateFileList(String ownerId,String ownerType, CommonBean bean);

	/**
	 * 查询文件树结构，
	 * 	已经关联的节点，设置 map.put("ischecked", "true");
	 * @param session
	 * @param ownerId
	 * @return
	 */
	List<Map<String, String>> queryFileTree(String ownerId);

	/**
	 * 查询关联关系
	 * @param ownerId
	 * @return
	 */
	List<Map<String, Object>> queryRelateFile(String ownerId);

	/**
	 * 删除关联关系
	 * @param ownerId
	 * @param fileIdArr
	 * @return
	 */
	boolean deleteRelate(String ownerId, String[] fileIdArr);

	List<Map<String, Object>> queryFloadTree();

	boolean judgeHaveRelate(String ownerId, String fileId);

	Map<String, Object> queryUserFile(String pkId);

	boolean updateUserFile(String pkId, String page_name, String page_url,
			String open_mode);

	boolean deleteUserFile(String pkId);

	Map<String, Object> getOwnerIdBySatId(String satId);
}

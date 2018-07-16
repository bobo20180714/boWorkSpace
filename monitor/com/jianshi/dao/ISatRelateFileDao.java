package com.jianshi.dao;

import java.util.List;

import com.jianshi.model.PagTreeConfBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface ISatRelateFileDao {

	DBResult queryMonitorTree(String ownerId,String userId,int isAdmin);

	boolean addSatFileRelate(List<PagTreeConfBean> beanList);

	DBResult queryRelateFileList(String ownerId,String ownerType, CommonBean bean);

	DBResult queryFileTree();

	/**
	 * 查询已经关联的文件
	 * @param ownerId
	 * @return
	 */
	DBResult queryRelateFile(String ownerId);

	boolean deleteRelate(String ownerId, String[] fileIdArr);

	DBResult judgeHaveRelate(String ownerId, String fileId);

	DBResult queryUserFile(String pkId);

	boolean updateUserFile(String pkId, String page_name, String page_url,
			String open_mode);

	boolean deleteUserFile(String pkId);

	DBResult getOwnerIdBySatId(String satId);

	DBResult queryNodeBySpuerId(String super_id,String isroot);

}

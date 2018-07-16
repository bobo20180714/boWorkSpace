package com.jianshi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianshi.dao.ISatRelateFileDao;
import com.jianshi.model.PagTreeConfBean;
import com.jianshi.service.IProjectService;
import com.jianshi.service.ISatRelateFileService;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class SatRelateFileService implements ISatRelateFileService {

	@Autowired
	private ISatRelateFileDao satRelateFileDao;
	
	@Autowired
	private IProjectService projectService;
	
	@Override
	public List<Map<String, String>> queryMonitorTree(String ownerId,String userId,int isAdmin) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> dataMap = null;
		if("-1".equals(ownerId)){
			getRootList(dataList);
		}else{
			DBResult dbr = satRelateFileDao.queryMonitorTree(ownerId,userId,isAdmin);
			for (int i = 0; dbr != null && i < dbr.getRows(); i++) {
				dataMap = new HashMap<String, String>();
				dataMap.put("id", dbr.get(i, "pk_id"));
				dataMap.put("text",dbr.get(i, "node_name"));
				dataMap.put("pid", dbr.get(i, "super_id"));
				dataMap.put("node_type", dbr.get(i, "node_type"));
				dataMap.put("isroot", dbr.get(i, "isroot"));
				dataMap.put("isleaf", dbr.get(i, "isleaf"));
				dataMap.put("obj_id", dbr.get(i, "obj_id"));
				dataMap.put("open_mode", dbr.get(i, "open_mode"));
				dataMap.put("page_name", dbr.get(i, "node_name"));
				dataMap.put("page_url", dbr.get(i, "page_url"));
				dataList.add(dataMap);
			}
		}
		return dataList;
	}

	/**
	 * 获取根节点信息
	 * @param dataList
	 */
	private void getRootList(List<Map<String, String>> dataList) {
		//查询根节点
		DBResult dbr = satRelateFileDao.queryNodeBySpuerId("-1","0");
		
		Map<String, String> dataMap = null;
		for (int i = 0; i < dbr.getRows(); i++) {
			dataMap = new HashMap<String, String>();
			dataMap.put("id", dbr.get(i, "pk_id"));
			dataMap.put("text", dbr.get(i, "page_name"));
			dataMap.put("pid", "-1");
			dataMap.put("node_type",dbr.get(i, "type"));
			dataMap.put("isroot", dbr.get(i, "isroot"));
			dataList.add(dataMap);
		}
	}

	@Override
	public boolean addSatFileRelate(List<PagTreeConfBean> beanList) {
		return satRelateFileDao.addSatFileRelate(beanList);
	}

	@Override
	public Map<String, Object> queryRelateFileList(String ownerId,String ownerType,
			CommonBean bean) {
		DBResult dbr = satRelateFileDao.queryRelateFileList(ownerId,ownerType,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public List<Map<String, String>> queryFileTree(String ownerId) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> dataMap = null;
		DBResult dbr = satRelateFileDao.queryFileTree();
		//查询已经分配的文件列表
		List<Map<String, Object>> alreadyFileList = queryRelateFile(ownerId);
		for (int i = 0; dbr != null && i < dbr.getRows(); i++) {
			dataMap = new HashMap<String, String>();
			Map<String, Object> alreadyFileMap = null;
			for (int j = 0; alreadyFileList != null && j < alreadyFileList.size(); j++) {
				alreadyFileMap = alreadyFileList.get(i);
				if(dbr.get(i, "pk_id").equals(alreadyFileMap.get("obj_id"))){
					dataMap.put("ischecked", "true");//设置选中
					break;
				}
			}
			dataMap.put("id", dbr.get(i, "id"));
			dataMap.put("text",dbr.get(i, "text"));
			dataMap.put("pid", dbr.get(i, "owner"));
			dataList.add(dataMap);
		}
		return dataList;
	}
	
	@Override
	public List<Map<String, Object>> queryRelateFile(String ownerId) {
		//查询已经分配的文件列表
		DBResult dbr = satRelateFileDao.queryRelateFile(ownerId);
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public boolean deleteRelate(String ownerId, String[] fileIdArr) {
		return satRelateFileDao.deleteRelate(ownerId,fileIdArr);
	}

	@Override
	public List<Map<String, Object>> queryFloadTree() {
		
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", "-1");
		dataMap.put("name", "卫星");
		dataMap.put("owner", "-10");
		dataMap.put("node_type", "1");
		dataMap.put("isroot", "0");
		rsList.add(dataMap);
		rsList.addAll(projectService.getFloadTree());
		
		return rsList;
	}

	@Override
	public boolean judgeHaveRelate(String ownerId,
			String fileId) {
		DBResult dbr = satRelateFileDao.judgeHaveRelate(ownerId,fileId);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> queryUserFile(String pkId) {
		DBResult dbr = satRelateFileDao.queryUserFile(pkId);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public boolean updateUserFile(String pkId, String page_name,
			String page_url, String open_mode) {
		return satRelateFileDao.updateUserFile(pkId, page_name,page_url,open_mode);
	}

	@Override
	public boolean deleteUserFile(String pkId) {
		return satRelateFileDao.deleteUserFile(pkId);
	}

	@Override
	public Map<String, Object> getOwnerIdBySatId(String satId) {
		DBResult dbr = satRelateFileDao.getOwnerIdBySatId(satId);
		return DBResultUtil.dbResultToMap(dbr);
	}

}

package com.jianshi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jianshi.dao.ISatRelateFileDao;
import com.jianshi.model.PagTreeConfBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class SatRelateFileDao implements ISatRelateFileDao {

	@Override
	public DBResult queryMonitorTree(String ownerId,String userId,int isAdmin) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("ownerId", ownerId);
		dbp.setObject("userId", userId);
		dbp.setObject("isAdmin", isAdmin);//0:是超级管理员 1：否
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "queryMonitorTree", dbp);
	}

	@Override
	public boolean addSatFileRelate(List<PagTreeConfBean> beanList) {
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = null;
		for (PagTreeConfBean bean : beanList) {
			dbp = new DBParameter();
			dbp.setObject("pk_id", bean.getPk_id());
			dbp.setObject("super_id", bean.getSuper_id());
			dbp.setObject("type", bean.getType());
			dbp.setObject("isroot", bean.getIsroot());
			dbp.setObject("isleaf", bean.getIsleaf());
			dbp.setObject("page_name", bean.getPage_name());
			dbp.setObject("page_url", bean.getPage_url());
			dbp.setObject("open_mode", bean.getOpen_mode());
			dbp.setObject("isalias", bean.getIsalias());
			dbp.setObject("obj_id", bean.getObj_id());
			dbp.setObject("create_user", bean.getCreate_user());
			dbpList.add(dbp);
		}
		return SQLFactory.getSqlComponent().batchUpdate("SatRelateFile", "addSatFileRelate", dbpList);
	}

	@Override
	public DBResult queryRelateFileList(String ownerId, String ownerType,CommonBean bean) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("ownerId", ownerId);
		if("0".equals(ownerType)){
			return SQLFactory.getSqlComponent().pagingQueryInfo("SatRelateFile", "queryRelateFile", 
					dbp,bean.getPage(),bean.getPagesize());
		}
		return SQLFactory.getSqlComponent().pagingQueryInfo("SatRelateFile", "queryRelateFileList", 
				dbp,bean.getPage(),bean.getPagesize());
	}

	@Override
	public DBResult queryFileTree() {
		DBParameter dbp = new DBParameter();
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "queryFileTree", dbp);
	}

	@Override
	public DBResult queryRelateFile(String ownerId) {
		DBParameter dbp = new DBParameter();
		 dbp.setObject("ownerId", ownerId);
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "queryRelateFile", dbp);
	}

	@Override
	public boolean deleteRelate(String ownerId, String[] fileIdArr) {
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = null;
		for (int i = 0; i < fileIdArr.length; i++) {
			 dbp = new DBParameter();
			 dbp.setObject("ownerId", ownerId);
			 dbp.setObject("fileId", fileIdArr[i]);
			 dbpList.add(dbp);
		}
		return SQLFactory.getSqlComponent().batchUpdate("SatRelateFile", "deleteRelate", dbpList);
	}

	@Override
	public DBResult judgeHaveRelate(String ownerId, String fileId) {
		DBParameter dbp = new DBParameter();
		 dbp.setObject("ownerId", ownerId);
		 dbp.setObject("fileId", fileId);
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "judgeHaveRelate", dbp);
	}

	@Override
	public DBResult queryUserFile(String pkId) {
		DBParameter dbp = new DBParameter();
		 dbp.setObject("pkId", pkId);
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "queryUserFile", dbp);
	}

	@Override
	public boolean updateUserFile(String pkId, String page_name,
			String page_url, String open_mode) {
		DBParameter dbp =  new DBParameter();
		dbp.setObject("pk_id", pkId);
		dbp.setObject("page_name", page_name);
		dbp.setObject("page_url", page_url);
		dbp.setObject("open_mode", open_mode);
		return SQLFactory.getSqlComponent().updateInfo("SatRelateFile", "updateUserFile", dbp);
	}

	@Override
	public boolean deleteUserFile(String pkId) {
		DBParameter dbp =  new DBParameter();
		dbp.setObject("pkId", pkId);
		return SQLFactory.getSqlComponent().updateInfo("SatRelateFile", "deleteUserFile", dbp);
	}

	@Override
	public DBResult getOwnerIdBySatId(String satId) {
		DBParameter dbp =  new DBParameter();
		dbp.setObject("satId", satId);
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "getOwnerIdBySatId", dbp);
	}

	@Override
	public DBResult queryNodeBySpuerId(String super_id,String isroot) {
		DBParameter dbp =  new DBParameter();
		dbp.setObject("super_id", super_id);
		dbp.setObject("isroot", isroot);
		return SQLFactory.getSqlComponent().queryInfo("SatRelateFile", "queryNodeBySpuerId", dbp);
	}

}

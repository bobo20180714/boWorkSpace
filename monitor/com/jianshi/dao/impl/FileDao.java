package com.jianshi.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.IFileDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class FileDao implements IFileDao {

	@Override
	public Map<String, Object> getLastFile(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("file","getLastFile", para);
		return Common.getClob(result);
	}

	@Override
	public boolean addFile(String proId, String name, String data,String uid) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("name", name);
		para.setObject("data", data);
		para.setObject("uid", uid);
		return SQLFactory.getSqlComponent().updateInfo("file", "addFile", para);
	}

	@Override
	public List<Map<String, Object>> getFileByProId(String proId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("file","getFileByProId", para);		
		return Common.getClobs(result);
	}

	@Override
	public Map<String, Object> getFileById(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		DBResult result = SQLFactory.getSqlComponent().queryInfo("file","getFileById", para);
		return Common.getClob(result);
	}

	@Override
	public void delFile(String id) {
		DBParameter para = new DBParameter();
		para.setObject("id", Integer.parseInt(id));
		SQLFactory.getSqlComponent().updateInfo("file", "delFile", para);
	}

	@Override
	public boolean existFile(String proId, String name) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("name", name);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("file", "existFile", para);
		return Long.parseLong(result.getObject(0, 0).toString())>0;
	}

	@Override
	public boolean updateFile(String proId, String name, String data) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("name", name);
		para.setObject("data", data);
		return SQLFactory.getSqlComponent().updateInfo("file", "updateFile", para);
	}
	
	@Override
	public boolean updateFileName(String proId, String name) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("name", name);
		return SQLFactory.getSqlComponent().updateInfo("file", "updateFileName", para);
	}

	@Override
	public void addLib(String proId,String srcProjId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("srcProjId", Integer.parseInt(srcProjId));
		SQLFactory.getSqlComponent().updateInfo("file", "addLib", para);
	}

	@Override
	public void addJar(String proId,String srcProjId) {
		DBParameter para = new DBParameter();
		para.setObject("proId", Integer.parseInt(proId));
		para.setObject("srcProjId", Integer.parseInt(srcProjId));
		SQLFactory.getSqlComponent().updateInfo("file", "addJar", para);
	}

	@Override
	public DBResult getFileListByFload(String key,String proId) {
		DBParameter para = new DBParameter();
		para.setObject("key", key);
		para.setObject("proId", proId);
		return SQLFactory.getSqlComponent().queryInfo("file","getFileListByFload", para);
	}

	@Override
	public DBResult getAllFileList(CommonBean bean, String key) {
		DBParameter para = new DBParameter();
		para.setObject("key", key);
		return SQLFactory.getSqlComponent().pagingQueryInfo("file","getAllFileList", para, bean.getPage(), bean.getPagesize());
	}
}

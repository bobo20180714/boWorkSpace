package com.jianshi.dao;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IFileDao {
	
	Map<String, Object> getLastFile(String proId);

	boolean addFile(String proId, String name, String data,String uid);

	List<Map<String,Object>> getFileByProId(String proId);

	Map<String, Object> getFileById(String id);

	void delFile(String id);
	
	boolean existFile(String proId, String name);
	
	boolean updateFile(String proId, String name, String data);
	
	boolean updateFileName(String proId, String name);

	void addLib(String proId,String srcProjId);

	void addJar(String proId,String srcProjId);

	DBResult getFileListByFload(String key,String proId);

	DBResult getAllFileList(CommonBean bean, String key);
}

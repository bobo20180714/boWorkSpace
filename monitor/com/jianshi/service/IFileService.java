package com.jianshi.service;

import java.util.Map;

import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IFileService {

	String getLastFile(String proId);

	String addFile(String proId, String name, String data,String uid);

	String getFileByProId(String proId);

	String getFileById(String id);

	boolean delFile(String id);

	boolean addLibJar(String proId,String srcProjId);

	Map<String, Object> getFileListByFload(String key,String ownerId,String proId);

	Map<String, Object> getAllFileList(CommonBean bean, String key);

	boolean updateFileName( String id,String name);

}

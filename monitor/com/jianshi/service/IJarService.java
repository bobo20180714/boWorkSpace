package com.jianshi.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface IJarService {

	boolean registJar(MultipartFile jarFile);

	String getSelectJar(String proId);

	boolean addProjJar(String proId, String[] jarIds);

	String getSelectedJar(String proId,String key);

	boolean delJar(String id);

	boolean delProjJar(String proId, String jarId);

	String getJarGraph(String jarId, String plugId);

	boolean registBaseData(String[] data);

	String getParams(String jarId,CommonBean bean,String key);
	
	String getJsjg(String satId,String key);
	
	String getJsjgField(String jsjgId);
	
	String getCezhan();

	String getGraphId(String devId, String dataSource, String parIds);

	String getParIds(String jarId);

	List<Map<String, Object>> getSats(String key);

}

package com.jianshi.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.bean.ResultBean;

public interface IProjectService {

	List<Map<String, Object>> getProj(String uid);
	
	//用于验证文件名是否重复
	boolean getProj(String uid,int owner,String name);
	boolean getProj(String uid,int owner);
	
	String addNode(String name, int owner, String uid, String isleaf);
	
	String addNode(String name, int owner, String uid, String isleaf,String data);

	boolean editNode(String id, String name);

	boolean delNode(String id);

	boolean addProjectLib(String values);

	String getProjLib(String proId);

	boolean delProjLib(String id);

	Object getAllProj();

	/**
	 * 获取文件夹树
	 * @param session
	 * @return
	 */
	List<Map<String,Object>> getFloadTree();

	ResultBean addFload(String name, String owner, String isleaf);

}

package com.jianshi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.jianshi.dao.IFileDao;
import com.jianshi.dao.IPlugDao;
import com.jianshi.dao.IProjectDao;
import com.jianshi.model.PagTreeConfBean;
import com.jianshi.service.IProjectService;
import com.jianshi.service.ISatRelateFileService;
import com.jianshi.util.Common;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class ProjectService implements IProjectService {
	
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IPlugDao plugDao;
	@Autowired
	private IFileDao fileDao;
	
	@Autowired
	private ISatRelateFileService satRelateFileService;

	@Override
	public List<Map<String, Object>> getProj(String uid) {
		return projectDao.getProj(uid);
	}
	
	@Override
	public List<Map<String, Object>> getAllProj() {
		return projectDao.getAllProj();
	}

	@Override
	public String addNode(String name, int owner, String uid, String isleaf) {
		try {
			return projectDao.addNode(name,owner,uid,isleaf);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String addNode(String name, int owner, String uid, String isleaf,String data) {
		try {
			String id=projectDao.addNode(name,owner,uid,isleaf);
			fileDao.addFile(id,name,data,uid);
			return id;
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean editNode(String id, String name) {
		try {
			projectDao.editNode(id,name);
			fileDao.updateFileName(id, name);
			return true;
			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delNode(String id) {
		try {
			projectDao.delNode(id);//该文件夹下没有子节点
			return true;
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;//文件夹下有子节点
	}

	@Override
	public boolean addProjectLib(String values) {
		try {
			Map<String, String> map=new HashMap<String, String>();
			map.put("values", values);
			projectDao.addProjectLib(map);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getProjLib(String proId) {
		List<Map<String, Object>> libs=projectDao.getProjLib(proId);
		for (Map<String, Object> lib : libs) {
			List<Map<String, Object>> plugs=plugDao.getPlugByLibId(lib.get("id").toString());
			for(Map<String, Object> plug : plugs){
				if(plug.get("type").toString().equals("0")){
					List<Map<String, Object>> statics=plugDao.getStaticData(plug.get("id").toString());
					plug.put("exps", statics);
				}
			}
			lib.put("plugs", plugs);
		}
		return JSONArray.toJSONString(libs);
	}

	@Override
	public boolean delProjLib(String id) {
		try {
			projectDao.delProjLib(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getFloadTree() {
		DBResult dbr = projectDao.getFloadTree();
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public ResultBean addFload(String name, String owner, String isleaf) {
		ResultBean rb = new ResultBean();
		//添加文件夹，返回文件夹ID
//		String nodeId = addNode(name, Integer.parseInt(owner), owner, isleaf);
		//添加关联信息
		PagTreeConfBean bean = null;
		List<PagTreeConfBean> beanList = new ArrayList<PagTreeConfBean>();
		bean = new PagTreeConfBean();
		String id=Common.getId("SAT_TM_ID_SEQ");
		bean.setPk_id(id);
		bean.setPage_name(name);
		bean.setSuper_id(owner);
		bean.setType(4);
		bean.setIsroot(1);
		bean.setIsleaf(0);
		bean.setIsalias(1);
		bean.setObj_id(id);
		beanList.add(bean);
		boolean flag = satRelateFileService.addSatFileRelate(beanList);
		if(flag){
			rb.setData(id);
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return rb;
	}

	@Override
	public boolean getProj(String uid,int owner, String name) {
		boolean flag=false;
		Map<String, Object> map = null;
		map = projectDao.getProj(uid, owner, name);
		if(map == null){//文件名不重复，可以添加
			flag=true;
		}
		return flag;//文件名重复
	}

	@Override
	public boolean getProj(String uid, int owner) {
		boolean flag = false;
		Map<String, Object> map = null;
		map = projectDao.getProj(uid, owner);
		if(map == null || map.size() == 0){
			flag = true;//可以删除
		}
		return flag;//存在子节点，不能删除
	}

}

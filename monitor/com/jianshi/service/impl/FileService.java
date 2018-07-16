package com.jianshi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jianshi.dao.IFileDao;
import com.jianshi.dao.IPlugDao;
import com.jianshi.service.IFileService;
import com.jianshi.service.ISatRelateFileService;
import com.jianshi.util.Common;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class FileService implements IFileService {
	
	private static Log log = LogFactory.getLog(FileService.class);
	
	@Autowired
	private IFileDao fileDao;
	@Autowired
	private IPlugDao plugDao;

	@Autowired
	private ISatRelateFileService satRelateFileService;

	@Override
	public String getLastFile(String proId) {
		Map<String, Object> files=fileDao.getLastFile(proId);
		Common.getJson(files, "data");
		return JSONObject.toJSONString(files);
	}

	@Override
	public String getFileById(String id) {
		Map<String, Object> files=fileDao.getFileById(id);
		Common.getJson(files, "data");
		return JSONObject.toJSONString(files);
	}

	@Override
	public String addFile(String proId, String name, String data,String uid) {
		try {
			boolean flag = false;
			if(fileDao.existFile(proId, name)){
				if(log.isDebugEnabled()){
					log.debug("proId=["+proId+"]name=["+name+"]已经存在，进行编辑。");
				}
				flag = fileDao.updateFile(proId, name, data);
			}
			else{
				if(log.isDebugEnabled()){
					log.debug("proId=["+proId+"]name=["+name+"]不存在，进行新增。");
				}
				flag = fileDao.addFile(proId,name,data,uid);
			}
			if(flag){
				return "T";
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return "F";
	}
	
	@Override
	public String getFileByProId(String proId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("Rows", fileDao.getFileByProId(proId));
		return JSONObject.toJSONString(map);
	}

	@Override
	public boolean delFile(String id) {
		try {
			fileDao.delFile(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addLibJar(String proId,String srcProjId) {		
		try {
			fileDao.addLib(proId,srcProjId);
			fileDao.addJar(proId,srcProjId);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Map<String, Object> getFileListByFload(String key,String ownerId,String proId) {
		key = key == null?"":key;
		DBResult dbResult = fileDao.getFileListByFload(key,proId);

		List<Map<String, Object>> alreadyFileList = satRelateFileService.queryRelateFile(ownerId);
		
		Map<String,Object> pageData = new HashMap<String, Object>();
		//列表数据
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if (dbResult == null || dbResult.getRows() <= 0) {
			pageData.put("Total",0);
		} else {
			//获取总条数
			int rows = dbResult.getRows();
			pageData.put("Total",dbResult.getTotal());
			Map<String,Object> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,Object>();
				String id = dbResult.get(i, "id");

				cellMap.put("id", id);
				Object name = dbResult.getObject(i, "name");
				cellMap.put("name", name);
				Object page_name = dbResult.getObject(i,"page_name");
				cellMap.put("page_name", page_name==null?name:page_name);
				Map<String, Object> alreadyFileMap = null;
				for (int j = 0; alreadyFileList != null && j < alreadyFileList.size(); j++) {
					alreadyFileMap = alreadyFileList.get(j);
					if(id.equals(alreadyFileMap.get("obj_id"))){
						cellMap.put("isChecked", "true");//设置选中
						Object page_name2 = alreadyFileMap.get("page_name");
						cellMap.put("page_name", page_name2==null?name:page_name2);
						break;
					}
				}
				
				Object open_mode = dbResult.getObject(i, "open_mode");
				cellMap.put("open_mode", open_mode==null?"2":open_mode);
				String isleaf = dbResult.get(i, "isleaf");
				cellMap.put("isleaf", isleaf);
				infoList.add(cellMap);
			}
		}
		pageData.put("Rows",infoList);
		return pageData;
	}

	@Override
	public Map<String, Object> getAllFileList(CommonBean bean, String key) {
		DBResult dbResult = fileDao.getAllFileList(bean,key);
		return DBResultUtil.dbResultToPageData(dbResult);
	}

	@Override
	public boolean updateFileName(String id, String name) {		
		return fileDao.updateFileName(id,name);
	}
}

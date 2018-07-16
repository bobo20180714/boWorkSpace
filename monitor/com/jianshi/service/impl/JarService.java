package com.jianshi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataSource.model.Config;
import com.dataSource.util.Utils;
import com.jianshi.dao.IJarDao;
import com.jianshi.dao.IPlugDao;
import com.jianshi.db.DB;
import com.jianshi.service.IJarService;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Service
public class JarService implements IJarService {
	@Autowired
	private IJarDao jarDao;
	@Autowired
	private IPlugDao plugDao;

	@Override
	public boolean registJar(MultipartFile jarFile) {		
		try {
			InputStream is=Common.getJarFile(jarFile.getInputStream(), "config.xml");
			Config config=Utils.loadConfig(is);
			Map<String, String> dataSource=config.getDataSource();
			String device=dataSource.get("device");
			String path=Common.getConfigVal("jarPath");
			String fileName=device+".jar";
			File file = new File(path,fileName); 
			jarFile.transferTo(file);
			jarDao.registJar(device,1,dataSource.get("name"),dataSource.get("ver"),dataSource.get("desc"));
			String jarId=jarDao.registJar(device,1,dataSource.get("name"),dataSource.get("ver"),dataSource.get("desc"));
			List<Map<String, Object>> params=config.getParams();
			for (Map<String, Object> param : params) {
				jarDao.registParams(jarId, param.get("id").toString(), param.get("name").toString(), param.get("code").toString());
			}
			return true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}		
		return false;
	}

	@Override
	public String getSelectJar(String proId) {
		List<Map<String,Object>> jars=jarDao.getSelectJar(proId);			
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows",jars);
		return JSONObject.toJSONString(map);
	}

	@Override
	public boolean addProjJar(String proId, String[] jarIds) {
		try {
			List<String> jars=jarDao.getSelectedJar(proId);
			for (String jarId : jarIds) {
				if(!jars.contains(jarId)){
					jarDao.addProjJar(proId,jarId);
				}
			}			
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getSelectedJar(String proId,String key) {
		List<Map<String, Object>> devs=getDevs(key);
		List<String> jars=jarDao.getSelectedJar(proId);
		List<Map<String, Object>> selectedJars=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> dev : devs) {
			if(jars.contains(dev.get("id").toString())){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", dev.get("id"));
				map.put("name", dev.get("name"));
				map.put("ver", dev.get("ver"));
				map.put("desc", dev.get("desc"));
				map.put("text", dev.get("name"));
				selectedJars.add(map);
			}
		}
		return JSONArray.toJSONString(selectedJars);
	}
	
	@Override
	public boolean delJar(String id) {
		try {
			Map<String,Object> jar=jarDao.getSingleJar(id);
			String devId=jar.get("device_id").toString();
			String path=Common.getConfigVal("jarPath");
			File file=new File(path+devId+".jar");
			if(file.exists())
				file.delete();
			jarDao.delJar(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delProjJar(String proId, String jarId) {
		try {
			jarDao.delProjJar(proId,jarId);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getJarGraph(String jarId, String plugId) {
		Map<String,Object> jar=jarDao.getSingleJar(jarId);
		String devId=jar.get("device_id").toString();
		String path=Common.getConfigVal("jarPath");
		InputStream is=Common.getJarFile(path+devId+".jar", "config.xml");
		Config config=Utils.loadConfig(is);
		List<Map<String, Object>> params=config.getParams();
		List<Map<String, Object>> states=null;
		for (Map<String, Object> param : params) {
			if(param.get("id").toString().equals(devId)){
				states=(List<Map<String, Object>>)param.get("states");
				break;
			}
		}
		List<Map<String, Object>> staList=Common.base64(plugDao.getStaticData(plugId), "img");
		if(staList.size()>0){
			Map<String, Object> staMap=Common.getJson(staList.get(0), "state");
			for (Map<String, Object> state : states) {
				state.putAll(staMap);
			}
		}		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("Rows",states);
		return JSONObject.toJSONString(map);
	}

	private List<Map<String, Object>> getDevs(String key) {
		List<Map<String, Object>> jars=new ArrayList<Map<String,Object>>();
		String deviceSql=Common.getConfigVal("deviceSql");
		if(deviceSql!=null){
			PreparedStatement sql = DB.createSql(deviceSql);
			if(sql!=null){
				try {
					sql.setString(1,key);
					ResultSet rst = sql.executeQuery();
					while(rst.next()){
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("id", rst.getString("id"));
						map.put("name", rst.getString("name"));
						map.put("ver", rst.getString("ver"));
						map.put("desc", "");
						jars.add(map);
					}
					return jars;
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return jars;
	}
	

	@Override
	public List<Map<String, Object>> getSats(String key) {
		List<Map<String, Object>> jars=new ArrayList<Map<String,Object>>();
		String deviceSql=Common.getConfigVal("deviceSql");
		if(deviceSql!=null){
			PreparedStatement sql = DB.createSql(deviceSql);
			if(sql!=null){
				try {
					sql.setString(1,key);
					ResultSet rst = sql.executeQuery();
					while(rst.next()){
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("id", rst.getString("id"));
						map.put("text", rst.getString("name"));
						map.put("ver", rst.getString("ver"));
						map.put("desc", "");
						jars.add(map);
					}
					return jars;
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return jars;
	}
	
	@Override
	public boolean registBaseData(String[] data) {		
		try {
			for (String json : data) {
				JSONObject obj=JSONObject.parseObject(json);
				String id=obj.getString("id");
				String jarId=jarDao.existJar(id, 0);
				if(jarId!=null){
					jarDao.updateJar(id, 0, obj.getString("name"), obj.getString("ver"), obj.getString("desc"));
				}
				else{
					jarId=jarDao.registJar(id, 0, obj.getString("name"), obj.getString("ver"), obj.getString("desc"));
				}				
				String paramSql=Common.getConfigVal("paramSql");
				if(paramSql!=null){
					PreparedStatement sql = DB.createSql(paramSql);
					if(sql!=null){						
						try {
							sql.setInt(1,Integer.parseInt(id));
							ResultSet rst = sql.executeQuery();
							while(rst.next()){
								if(jarDao.existParams(jarId, rst.getString("id"))){
									jarDao.updateParams(jarId, rst.getString("id"),rst.getString("name"),rst.getString("code"));
								}
								else{
									jarDao.registParams(jarId,rst.getString("id"),rst.getString("name"),rst.getString("code"));
								}								
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}				
					}
				}
			}
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getParams(String jarId,CommonBean bean,String key) {
		String paramSql=Common.getConfigVal("paramSql_new");
		Map<String,Object> params = new HashMap<String,Object>();
		if(paramSql!=null){
			paramSql = paramSql + " and sat_id="+jarId;
			if(key != null){
				paramSql = paramSql + " and (lower(tm_param_name) like '%"+key.toLowerCase()+"%' or lower(tm_param_code) like '%"+key.toLowerCase()+"%') ";
			}
			@SuppressWarnings("deprecation")
			DBResult dbr = SQLFactory.getSqlComponent().pagingQueryInfo(paramSql,bean.getPage(), bean.getPagesize());
			params = dbResultToPageData(dbr);
		}
		return JSONArray.toJSONString(params);
	}
	
	public static Map<String,Object> dbResultToPageData(DBResult dbResult) {
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
				cellMap.put("id", dbResult.get(i, "id"));						
				cellMap.put("name", dbResult.get(i, "name"));
				cellMap.put("code", dbResult.get(i, "code"));
				infoList.add(cellMap);
			}
		}
		pageData.put("Rows",infoList);
		
		return pageData;
	}
	
	@Override
	public String getJsjg(String satId,String key) {
		String deviceSql=Common.getConfigVal("jsjgSql");
		if(deviceSql!=null){
			PreparedStatement sql = DB.createSql(deviceSql);
			if(sql!=null){
				try {
					sql.setInt(1,Integer.parseInt(satId));
					List<Map<String, Object>> jsjgs=new ArrayList<Map<String,Object>>();
					ResultSet rst = sql.executeQuery();
					while(rst.next()){
						String name=rst.getString("name");
						String code=rst.getString("code");
						if(key.equals("")
								||name.toUpperCase().indexOf(key.toUpperCase())>-1
								||code.toUpperCase().indexOf(key.toUpperCase())>-1){
							Map<String, Object> map=new HashMap<String, Object>();
							map.put("id", rst.getString("id"));
							map.put("name", name);
							map.put("code", code);
							jsjgs.add(map);
						}						
					}
					return "{\"Rows\":"+JSONArray.toJSONString(jsjgs)+"}";
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return null;
	}
	
	@Override
	public String getJsjgField(String jsjgId) {
		String deviceSql=Common.getConfigVal("jsjgFieldSql");
		if(deviceSql!=null){
			PreparedStatement sql = DB.createSql(deviceSql);
			if(sql!=null){
				try {
					sql.setInt(1,Integer.parseInt(jsjgId));
					List<Map<String, Object>> fields=new ArrayList<Map<String,Object>>();
					ResultSet rst = sql.executeQuery();
					while(rst.next()){
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("id", rst.getString("id"));
						map.put("name", rst.getString("name"));
						map.put("code", rst.getString("code"));
						fields.add(map);					
					}
					return "{\"Rows\":"+JSONArray.toJSONString(fields)+"}";
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return null;
	}
	
	@Override
	public String getCezhan() {
		String deviceSql=Common.getConfigVal("cezhanSql");
		if(deviceSql!=null){
			PreparedStatement sql = DB.createSql(deviceSql);
			if(sql!=null){
				try {
					List<Map<String, Object>> jars=new ArrayList<Map<String,Object>>();
					ResultSet rst = sql.executeQuery();
					while(rst.next()){
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("id", rst.getString("id"));
						map.put("text", rst.getString("text"));
						jars.add(map);
					}
					return JSONArray.toJSONString(jars);
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}
		return null;
	}

	@Override
	public String getGraphId(String devId, String dataSource, String parIds) {
		Map<String, Object> ret=new HashMap<String, Object>();
		String jarId=jarDao.getJarId(devId,dataSource);
		Map<String, String> map=new HashMap<String, String>();
		map.put("jarId", jarId);
		map.put("parIds", parIds);
		Map<String, String> map1=new HashMap<String, String>();
		List<Map<String, Object>> pids=jarDao.getParIds(map);
		for (Map<String, Object> pid : pids) {
			map1.put(pid.get("parId").toString(), pid.get("id").toString());
		}
		ret.put("jarId", jarId);
		ret.put("parIds", map1);
		return JSONObject.toJSONString(ret);
	}

	@Override
	public String getParIds(String jarId) {
		List<Map<String, Object>> pids=jarDao.getAllParIds(jarId);
		Map<String, String> map=new HashMap<String, String>();
		for (Map<String, Object> pid : pids) {
			map.put(pid.get("parId").toString(), pid.get("id").toString());
		}
		return JSONObject.toJSONString(map);
	}

}

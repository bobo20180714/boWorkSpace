package com.xpoplarsoft.baseInfo.tmparams.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.tmparams.bean.SatSubAloneParam;
import com.xpoplarsoft.baseInfo.tmparams.bean.TmParamView;
import com.xpoplarsoft.baseInfo.tmparams.dao.ITmParamsDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;


@Service
public class TmParamsService implements ITmParamsService{
	@Autowired
	private ITmParamsDao tmParamsDao;
	//日志
	private static Log log = LogFactory.getLog(TmParamsService.class);

	@Override
	public String findGrantUserGroupTmParam(DBParameter param) {
		DBResult result = tmParamsDao.findGrantUserGroupTmParam(param);
		String json = "";
		String total = tmParamsDao.getfindTmParamCount(param);
		int cou = Integer.parseInt(total);
		for (int i = 0; i < result.getRows(); i++) {
			json += ",{tm_param_id:" + result.get(i, "tm_param_id") + ",tm_param_name:'"
					+ result.get(i, "tm_param_name") + "',tm_param_code:'"
					+ result.get(i, "tm_param_code") + "',tm_param_type:'"
					+ result.get(i, "tm_param_type") +"',tm_param_num:" + result.get(i, "tm_param_num")
					+ "}";
		}
		if (json.length() > 0)
			json = json.substring(1);
		json= "[" + json + "]";
		return "{\"Rows\":"+json+",Total:"+cou+"}";
//		return "{records:[" + json + "]}";
		
	}

	
	@Override
	public String findGrantUserGroupTmParamById(DBParameter param){
		Map<String,Object> map = new HashMap<String, Object>();
		DBResult result = tmParamsDao.findGrantUserGroupTmParamById(param);
		String total = tmParamsDao.getSatParamInfoCount(param);
		int cou = Integer.parseInt(total);
		List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
		Map<String,String> rsMap = null;
		for (int i = 0; i < result.getRows(); i++) {
			rsMap = new HashMap<String, String>();
			rsMap.put("tm_param_id", result.get(i, "tm_param_id"));
			rsMap.put("tm_param_num", result.get(i, "tm_param_num"));
			rsMap.put("tm_param_name", result.get(i, "tm_param_name"));
			rsMap.put("tm_param_code", result.get(i, "tm_param_code"));
			rsMap.put("owner_name", result.get(i, "name"));
			rsMap.put("tm_param_type", result.get(i, "tm_param_type"));
			rsList.add(rsMap);
		}
		map.put("Rows", rsList);
		map.put("Total", cou);
		return new Gson().toJson(map);
		
	}
	
	@Override
	public String tmParamUpdate(DBParameter param){
		
		Boolean flag = tmParamsDao.tmParamUpdate(param);
		if(flag){
			return "{success:true,message:'修改成功'}";
		}else
			return "{success:false,message:'修改失败'}";
	}
	@Override
	public String getTmParamsInfoById(DBParameter param){
		DBResult result = tmParamsDao.getTmParamsInfoById(param);
		
		return JSONObject.toJSONString(DBResultUtil.objectToJson(result));
	}
	@Override
	public String tmParamDelete(String ids){
		List<DBParameter> paramList = new ArrayList<DBParameter>(); 
		if (ids != null) {
			String aa[] = ids.split(",");
			for(int i=0;i<aa.length;i++){
				DBParameter param = new DBParameter();
				param.setObject("tm_param_id", aa[i]);
				paramList.add(param);
			}
		}
		Boolean flag = tmParamsDao.tmParamDelete(paramList);
		if(flag){
			return "{success:true,message:'删除成功'}";
		}else{
			return "{success:false,message:'删除失败'}";
		}

	}
	@Override
	public String getSubNoParamCount(String page, String id, String key, String owner_id){
		DBParameter param = new DBParameter();
		param.setObject("page", page);
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("owner_id", owner_id);
	return 	tmParamsDao.getSubNoParamCount(param);
	}
	
	@Override
	public String getSubHasParamCount(String page, String id, String key){
		DBParameter param = new DBParameter();
		param.setObject("page", page);
		param.setObject("id", id);
		param.setObject("key", key);
	return 	tmParamsDao.getSubHasParamCount(param);
	}
	@Override
	public Map<String,Object> findSubNoParamBySubQueryPage(
			int page, String id, String key ,int pagesize, String owner_id) {
		Map<String,Object> rsMap = new HashMap<String, Object>();
		
		List<SatSubAloneParam> satSubAloneParam = new ArrayList<SatSubAloneParam>();
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("owner_id", owner_id);
		DBResult result = tmParamsDao.findSubNoParamBySubQueryPage(param,page,pagesize);
		 for(int i=0;result != null && i < result.getRows();i++){
			 SatSubAloneParam alone = new SatSubAloneParam();
			 alone.setTm_param_id(result.get(i, "tm_param_id"));
			 alone.setTm_param_code(result.get(i, "tm_param_code"));
			 alone.setTm_param_name(result.get(i, "tm_param_name"));
			 alone.setTm_param_num(result.get(i, "tm_param_num"));
			 satSubAloneParam.add(alone);
		 }
		 rsMap.put("Rows", satSubAloneParam);
		 rsMap.put("Total", result.getTotal());
		 return rsMap;
	}


	@Override
	public List<SatSubAloneParam> findStandHasParamByStandQueryPage(
			DBParameter param) {
		List<SatSubAloneParam> satSubAloneParam =new ArrayList<SatSubAloneParam>();
		DBResult result = tmParamsDao.findStandHasParamByStandQueryPage(param);
		for(int i=0;i<result.getRows();i++){
			SatSubAloneParam alone = new SatSubAloneParam();
			alone.setTm_param_code(result.get(i, "tm_param_code"));
			alone.setTm_param_name(result.get(i, "tm_param_name"));
			alone.setTm_param_type(result.get(i, "tm_param_type"));
			satSubAloneParam.add(alone);
		}
		return satSubAloneParam;
	}


	@Override
	public String getStandHasParamCount(String page, String id, String key) {
		DBParameter param = new DBParameter();
		param.setObject("page", page);
		param.setObject("id", id);
		param.setObject("key", key);
	return 	tmParamsDao.getStandHasParamCount(param);
	}


	@Override
	public Boolean paramDrop(String id, String owner_id) {
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("owner_id", owner_id);
		return tmParamsDao.paramResourceUpdate(param);
	}


	@Override
	public Boolean paramDropToRightAll(String id, String key, String owner_id) {
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		//根据ownerid删除之前已分配参数
		List<DBParameter> paramList = new ArrayList<DBParameter>();
		DBParameter param = new DBParameter();
		param.setObject("owner_id", owner_id);
		paramList.add(param);
		map.put("paramDropToLeftAll", paramList);
		
		//根据id和key查询所有的,添加到SUB_ALONE_TM中
		List<DBParameter> paramList2 = new ArrayList<DBParameter>();
		DBParameter param2 = new DBParameter();
		param2.setObject("id", id);
		param2.setObject("key", key);
		param2.setObject("owner_id", owner_id);
		paramList2.add(param2);
		map.put("insertToAloneTm", paramList2);
		return tmParamsDao.paramDropToRightAll(map);
	}


	@Override
	public Boolean aloneParamDropToRightAll(String id, String key,
			String owner_id) {
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("owner_id", owner_id);	
		return tmParamsDao.aloneParamDropToRightAll(param);
	}


	@Override
	public Boolean paramDropToLeftAll(String id, String key, String owner_id) {
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("owner_id", owner_id);	
		return tmParamsDao.paramDropToLeftAll(param);
	}


	@Override
	public Boolean ParamDropToLeft(String ids, String key,
			String owner_id) {
		String aa[]=ids.split(",");
		List<DBParameter> paramList = new ArrayList<DBParameter>();
		for(int i=0;i<aa.length;i++){
			DBParameter param = new DBParameter();
			param.setObject("id", aa[i]);
			param.setObject("owner_id", owner_id);
			paramList.add(param);
		}
		return tmParamsDao.ParamDropToLeft(paramList);
	}


	@Override
	public List<SatSubAloneParam> findStandNoParamByStandQueryPage(String page,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SatSubAloneParam> findSubHasParamBySubQueryPage(String page,
			String id, String key,String pagesize) {
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("page", page);
		param.setObject("pagesize", pagesize);
		List<SatSubAloneParam> satSubAloneParam =new ArrayList<SatSubAloneParam>();
		DBResult result = tmParamsDao.findSubHasParamBySubQueryPage(param);
		for(int i=0;i<result.getRows();i++){
			SatSubAloneParam alone = new SatSubAloneParam();
			alone.setTm_param_id(result.get(i, "tm_param_id"));
			alone.setTm_param_code(result.get(i, "tm_param_code"));
			alone.setTm_param_name(result.get(i, "tm_param_name"));
			alone.setTm_param_num(result.get(i, "tm_param_num"));
			satSubAloneParam.add(alone);
		}
		return satSubAloneParam;
	}
	
	/**
	 * @Description: 获取遥测参数
	 * @author 孟祥超
	 * @date 2015.08.06
	 */
	@Override
	public Map<String,Object> queryParamsInfo(String sat_id,String param_code,CommonBean commonBean) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsService][queryParamsInfo]开始执行");
		}
		//返回结果
		List<Map<String, Object>> rsList = new ArrayList<Map<String,Object>>();
		//SQL参数
		DBParameter param = new DBParameter();
		param.setObject("sat_id", sat_id);
		if(param_code == null){
			param_code = "";
		}
		param.setObject("tm_param_code", param_code);
		param.setObject("tm_param_name", param_code);
		//执行查询
		DBResult result = tmParamsDao.queryParamsInfo(param,commonBean);
		
		Map<String,Object> rsMap = new HashMap<String, Object>();
		//处理结果
		if(result == null){
			rsMap.put("Total", 0);
			return rsMap;
		}
		rsMap.put("Total", result.getTotal());
		//获取结果放入LIST
		Map<String,Object> map = null;
		String[] colNames = result.getColName();
		for (int i=0;i<result.getRows();i++) {
			map = new HashMap<String, Object>();
			for (int j=0;j<colNames.length;j++) {
				String key = colNames[j];
				String value = result.getValue(i, colNames[j]);
				map.put(key.toLowerCase(), value);
			}
			rsList.add(map);
		}
		rsMap.put("Rows", rsList);
		log.debug("result:["+rsList+"]");
		return rsMap;
	}


	@Override
	public boolean add(TmParamView tmParamView) {
		return tmParamsDao.add(tmParamView);
	}


	@Override
	public Map<String, Object> queryTmByCode(String satId,String tmCode,String tmId) {
		DBResult dbr = tmParamsDao.queryTmByCode(satId,tmCode,tmId);
		return DBResultUtil.dbResultToMap(dbr);
	}


	@Override
	public Map<String, Object> queryTmByNum(String satId,String tmNum,String tmId) {
		DBResult dbr = tmParamsDao.queryTmByNum(satId,tmNum,tmId);
		return DBResultUtil.dbResultToMap(dbr);
	}


	@Override
	public int tmParamInput(List<TmParamView> tms) {
		int r=0;
		boolean flag = true;
		for(TmParamView tm : tms){
			boolean iscontinue = false;
			String sat_id = tm.getSat_id();
			Map<String, Object> dbResult = this.queryTmByCode(sat_id, tm.getTm_param_code(),null);
			if(dbResult != null && dbResult.size() > 0){
				log.error("卫星【"+sat_id+"】导入参数CODE【"+tm.getTm_param_code()+"】重复");
				iscontinue = true;
			}
			/*dbResult = this.queryTmByNum(sat_id, tm.getTm_param_num(),null);
			if(dbResult != null && dbResult.size() > 0){
				log.error("卫星【"+sat_id+"】导入参数NUM【"+tm.getTm_param_num()+"】重复");
				iscontinue = true;
			}*/
			if(iscontinue){
				r=2;
				continue;
			}
			flag &= this.add(tm);
		}
		if(r==0 && !flag){
			r=1;
		}
		return r;
	}

	
}

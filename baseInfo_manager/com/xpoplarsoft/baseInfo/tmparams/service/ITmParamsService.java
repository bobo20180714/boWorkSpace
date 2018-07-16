package com.xpoplarsoft.baseInfo.tmparams.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.baseInfo.tmparams.bean.SatSubAloneParam;
import com.xpoplarsoft.baseInfo.tmparams.bean.TmParamView;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;

public interface ITmParamsService {

	/**
	 * @Description: 获取遥测参数
	 * @author 孟祥超
	 * @date 2015.08.06
	 */
	public Map<String,Object> queryParamsInfo(String device_id,String param_code,CommonBean commonBean);
	
	public String findGrantUserGroupTmParam(DBParameter param);
	
//	public String findGrantUserGroupTmParamById(DBParameter param);

	public String findGrantUserGroupTmParamById(DBParameter param); 
	
	public String tmParamUpdate(DBParameter param); 
	
	public String getTmParamsInfoById(DBParameter param);
	
	public String tmParamDelete(String ids);
	
	public String getSubNoParamCount(String page, String id, String key, String owner_id);
	
	public Map<String,Object> findSubNoParamBySubQueryPage(int page, String id, String key, int pagesize, String owner_id);

	public List<SatSubAloneParam> findStandHasParamByStandQueryPage(
			DBParameter param);

	public String getStandHasParamCount(String page, String id, String key);

	public Boolean paramDrop(String id, String owner_id);

	public Boolean paramDropToRightAll(String id, String key, String owner_id);

	public Boolean aloneParamDropToRightAll(String id, String key,
			String owner_id);

	public Boolean paramDropToLeftAll(String id, String key, String owner_id);

	public Boolean ParamDropToLeft(String ids, String key,
			String owner_id);

	public List<SatSubAloneParam> findStandNoParamByStandQueryPage(String page,
			String id);

	public List<SatSubAloneParam> findSubHasParamBySubQueryPage(String page,
			String id, String key, String pagesize);

	public String getSubHasParamCount(String page, String id, String key);

	/**
	 * 新增参数
	 * @param tmParamView
	 * @return
	 */
	public boolean add(TmParamView tmParamView);

	/**
	 * 根据参数code查询参数
	 * @param tmCode
	 * @return
	 */
	public Map<String, Object> queryTmByCode(String satId,String tmCode,String tmId);

	/**
	 * 根据参数num查询参数
	 * @param tmCode
	 * @return
	 */
	public Map<String, Object> queryTmByNum(String satId,String tmNum,String tmId);

	public int tmParamInput(List<TmParamView> tms);
}

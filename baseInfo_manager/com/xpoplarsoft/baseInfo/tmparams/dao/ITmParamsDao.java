package com.xpoplarsoft.baseInfo.tmparams.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.xpoplarsoft.baseInfo.tmparams.bean.TmParamView;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

public interface ITmParamsDao{
	
	/**
	 * @Description: 获取遥测参数
	 * @author 孟祥超
	 * @date 2015.08.06
	 */
	DBResult queryParamsInfo(DBParameter param,CommonBean commonBean);
	
	public DBResult findGrantUserGroupTmParam(DBParameter param);
	public String getSatParamInfoCount(DBParameter param);
	
	public DBResult findGrantUserGroupTmParamById(DBParameter param);
	
	public Boolean tmParamUpdate(DBParameter param);
	
	public DBResult getTmParamsInfoById(DBParameter param);
	
	public Boolean tmParamDelete(List<DBParameter> paramList);
	
	public DBResult findSubNoParamBySubQueryPage(DBParameter param,int page,int pagesize);
	
	public String getSubNoParamCount(DBParameter param);
	
	public DBResult findStandHasParamByStandQueryPage(DBParameter param);
	
	public String getStandHasParamCount(DBParameter param);
	
	public Boolean paramResourceUpdate(DBParameter param);
	
	public Boolean paramDropToRightAll(LinkedHashMap<String, List<DBParameter>> map);
	
	public Boolean aloneParamDropToRightAll(DBParameter param);
	
	public Boolean paramDropToLeftAll(DBParameter param);
	
	public Boolean ParamDropToLeft(List<DBParameter> paramList);
	
	public DBResult findSubHasParamBySubQueryPage(
			DBParameter param);
	
	public String getfindTmParamCount(DBParameter param);
	
	public String getSubHasParamCount(DBParameter param);

	boolean add(TmParamView tmParamView);

	DBResult queryTmByCode(String satId,String tmCode,String tmId);

	DBResult queryTmByNum(String satId,String tmNum,String tmId);
	
}


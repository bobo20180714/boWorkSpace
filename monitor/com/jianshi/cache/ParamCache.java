package com.jianshi.cache;

import java.util.HashMap;
import java.util.Map;

import com.bydz.fltp.connector.param.Param;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 参数缓存
 * @author mengxiangchao
 *
 */
public class ParamCache {
	
	private static ParamCache cacheObj = null;
	
	private static Map<Integer,Param> paramCache = new HashMap<Integer,Param>();

	private String sql = "select * from tm where STATUS = 0";
	
	/**
	 * 获取实例
	 * @return
	 */
	public static ParamCache getInstance(){
		if(cacheObj == null){
			cacheObj = new ParamCache();
		}
		return cacheObj;
	}
	
	public void putToCache(Param p){
		paramCache.put(p.getId(), p);
	}

	/**
	 * 获取参数编号
	 * @param paramId
	 * @return
	 */
	public String getParamCode(int paramId){
		Param p = paramCache.get(paramId);
		if(p != null){
			return p.getCode();
		}
		return null;
	}
	
	
	public void getAllTmFromDB() {
		@SuppressWarnings("deprecation")
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		Param p = null;
		for (int i = 0; i < dbr.getRows(); i++) {
			p = new Param();
			p.setCode(dbr.get(i, "tm_param_code"));
			p.setId(Integer.parseInt(dbr.get(i, "tm_param_id")));
			p.setName(dbr.get(i, "tm_param_name"));
			putToCache(p);
		}
	}
}

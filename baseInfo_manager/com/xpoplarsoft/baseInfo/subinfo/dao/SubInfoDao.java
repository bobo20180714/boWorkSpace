package com.xpoplarsoft.baseInfo.subinfo.dao;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class SubInfoDao implements ISubInfoDao{

	
	@SuppressWarnings("deprecation")
	public int findSeqId(){
		String sql = "select seq_edq.NEXTVAL from dual";
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		int id = Integer.parseInt(dbr.getValue(0, 0));
		return id;
	}
	
	public Boolean resourceAdd(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("subinfomapper",
				"resourceAdd", param);
	}

	public Boolean subSystemInfoAdd(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("subinfomapper",
				"subSystemInfoAdd", param);
	}

	public DBResult subSystemInfoById(DBParameter param){
		return SQLFactory.getSqlComponent().queryInfo("subinfomapper",
				"subSystemInfoById", param);
	}

	public Boolean subSystemInfoUpdate(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("subinfomapper",
				"subSystemInfoUpdate", param);
	}

	public Boolean subSystemInfoDeleteById(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("subinfomapper",
				"subSystemInfoDeleteById", param);
	}

	@Override
	public DBResult queryChildInfo(String owner_id) {
		DBParameter param = new DBParameter();
		param.setObject("owner_id", owner_id);
		return SQLFactory.getSqlComponent().queryInfo("subinfomapper",
				"queryChildInfo", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgeCodeExit(String sat_id,String sub_system_code, String sub_system_id) {
		String sql = "select * from SUB_SYSTEM_INFO where SAT_ID = "+sat_id+" and STATUS = 0 and SUB_SYSTEM_CODE = '"+sub_system_code+"' ";
		if(sub_system_id != null && !"".equals(sub_system_id)){
			sql = sql + " and SUB_SYSTEM_ID != "+sub_system_id;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgeNameExit(String sat_id,String sub_system_name, String sub_system_id) {
		String sql = "select * from SUB_SYSTEM_INFO where SAT_ID = "+sat_id+" and STATUS = 0 and sub_system_name = '"+sub_system_name+"' ";
		if(sub_system_id != null && !"".equals(sub_system_id)){
			sql = sql + " and SUB_SYSTEM_ID != "+sub_system_id;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}
	


}
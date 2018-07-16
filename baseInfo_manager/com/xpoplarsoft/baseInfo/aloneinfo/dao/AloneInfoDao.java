package com.xpoplarsoft.baseInfo.aloneinfo.dao;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class AloneInfoDao implements IAloneInfoDao{

	public int findSeqId(){
		String sql ="select seq_edq.NEXTVAL from dual";
		DBResult result = SQLFactory.getSqlComponent().queryInfo(sql);
		int id = Integer.parseInt(result.get(0, 0));
		return id;
	}
	
//	@SuppressWarnings("rawtypes")
//	@Insert("insert into sys_resource(sys_resource_id,owner_id) values(#{sys_resource_id},#{owner_id})	")
//	public Boolean resourceAdd(Map map){
//		return SQLFactory.getSqlComponent().updateInfo(tableSpace, sqlId, jParameter);
//	}

	public Boolean subSystemInfoAdd(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("AloneInfoMapper", "subSystemInfoAdd", param);	
	}

	public DBResult standAloneInfoById(/*@Param(value="stand_alone_id")String stand_alone_id*/DBParameter param){
		return SQLFactory.getSqlComponent().queryInfo("AloneInfoMapper", "standAloneInfoById", param);
	}
	public Boolean standAloneInfoUpdate(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("AloneInfoMapper", "standAloneInfoUpdate", param);
	}

	public Boolean standAloneInfoDeleteById(DBParameter param){
		return SQLFactory.getSqlComponent().updateInfo("AloneInfoMapper", "standAloneInfoDeleteById", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgeCodeExit(String sub_system_id,
			String stand_alone_code, String stand_alone_id) {
		String sql = "select * from STAND_ALONE_INFO where SUB_SYSTEM_ID = "+sub_system_id+" and STATUS = 0 and STAND_ALONE_CODE = '"+stand_alone_code+"' ";
		if(stand_alone_id != null && !"".equals(stand_alone_id)){
			sql = sql + " and STAND_ALONE_ID != "+stand_alone_id;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgeNameExit(String sub_system_id,
			String stand_alone_name, String stand_alone_id) {
		String sql = "select * from STAND_ALONE_INFO where SUB_SYSTEM_ID = "+sub_system_id+" and STATUS = 0 and STAND_ALONE_NAME = '"+stand_alone_name+"' ";
		if(stand_alone_id != null && !"".equals(stand_alone_id)){
			sql = sql + " and STAND_ALONE_ID != "+stand_alone_id;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}
}
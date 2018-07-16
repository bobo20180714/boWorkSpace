package com.xpoplarsoft.baseInfo.aloneinfo.dao;



import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;


public interface IAloneInfoDao{

	public int findSeqId();
	
//	@Insert("insert into sys_resource(sys_resource_id,owner_id) values(#{sys_resource_id},#{owner_id})	")
//	public int resourceAdd(Map map);

	public Boolean subSystemInfoAdd(DBParameter param);

	public DBResult standAloneInfoById(DBParameter param);

	public Boolean standAloneInfoUpdate(DBParameter param);

	public Boolean standAloneInfoDeleteById(DBParameter param);

	public DBResult judgeCodeExit(String sub_system_id,
			String stand_alone_code, String stand_alone_id);

	public DBResult judgeNameExit(String sub_system_id,
			String stand_alone_name, String stand_alone_id);
}
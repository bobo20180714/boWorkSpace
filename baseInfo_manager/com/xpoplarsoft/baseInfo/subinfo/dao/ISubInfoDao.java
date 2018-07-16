package com.xpoplarsoft.baseInfo.subinfo.dao;


import org.apache.ibatis.annotations.Select;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;


public interface ISubInfoDao{

	@Select("select seq_edq.NEXTVAL from dual")
	public int findSeqId();
	
//	@SuppressWarnings("rawtypes")
//	@Insert("insert into sys_resource(sys_resource_id,owner_id) values(#{sys_resource_id},#{owner_id})	")
	public Boolean resourceAdd(DBParameter param);

	public Boolean subSystemInfoAdd(DBParameter param);

	public DBResult subSystemInfoById(DBParameter param);

	public Boolean subSystemInfoUpdate(DBParameter param);

	public Boolean subSystemInfoDeleteById(DBParameter param);

	/**
	 * 根据owner_id查询子信息
	 * @author 孟祥超
	 * @param owner_id
	 * @return
	 */
	public DBResult queryChildInfo(String owner_id);

	public DBResult judgeCodeExit(String sat_id,String sub_system_code, String sub_system_id);

	public DBResult judgeNameExit(String sat_id,String sub_system_name, String sub_system_id);
	

}
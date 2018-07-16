package com.xpoplarsoft.baseInfo.satInfo.dao;

import java.util.List;

import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

public interface ISatInfoDao{
	
	public DBResult getSatBasicInfoById(DBParameter param);

	public DBResult findSatBasicInfoQueryPage(DBParameter param);

	public DBResult findSeqId();
	
	
	public Boolean satBasicInfoUpdate(DBParameter param);
	
	public Boolean satBasicInfoDelete(List<DBParameter> paramList);
	
	public String getSatBasicInfoCount(DBParameter param);

	/**
	 * 查询航天器列表
	 * @param commBean
	 * @param dbParameter
	 * @return
	 */
	public DBResult satList(CommonBean commBean, DBParameter dbParameter);

	public boolean add(SatInfoDetail satInfoDetail);

	public DBResult querySatByCode(String satCode);

	public DBResult querySatByMid(String mid);

	/**
	 * 根据权限查询卫星列表数据
	 * @param userId
	 * @param sat_name
	 * @param design_org
	 * @param user_org
	 * @param pageSize
	 * @param pageIndex
	 * @param launch_time_start
	 * @param launch_time_end
	 * @return
	 */
	public DBResult findSatBasicInfoQueryPageLimit(String userId,String sat_name,
			String design_org, String user_org, String pageSize,
			String pageIndex, String launch_time_start, String launch_time_end);
	
}
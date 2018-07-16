package com.xpoplarsoft.baseInfo.satInfo.service;

import java.util.Map;

import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.framework.common.bean.CommonBean;

public interface ISatInfoService {

	public String findSatBasicInfoQueryPage(String sat_name,String design_org,String user_org,String design_life, String pageSize, String pageIndex, String over_life, String multicast_address, String udp_port, String launch_time, String sat_id, String sat_id2);

	public String satBasicInfoUpdate(SatInfoDetail satInfoDetail);

	public boolean satBasicInfoDelete(String ids);
	
	public SatInfoDetail getSatBasicInfoById(String ids);
	
	/**
	 * 查询航天器列表
	 * @param ownerId
	 * @param commBean
	 * @return
	 */
	public String satList(String ownerId,String key,CommonBean commBean);

	/**
	 * 新增卫星
	 * @param satInfoDetail
	 * @return
	 */
	public boolean add(SatInfoDetail satInfoDetail);

	/**
	 * 根据卫星编号查询卫星信息
	 * @param satCode
	 * @return
	 */
	public Map<String, Object> querySatByCode(String satCode);

	/**
	 * 根据卫星mid查询卫星信息
	 * @param mid
	 * @return
	 */
	public Map<String, Object> querySatByMid(String mid);

	/**
	 * 根据权限分页查询卫星数据
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
	public String findSatBasicInfoQueryPageLimit(String userId,String sat_name,
			String design_org, String user_org, String pageSize,
			String pageIndex, String launch_time_start, String launch_time_end);
}

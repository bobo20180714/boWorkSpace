package com.xpoplarsoft.baseInfo.subinfo.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.baseInfo.subinfo.bean.SubSystemInfo;

public interface ISubInfoService {

	public String subSystemInfoAdd(SubSystemInfo subSystemInfo, String sat_id);

	public SubSystemInfo subSystemInfoById(String sub_system_id);

	public String subSystemInfoUpdate(SubSystemInfo subSystemInfo);

	public Boolean subSystemInfoDeleteById(String sub_system_id);

	/**
	 * 查询子信息
	 * @author 孟祥超
	 * @param owner_id
	 * @return
	 */
	public List<Map<String, Object>> queryChildInfo(String owner_id);

	public boolean judgeCodeExit(String sat_id,String sub_system_code, String sub_system_id);

	public boolean judgeNameExit(String sat_id,String sub_system_name, String sub_system_id);
	
}

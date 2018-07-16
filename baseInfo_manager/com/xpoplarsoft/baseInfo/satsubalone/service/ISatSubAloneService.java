package com.xpoplarsoft.baseInfo.satsubalone.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;

public interface ISatSubAloneService {
	/**
	 * @Description: 获取航天器树
	 * @author 孟祥超
	 * @param key 关键字
	 * @throws
	 */
	public List<Map<String, String>> findSatTree(String key);

	public List<SatSubAlone> findGrantUserGroupEquipmentTree(
			String sys_resource_id, String key);
	
}

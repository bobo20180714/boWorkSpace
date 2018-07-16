package com.xpoplarsoft.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseinfoquery.bean.SatInfoDetail;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.monitor.dao.ISatDao;
import com.xpoplarsoft.monitor.service.ISatService;

/**
 * 卫星信息查询 服务层
 * @author mengxiangchao
 *
 */
@Service
public class SatService implements ISatService {


	@Autowired
	private ISatDao satDao;
	
	@Override
	public SatInfoDetail getSatInfoByMid(String satMid) {
		DBResult dbr = satDao.getSatInfoByMid(satMid);
		SatInfoDetail satInfo = new SatInfoDetail();
		satInfo.setSat_code(dbr.get(0, "sat_code"));
		satInfo.setMid(Integer.parseInt(satMid));
		satInfo.setSat_name(dbr.get(0, "sat_name"));
		return satInfo;
	}
}

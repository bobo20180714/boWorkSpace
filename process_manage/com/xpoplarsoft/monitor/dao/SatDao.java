package com.xpoplarsoft.monitor.dao;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Component
public class SatDao implements ISatDao {

	@Override
	public DBResult getSatInfoByMid(String satMid) {
		DBParameter param = new DBParameter();
		param.setObject("satMid", satMid);
		return SQLFactory.getSqlComponent().queryInfo("satInfo", "getSatInfoByMid", param);
	}

}

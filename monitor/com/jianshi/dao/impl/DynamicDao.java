package com.jianshi.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jianshi.dao.IDynamicDao;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class DynamicDao implements IDynamicDao {

	@Override
	public List<Map<String, Object>> getDynamic() {
		DBResult result = SQLFactory.getSqlComponent().queryInfo("dynamic","getDynamic", null);
		return Common.getMaps(result);
	}

}

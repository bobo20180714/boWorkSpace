package com.xpoplarsoft.alarm.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
@Qualifier("globalConfigDao")
public class GlobalConfigDao implements IGlobalConfigDao {

	@Override
	public Map<String, String> findGlobalConfig(String key) {
		Map<String, String> rs = new HashMap<String, String>();
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("key", key);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"findGlobalConfig", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				rs.put(dbs.getValue(i, "configitem"),
						dbs.getValue(i, "content"));
			}
		}
		return rs;
	}

	@Override
	public String getGlobalConfig(String configItem) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("configItem", configItem);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"getGlobalConfig", jParameter);
		if (dbs != null && dbs.getRows() > 0) {

			return dbs.getValue(0, "content");

		}
		return "";
	}

	@Override
	public boolean insertGlobalConfig(String configItem, String content) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("configItem", configItem);
		jParameter.setObject("content", content);
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"addGlobalConfig", jParameter);
	}

	@Override
	public boolean updateGlobalConfig(String configItem, String content) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("configItem", configItem);
		jParameter.setObject("content", content);
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"updateGlobalConfig", jParameter);
	}

}

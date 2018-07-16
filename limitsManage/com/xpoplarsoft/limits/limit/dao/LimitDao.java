package com.xpoplarsoft.limits.limit.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.limits.limit.bean.LimitBean;

@Component
public class LimitDao implements ILimitDao {

	@Override
	public boolean grantRoleResourceAdd(List<LimitBean> beanList) {
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = null;
		for (LimitBean bean : beanList) {
			 dbp = new DBParameter();
			 dbp.setObject("sys_resource_id", bean.getSys_resource_id());
			 dbp.setObject("ug_id", bean.getUg_id());
			 dbp.setObject("grant_manage_type", bean.getGrant_manage_type());
			 dbp.setObject("grant_type", bean.getGrant_type());
			 dbp.setObject("create_user", bean.getCreate_user());
			 dbp.setObject("end_time", bean.getEnd_time());
			 dbpList.add(dbp);
		}
		return SQLFactory.getSqlComponent().batchUpdate("grantData", "grantRoleResourceAdd", dbpList);
	}

	@Override
	public boolean resourceAuthorizationDelete(List<LimitBean> beanList) {
		List<DBParameter> dbpList = new ArrayList<DBParameter>();
		DBParameter dbp = null;
		for (LimitBean bean : beanList) {
			 dbp = new DBParameter();
			 dbp.setObject("sys_resource_id", bean.getSys_resource_id());
			 dbp.setObject("ug_id", bean.getUg_id());
			 dbp.setObject("grant_type", bean.getGrant_type());
			 dbpList.add(dbp);
		}
		return SQLFactory.getSqlComponent().batchUpdate("grantData", "grantRoleResourceDelete", dbpList);
	}

	@Override
	public DBResult satList(CommonBean bean,
			String ug_id) {
		DBParameter dbp = new DBParameter();
		dbp.setObject("ug_id", ug_id);
		return SQLFactory.getSqlComponent().pagingQueryInfo("grantData", "satList", dbp,bean.getPage(),bean.getPagesize());
	}

	@Override
	public DBResult queryAlreadyGrantSat(String ug_id, int grant_type) {
		DBParameter dbp = new DBParameter();
		 dbp.setObject("ug_id", ug_id);
		 dbp.setObject("grant_type", grant_type);
		return SQLFactory.getSqlComponent().queryInfo("grantData", "queryAlreadyGrantSat",  dbp);
	}

}

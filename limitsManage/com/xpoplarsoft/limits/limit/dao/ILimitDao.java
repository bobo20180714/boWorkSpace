package com.xpoplarsoft.limits.limit.dao;

import java.util.List;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.limit.bean.LimitBean;

public interface ILimitDao {

	boolean grantRoleResourceAdd(List<LimitBean> beanList);
	
	boolean resourceAuthorizationDelete(List<LimitBean> beanList);

	DBResult satList(CommonBean bean,
			String ug_id);

	DBResult queryAlreadyGrantSat(String ug_id, int grant_type);

}

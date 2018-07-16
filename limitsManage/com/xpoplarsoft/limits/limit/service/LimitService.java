package com.xpoplarsoft.limits.limit.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.limit.bean.LimitBean;
import com.xpoplarsoft.limits.limit.dao.ILimitDao;

@Service
public class LimitService implements ILimitService {

	@Autowired
	public ILimitDao dao;
	
	@Override
	public boolean resourceAuthorizationAdd(List<LimitBean> beanList) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		LimitBean limitBean = null;
		for (int i = 0; i < beanList.size(); i++) {
			limitBean = beanList.get(i);
			String end_time = limitBean.getEnd_time();
			if (end_time == null || end_time.length() == 0) {
				end_time = df.format(DateUtils.addYears(new Date(), 1));
				limitBean.setEnd_time(end_time);
			}
		}
		return dao.grantRoleResourceAdd(beanList);
	}

	@Override
	public boolean resourceAuthorizationDelete(List<LimitBean> beanList) {
		return dao.resourceAuthorizationDelete(beanList);
	}

	@Override
	public Map<String, Object> satList(CommonBean bean,
			String ug_id) {
		DBResult dbr = dao.satList(bean,ug_id);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public List<Map<String, Object>> queryAlreadyGrantSat(String ug_id,
			int grant_type) {
		DBResult dbr = dao.queryAlreadyGrantSat(ug_id,grant_type);
		return DBResultUtil.resultToList(dbr);
	}

}

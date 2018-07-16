package com.xpoplarsoft.limits.log.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.log.dao.ILogDao;

/**
 * 类功能: 日志管理Service类
 * 
 * @author admin
 * @date 2014-04-11
 */
@Service
@Qualifier("LogService")
public class LogService implements ILogService {

	@Autowired
	@Qualifier("LogDao")
	public ILogDao dao;

	@Override
	public Map<String, Object> listInfo(CommonBean bean,String user_name,
			String begin_time,String end_time) {
		//查询状态参数列表
		DBResult dbResult = dao.listInfo(bean, user_name, begin_time, end_time);
		Map<String, Object> rsMap = DBResultUtil.dbResultToPageData(dbResult);
		return rsMap;
	}


}

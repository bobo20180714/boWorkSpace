package com.xpoplarsoft.limits.log.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 类功能: 日志管理dao类
 * 
 * @author admin
 * @date 2014-04-11
 */
@Repository
@Qualifier("LogDao")
public class LogDao implements ILogDao {

	@Override
	public DBResult listInfo(CommonBean bean,String user_name,
			String begin_time,String end_time) {
		 DBParameter param = new DBParameter();
		 param.setObject("user_name", user_name);
		 param.setObject("begin_time", begin_time);
		 param.setObject("end_time", end_time);
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				"oprateLog", "query_log_info", param,
				bean.getPage(), bean.getPagesize());
		return result;
	}
	
}

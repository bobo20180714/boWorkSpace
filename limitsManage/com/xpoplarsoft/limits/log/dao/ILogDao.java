package com.xpoplarsoft.limits.log.dao;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;


/**
 * 类功能: 日志管理dao接口
 * 
 * @author admin
 * @date 2014-04-11
 */
public interface ILogDao {
	
	/**
	 * 查询多条数据
	 * 
	 * @param bean
	 * @param user_name
	 * @param begin_time
	 * @param end_time(开始时间的时间区间，和表里的end_time无关)
	 * @return
	 */
	public DBResult listInfo(CommonBean bean,String user_name,
			String begin_time,String end_time);
}

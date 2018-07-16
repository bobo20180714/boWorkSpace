package com.xpoplarsoft.limits.limit.dao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 类功能: 权限管理dao类
 * 
 * @author admin
 * @date 2014-08-05
 */
@Repository
@Qualifier("LimitManagerDao")
public class LimitManagerDao implements ILimitManagerDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(LimitManagerDao.class);

	/**
	 * 查询多条数据
	 * 
	 * @param bean
	 * @return DBResult
	 */
	public DBResult limits(Map bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerDao][limits]开始执行");
		}

		DBParameter para = new DBParameter();
		for (Object col : bean.keySet()) {
			para.setObject(col.toString(), bean.get(col).toString());
		}

		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				"resourcesInfo", "query_page_limit_res", para);

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerDao][limits]执行结束");
		}

		return result;
	}
}
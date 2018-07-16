package com.xpoplarsoft.limits.limit.dao;

import java.util.Map;

import com.xpoplarsoft.framework.db.DBResult;

/**
 * 类功能: 权限管理dao接口
 * 
 * @author admin
 * @date 2014-08-05
 */
public interface ILimitManagerDao {

	/**
	 * 查询多条数据
	 * 
	 * @param bean
	 * @return
	 */
	public DBResult limits(Map bean);

}

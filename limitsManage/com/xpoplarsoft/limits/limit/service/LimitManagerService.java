package com.xpoplarsoft.limits.limit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.limit.dao.ILimitManagerDao;

/**
 * 类功能: 权限管理Service类
 * 
 * @author admin
 * @date 2014-08-05
 */
@Service
@Qualifier("LimitManagerService")
public class LimitManagerService implements ILimitManagerService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(LimitManagerService.class);

	@Autowired
	@Qualifier("LimitManagerDao")
	public ILimitManagerDao dao;

	/**
	 * 查询多条数据
	 * 
	 * @param bean
	 * @return String
	 */
	public String limits(Map bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerService][listInfo]开始执行");
		}

		Gson json = new Gson();
		DBResult result = dao.limits(bean);

		// 返回json
		String retJson = null;
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		Map<String, String> cellMap = null;

		List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();
		if (result == null || result.getRows() <= 0) {
			pageInfo.put("Total", 0);
		} else {
			// 总条数
			int total = result.getTotal();
			pageInfo.put("Total", total);
			int rows = result.getRows();
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String, String>();
				for (int j = 0; j < result.getCols(); j++) {
					// gwb 2014-9-3 把字段名转为小写
					String columnName = result.getColName()[j].toLowerCase();
					String columnValue = result.getValue(i, columnName);
					cellMap.put(columnName, columnValue);
				}
				infoList.add(cellMap);
			}
			pageInfo.put("Rows", infoList);
		}
		retJson = json.toJson(pageInfo);

		if (log.isDebugEnabled()) {
			log.debug("返回json数据为[" + retJson + "]");
		}

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerService][alterInfo]执行结束");
		}

		return retJson;
	}

}

package com.xpoplarsoft.compute.db;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库连接参数配置类
 * 
 * @author chenjie
 * @date 2013-10-4
 */
public class DBParameter {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(DBParameter.class);

	/**
	 * sql参数
	 */
	private Map<String, Object> paraMap = new LinkedHashMap<String, Object>();

	/**
	 * 获得sql参数
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, Object> getParaMap() {

		return this.paraMap;

	}

	/**
	 * 设置字符串类型参数
	 * 
	 * @param String
	 * @param Object
	 * @return
	 */
	public void setObject(String key, Object value) {
		this.paraMap.put(key, value);
	}

}

package com.xpoplarsoft.compute.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库连接类
 * 
 * @author chenjie
 * @date 2013-10-4
 */
public class DBConnect {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(DBConnect.class);

	/**
	 * 单实例
	 */
	private static DBConnect connect;

	/**
	 * 获取DBConnect对象
	 * 
	 * @return DBConnect
	 */
	public synchronized static DBConnect getDBConnect() {
		if (null == connect) {
			connect = new DBConnect();
		}
		return connect;
	}

	/**
	 * 关闭数据库连接
	 * 
	 */
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("关闭数据库连接异常", e);
			}
		}

	}

	/**
	 * 获得连接
	 * 
	 * @param dbSource
	 * @return Connection
	 */
	public Connection getConnect(String dbSource) {
		Connection conn = null;

		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			conn = DriverManager.getConnection("proxool." + dbSource);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("获取数据库连接异常", e);
			}
		}
		return conn;
	}

}

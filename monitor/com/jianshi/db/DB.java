package com.jianshi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jianshi.util.Common;

/**
 * 数据库操作类
 * @author Administrator
 * 2017.2.6
 */
public class DB {
	private static String driver=null;
	private static String url=null;
	private static String username=null;
	private static String password=null;
	
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	
	static{
		driver=Common.getConfigVal("driver");
		url=Common.getConfigVal("url");
		username=Common.getConfigVal("username");
		password=Common.getConfigVal("password");
	}
	
	public DB() {
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建SQl查询语句
	 * @param sql
	 * @return
	 */
	public static PreparedStatement createSql(String sql) {
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, username, password);
			pst=conn.prepareStatement(sql);
			return pst;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询数据
	 * @param sql
	 * @return:记录集合
	 */
	public ResultSet query(String sql){
		try {
			pst = conn.prepareStatement(sql);
			return pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新数据
	 * @param sql
	 * @return
	 */
	public int update(String sql){
		try {
			pst = conn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 关闭连接
	 */
	public void close() {
		destroy();
	}
	
	/**
	 * 关闭连接
	 */
	public static void destroy() {
		try {
			conn.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

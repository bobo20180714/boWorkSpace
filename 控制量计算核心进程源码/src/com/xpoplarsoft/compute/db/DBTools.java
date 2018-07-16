package com.xpoplarsoft.compute.db;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBTools {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(DBTools.class);

	/**
	 * 查询数据库信息
	 * 
	 * @param dbSource
	 *            数据源名称
	 * @param sql
	 *            查询语句
	 * @param DBParameter
	 *            参数
	 * @return
	 */
	public static boolean update(String dbSource, String sql, DBParameter para) {

		if (log.isDebugEnabled()) {
			log.debug("开始更新信息,dbSource=[" + dbSource + "]sql=[" + sql + "]");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = DBConnect.getDBConnect().getConnect(dbSource);
			pstmt = conn.prepareStatement(sql);

			setPreparedStatement(pstmt, para);

			int flag = pstmt.executeUpdate();
			
			if (flag < 0) {
				result = false;
			}

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("update方法执行发生异常："+sql, e);
			}
		} finally {
			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	/**
	 * 查询数据库信息
	 * 
	 * @param dbSource
	 *            数据源名称
	 * @param sql
	 *            查询语句
	 * @param List
	 *            <DBParameter> 参数
	 * @return
	 */
	public static boolean batchUpdate(String dbSource, String sql,
			List<DBParameter> list) {

		if (log.isDebugEnabled()) {
			log.debug("开始批量更新信息,dbSource=[" + dbSource + "]sql=[" + sql + "]");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			conn = DBConnect.getDBConnect().getConnect(dbSource);
			conn.setAutoCommit(false);
			for (int i = 0; i < list.size(); i++) {
				DBParameter para = list.get(i);
				pstmt = conn.prepareStatement(sql);
				setPreparedStatement(pstmt, para);
				int flag = pstmt.executeUpdate();

				if (flag < 0) {
					result = false;
					break;
				}
			}

		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("batchUpdate方法执行发生异常", e);
			}
			result = false;
		} finally {
			try {
				if (result) {
					if (null != conn) {
						conn.commit();
					}
				} else {
					if (null != conn) {
						conn.rollback();
					}
				}
			} catch (SQLException e) {
			}

			try {
				if (null != conn) {
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
			}

			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	/**
	 * 查询数据库信息
	 * 
	 * @param dbSource
	 *            数据源名称
	 * @param sql
	 *            查询语句
	 * @param DBParameter
	 *            参数
	 * @return
	 */
	public static DBResult queryInfo(String dbSource, String sql,
			DBParameter para) {

		if (log.isDebugEnabled()) {
			log.debug("开始查询信息,dbSource=[" + dbSource + "]sql=[" + sql + "]");
		}

		DBResult jResult = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnect.getDBConnect().getConnect(dbSource);
			pstmt = conn.prepareStatement(sql);

			setPreparedStatement(pstmt, para);

			resultSet = pstmt.executeQuery();

			jResult = resultSetToDBResult(resultSet, -1, -1);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("queryInfo方法执行发生异常", e);
			}
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (null != pstmt) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return jResult;
	}

	/**
	 * 设置PreparedStatement参数
	 * 
	 * @param pstmt
	 * @param para
	 * @throws SQLException
	 */
	public static void setPreparedStatement(PreparedStatement pstmt,
			DBParameter para) throws SQLException {
		int count = 1;
		Object value;
		for (Map.Entry<String, Object> entry : para.getParaMap().entrySet()) {
//			if (log.isDebugEnabled()) {
//				log.debug("setPreparedStatement key=[" + entry.getKey() + "]");
//			}
			value = entry.getValue();
			if (null == value) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is NULL");
//				}
				pstmt.setNull(count, java.sql.Types.NULL);
			} else if (value instanceof String) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is String");
//				}
				pstmt.setString(count, (String) value);
			} else if (value instanceof Integer) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is Integer");
//				}
				pstmt.setInt(count, (Integer) value);
			} else if (value instanceof Long) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is Long");
//				}
				pstmt.setLong(count, (Long) value);
			} else if (value instanceof BigDecimal) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is BigDecimal");
				}
				pstmt.setBigDecimal(count, (BigDecimal) value);
			} else if (value instanceof Double) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is Double");
//				}
				pstmt.setDouble(count, (Double) value);
			} else if (value instanceof Float) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is Float");
//				}
				pstmt.setFloat(count, (Float) value);
			} else if (value instanceof byte[]) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is byte[]");
				}
				pstmt.setBytes(count, (byte[]) value);
			} else if (value instanceof Blob) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is Blob");
				}
				pstmt.setBlob(count, (Blob) value);
			} else if (value instanceof Date) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is Date");
				}
				pstmt.setDate(count, (Date) value);
			} else if (value instanceof Time) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is Time");
				}
				pstmt.setTime(count, (Time) value);
			} else if (value instanceof Timestamp) {
				if (log.isDebugEnabled()) {
					log.debug("value instanceof is Timestamp");
				}
				pstmt.setTimestamp(count, (Timestamp) value);
			} else if (value instanceof Boolean) {
//				if (log.isDebugEnabled()) {
//					log.debug("value instanceof is Boolean");
//				}
				pstmt.setBoolean(count, (Boolean) value);
			}
			count++;
		}
	}

	/**
	 * ResultSet 转换成 DBResult
	 * 
	 * @param rset
	 * @param iFrom
	 * @param iTo
	 * @return
	 */
	public static DBResult resultSetToDBResult(ResultSet rset, int iFrom,
			int iTo) {
		try {
			// 列数
			int iCols = 0;
			// 行数
			int iRows = 0;

			Vector<Object> vData = new Vector<Object>();
			ResultSetMetaData rsetMD = null;
			rsetMD = rset.getMetaData();
			// 列数
			iCols = rsetMD.getColumnCount();

			String sColName[] = null;
			if (iCols > 0) {
				sColName = new String[iCols];
				for (int i = 0; i < iCols; i++)
					// sColName[i] = rsetMD.getColumnName(i + 1);
					sColName[i] = rsetMD.getColumnLabel(i + 1);// mysql取列表（别名）
			}
			// 查询总条数
			int iTotal = 0;
			if (iCols > 0) {
				while (rset.next()) {

					if (iFrom <= iTotal && (iTo < 0 || iTotal <= iTo)) {
						iRows++;
						for (int i = 1; i <= iCols; i++) {
							switch (rsetMD.getColumnType(i)) {
							case Types.TIMESTAMP:
								vData.addElement(rset.getTimestamp(i));
								break;
							case Types.BLOB:
								vData.addElement(rset.getObject(i));
								break;
							// 处理以0开始的小数不返回0的问题start by rt 2014-5-8
							case Types.NUMERIC:
								if (rsetMD.getPrecision(i) > 0) {
									if (rsetMD.getScale(i) > 0) {
										String v = rset.getString(i);
										if (v != null && v.startsWith(".")) {
											v = "0" + v;
										}
										vData.addElement(v);
									} else {
										vData.addElement(rset.getString(i));
									}
								} else {
									vData.addElement(rset.getString(i));
								}
								break;
							// 处理以0开始的小数不返回0的问题end by rt 2014-5-8
							default:
								vData.addElement(rset.getString(i));
								break;
							}
						}
					}

					iTotal++;
				}
			}
			DBResult ret = new DBResult(vData, iRows, iCols);
			ret.setColName(sColName);
			ret.setTotal(iTotal);
			DBResult jresult = ret;
			return jresult;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("ResultSet转换成DBResult发生异常异常", e);
			}
		}
		return null;
	}
}

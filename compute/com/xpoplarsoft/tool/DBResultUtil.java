package com.xpoplarsoft.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import com.xpoplarsoft.framework.db.DBResult;

/**
 * 将DBResult转换为json字符串的工具类
 * 
 * @author admin
 * @date 2015-03-05
 */
public class DBResultUtil {

	/**
	 * 将dbResult结果集转换为Json字符串
	 * 
	 * @param rst
	 *            dbResult结果集
	 * @return
	 */
	public static Map<String, Object> dbResultToJson(DBResult rst) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (rst != null) {
			String[] colName = rst.getColName();
			Map<String, String> beanMap = null;
			for (int i = 0; i < rst.getTotal(); i++) {
				beanMap = new HashMap<String, String>();
				for (String col : colName) {
					beanMap.put(col, rst.get(i, col));
				}
				list.add(beanMap);
			}
		}
		map.put("Rows", list);
		return map;
	}

	/**
	 * 将单条数据转换为Json字符串
	 * 
	 * @param rst
	 *            dbResult结果集
	 * @return
	 */
	public static Map<String, String> objectToJson(DBResult rst) {
		Map<String, String> beanMap = new HashMap<String, String>();
		if (rst != null) {
			String[] colName = rst.getColName();
			for (int i = 0; i < rst.getTotal(); i++) {
				for (String col : colName) {
					beanMap.put(col.toLowerCase(), rst.get(i, col));
				}
			}
			return beanMap;
		}
		return null;
	}

	/**
	 * 将dbResult结果集转换为ligerUI树形菜单使用的Json字符串
	 * 
	 * @param rst
	 *            dbResult结果集
	 * @return
	 */
	public static List<Map<String, String>> dbResultToTreeJson(DBResult rst) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = null;
		if (rst != null) {
			String[] colName = rst.getColName();
			for (int i = 0; i < rst.getTotal(); i++) {
				beanMap = new HashMap<String, String>();
				for (String col : colName) {
					beanMap.put(col, rst.get(i, col));
				}
				list.add(beanMap);
			}
		}
		return list;
	}
	
	/**
	 * 查询结果数据转为列表分页数据
	 * @author 孟祥超
	 * @param dbr
	 * @return
	 */
	public static Map<String,Object> dbResultToPageData(DBResult dbResult) {
		Map<String,Object> pageData = new HashMap<String, Object>();

		//列表数据
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if (dbResult == null || dbResult.getRows() <= 0) {
			pageData.put("Total",0);
		} else {
			//获取总条数
			int rows = dbResult.getRows();
			pageData.put("Total",dbResult.getTotal());
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,Object> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,Object>();
				for (int j = 0; j < columnName.length; j++) {
					Object obj = dbResult.getObject(i, columnName[j]);
					if(obj == null){
						cellMap.put(columnName[j].toLowerCase(), "");
					}else{
						cellMap.put(columnName[j].toLowerCase(), obj);
					}
				}
				infoList.add(cellMap);
			}
		}
		pageData.put("Rows",infoList);
		
		return pageData;
	}
	
	/**
	 * 查询结果数据转化为map(单条数据)
	 * @author 孟祥超
	 * @param rst
	 * @return
	 */
	public static Map<String, Object> dbResultToMap(DBResult rst) {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		if (rst != null && rst.getRows() > 0) {
			String[] colName = rst.getColName();
			for (String col : colName) {
				beanMap.put(col.toLowerCase(), rst.getObject(0, col));
			}
			return beanMap;
		}
		return beanMap;
	}

	/**
	 * 查询结果数据转化为list
	 * @author 孟祥超
	 * @param rst
	 * @return
	 */
	public static List<Map<String, Object>> resultToList(DBResult dbResult) {
		//列表数据
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,Object> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,Object>();
				for (int j = 0; j < columnName.length; j++) {
					Object obj = dbResult.getObject(i, columnName[j]);
					if(obj == null){
						cellMap.put(columnName[j].toLowerCase(), "");
					}else{
						cellMap.put(columnName[j].toLowerCase(), obj);
					}
				}
				infoList.add(cellMap);
			}
		}
		return infoList;
	}
	
	
	/**
	 * oracle.sql.Clob类型转换成String类型
	 * @param clob
	 * @return
	 */
    public static String ClobToString(CLOB clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 得到流
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            try {
                s = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        reString = sb.toString();
        return reString;
    }
}

package com.xpoplarsoft.compute.db;

import java.util.Vector;

/**
 * 类功能: 数据库执行语句结果类
 * 
 * @author chen.jie
 * @date 2014-11-26
 */
public class DBResult implements IResultSet {

	/**
	 * 序列号版本
	 */
	private static final long serialVersionUID = 3977149680796245074L;

	protected Vector cData;

	protected int iRows;

	protected int iCols;

	protected String sColName[];

	protected int iTotal;

	public void setColName(String sName[]) {
		sColName = sName;
		if (sColName == null)
			return;
		for (int i = 0; i < sColName.length; i++) {
			if (sColName[i] != null)
				sColName[i] = sColName[i].trim();
			if (sColName[i] == null || sColName[i].length() == 0)
				sColName[i] = String.valueOf(i);
		}

	}

	public DBResult(Vector obj, int iRows, int iCols) {
		cData = null;
		this.iRows = -1;
		this.iCols = -1;
		sColName = null;
		iTotal = -1;
		if (obj != null)
			cData = obj;
		else
			cData = null;
		this.iRows = iRows;
		this.iCols = iCols;
	}

	public int getRows() {
		return iRows;
	}

	public int getCols() {
		return iCols;
	}

	public String getString(int iRow, int iCol) {
		if (iRow < 0 || iCol < 0 || iRow >= iRows || iCol >= iCols
				|| iRow * iCols + iCol >= iRows * iCols)
			return null;
		else
			return (String) cData.elementAt(iRow * iCols + iCol);
	}

	public Object getObject(int iRow, int iCol) {
		if (iRow < 0 || iCol < 0 || iRow >= iRows || iCol >= iCols
				|| iRow * iCols + iCol >= iRows * iCols)
			return null;
		else
			return cData.elementAt(iRow * iCols + iCol);
	}

	public Object getObject(int iRow, String sName) {
		int iCol = getColIndex(sName);
		if (iRow < 0 || iCol < 0 || iRow >= iRows || iCol >= iCols
				|| iRow * iCols + iCol >= iRows * iCols)
			return null;
		else
			return cData.elementAt(iRow * iCols + iCol);
	}

	public String getString(int iRow, String sName) {
		int iCol = getColIndex(sName);
		return getString(iRow, iCol);
	}

	public int getColIndex(String sName) {
		if (sColName == null)
			return -1;
		for (int i = 0; i < sColName.length; i++)
			if (sColName[i].equalsIgnoreCase(sName))
				return i;

		return -1;
	}

	public String[] getColName() {
		return sColName;
	}

	public void setTotal(int i) {
		iTotal = i;
	}

	public int getTotal() {
		return iTotal;
	}
}

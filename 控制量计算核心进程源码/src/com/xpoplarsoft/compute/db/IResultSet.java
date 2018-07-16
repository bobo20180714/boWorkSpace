package com.xpoplarsoft.compute.db;

import java.io.Serializable;

public interface IResultSet extends Serializable {

	public int getRows();

	public int getCols();

	public String getString(int iRow, int iCol);

	public Object getObject(int iRow, int iCol);

	public String getString(int iRow, String sName);

	public Object getObject(int iRow, String sName);

	public int getColIndex(String sName);

	public String[] getColName();

	public void setTotal(int i);

	public int getTotal();

}

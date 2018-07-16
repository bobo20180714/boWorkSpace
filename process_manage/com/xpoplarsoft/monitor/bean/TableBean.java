package com.xpoplarsoft.monitor.bean;

import java.util.List;
import java.util.Map;

/**
 * 附加内容二维表bean
 * @author mengxiangchao
 *
 */
public class TableBean {

	private List<ColumnBean> columnData;
	
	private List<Map<String,String>> rowData;

	public List<ColumnBean> getColumnData() {
		return columnData;
	}

	public void setColumnData(List<ColumnBean> columnData) {
		this.columnData = columnData;
	}

	public List<Map<String,String>> getRowData() {
		return rowData;
	}

	public void setRowData(List<Map<String,String>> rowData) {
		this.rowData = rowData;
	}
	
}

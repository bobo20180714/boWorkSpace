package com.xpoplarsoft.baseInfo.orbitrelated.bean;

import java.util.List;
/**
 * @ClassName: PageView
 * @Description: 分页信息封装类
 * @author jingkewen
 * @data 2014-11-3 下午5:21:09
 *
 */
public class PageView {
	private int page = 1;//当前页
	private int start;//起始记录
	private int limit;//每页显示数
	private long pageCount;//总页数
	private List<?> records;//分页记录
	private long rowCount;//总记录数
	public PageView() {
		
	}
	
	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public long getPageCount() {
		return pageCount;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
		setPageCount(this.rowCount % this.limit == 0?this.rowCount/this.limit:this.rowCount/this.limit+1);
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
}

package com.xpoplarsoft.alarm.modal;

import java.util.List;

/**
 * 航天器组bean
 * @author 孟祥超
 *
 */
public class PageBean {

	private String pageId;
	private String pageName;
	private List<String> satIdArr;
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public List<String> getSatIdArr() {
		return satIdArr;
	}
	public void setSatIdArr(List<String> satIdArr) {
		this.satIdArr = satIdArr;
	}
}

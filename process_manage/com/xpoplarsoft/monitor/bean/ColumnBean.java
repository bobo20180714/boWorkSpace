package com.xpoplarsoft.monitor.bean;

/**
 * 列对象
 * @author mengxiangchao
 *
 */
public class ColumnBean {

	/**
	 * 列中文名称
	 */
	private String display;

	/**
	 * 列英文名称
	 */
	private String name;

	/**
	 * 单元格展示位置
	 */
	private String align;

	/**
	 * 列宽
	 */
	private int width;

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}

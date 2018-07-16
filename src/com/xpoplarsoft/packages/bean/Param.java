package com.xpoplarsoft.packages.bean;

/**
 * 采样数据内容
 * @author zhouxignlu
 * 2016年1月8日
 */
public class Param {
	/**
	 * 参数编号
	 */
	private int paramId;

	/**
	 * 数据类型
	 */
	private int dataType;

	/**
	 * 数据
	 */
	private Object content;

	/**
	 * 可信度
	 */
	private int reliability = 255;

	/**
	 * 报警级别
	 */
	private int alarmLevel = 0;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("paramId=[").append(paramId).append("]");
		sb.append("dataType=[").append(dataType).append("]");
		sb.append("content=[").append(content).append("]");
		sb.append("reliability=[").append(reliability).append("]");
		sb.append("alarmLevel=[").append(alarmLevel).append("]");

		return sb.toString();
	}

	public int getParamId() {
		return this.paramId;
	}

	public void setParamId(int paramId) {
		this.paramId = paramId;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public int getReliability() {
		return reliability;
	}

	public void setReliability(int reliability) {
		this.reliability = reliability;
	}

	public int getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(int alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

}

package com.xpoplarsoft.limits.resources.bean;

/**
 * 类功能: 资源实体类
 * 
 * @author 王晓东
 * @date 2015-01-22
 */
public class Resources {

	/**
	 * 主键
	 */
	private String pkId;

	/**
	 * 资源名称
	 */
	private String resName;
	
	/**
	 * 资源编号
	 */
	private String resCode;

	/**
	 * 资源类型
	 */
	private String resType;
	
	/**
	 * 显示类型
	 */
	private String showType;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 资源值
	 */
	private String resValue;

	/**
	 * 上级资源ID
	 */
	private String resFather;
	
	/**
	 * 上级资源编号
	 */
	private String resFatherCode;

	/**
	 * 上级资源名称
	 */
	private String resFatherName;

	/**
	 * 创建人
	 */
	private String updateUserCode;

	/**
	 * 创建时间
	 */
	private String updateTime;
	
	/**
	 * 菜单级数 
	 * @update 孟祥超
	 * @date 2015.09.14 15:45
	 */
	private int grade;
	
	/**
	 * 二级节点个数
	 */
	private int secondNum;


	/**
	 * 排序序号
	 */
	private int orderNum;
	
	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResValue() {
		return resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}

	public String getResFather() {
		return resFather;
	}

	public void setResFather(String resFather) {
		this.resFather = resFather;
	}

	public String getResFatherName() {
		return resFatherName;
	}

	public void setResFatherName(String resFatherName) {
		this.resFatherName = resFatherName;
	}

	public String getUpdateUserCode() {
		return updateUserCode;
	}

	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setSecondNum(int secondNum) {
		this.secondNum = secondNum;
	}

	public int getSecondNum() {
		return secondNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResFatherCode() {
		return resFatherCode;
	}

	public void setResFatherCode(String resFatherCode) {
		this.resFatherCode = resFatherCode;
	}
}

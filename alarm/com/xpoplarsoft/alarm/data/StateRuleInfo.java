package com.xpoplarsoft.alarm.data;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 状态字报警规则
 * @author zhouxignlu
 * 2015年8月28日
 */
@XStreamAlias("StateRule")
public class StateRuleInfo extends AlarmRuleInfo {
	//关联条件
	private String relation;
	
	//关联条件是否有效
	private String relationValid;
	
	//已选择的字节位转换的整数
	@XStreamAsAttribute
	private String mask;
	
	@XStreamAlias("unitList")
	private List<Unit> unit;
	
	private String statusName;//状态名称
	private String startWhere;//从第{0}开始
	private String orderType;//顺序，1：正序，2：反序
	
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelationValid() {
		return relationValid;
	}

	public void setRelationValid(String relationValid) {
		this.relationValid = relationValid;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public List<Unit> getUnit() {
		return unit;
	}

	public void setUnit(List<Unit> unit) {
		this.unit = unit;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStartWhere() {
		return startWhere;
	}

	public void setStartWhere(String startWhere) {
		this.startWhere = startWhere;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}

package com.xpoplarsoft.alarm.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 门限报警规则
 * @author zhouxignlu
 * 2015年8月28日
 */
@XStreamAlias("Value")
public class LimitRuleInfo extends AlarmRuleInfo {
	//关联条件
	private String relation;
	//关联条件是否有效
	private String relationValid;
	//重度下限	
	private String lowerfirst;
	//重度上限	
	private String upperfirst;
	//中度下限	
	private String lowersecond;
	//重度上限	
	private String uppersecond;
	//轻度下限	
	private String lowerthrid;
	//轻度上限	
	private String upperthrid;	
	//各级报警是否有效0：都无效；1：轻度有效；2：中度有效；3：轻度、中度有效；4：重度有效；5：轻度、重度有效；6：中度、重度有效；7：全有效
	private String rangevalidity;
	//有效值下限	
	private String rangevaluelower;
	//有效值上限	
	private String rangevalueupper;
	
	
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getLowerfirst() {
		return lowerfirst;
	}
	public void setLowerfirst(String lowerfirst) {
		this.lowerfirst = lowerfirst;
	}
	public String getUpperfirst() {
		return upperfirst;
	}
	public void setUpperfirst(String upperfirst) {
		this.upperfirst = upperfirst;
	}
	public String getLowersecond() {
		return lowersecond;
	}
	public void setLowersecond(String lowersecond) {
		this.lowersecond = lowersecond;
	}
	public String getUppersecond() {
		return uppersecond;
	}
	public void setUppersecond(String uppersecond) {
		this.uppersecond = uppersecond;
	}
	public String getLowerthrid() {
		return lowerthrid;
	}
	public void setLowerthrid(String lowerthrid) {
		this.lowerthrid = lowerthrid;
	}
	public String getUpperthrid() {
		return upperthrid;
	}
	public void setUpperthrid(String upperthrid) {
		this.upperthrid = upperthrid;
	}
	
	public String getRangevalidity() {
		return rangevalidity;
	}
	public void setRangevalidity(String rangevalidity) {
		this.rangevalidity = rangevalidity;
	}
	public String getRangevaluelower() {
		return rangevaluelower;
	}
	public void setRangevaluelower(String rangevaluelower) {
		this.rangevaluelower = rangevaluelower;
	}
	public String getRangevalueupper() {
		return rangevalueupper;
	}
	public void setRangevalueupper(String rangevalueupper) {
		this.rangevalueupper = rangevalueupper;
	}
	public String getRelationValid() {
		return relationValid;
	}
	public void setRelationValid(String relationValid) {
		this.relationValid = relationValid;
	}
}

package com.xpoplarsoft.alarm.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class AlarmRuleInfo {
	//报警配置信息主键
	private String ruleid;
	//卫星主键
	private String satid;
	//遥测参数名称
	@XStreamAsAttribute
	@XStreamAlias("Cap")
	private String tmname;
	//遥测参数编码
	private String tmcode;
	//遥测参数主键
	private String tmid;	
	//遥测参数类型
	private String tmtype;
	//报警类型0：门限报警；2：状态字报警
	@XStreamAsAttribute
	private String judgetype;
	//状态字报警规则
	private String rulecontent;
	
	//报警次数
	@XStreamAsAttribute
	private String judgecount;
	//是否报警0：是；1：否
	@XStreamAsAttribute
	private String canalarm;
	// 规则是否发生变化
	private boolean isChange;
	// 最近一次获取规则的时间
	private long getTimes;
	
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	public String getSatid() {
		return satid;
	}
	public void setSatid(String satid) {
		this.satid = satid;
	}
	public String getTmid() {
		return tmid;
	}
	public void setTmid(String tmid) {
		this.tmid = tmid;
	}

	public String getJudgetype() {
		return judgetype;
	}
	public void setJudgetype(String judgetype) {
		this.judgetype = judgetype;
	}
	public String getRulecontent() {
		return rulecontent;
	}
	public void setRulecontent(String rulecontent) {
		this.rulecontent = rulecontent;
	}
	
	public String getTmname() {
		return tmname;
	}
	public void setTmname(String tmname) {
		this.tmname = tmname;
	}
	public String getTmcode() {
		return tmcode;
	}
	public void setTmcode(String tmcode) {
		this.tmcode = tmcode;
	}
	public String getJudgecount() {
		return judgecount;
	}
	public void setJudgecount(String judgecount) {
		this.judgecount = judgecount;
	}
	public String getCanalarm() {
		return canalarm;
	}
	public void setCanalarm(String canalarm) {
		this.canalarm = canalarm;
	}
	public boolean isChange() {
		return isChange;
	}
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	public long getGetTimes() {
		return getTimes;
	}
	public void setGetTimes(long getTimes) {
		this.getTimes = getTimes;
	}
	public String getTmtype() {
		return tmtype;
	}
	public void setTmtype(String tmtype) {
		this.tmtype = tmtype;
	}
}
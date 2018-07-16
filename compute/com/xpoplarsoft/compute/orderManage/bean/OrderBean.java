package com.xpoplarsoft.compute.orderManage.bean;

import java.util.List;
import java.util.Map;

/**
 * 订单信息bean
 * @author mengxiangchao
 *
 */
public class OrderBean {

	private String pkId;
	private String orderId;
	private String orderName;
	private String satId;
	private String satMid;
	private String satName;
	

	private String order_class;
	private String loop_space;
	private String loop_maxnum;
	private String loop_endtime;
	
	private String time;
	private String overTime;
	private String computeCount;
	private String orderState;
	private String orderContent;
	private String orderErr;
	private String isGetData;
	private String getDataId;
	private String getDataFunName;
	private String getDataParam;
	private List<FuncParam> getDataParamList;
	private String computId;
	private String computFunName;
	private String computParam;
	private List<FuncParam> computParamList;
	private String isResult;
	private String resultId;
	private String resultFunName;
	private String resultParam;
	private List<FuncParam> resultParamList;
	
	private String computTypeId;
	private List<Map<String,Object>> inputParamObj;
	
	
	
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOrderContent() {
		return orderContent;
	}
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	public String getOrderErr() {
		return orderErr;
	}
	public void setOrderErr(String orderErr) {
		this.orderErr = orderErr;
	}
	public String getIsGetData() {
		return isGetData;
	}
	public void setIsGetData(String isGetData) {
		this.isGetData = isGetData;
	}
	public String getGetDataId() {
		return getDataId;
	}
	public void setGetDataId(String getDataId) {
		this.getDataId = getDataId;
	}
	public String getGetDataParam() {
		return getDataParam;
	}
	public void setGetDataParam(String getDataParam) {
		this.getDataParam = getDataParam;
	}
	public String getComputId() {
		return computId;
	}
	public void setComputId(String computId) {
		this.computId = computId;
	}
	public String getComputParam() {
		return computParam;
	}
	public void setComputParam(String computParam) {
		this.computParam = computParam;
	}
	public String getIsResult() {
		return isResult;
	}
	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getResultParam() {
		return resultParam;
	}
	public void setResultParam(String resultParam) {
		this.resultParam = resultParam;
	}
	public String getSatMid() {
		return satMid;
	}
	public void setSatMid(String satMid) {
		this.satMid = satMid;
	}
	public String getSatName() {
		return satName;
	}
	public void setSatName(String satName) {
		this.satName = satName;
	}
	public String getSatId() {
		return satId;
	}
	public void setSatId(String satId) {
		this.satId = satId;
	}
	public String getGetDataFunName() {
		return getDataFunName;
	}
	public void setGetDataFunName(String getDataFunName) {
		this.getDataFunName = getDataFunName;
	}
	public String getComputFunName() {
		return computFunName;
	}
	public void setComputFunName(String computFunName) {
		this.computFunName = computFunName;
	}
	public String getResultFunName() {
		return resultFunName;
	}
	public void setResultFunName(String resultFunName) {
		this.resultFunName = resultFunName;
	}
	public List<FuncParam> getGetDataParamList() {
		return getDataParamList;
	}
	public void setGetDataParamList(List<FuncParam> getDataParamList) {
		this.getDataParamList = getDataParamList;
	}
	public List<FuncParam> getComputParamList() {
		return computParamList;
	}
	public void setComputParamList(List<FuncParam> computParamList) {
		this.computParamList = computParamList;
	}
	public List<FuncParam> getResultParamList() {
		return resultParamList;
	}
	public void setResultParamList(List<FuncParam> resultParamList) {
		this.resultParamList = resultParamList;
	}
	public List<Map<String,Object>> getInputParamObj() {
		return inputParamObj;
	}
	public void setInputParamObj(List<Map<String,Object>> inputParamObj) {
		this.inputParamObj = inputParamObj;
	}
	public String getComputTypeId() {
		return computTypeId;
	}
	public void setComputTypeId(String computTypeId) {
		this.computTypeId = computTypeId;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getComputeCount() {
		return computeCount;
	}
	public void setComputeCount(String computeCount) {
		this.computeCount = computeCount;
	}
	public String getOrder_class() {
		return order_class;
	}
	public void setOrder_class(String order_class) {
		this.order_class = order_class;
	}
	public String getLoop_space() {
		return loop_space;
	}
	public void setLoop_space(String loop_space) {
		this.loop_space = loop_space;
	}
	public String getLoop_maxnum() {
		return loop_maxnum;
	}
	public void setLoop_maxnum(String loop_maxnum) {
		this.loop_maxnum = loop_maxnum;
	}
	public String getLoop_endtime() {
		return loop_endtime;
	}
	public void setLoop_endtime(String loop_endtime) {
		this.loop_endtime = loop_endtime;
	}
	
	
	
}

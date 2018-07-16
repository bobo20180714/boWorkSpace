package com.dataSource.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianshi.pack.NetLink;

/**
 * 解析结果
 * @author Administrator
 *
 */
public class DataPackage {
	//数据来源：0-基础、1-外部
	private int dataSource;
	//设备标识
	private String deviceId;
	//数据时间 yyyy-MM-dd HH:mm:ss.SSS
	private String dataTime;
	//参数值列表 <参数名,参数值>
	private Map<String,String> params;
	
	private List<ShowParam> paramList;
	
	//测站
	private String sid;
	
	/**
	 * 数据包类型 （2：链路信息）
	 */
	private int packageType;
	
	/**
	 * 链路监视信息
	 */
	private NetLink netLink;
	
	public DataPackage(){
		params=new HashMap<String, String>();
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dateTime) {
		this.dataTime = dateTime;
	}
	
	public Map<String,String> getParams(){
		return params;
	}
	
	public void addVal(String key,String val){
		this.params.put(key, val);
	}
	
	public String getVal(String key){
		return this.params.get(key);
	}

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	public NetLink getNetLink() {
		return netLink;
	}

	public void setNetLink(NetLink netLink) {
		this.netLink = netLink;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public List<ShowParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<ShowParam> paramList) {
		this.paramList = paramList;
	}
}

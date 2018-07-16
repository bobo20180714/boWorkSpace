package com.xpoplarsoft.process.bean;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程信息对象
 * @author zhouxignlu
 * 2017年2月14日
 */
public class ProcessBean {
	//进程内部标识
	private String id;
	//进程标识	
	private String code;
	//进程名称	
	private String name;
	
	private String type;
	//进程类型名称
	private String class_name;
	//相关卫星唯一标识
	private String sat_num;
	//进程状态:0、空闲 1、工作中 2、暂停 3、停止 4、异常 5、未知
	private String state;
	//进程启动时间
	private long start_time;
	//最近一次收到心跳时间
	private long life_time;
	//心跳发送周期
	private String round;
	//心跳信息接收进城标识
	private String targetId;
	
	private String errMessage;
	//进程使用adapter名称
	private String adapterName;
	
	//进程使用connector名称
	private String connectorName;
	
	//待反馈报文轮询间隔时间（毫秒）
	private int reedbackInterval;
	
	//待反馈报文重复请求次数上限,-1为不限次数
	private int limit;
	//是否为主、备进程，非主备进程时为-1
	private int isMain = -1;
	/**
	 * 服务器IP
	 */
	private String computerIp;
	/**
	 * 服务代理进程标识
	 */
	private String agencyProcessCode;
	/**
	 * 主进程标识
	 */
	private String mainProcessCode;
	//由进程启动的业务类对象集合
	private List<ProgramBean> programs = new ArrayList<ProgramBean>();
	
	public ProcessBean(){
		this.start_time = System.currentTimeMillis();
		this.id = String.valueOf(start_time % 1000000000);
		this.code = this.id;
		this.state = "0";
		this.round = "1000";
		this.life_time = -1;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getSat_num() {
		return sat_num;
	}
	public void setSat_num(String sat_num) {
		this.sat_num = sat_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	public long getLife_time() {
		return life_time;
	}
	public void setLife_time(long life_time) {
		this.life_time = life_time;
	}

	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}

	public String getConnectorName() {
		return connectorName;
	}

	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}

	public List<ProgramBean> getPrograms() {
		return programs;
	}

	public void setPrograms(List<ProgramBean> programs) {
		this.programs = programs;
	}
	
	public void addProgramBean(ProgramBean bean){
		this.programs.add(bean);
	}
	
	public ProgramBean getProgramBean(int index){
		return this.programs.get(index);
	}
	
	public int getReedbackInterval() {
		return reedbackInterval;
	}

	public void setReedbackInterval(int reedbackInterval) {
		this.reedbackInterval = reedbackInterval;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIsMain() {
		return isMain;
	}

	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}

	public String getComputerIp() {
		return computerIp;
	}

	public void setComputerIp(String computerIp) {
		this.computerIp = computerIp;
	}

	public String getAgencyProcessCode() {
		return agencyProcessCode;
	}

	public void setAgencyProcessCode(String agencyProcessCode) {
		this.agencyProcessCode = agencyProcessCode;
	}

	public String getMainProcessCode() {
		return mainProcessCode;
	}

	public void setMainProcessCode(String mainProcessCode) {
		this.mainProcessCode = mainProcessCode;
	}

	public static void main(String[] arg){
		try {
			new FileInputStream("X:\1.txt");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getStackTrace()[0].toString());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
} 

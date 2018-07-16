package com.xpoplarsoft.baseInfo.satInfo.bean;

/**
 * @ClassName: SatInfoDetail
 * @Description: 飞行器基本信息
 * @author jingkewen
 * @data 2014-9-2 下午2:06:53
 *
 */
public class SatInfoDetail{
	private int sat_id;
	private String sat_name;
	private String sat_code;
	private String design_org;
	private String user_org;
	private String design_life;
	private String launch_time;
	private String launch_time_start;
	private String launch_time_end;
	private String over_life;
	private String location;
	private String domain;
	private String platform;
	private String first_designer;
	private String team;
	private String duty_officer;
	private String create_user;
	private String create_time;
	private String status;
	private int mid;
	private String leaf;
	
	private String create_time_start;//查询条件需要
	private String create_time_end;
	
	private String multicast_address;
	private String udp_port;
	
	private String sat_ftm;
	private String sat_ftc;
	private String sat_orbit_height;
	private String sat_cycle;
	private String sat_longtitude;
	
	
	public String getSat_ftm() {
		return sat_ftm;
	}
	public void setSat_ftm(String sat_ftm) {
		this.sat_ftm = sat_ftm;
	}
	public String getSat_ftc() {
		return sat_ftc;
	}
	public void setSat_ftc(String sat_ftc) {
		this.sat_ftc = sat_ftc;
	}
	public String getSat_orbit_height() {
		return sat_orbit_height;
	}
	public void setSat_orbit_height(String sat_orbit_height) {
		this.sat_orbit_height = sat_orbit_height;
	}
	public String getSat_cycle() {
		return sat_cycle;
	}
	public void setSat_cycle(String sat_cycle) {
		this.sat_cycle = sat_cycle;
	}
	public String getSat_longtitude() {
		return sat_longtitude;
	}
	public void setSat_longtitude(String sat_longtitude) {
		this.sat_longtitude = sat_longtitude;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	public int getSat_id() {
		return sat_id;
	}
	public void setSat_id(int sat_id) {
		this.sat_id = sat_id;
	}
	public String getSat_name() {
		return sat_name;
	}
	public void setSat_name(String sat_name) {
		this.sat_name = sat_name;
	}
	public String getSat_code() {
		return sat_code;
	}
	public void setSat_code(String sat_code) {
		this.sat_code = sat_code;
	}
	public String getDesign_org() {
		return design_org;
	}
	public void setDesign_org(String design_org) {
		this.design_org = design_org;
	}
	public String getUser_org() {
		return user_org;
	}
	public void setUser_org(String user_org) {
		this.user_org = user_org;
	}
	public String getDesign_life() {
		return design_life;
	}
	public void setDesign_life(String design_life) {
		this.design_life = design_life;
	}
	public String getLaunch_time() {
		return launch_time;
	}
	public void setLaunch_time(String launch_time) {
		this.launch_time = launch_time;
	}
	public String getOver_life() {
		return over_life;
	}
	public void setOver_life(String over_life) {
		this.over_life = over_life;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getFirst_designer() {
		return first_designer;
	}
	public void setFirst_designer(String first_designer) {
		this.first_designer = first_designer;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getDuty_officer() {
		return duty_officer;
	}
	public void setDuty_officer(String duty_officer) {
		this.duty_officer = duty_officer;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getLaunch_time_start() {
		return launch_time_start;
	}
	public void setLaunch_time_start(String launch_time_start) {
		this.launch_time_start = launch_time_start;
	}
	public String getLaunch_time_end() {
		return launch_time_end;
	}
	public void setLaunch_time_end(String launch_time_end) {
		this.launch_time_end = launch_time_end;
	}
	
	public String getDetail(){
		return sat_code+","+sat_name+","+mid;
	}
	public String getMulticast_address() {
		return multicast_address;
	}
	public void setMulticast_address(String multicast_address) {
		this.multicast_address = multicast_address;
	}
	public String getUdp_port() {
		return udp_port;
	}
	public void setUdp_port(String udp_port) {
		this.udp_port = udp_port;
	}
	
}

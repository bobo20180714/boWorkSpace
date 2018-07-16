package com.xpoplarsoft.baseInfo.tmparams.bean;

/**
 * @ClassName: TmParamView
 * @Description: 参数信息
 * @author jingkewen
 * @data 2014-9-5 上午9:33:45
 *
 */
public class TmParamView {
	private int tm_param_id;
	private String owner_id;
	private String owner_name;
	private String tm_param_name;
	private String tm_param_code;
	private String tm_param_num;
	private String tm_param_type;
	private String tm_param_bdh;
	private String struct;
	private String create_user;
	private String create_user_name;
	private String create_time;
	private String create_time_start;
	private String create_time_end;
	private String type;
	private String sat_id;
	private String formula;
	
	private int id;
	private String name;
	private String code;
	private String num;
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getTm_param_bdh() {
		return tm_param_bdh;
	}
	public void setTm_param_bdh(String tm_param_bdh) {
		this.tm_param_bdh = tm_param_bdh;
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
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	
	public int getTm_param_id() {
		return tm_param_id;
	}
	public void setTm_param_id(int tm_param_id) {
		this.tm_param_id = tm_param_id;
		this.setId(tm_param_id);
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getTm_param_name() {
		return tm_param_name;
	}
	public void setTm_param_name(String tm_param_name) {
		this.tm_param_name = tm_param_name;
		this.setName(tm_param_name);
	}
	public String getTm_param_code() {
		return tm_param_code;
	}
	public void setTm_param_code(String tm_param_code) {
		this.tm_param_code = tm_param_code;
		this.setCode(tm_param_code);
	}
	public String getTm_param_num() {
		return tm_param_num;
	}
	public void setTm_param_num(String tm_param_num) {
		this.tm_param_num = tm_param_num;
		this.setNum(tm_param_num);
	}
	public String getTm_param_type() {
		return tm_param_type;
	}
	public void setTm_param_type(String tm_param_type) {
		this.tm_param_type = tm_param_type;
		this.setType(tm_param_type);
	}
	public String getStruct() {
		return struct;
	}
	public void setStruct(String struct) {
		this.struct = struct;
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
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSat_id() {
		return sat_id;
	}
	public void setSat_id(String sat_id) {
		this.sat_id = sat_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}

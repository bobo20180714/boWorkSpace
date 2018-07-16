package com.xpoplarsoft.baseInfo.orbitrelated.bean;

/**
 * @ClassName: OrbitRelatedFieldBean
 * @Description: 存储格式单元bean
 * @author jingkewen
 * @data 2014-10-10 上午10:14:24
 *
 */
public class OrbitRelatedFieldBean {
    private String field_id;
	private int jsjg_id;
	private String field_name;		//字段名称
	private String field_code;		//字段代号
	private String field_length;		//字段代号
	private int field_type;
	private String fiel_dscale;
	private String field_comment;	//字段名称
	private String create_user;	
	private String create_user_name;
	private String create_time;
	private String field_order;

	private String create_time_start;//查询条件需要
	private String create_time_end;
	private String is_display_flag;
	
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public int getJsjg_id() {
		return jsjg_id;
	}
	public void setJsjg_id(int jsjg_id) {
		this.jsjg_id = jsjg_id;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public int getField_type() {
		return field_type;
	}
	public void setField_type(int field_type) {
		this.field_type = field_type;
	}
	public String getField_comment() {
		return field_comment;
	}
	public void setField_comment(String field_comment) {
		this.field_comment = field_comment;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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
	public String getFieldTypeString() {
		//TODO  根据数据类型获取对应的显示字符串，待实现
		return String.valueOf(field_type);
	}
	public String getIs_display_flag() {
		return is_display_flag;
	}
	public void setIs_display_flag(String is_display_flag) {
		this.is_display_flag = is_display_flag;
	}
	public String getField_length() {
		return field_length;
	}
	public void setField_length(String field_length) {
		this.field_length = field_length;
	}
	public String getFiel_dscale() {
		return fiel_dscale;
	}
	public void setFiel_dscale(String fiel_dscale) {
		this.fiel_dscale = fiel_dscale;
	}
	public String getField_code() {
		return field_code;
	}
	public void setField_code(String field_code) {
		this.field_code = field_code;
	}
	public String getField_order() {
		return field_order;
	}
	public void setField_order(String field_order) {
		this.field_order = field_order;
	}
}

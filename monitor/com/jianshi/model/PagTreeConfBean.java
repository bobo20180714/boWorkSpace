package com.jianshi.model;

/**
 * 综合显示页面配置树信息表
 * @author mengxiangchao
 *
 */
public class PagTreeConfBean {

	private String pk_id;
	private String super_id;
	private int type;
	private int isroot;
	private int isleaf;
	private String page_name;
	private String page_url;
	private int open_mode;
	private int isalias;
	private String obj_id;
	private String create_user;
	private String update_user;
	
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
	public String getSuper_id() {
		return super_id;
	}
	public void setSuper_id(String super_id) {
		this.super_id = super_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsroot() {
		return isroot;
	}
	public void setIsroot(int isroot) {
		this.isroot = isroot;
	}
	public int getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(int isleaf) {
		this.isleaf = isleaf;
	}
	public String getPage_name() {
		return page_name;
	}
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public int getOpen_mode() {
		return open_mode;
	}
	public void setOpen_mode(int open_mode) {
		this.open_mode = open_mode;
	}
	public int getIsalias() {
		return isalias;
	}
	public void setIsalias(int isalias) {
		this.isalias = isalias;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	
}

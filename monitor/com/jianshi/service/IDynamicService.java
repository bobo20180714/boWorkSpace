package com.jianshi.service;

public interface IDynamicService {

	String getDynamic();

	String addDynamic(String name, String comment, String icon,String uid);

	boolean editDynamic(String id, String name, String comment, String icon);

	boolean delDynamic(String id);

}

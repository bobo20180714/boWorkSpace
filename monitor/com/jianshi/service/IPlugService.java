package com.jianshi.service;

public interface IPlugService {

	String addPlug(String name, int type, String comment,String uid, String icon);

	String getPlug(String uid,String key);

	String addStatic(String name, String comment, String type, String exp,
			String plug_id, String img);

	boolean delPlug(String id);

	String editStatic(String id, String name, String comment, String type,
			String exp, String img);

	boolean delGraph(String id);

	boolean setComplete(String id);

	String getStatic(String plugId);

	String editPlug(String id, String name, String comment,String icon);

	String getPlugByLibId(String libId);

	String getSelectPlug(String libId);

}

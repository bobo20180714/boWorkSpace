package com.jianshi.service;

public interface ILibService {

	String addLib(String name, String comment);

	String getLib(String key);

	String editLib(String id, String name, String comment);

	boolean delLib(String id);

	boolean addLibPlug(String values);

	boolean delLibPlug(String id);

	String getSelectLib(String proId);

}

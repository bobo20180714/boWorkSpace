package com.xpoplarsoft.packages.pack;

import java.util.List;

public interface IPack {
	
	/**
	 * 网络数据拆包
	 * @param data
	 * @return
	 */
	public Object unpack(byte[] data);
	
	
	public List<Object> unpackBatch(byte[] data);
	
	
	/**
	 * 组包
	 * @param obj
	 * @return
	 */
	public byte[] pack(Object obj);
	
	
	public byte[] packBatch(List<Object> list);

}

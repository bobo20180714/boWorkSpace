package com.xpoplarsoft.packages.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 采样数据包体
 * @author zhouxignlu
 * 2016年1月8日
 */
public class ParamBody {
	private Map<Integer, Param> paramContain = new HashMap<Integer, Param>();

	public Map<Integer, Param> getParamContain() {
		return paramContain;
	}

	public void addParam(Param value) {
		paramContain.put(value.getParamId(), value);
	}

	public Param getParam(int num) {
		return paramContain.get(num);
	}

	public void clear() {
		paramContain.clear();
	}

}

package com.xpoplarsoft.packages.pack;

import com.xpoplarsoft.packages.pack.impl.NoParamPackImpl;
import com.xpoplarsoft.packages.pack.impl.ParamPackImpl;


public class PackFactory {

	private static PackFactory instance;

	private IPack paramPack;

	private IPack noParamPack;

	private IPack repairPack;

	public static PackFactory getInstance() {
		if (null == instance) {
			instance = new PackFactory();
		}
		return instance;
	}

	public IPack getPackComponent(int type) {

		switch (type) {
		case PackConstant.PACK_PARAM:
			if (null == paramPack) {
				paramPack = new ParamPackImpl();
			}
			return paramPack;

		case PackConstant.PACK_NOPARAM:
			if (null == noParamPack) {
				noParamPack = new NoParamPackImpl();
			}
			return noParamPack;
		default:
			return null;
		}
	}
}

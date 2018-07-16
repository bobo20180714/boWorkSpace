package com.xpoplarsoft.packages.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.framework.utils.DataTools;

/**
 * 组包公共方法
 * @author mxc
 *
 */
public class PackagesUtil {

	private static Log log = LogFactory.getLog(PackagesUtil.class);
	
	public static byte[] check(byte[] data) {
		if (log.isDebugEnabled()) {
			log.debug("接收到的16进制数据为[" + DataTools.bytesToHesString(data) + "]");
		}
		// 取固定标识 EB 90
		byte[] fixed = new byte[2];

		System.arraycopy(data, 0, fixed, 0, 2);
		String fixedStr = DataTools.bytesToHesString(fixed);

		if (!fixedStr.equalsIgnoreCase("90EB")) {
			if (log.isErrorEnabled()) {
				log.error("解析的固定标识不正确,fixedStr=[" + fixedStr + "]");
			}
			return null;
		}

		// 进行校验位
		byte[] verifyByte = new byte[2];
		System.arraycopy(data, 8, verifyByte, 0, 2);
		int verifyData = DataTools.getLenth2(0, verifyByte);

		byte[] verifyByte2 = new byte[8];
		System.arraycopy(data, 0, verifyByte2, 0, 8);

		if (log.isDebugEnabled()) {
			log.debug("校验前8位16进制字符串为["
					+ DataTools.bytesToHesString(verifyByte2) + "]");
		}

		int verifyData2 = 0;
		for (byte bb : verifyByte2) {
			verifyData2 += bb & 0xFF;
		}

		if (verifyData != verifyData2) {
			if (log.isErrorEnabled()) {
				log.error("数据长度校验失败,verifyData =[" + verifyData
						+ "]verifyData2=[" + verifyData2 + "]");
			}
			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("数据验证通过");
		}

		// 信息分类
		byte[] dataTypeByte = new byte[1];
		System.arraycopy(data, 3, dataTypeByte, 0, 1);

		// 包体长度
		byte[] bodyLenByte = new byte[4];
		System.arraycopy(data, 4, bodyLenByte, 0, 4);
		int bodyLen = DataTools.getLenth2(0, bodyLenByte);

		if (log.isDebugEnabled()) {
			log.debug("bodyLen =[" + bodyLen + "]data.length=[" + data.length
					+ "]");
		}

		// 包体
		byte[] bodyByte = new byte[bodyLen];
		System.arraycopy(data, 10, bodyByte, 0, bodyLen);

		byte[] data2 = new byte[1 + bodyLen];

		System.arraycopy(dataTypeByte, 0, data2, 0, 1);

		System.arraycopy(bodyByte, 0, data2, 1, bodyLen);

		return data2;
	}
}

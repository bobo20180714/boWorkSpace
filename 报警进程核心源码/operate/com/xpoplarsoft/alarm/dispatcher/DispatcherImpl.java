package com.xpoplarsoft.alarm.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.config.ConnectConfig;
import com.bydz.fltp.connector.config.ConnectObj;
import com.bydz.fltp.connector.dispatcher.IDispatcher;
import com.bydz.fltp.connector.save.ISave;
import com.xpoplarsoft.framework.utils.ClassFactory;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.packages.pack.PackFactory;

public class DispatcherImpl implements IDispatcher {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(DispatcherImpl.class);
	
	private ISave save;

	/**
	 * 调度处理
	 * 
	 * @param data2
	 */
	public void dispatch(String connectorName, byte[] data) {

		if (log.isInfoEnabled()) {
			log.info("组件[DispatcherImpl]开始执行");
		}

		// 数据校验
		byte[] data2 = null;
		try {
			data2 = this.check(data);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("数据校验发生异常", e);
			}
		}
		if (null == data2) {
			return;
		}

		// 获取数据类型1：采样数据 2：非采样数据
		byte[] dataTypeByte = new byte[1];
		System.arraycopy(data2, 0, dataTypeByte, 0, 1);
		int dataType = DataTools.getLenth(0, dataTypeByte);

		if (log.isDebugEnabled()) {
			log.debug("dataType =[" + dataType + "]");
		}

		// 获取拆包组件
		IPack pack = PackFactory.getInstance().getPackComponent(dataType);

		if (null == pack) {
			if (log.isErrorEnabled()) {
				log.error("获取拆包组件为null");
			}
		}

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);

		// 拆包
		Comparable<Object> obj = (Comparable<Object>) pack.unpack(bodyByte);

		ConnectObj connectObj = ConnectConfig.getInstance().getParameter(
				connectorName);
		if (null == save) {
			String classImpl = connectObj.getParameter("cachedClassImpl");
			save = (ISave) ClassFactory.getInstance(classImpl);
		}
		// 调用数据存储组件进行存储
		save.save(obj);

	}

	public byte[] check(byte[] data) {
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

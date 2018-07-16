package com.xpoplarsoft.compute.orderDealLog.dispatch;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

import com.bydz.fltp.connector.dispatcher.IDispatcher;
import com.xpoplarsoft.common.util.JsonToMap;
import com.xpoplarsoft.compute.orderDealLog.bean.OrderDealLog;
import com.xpoplarsoft.compute.orderDealLog.cache.OrderDealLogCache;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.process.pack.ProcessData;
import com.xpoplarsoft.process.pack.ProcessPack;
import com.xpoplarsoft.process.pack.ProcessServiceBody;

/**
 * 订单日志接收处理
 * 
 */
public class OrderLogDispatcher implements IDispatcher {
	private static Log log = LogFactory.getLog(OrderLogDispatcher.class);
	@Override
	public void dispatch(String connectorName, byte[] data) {
		if (log.isInfoEnabled()) {
			log.info("组件[OrderLogDispatcher]开始执行,connectorName=[" + connectorName
					+ "]");
		}
		// 数据校验
		byte[] data2 = null;
		try {
			data2 = check(data);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("数据校验发生异常", e);
			}
		}
		if (null == data2) {
			return;
		}

		byte[] dataTypeByte = new byte[1];
		System.arraycopy(data2, 0, dataTypeByte, 0, 1);
		int dataType = DataTools.getLenth(0, dataTypeByte);
		if (log.isDebugEnabled()) {
			log.debug("dataType =[" + dataType + "]");
		}
		
		if(dataType != 3){
			return;
		}
		
		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);
		
		IPack pack = new ProcessPack();
		ProcessData processData = (ProcessData)pack.unpack(data);
		if (log.isDebugEnabled()) {
			log.debug("接收到的报文 =[" + processData + "]");
		}
		int msgType = processData.getHead().getType();
		if(processData.getBody() instanceof ProcessServiceBody){
			ProcessServiceBody orderLogBody = (ProcessServiceBody)processData.getBody();
			String serviceCode = orderLogBody.getServiceCode();
			OrderDealLog logObj = null;
			if(msgType != 1 && "orderLog".equals(serviceCode)){
				logObj = new OrderDealLog();
				logObj = getLogObj(orderLogBody.getContent());
				if(logObj != null){
					OrderDealLogCache.getInstance().putLog(System.currentTimeMillis(), logObj);
				}
			}
		}
	}

	private OrderDealLog getLogObj(String content) {
		OrderDealLog logObj = null;
		Map<String, Object> map = null;
		try {
			map = JsonToMap.jsonToMap(content);
			if(map != null && map.size() > 0){
				logObj = new OrderDealLog();
				logObj.setLogContent(map.get("content").toString());
				logObj.setLogTime(map.get("time").toString());
				logObj.setOrderCode(map.get("orderCode").toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return logObj;
	}

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

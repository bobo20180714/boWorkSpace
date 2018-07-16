package com.xpoplarsoft.single.databuffer.action;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.dispatcher.IDispatcher;
import com.bydz.fltp.connector.param.Device;
import com.dataSource.model.JsjgData;
import com.jianshi.cache.SatCache;
import com.jianshi.websocket.DataManager;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.bean.NoParamData;
import com.xpoplarsoft.packages.pack.PackConstant;
import com.xpoplarsoft.packages.pack.PackFactory;


/**
 * 类功能: 非遥测工程值存储调度类
 * 
 * @author mxc
 * @date 2016-01-25
 */
public class UNtmDispatcherImpl implements IDispatcher {

	private static Log log = LogFactory.getLog(UNtmDispatcherImpl.class);
	/* (non-Javadoc)
	 * @see com.bydz.fltp.connector.dispatcher.IDispatcher#dispatch(java.lang.String, byte[])
	 */
	@Override
	public void dispatch(String connectorName, byte[] data) {
		if (log.isInfoEnabled()) {
			log.info("组件[TMDispatcherImpl]开始执行,connectorName=[" + connectorName
					+ "]");
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
		
		if(dataType != 2){
			return;
		}
		
		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);

		NoParamData noParamData = (NoParamData) PackFactory.getInstance()
				.getPackComponent(PackConstant.PACK_NOPARAM).unpack(bodyByte);

		JsjgData jsjg=new JsjgData();
		int mid = noParamData.getHead().getMid();
		Device dev = SatCache.getInstance().getSat(mid);
        jsjg.setSatId(String.valueOf(dev.getId()));
        jsjg.setJsjgCode(noParamData.getHead().getInfoType());
        String xmlstr = noParamData.getBody().getXmlData();
		List<Map<String, String>> list = XmlUtil.xmlToListMap(xmlstr);
		Map<String, String> map = null;
		for (int i = 0; i < list.size(); i++) {
			map = list.get(i);
			for (String key : map.keySet()) {
				jsjg.addVal(key, map.get(key).toString());
			}
		}
        DataManager.addJsjg(jsjg);

	}
	public byte[] check(byte[] data) {
		if (log.isDebugEnabled()) {
			log.debug("接收到的16进制数据为[" + DataTools.bytesToHesString(data) + "]");
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

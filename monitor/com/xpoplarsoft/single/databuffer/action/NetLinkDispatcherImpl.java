package com.xpoplarsoft.single.databuffer.action;


import java.io.StringReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.bydz.fltp.connector.dispatcher.IDispatcher;
import com.bydz.packages.bean.NoParamData;
import com.bydz.packages.pack.IPack;
import com.bydz.packages.pack.PackFactory;
import com.bydz.packages.util.PackagesUtil;
import com.jianshi.cache.NetLinkCache;
import com.jianshi.pack.NetLink;
import com.xpoplarsoft.framework.utils.DataTools;


/**
 * 类功能: 链路监视调度类
 * 
 * @author mxc
 * @date 2017-04-19
 */
public class NetLinkDispatcherImpl implements IDispatcher {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(NetLinkDispatcherImpl.class);


	/**
	 * 调度处理
	 * 
	 * @param data2
	 */
	public void dispatch(String connectorName, byte[] data) {

		if (log.isInfoEnabled()) {
			log.info("组件[NetLinkDispatcherImpl]开始执行,connectorName=[" + connectorName
					+ "]");
		}
		
		// 数据校验
		byte[] data2 = null;
		try {
			data2 = PackagesUtil.check(data);
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

		// 获取拆包组件
		IPack pack = PackFactory.getInstance().getPackComponent(dataType);

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);

		NoParamData noParamData = (NoParamData)pack.unpack(data);
		if(noParamData.getHead().getInfoType() == 4){
			getNetLink(noParamData.getBody().getXmlData());
		}
	}

	/**
	 * 解析收到的链路信息
	 * @param xmlData
	 * @return
	 */
	private void getNetLink(String xmlData) {
		NetLink netlink = null;
		SAXBuilder builder = null;
		StringReader read = null;
		InputSource is = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("NetLinkDispatcherImpl getNetLink xmlData =[" + xmlData + "]");
			}
			read = new StringReader(xmlData);
			is = new InputSource(read);
			builder = new SAXBuilder();
			Document document = builder.build(is);
			Element root = document.getRootElement();
			List<Element> eleList = root.getChildren("line");
			for (Element eleLine : eleList) {
				netlink = new NetLink();
				netlink.setId(eleLine.getChild("sou").getTextTrim());
				netlink.setSendTime(eleLine.getChild("t").getTextTrim());
				netlink.setState("0");
				NetLinkCache.getInstance().putToCache(netlink);
			}
		}catch(Exception e){
			if(log.isErrorEnabled()){
				log.error("组件[NetLinkDispatcherImpl][getNetLink]解析发生异常！");
			}
			e.printStackTrace();
		}finally{
			if(read != null){
				read.close();
			}
		}
	}

}

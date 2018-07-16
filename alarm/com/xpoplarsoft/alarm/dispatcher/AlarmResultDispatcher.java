/**
 * com.xpoplarsoft.alarm.dispatcher
 */
package com.xpoplarsoft.alarm.dispatcher;

import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.bydz.fltp.connector.dispatcher.impl.DispatcherImpl;
import com.bydz.fltp.connector.pack.IPack;
import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.pack.AlarmNoParamPackImpl;
import com.xpoplarsoft.alarm.result.AlarmResult;
import com.xpoplarsoft.alarm.result.AlarmResultManager;
import com.xpoplarsoft.alarm.result.cache.AlarmResultCache;
import com.xpoplarsoft.framework.utils.DataTools;

/**
 * 报警结果报文接收处理
 * 
 * @author zhouxignlu 2017年4月6日
 */
public class AlarmResultDispatcher extends DispatcherImpl {
	private static Log log = LogFactory.getLog(AlarmResultDispatcher.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bydz.fltp.connector.dispatcher.IDispatcher#dispatch(java.lang.String,
	 * byte[])
	 */
	@Override
	public void dispatch(String connectorName, byte[] data) {
		if (log.isDebugEnabled()) {
			log.debug("门限报警结果报文接收解析开始!");
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
		
		if(dataType != 3){
			return;
		}

		// 获取拆包组件
		IPack pack = new AlarmNoParamPackImpl();

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);
		AlarmRuleInfo ruleInfo = (AlarmRuleInfo) pack.unpack(bodyByte);
		String resultXml = ruleInfo.getRulecontent();

		setAlarmResult(resultXml);
	}

	private void setAlarmResult(String resultXml) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new InputSource(new StringReader(resultXml)));			
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("解析报警结果报文["+resultXml+"]发生异常", e);
			}
			return;
		}
		AlarmResultManager arsm = new AlarmResultManager();

		Element rootEl = doc.getRootElement();
		String deviceid = rootEl.getChild("sat").getValue();
		String tmid = rootEl.getChild("tm").getValue();
		String time = rootEl.getChild("time").getValue();
		String data = rootEl.getChild("data").getValue();
		String level = rootEl.getChild("level").getValue();
		String message = rootEl.getChild("message").getValue();
		String isUpper = rootEl.getChild("isUpper").getValue();
		// 根据KEY获取报警结果
		AlarmResult alarmResult = AlarmResultCache.getAlarmResult(deviceid
				+ AlarmConst.SPLIT + tmid);

		if ((alarmResult.getLevel() == -1 || alarmResult.getLevel() == 0)
				&& "0".equals(level)) {
			// 若之前没报警，并且是正常，直接返回
			if (log.isDebugEnabled()) {
				log.debug("参数：[" + tmid + "之前没报警，并且是正常，不添加报警结果！]");
			}
			return;
		}
		arsm.setAlarmResult(deviceid + AlarmConst.SPLIT + tmid, data,
				Integer.parseInt(level), message,
				Boolean.valueOf(isUpper), Long.parseLong(time));
	}

}

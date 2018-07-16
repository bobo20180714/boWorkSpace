/**
 * com.xpoplarsoft.alarm.dispatcher
 */
package com.xpoplarsoft.alarm.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.dispatcher.impl.DispatcherImpl;
import com.bydz.fltp.connector.pack.IPack;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.pack.AlarmNoParamPackImpl;
import com.xpoplarsoft.framework.utils.DataTools;

/**
 * 门限报警规则报文接收解析处理类
 * 
 * @author zhouxignlu 2017年4月5日
 */
public class AlarmRuleDispatcherImpl extends DispatcherImpl {

	private static Log log = LogFactory.getLog(AlarmRuleDispatcherImpl.class);

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
			log.debug("门限报警规则报文接收解析开始!");
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
		//规则发送时给的是3，所以不是3的不做判断
		if(dataType != 3){
			return;
		}

		// 获取拆包组件
		IPack pack = new AlarmNoParamPackImpl();

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);
		AlarmRuleInfo ruleInfo = (AlarmRuleInfo) pack.unpack(bodyByte);
		AlarmCacheUtil.putAlarmRule(ruleInfo);
	}

}

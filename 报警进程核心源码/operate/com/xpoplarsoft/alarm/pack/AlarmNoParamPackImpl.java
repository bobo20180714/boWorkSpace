/**
 * com.xpoplarsoft.alarm
 */
package com.xpoplarsoft.alarm.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.pack.IPack;
import com.bydz.fltp.connector.tools.ConnectorTools;
import com.thoughtworks.xstream.XStream;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.framework.utils.DateTools;

/**
 * 报警规则报文拆组包
 * 
 * @author zhouxignlu 2017年4月5日
 */
public class AlarmNoParamPackImpl implements IPack {

	private static Log log = LogFactory.getLog(AlarmNoParamPackImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bydz.fltp.connector.pack.IPack#unpack(byte[])
	 */
	@Override
	public Object unpack(byte[] data) {
		if (log.isDebugEnabled()) {
			log.debug("AlarmNoParamPackImpl unpack数据的16进制字符串为["
					+ DataTools.bytesToHesString(data) + "]");
		}
		int n = 0;
		// 拆包
		byte[] lengthByte = new byte[4];
		System.arraycopy(data, n, lengthByte, 0, 4);
		int length = DataTools.getLenth2(0, lengthByte);
		n+=4;
		
		byte[] dateTimeTemp = new byte[8];
		System.arraycopy(data, n, dateTimeTemp, 0, 8);
		long dateTimeValue = ConnectorTools.bytes2long(dateTimeTemp);
		String dateTime = ConnectorTools.getDateTime(dateTimeValue);
		if (log.isDebugEnabled()) {
			log.debug("dateTimeValue =[" + dateTimeValue + "]转换后的时间为["
					+ dateTime + "]");
		}
		n+=8;
		
		byte[] typeByte = new byte[2];
		System.arraycopy(data, n, typeByte, 0, 2);
		int type = DataTools.getLenth2(0, typeByte);
		n+=2;
		
		byte[] bodyByte = new byte[length-14];
		System.arraycopy(data, n, bodyByte, 0, length-14);
		String rulecontent = null;
		try {
			rulecontent = new String(bodyByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isErrorEnabled()){
				log.error("AlarmNoParamPackImpl unpack转UTF-8码字符串时出现异常："+e);
			}
			return null;
		}
		XStream x = new XStream();
		AlarmRuleInfo ruleInfo = new AlarmRuleInfo();
		if(type == 0){//拆门限规则
			x.processAnnotations(LimitRuleInfo.class);
			ruleInfo = (AlarmRuleInfo) x.fromXML(rulecontent);
		}else if(type == 2){//拆状态字规则
			x.processAnnotations(StateRuleInfo.class);
			ruleInfo = (AlarmRuleInfo) x.fromXML(rulecontent);
		}else if(type == 4){//拆报警结果
			ruleInfo.setRulecontent(rulecontent);
		}
		ruleInfo.setJudgetype(String.valueOf(type));
		return ruleInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bydz.fltp.connector.pack.IPack#unpackBatch(byte[])
	 */
	@Override
	public List<Object> unpackBatch(byte[] data) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bydz.fltp.connector.pack.IPack#pack(java.lang.Object)
	 */
	@Override
	public byte[] pack(Object obj) {
		AlarmRuleInfo rul = (AlarmRuleInfo) obj;
		byte[] packData = null;
		try {
			byte[] bodyByte = rul.getRulecontent().getBytes("UTF-8");
			byte[] headByte = packHead(Integer.valueOf(rul.getJudgetype()),
					bodyByte.length);
			packData = new byte[headByte.length + bodyByte.length];

			System.arraycopy(headByte, 0, packData, 0, headByte.length);
			System.arraycopy(bodyByte, 0, packData, headByte.length,
					bodyByte.length);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("AlarmNoParamPackImpl pack 发生异常", e);
			}
		}
		return packData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bydz.fltp.connector.pack.IPack#packBatch(java.util.List)
	 */
	@Override
	public byte[] packBatch(List<Object> list) {

		return null;
	}

	private byte[] packHead(int type, int bodyLen) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] headByte = null;
		try {
			// 帧长度,不包含4字节的长度头4字节
			int frameLen = 14 + bodyLen;
			byte[] frameByte = DataTools.getLength2(4, frameLen);

			// 时间8字节
			long dateTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("dateTimeStr=["
						+ DateTools.getDatetime("yyyy-MM-dd HH:mm:ss.SSS")
						+ "]dateTime =[" + dateTime + "]");
			}
			byte[] dateTimeByte = ConnectorTools.long2bytes(dateTime);

			// 规则类型2字节 0：门限报警、2：状态字报警；
			byte[] typeByte = DataTools.getLength2(2, type);
			baos.write(frameByte);
			baos.write(dateTimeByte);
			baos.write(typeByte);
			headByte = baos.toByteArray();
		} finally {
			if (baos != null) {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
						if (log.isErrorEnabled()) {
							log.error("关闭baos发生异常", e);
						}
					}
				}
			}
		}

		return headByte;
	}
}

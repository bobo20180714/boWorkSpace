package com.xpoplarsoft.packages.pack.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.packages.bean.DataType;
import com.xpoplarsoft.packages.bean.Head;
import com.xpoplarsoft.packages.bean.Param;
import com.xpoplarsoft.packages.bean.ParamBody;
import com.xpoplarsoft.packages.bean.ParamData;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.packages.pack.PackConstant;

/**
 * 采样数据参数拆组包实现类
 * @author zhouxignlu
 * 2016年1月8日
 */
public class ParamPackImpl implements IPack {
	private static Log log = LogFactory.getLog(ParamPackImpl.class);
	
	public Object unpack(byte[] data) {

		if (log.isDebugEnabled()) {
			log.debug("unpack数据的16进制字符串为[" + DataTools.bytesToHesString(data)
					+ "]");
		}

		// 获取版本号
		byte[] versionByte = new byte[1];
		System.arraycopy(data, 2, versionByte, 0, 1);

		int version = DataTools.getLenth(0, versionByte);

		if (log.isDebugEnabled()) {
			log.debug("version =[" + version + "]");
		}

		// 拆包头
		byte[] head = new byte[21];
		System.arraycopy(data, 3, head, 0, 21);

		Head headData = this.unpackHead(head);

		// 拆包体
		byte[] body = new byte[data.length - 21];
		System.arraycopy(data, 21, body, 0, data.length - 21);
		ParamBody bodyData = this.unpackBody(body);

		ParamData paramData = new ParamData();

		paramData.setHead(headData);
		paramData.setBody(bodyData);

		return paramData;
	}

	public static void main(String[] arg) {

		long tt = System.currentTimeMillis();

		String dateTime = ConnectorTools.getDateTime(tt);

		System.out.println(dateTime);
	}

	private Head unpackHead(byte[] value) {

		if (log.isDebugEnabled()) {
			log.debug("unpackHead数据的16进制字符串为["
					+ DataTools.bytesToHesString(value) + "]");
		}
		Head headData = new Head();
		// 时间
		byte[] timeTemp = new byte[8];
		System.arraycopy(value, 0, timeTemp, 0, 8);
		long dateTimeValue = ConnectorTools.bytes2long(timeTemp);

		String dateTime = ConnectorTools.getDateTime(dateTimeValue);

		if (log.isDebugEnabled()) {
			log.debug("dateTimeValue =[" + dateTimeValue + "]转换后的时间为["
					+ dateTime + "]");
		}
		headData.setDateTime(dateTime);

		// 信源
		byte[] sourceTemp = new byte[2];
		System.arraycopy(value, 8, sourceTemp, 0, 2);
		int source = DataTools.getLenth2(0, sourceTemp);
		headData.setSource(source);

		// 信宿
		byte[] targetTemp = new byte[2];
		System.arraycopy(value, 10, targetTemp, 0, 2);
		int target = DataTools.getLenth2(0, targetTemp);
		headData.setTarget(target);

		// 地面设备序号
		byte[] deviceIdTemp = new byte[2];
		System.arraycopy(value, 12, deviceIdTemp, 0, 2);
		int deviceId = DataTools.getLenth2(0, deviceIdTemp);
		headData.setDeviceId(deviceId);
		
		// 设备序号
		byte[] satIdTemp = new byte[2];
		System.arraycopy(value, 14, satIdTemp, 0, 2);
		int satId = DataTools.getLenth2(0, satIdTemp);
		headData.setSatId(satId);;
		
		// 采样数据类型
		byte[] typeTemp = new byte[1];
		System.arraycopy(value, 16, typeTemp, 0, 1);
		int type= DataTools.getLenth2(0, typeTemp);
		headData.setType(type);

		// 帧可信度
		byte[] reliabilityTemp = new byte[1];
		System.arraycopy(value, 17, reliabilityTemp, 0, 1);
		int reliability = DataTools.getLenth(0, reliabilityTemp);
		headData.setReliability(reliability);

		if (log.isDebugEnabled()) {
			log.debug("unpackHead head=[" + headData.toString() + "]");
		}

		return headData;
	}

	private ParamBody unpackBody(byte[] bodyData) {

		ParamBody body = new ParamBody();

		int count = 0;
		int length = bodyData.length;

		while (count < length) {
			Param param = new Param();
			// 参数编号
			byte[] paramIdByte = new byte[4];
			System.arraycopy(bodyData, count, paramIdByte, 0, 4);
			int paramId = DataTools.getLenth2(0, paramIdByte);
			param.setParamId(paramId);
			count = count + 4;

			if (log.isDebugEnabled()) {
				log.debug("paramId =[" + paramId + "]");
			}

			// 可信度
			byte[] reliabilityByte = new byte[1];
			System.arraycopy(bodyData, count, reliabilityByte, 0, 1);
			int reliability = DataTools.getLenth(0, reliabilityByte);
			param.setReliability(reliability);
			count = count + 1;
			if (log.isDebugEnabled()) {
				log.debug("reliability =[" + reliability + "]");
			}

			// 报警级别
			byte[] alarmLevelByte = new byte[1];
			System.arraycopy(bodyData, count, alarmLevelByte, 0, 1);
			int alarmLevel = DataTools.getLenth(0, alarmLevelByte);
			param.setAlarmLevel(alarmLevel);
			count = count + 1;
			if (log.isDebugEnabled()) {
				log.debug("alarmLevel =[" + alarmLevel + "]");
			}

			// 数据类型
			byte[] dataTypeByte = new byte[1];
			System.arraycopy(bodyData, count, dataTypeByte, 0, 1);
			int dataType = DataTools.getLenth(0, dataTypeByte);
			param.setDataType(dataType);
			count = count + 1;
			if (log.isDebugEnabled()) {
				log.debug("dataType =[" + dataType + "]");
			}

			// 数据长度
			byte[] dataLenByte = new byte[2];
			System.arraycopy(bodyData, count, dataLenByte, 0, 2);
			int dataLen = DataTools.getLenth2(0, dataLenByte);
			// param.setDataLen(dataLen);
			count = count + 2;
			if (log.isDebugEnabled()) {
				log.debug("dataLen =[" + dataLen + "]");
			}

			byte[] contentByte = new byte[dataLen];
			System.arraycopy(bodyData, count, contentByte, 0, dataLen);

			try {
				switch (dataType) {
				case DataType.DATA_TYPE_INT:
					int dataValue1 = DataTools.getLenth2(0, contentByte);
					param.setContent(dataValue1);
					break;
				case DataType.DATA_TYPE_STRING:
					String dataValue3 = null;
					try {
						dataValue3 = new String(contentByte, PackConstant.ENCODING);
					} catch (UnsupportedEncodingException e) {
					}
					param.setContent(dataValue3);
					break;
				case DataType.DATA_TYPE_LONG:
					long dataValue4 = ConnectorTools.bytes2long(contentByte);
					param.setContent(dataValue4);
					break;
				case DataType.DATA_TYPE_DOUBLE:
					double dataValue5 = ConnectorTools.bytes2double(contentByte);
					param.setContent(dataValue5);
					break;
				}
			} catch (Exception e) {
				log.error(param.getParamId());
				e.printStackTrace();
			}

			count = count + dataLen;

			if (log.isDebugEnabled()) {
				log.debug("content =[" + param.getContent() + "]");
			}

			body.addParam(param);
		}

		if (log.isDebugEnabled()) {
			log.debug("unpackBody result=[" + body.getParamContain() + "]");
		}

		return body;

	}

	public byte[] pack(Object obj) {

		byte[] packData = null;
		try {
			ParamData paramData = (ParamData) obj;
			// 组包体
			byte[] bodyByte = this.packBody(paramData.getBody());

			// 组包头
			byte[] headByte = this.packHead(paramData.getHead(),
					bodyByte.length);

			packData = new byte[headByte.length + bodyByte.length];

			System.arraycopy(headByte, 0, packData, 0, headByte.length);
			System.arraycopy(bodyByte, 0, packData, headByte.length,
					bodyByte.length);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("ParamPack pack 发生异常", e);
			}
		}

		return packData;
	}

	private byte[] packHead(Head headData, int bodyLen) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] headByte = null;
		try {
			// 帧长度
			int frameLen = 16 + bodyLen;
			byte[] frameByte = DataTools.getLength2(2, frameLen);

			// 版本
			int version = headData.getVersion();
			byte[] versionByte = DataTools.getLength(1, version);

			// 时间
			String dateTimeStr = headData.getDateTime();
			Date date = DateTools.strToDate(dateTimeStr,
					"yyyy-MM-dd HH:mm:ss.SSS");
			long dateTime = date.getTime();
			if (log.isDebugEnabled()) {
				log.debug("dateTimeStr=[" + dateTimeStr + "]dateTime =["
						+ dateTime + "]");
			}
			byte[] dateTimeByte = ConnectorTools.long2bytes(dateTime);

			// 信源
			int source = headData.getSource();
			byte[] sourceByte = DataTools.getLength2(2, source);

			// 信宿
			int target = headData.getTarget();
			byte[] targetByte = DataTools.getLength2(2, target);
			
			// 地面设备序号
			int deviceId = headData.getDeviceId();
			byte[] deviceIdByte = DataTools.getLength2(2, deviceId);

			// 设备序号
			int satId = headData.getSatId();
			byte[] satIdByte = DataTools.getLength2(2, satId);
			
			// 采样数据类型
			int type = headData.getType();
			byte[] typeByte = DataTools.getLength2(1, type);

			// 帧可信度
			int reliability = headData.getReliability();
			byte[] reliabilityByte = DataTools.getLength(1, reliability);

			baos.write(frameByte);
			baos.write(versionByte);
			baos.write(dateTimeByte);
			baos.write(sourceByte);
			baos.write(targetByte);
			baos.write(deviceIdByte);
			baos.write(satIdByte);
			baos.write(typeByte);
			baos.write(reliabilityByte);

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

	private byte[] packBody(ParamBody body) throws IOException {

		Map<Integer, Param> paramList = body.getParamContain();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bodyByte = null;
		try {
			for (Map.Entry<Integer, Param> entry : paramList.entrySet()) {

				Param param = entry.getValue();
				if (log.isDebugEnabled()) {
					log.debug("param=[" + param + "]");
				}
				int paramId = param.getParamId();
				byte[] paramIdByte = DataTools.getLength2(4, paramId);

				int reliability = param.getReliability();
				byte[] reliabilityByte = DataTools.getLength(1, reliability);

				int alarmLevel = param.getAlarmLevel();
				byte[] alarmLevelByte = DataTools.getLength(1, alarmLevel);

				int dataType = param.getDataType();
				byte[] dataTypeByte = DataTools.getLength(1, dataType);

				byte[] contentByte = null;
				switch (dataType) {
				case DataType.DATA_TYPE_INT:
					long dataValue1 = (Long) param.getContent();
					contentByte = ConnectorTools.long2bytes(dataValue1);
					break;
				case DataType.DATA_TYPE_STRING:
					String dataValue3 = (String) param.getContent();
					try {
						contentByte = dataValue3
								.getBytes(PackConstant.ENCODING);
					} catch (UnsupportedEncodingException e) {
					}
					break;
				case DataType.DATA_TYPE_LONG:
//					long dataValue4 = ((Long) param.getContent());
					long dataValue4 = Long.parseLong(param.getContent()==null?"0":param.getContent().toString());
					contentByte = ConnectorTools.long2bytes(dataValue4);
					break;
				case DataType.DATA_TYPE_DOUBLE:
					double dataValue5 = (Double) param.getContent();
					contentByte = ConnectorTools.double2bytes(dataValue5);
					break;
				}

				// 数据长度
				int dataLen = contentByte.length;

				if (log.isDebugEnabled()) {
					log.debug("dataLen=[" + dataLen + "]");
				}

				byte[] dataLenByte = DataTools.getLength2(2, dataLen);

				baos.write(paramIdByte);
				baos.write(reliabilityByte);
				baos.write(alarmLevelByte);
				baos.write(dataTypeByte);
				baos.write(dataLenByte);
				baos.write(contentByte);

			}

			bodyByte = baos.toByteArray();
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

		return bodyByte;

	}

	public List<Object> unpackBatch(byte[] data) {
		return null;
	}

	public byte[] packBatch(List<Object> list) {
		return null;
	}

}

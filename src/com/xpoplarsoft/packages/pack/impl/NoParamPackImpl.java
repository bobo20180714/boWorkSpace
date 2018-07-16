package com.xpoplarsoft.packages.pack.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.packages.bean.NoParamBody;
import com.xpoplarsoft.packages.bean.NoParamData;
import com.xpoplarsoft.packages.bean.NoParamHead;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.packages.pack.PackConstant;

public class NoParamPackImpl implements IPack {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(NoParamPackImpl.class);

	public Object unpack(byte[] data) {

		if (log.isDebugEnabled()) {
			log.debug("unpack数据的16进制字符串为[" + DataTools.bytesToHesString(data)
					+ "]");
		}

		//从第几位开始
		int start = 0;
		
		// 长度头
		byte[] lengthTemp = new byte[4];
		System.arraycopy(data, start, lengthTemp, 0, 4);
		int length = DataTools.getLenth2(0, lengthTemp);

		// 数据版本号
		byte[] versionTemp = new byte[1];
		System.arraycopy(data, start + 4, versionTemp, 0, 1);
		int version = DataTools.getLenth2(0, versionTemp);
		if (log.isDebugEnabled()) {
			log.debug("非采样数据报文版本version =[" + version + "]");
		}

		// 拆包头
		byte[] head = new byte[29];
		System.arraycopy(data, start, head, 0, 29);
		NoParamHead headData = this.unpackHead(head);

		// 拆包体
		byte[] body = new byte[data.length - 29];
		System.arraycopy(data, start + 29, body, 0, length - 25);
		NoParamBody bodyData = this.unpackBody(body);

		NoParamData paramData = new NoParamData();

		paramData.setHead(headData);
		paramData.setBody(bodyData);

		return paramData;
	}

	private NoParamHead unpackHead(byte[] headData) {
		int n = 0;
		// 长度头
		byte[] lengthTemp = new byte[4];
		System.arraycopy(headData, n, lengthTemp, 0, 4);
		int length = DataTools.getLenth2(0, lengthTemp);
		n += 4;
		// 数据版本号
		byte[] versionTemp = new byte[1];
		System.arraycopy(headData, n, versionTemp, 0, 1);
		int version = DataTools.getLenth2(0, versionTemp);
		n += 1;
		// 信源
		byte[] sourceTemp = new byte[2];
		System.arraycopy(headData, n, sourceTemp, 0, 2);
		int source = DataTools.getLenth2(0, sourceTemp);
		n += 2;
		// 信宿
		byte[] targetTemp = new byte[2];
		System.arraycopy(headData, n, targetTemp, 0, 2);
		int target = DataTools.getLenth2(0, targetTemp);
		n += 2;
		// 获取mid
		byte[] midByte = new byte[2];
		System.arraycopy(headData, n, midByte, 0, 2);
		int mid = DataTools.getLenth2(0, midByte);
		if (log.isDebugEnabled()) {
			log.debug("卫星mid =[" + mid + "]");
		}
		n += 2;
		// 获取信息类型
		byte[] infoTypeByte = new byte[8];
		System.arraycopy(headData, n, infoTypeByte, 0, 8);
		String infoType = DataTools.hex2Str(infoTypeByte).trim();

		n += 8;
		// 时间
		byte[] timeTemp = new byte[8];
		System.arraycopy(headData, n, timeTemp, 0, 8);
		long dateTimeValue = ConnectorTools.bytes2long(timeTemp);

		String dateTime = ConnectorTools.getDateTime(dateTimeValue);

		if (log.isDebugEnabled()) {
			log.debug("dateTimeValue =[" + dateTimeValue + "]转换后的时间为["
					+ dateTime + "]");
		}
		n += 8;
		// 预留
		byte[] tempByte = new byte[2];
		System.arraycopy(headData, n, tempByte, 0, 2);
		int temp = DataTools.getLenth2(0, tempByte);
		n += 2;
		NoParamHead noParamHead = new NoParamHead();
		noParamHead.setVersion(version);
		noParamHead.setLength(length);
		noParamHead.setSource(source);
		noParamHead.setTarget(target);
		noParamHead.setMid(mid);
		noParamHead.setInfoType(infoType);
		noParamHead.setDateTime(dateTime);
		noParamHead.setRemain(temp);
		return noParamHead;
	}

	private NoParamBody unpackBody(byte[] body) {
		NoParamBody noParambody = new NoParamBody();
		try {
			noParambody.setXmlData(new String(body, PackConstant.ENCODING));
		} catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error("ParamUnPack unpackBody 发生异常", e);
			}
		}
		return noParambody;
	}

	public byte[] pack(Object obj) {

		byte[] packData = null;
		try {
			NoParamData noParamData = (NoParamData) obj;
			// 组包体
			byte[] bodyByte = this.packBody(noParamData.getBody());

			// 组包头
			byte[] headByte = this.packHead(noParamData.getHead(),
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

	private byte[] packHead(NoParamHead headData, int bodyLen)
			throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] headByte = null;
		try {
			// 长度头
			int length = bodyLen + 25;
			byte[] lengthByte = DataTools.getLength2(4, length);

			// 数据版本号
			int v = 1;
			byte[] vByte = DataTools.getLength2(1, v);

			// 信源
			int source = headData.getSource();
			byte[] sourceByte = DataTools.getLength2(2, source);

			// 信宿
			int target = headData.getTarget();
			byte[] targetByte = DataTools.getLength2(2, target);

			// 卫星唯一标识mid
			int mid = headData.getMid();
			byte[] midByte = DataTools.getLength2(2, mid);

			// 信息类型
			String infoType = headData.getInfoType();
			byte[] infoTypeByte = DataTools.hesStrToByte(DataTools
					.str2Hex(DataTools.fillChar(infoType, 8, " ")));

			// 发送时间
			String dateTimeStr = headData.getDateTime();
			Date date = DateTools.strToDate(dateTimeStr,
					"yyyy-MM-dd HH:mm:ss.SSS");
			byte[] timesByte = ConnectorTools.long2bytes(date.getTime());

			// 预留
			int remain = headData.getRemain();
			byte[] remainByte = DataTools.getLength2(2, remain);

			baos.write(lengthByte);
			baos.write(vByte);
			baos.write(sourceByte);
			baos.write(targetByte);
			baos.write(midByte);
			baos.write(infoTypeByte);
			baos.write(timesByte);
			baos.write(remainByte);

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

	private byte[] packBody(NoParamBody body) throws IOException {
		String xmlData = body.getXmlData();
		return xmlData.getBytes(PackConstant.ENCODING);
	}

	public List<Object> unpackBatch(byte[] data) {
		return null;
	}

	public byte[] packBatch(List<Object> list) {
		return null;
	}

	public static void main(String[] aq) {
		byte[] infoTypeByte = DataTools.getLength2(2, 24459);
		for (byte b : infoTypeByte) {
			System.out.println(b);
		}
	}

}

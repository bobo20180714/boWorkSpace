package com.xpoplarsoft.process.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.tools.ConnectorTools;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.process.core.IProcessCore;

/**
 * 进程报文拆组包工具
 * @author zhouxignlu
 * 2017年2月27日
 */
public class ProcessPack implements IPack {
	
	private static Log log = LogFactory.getLog(ProcessPack.class);
	
	@Override
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
		byte[] head = new byte[23];
		System.arraycopy(data, 10, head, 0, 23);
		ProcessHead headData = this.unpackHead(head);
		
		int type = headData.getType();
		
		// 拆包体
		ProcessBody bodyData = new ProcessBody();
		byte[] body = new byte[data.length - 33];
		System.arraycopy(data, 33, body, 0, data.length - 33);
		switch (type) {
		case 1:
			bodyData = this.unpackHeartbeatBody(body, type);
			break;
		case 2:
		case 3:
			bodyData = this.unpackOrderBody(body, type);
			break;
		case 4:
			bodyData = this.unpackServiceBody(body, type);
			break;
		case 9:
			bodyData = this.unpackReedbackBody(body, type);
			break;
		default:
			break;
		}
		
		ProcessData processData = new ProcessData();
		processData.setHead(headData);
		processData.setBody(bodyData);
		return processData;
	}

	@Override
	public List<Object> unpackBatch(byte[] data) {
		
		return null;
	}
	
	private ProcessHead unpackHead(byte[] headByte){
		if (log.isDebugEnabled()) {
			log.debug("unpackHead数据的16进制字符串为["
					+ DataTools.bytesToHesString(headByte) + "]");
		}
		ProcessHead headData = new ProcessHead();
		//报文发送时间
		byte[] dateTimeTemp = new byte[8];
		System.arraycopy(headByte, 4, dateTimeTemp, 0, 8);
		long dateTimeValue = ConnectorTools.bytes2long(dateTimeTemp);
		String dateTime = ConnectorTools.getDateTime(dateTimeValue);
		if (log.isDebugEnabled()) {
			log.debug("dateTimeValue =[" + dateTimeValue + "]转换后的时间为["
					+ dateTime + "]");
		}
		headData.setDateTime(dateTime);
		
		//业务类型
		byte[] typeTemp = new byte[2];
		System.arraycopy(headByte, 12, typeTemp, 0, 2);
		int type = DataTools.getLenth2(0, typeTemp);
		headData.setType(type);
		
		//是否需要反馈
		byte[] needReedbackTemp = new byte[1];
		System.arraycopy(headByte, 14, needReedbackTemp, 0, 1);
		int needReedback = DataTools.getLenth2(0, needReedbackTemp);
		headData.setNeedReedback(needReedback);

		// 信源
		byte[] sourceTemp = new byte[4];
		System.arraycopy(headByte, 15, sourceTemp, 0, 4);
		int source = DataTools.getLenth2(0, sourceTemp);
		headData.setSource(source);

		// 信宿
		byte[] targetTemp = new byte[4];
		System.arraycopy(headByte, 19, targetTemp, 0, 4);
		int target = DataTools.getLenth2(0, targetTemp);
		headData.setTarget(target);
		
		return headData;
	}
	
	private ProcessBody unpackHeartbeatBody(byte[] bodyByte, int type){
		ProcessHeartbeatBody bodyData = new ProcessHeartbeatBody();
		//报文代码
		byte[] messageIdByte = new byte[4];
		System.arraycopy(bodyByte, 0, messageIdByte, 0, 4);
		int messageId = DataTools.getLenth2(0, messageIdByte);
		bodyData.setMessageId(messageId);
		
		//航天器任务号
		//byte[] satNumByte = new byte[4];
		//System.arraycopy(bodyByte, 4, satNumByte, 0, 4);
		//int satNum = DataTools.getLenth2(0, satNumByte);
		//bodyData.setSatNum(satNum);
		
		//进程状态
		byte[] stateByte = new byte[1];
		System.arraycopy(bodyByte, 4, stateByte, 0, 1);
		int state = DataTools.getLenth2(0, stateByte);
		bodyData.setState(state);
		
		//附加信息长度
		byte[] lengthByte = new byte[2];
		System.arraycopy(bodyByte, 5, lengthByte, 0, 2);
		int length = DataTools.getLenth2(0, lengthByte);
		bodyData.setLength(length);
		
		//附加信息
		byte[] contentMessageByte = new byte[length];
		System.arraycopy(bodyByte, 7, contentMessageByte, 0, length);
		String contentMessage = "";
		try {
			contentMessage = new String(contentMessageByte,IProcessCore.CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			if(log.isErrorEnabled()){
				log.error("报文【"+messageId+"】附加信息转UTF-8码字符串时出现异常："+e);
			}
		}
		bodyData.setContent(contentMessage);

		return bodyData;
	}
	
	private ProcessBody unpackOrderBody(byte[] bodyByte, int type){
		ProcessOrderBody bodyData = new ProcessOrderBody();
		int n=0;
		//报文代码
		byte[] messageIdByte = new byte[4];
		System.arraycopy(bodyByte, n, messageIdByte, 0, 4);
		int messageId = DataTools.getLenth2(0, messageIdByte);
		bodyData.setMessageId(messageId);
		n+=4;
		
		//航天器任务号
		//byte[] satNumByte = new byte[4];
		//System.arraycopy(bodyByte, n, satNumByte, 0, 4);
		//int satNum = DataTools.getLenth2(0, satNumByte);
		//bodyData.setSatNum(satNum);
		//n+=4;
		
		//指令编号
		byte[] orderIdByte = new byte[2];
		
		if(type == 2 || type == 3){
			System.arraycopy(bodyByte, n, orderIdByte, 0, 2);
			int orderCode = DataTools.getLenth2(0, orderIdByte);
			bodyData.setOrderCode(orderCode);
			n+=2;
		}
		
		//发送次数
		byte[] sendNumByte = new byte[2];
		System.arraycopy(bodyByte, n, sendNumByte, 0, 2);
		int sendNum = DataTools.getLenth2(0, sendNumByte);
		bodyData.setSendNum(sendNum);
		n+=2;
		
		if(type == 4 || type == 3){
			//附加内容长度
			byte[] lengthByte = new byte[2];
			System.arraycopy(bodyByte, n, lengthByte, 0, 2);
			int length = DataTools.getLenth2(0, lengthByte);
			bodyData.setLength(length);
			n+=2;
			//附加内容
			byte[] contentByte = new byte[length];
			System.arraycopy(bodyByte, n, contentByte, 0, length);
			try {
				String content = new String(contentByte,IProcessCore.CHARSET_NAME);
				bodyData.setContent(content);
			} catch (UnsupportedEncodingException e) {
				if(log.isErrorEnabled()){
					log.error("报文【"+messageId+"】指令信息转UTF-8码字符串时出现异常："+e);
				}
			}
		}

		return bodyData;
	}
	
	private ProcessBody unpackReedbackBody(byte[] bodyByte, int type){
		ProcessReedbackBody bodyData = new ProcessReedbackBody();
		//报文代码
		byte[] messageIdByte = new byte[4];
		System.arraycopy(bodyByte, 0, messageIdByte, 0, 4);
		int messageId = DataTools.getLenth2(0, messageIdByte);
		bodyData.setMessageId(messageId);
		
		byte[] rbMessageidByte = new byte[4];
		System.arraycopy(bodyByte, 4, rbMessageidByte, 0, 4);
		int rbMessageid = DataTools.getLenth2(0, rbMessageidByte);
		bodyData.setRbMessageId(rbMessageid);
		
		byte[] rbDateTimeByte = new byte[8];
		System.arraycopy(bodyByte, 8, rbDateTimeByte, 0, 8);
		long rbDateTime = ConnectorTools.bytes2long(rbDateTimeByte);
		Date rbDate = new Date(rbDateTime);
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String rbDateTimeStr = sb.format(rbDate);
		bodyData.setRbTime(rbDateTimeStr);
		
		byte[] rbTypeByte = new byte[2];
		System.arraycopy(bodyByte, 16, rbTypeByte, 0, 2);
		int rbType = DataTools.getLenth2(0, rbTypeByte);
		bodyData.setRbType(rbType);
		 
		byte[] rbLengthByte = new byte[2];
		System.arraycopy(bodyByte, 18, rbLengthByte, 0, 2);
		int rbLength = DataTools.getLenth2(0, rbLengthByte);
		bodyData.setLength(rbLength);
		
		byte[] reedbackByte = new byte[rbLength];
		System.arraycopy(bodyByte, 20, reedbackByte, 0, rbLength);
		String reedback;
		try {
			reedback = new String(reedbackByte,IProcessCore.CHARSET_NAME);
			bodyData.setReedback(reedback);
		} catch (UnsupportedEncodingException e) {
			if(log.isErrorEnabled()){
				log.error("报文【"+messageId+"】反馈信息转UTF-8码字符串时出现异常："+e);
			}
		}
		
		return bodyData;
	}
	
	private ProcessBody unpackServiceBody(byte[] bodyByte, int type){
		ProcessServiceBody bodyData = new ProcessServiceBody();
		int n=0;
		//报文代码
		byte[] messageIdByte = new byte[4];
		System.arraycopy(bodyByte, n, messageIdByte, 0, 4);
		int messageId = DataTools.getLenth2(0, messageIdByte);
		bodyData.setMessageId(messageId);
		n+=4;
		
		//业务编码
		byte[] serviceCodeByte = new byte[8];
		System.arraycopy(bodyByte, n, serviceCodeByte, 0, 8);
		bodyData.setServiceCode(DataTools.hex2Str(serviceCodeByte).trim());
		n+=8;
		
		//航天器任务号
		//byte[] satNumByte = new byte[4];
		//System.arraycopy(bodyByte, n, satNumByte, 0, 4);
		//int satNum = DataTools.getLenth2(0, satNumByte);
		//bodyData.setSatNum(satNum);
		//n+=4;
		
		//发送次数
		byte[] sendNumByte = new byte[2];
		System.arraycopy(bodyByte, n, sendNumByte, 0, 2);
		int sendNum = DataTools.getLenth2(0, sendNumByte);
		bodyData.setSendNum(sendNum);
		n+=2;
		
		if(type == 4 || type == 3){
			//附加内容长度
			byte[] lengthByte = new byte[2];
			System.arraycopy(bodyByte, n, lengthByte, 0, 2);
			int length = DataTools.getLenth2(0, lengthByte);
			bodyData.setLength(length);
			n+=2;
			//附加内容
			byte[] contentByte = new byte[length];
			System.arraycopy(bodyByte, n, contentByte, 0, length);
			try {
				String content = new String(contentByte,IProcessCore.CHARSET_NAME);
				bodyData.setContent(content);
			} catch (UnsupportedEncodingException e) {
				if(log.isErrorEnabled()){
					log.error("报文【"+messageId+"】指令信息转UTF-8码字符串时出现异常："+e);
				}
			}
		}

		return bodyData;
	}

	@Override
	public byte[] pack(Object obj) {
		ProcessData processData = (ProcessData)obj;
		
		byte[] packData = null;
		try {
			//业务类型
			int type = processData.getHead().getType();
			
			byte[] bodyByte = null;
			// 组包体
			switch (type) {
			case 1:
				//心跳报文组包
				bodyByte = this.packHeartbeatBody(processData.getBody(),type);
				break;
			case 2:
			case 3:
				//指令报文组包
				bodyByte = this.packOrderBody(processData.getBody(),type);
				break;
			case 4:
				//业务报文组包
				bodyByte = this.packServiceBody(processData.getBody(),type);
				break;
			case 9:
				//反馈报文组包
				bodyByte = this.packReedbackBody(processData.getBody(),type);
				break;
			default:
				break;
			}

			// 组包头
			byte[] headByte = this.packHead(processData.getHead(),
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

	@Override
	public byte[] packBatch(List<Object> list) {
		
		return null;
	}

	private byte[] packHead(ProcessHead headData, int bodyLen) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] headByte = null;
		try {
			// 帧长度,不包含4字节的长度头
			int frameLen = 19 + bodyLen;
			byte[] frameByte = DataTools.getLength2(4, frameLen);

			// 时间
			long dateTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("dateTimeStr=[" + DateTools.getDatetime("yyyy-MM-dd HH:mm:ss.SSS") + "]dateTime =["
						+ dateTime + "]");
			}
			byte[] dateTimeByte = ConnectorTools.long2bytes(dateTime);

			// 报文业务类型
			int type = headData.getType();
			byte[] typeByte = DataTools.getLength2(2, type);
			
			//是否需要反馈
			int needReedback = headData.getNeedReedback();
			byte[] needReedbackByte = DataTools.getLength2(1, needReedback);
			// 信源
			int source = headData.getSource();
			byte[] sourceByte = DataTools.getLength2(4, source);

			// 信宿
			int target = headData.getTarget();
			byte[] targetByte = DataTools.getLength2(4, target);

			baos.write(frameByte);
			baos.write(dateTimeByte);
			baos.write(typeByte);
			baos.write(needReedbackByte);
			baos.write(sourceByte);
			baos.write(targetByte);
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
	
	private byte[] packHeartbeatBody(ProcessBody body, int type) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bodyByte = null;
		ProcessHeartbeatBody bodyData = (ProcessHeartbeatBody)body;
		try {
			int messageid = bodyData.getMessageId();
			byte[] messageIdByte = DataTools.getLength2(4, messageid);
			//int satNum = bodyData.getSatNum();
			//byte[] satNumByte = DataTools.getLength2(4, satNum);
			int state = bodyData.getState();
			byte[] stateByte = DataTools.getLength2(1, state);

			baos.write(messageIdByte);
			//baos.write(satNumByte);
			baos.write(stateByte);
			
			String content = bodyData.getContent();
			if(content!= null && !"".equals(content)){
				byte[] contentByte = content.getBytes(IProcessCore.CHARSET_NAME);
				int length = contentByte.length;
				byte[] lengthByte = DataTools.getLength2(2, length);
				baos.write(lengthByte);
				baos.write(contentByte);
			}else{
				baos.write(DataTools.getLength2(2, 0));
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
	
	private byte[] packOrderBody(ProcessBody body, int type) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bodyByte = null;
		ProcessOrderBody bodyData = (ProcessOrderBody)body;
		try {
			//报文编号
			int messageid = bodyData.getMessageId();
			byte[] messageIdByte = DataTools.getLength2(4, messageid);
			baos.write(messageIdByte);
			//航天器编号
			//int satNum = bodyData.getSatNum();
			//byte[] satNumByte = DataTools.getLength2(4, satNum);
			//baos.write(satNumByte);
			if(type == 2 || type == 3){
				//指令编号
				int orderCode = bodyData.getOrderCode();
				byte[] orderCodeByte = DataTools.getLength2(2, orderCode);
				baos.write(orderCodeByte);
			}
			//发送次数
			int sendNum = bodyData.getSendNum();
			byte[] sendNumByte = DataTools.getLength2(2, sendNum);
			baos.write(sendNumByte);
			if(type == 4 || type == 3){
				//附加内容
				String content = bodyData.getContent();
				byte[] contentByte = content.getBytes(IProcessCore.CHARSET_NAME);
				//附加内容长度
				int length = contentByte.length;
				byte[] lengthByte = DataTools.getLength2(2, length);
				baos.write(lengthByte);
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
	
	private byte[] packReedbackBody(ProcessBody body, int type) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bodyByte = null;
		ProcessReedbackBody bodyData = (ProcessReedbackBody)body;
		try {
			int messageid = bodyData.getMessageId();
			byte[] messageIdByte = DataTools.getLength2(4, messageid);
			int rbMessageid = bodyData.getRbMessageId();
			byte[] rbMessageidByte = DataTools.getLength2(4, rbMessageid);
			String rbDateTimeStr = bodyData.getRbTime();
			Date rbDate = DateTools.strToDate(rbDateTimeStr,
					"yyyy-MM-dd HH:mm:ss.SSS");
			long rbDateTime = rbDate.getTime();
			byte[] rbDateTimeByte = ConnectorTools.long2bytes(rbDateTime);
			int rbType = bodyData.getRbType();
			byte[] rbTypeByte = DataTools.getLength2(2, rbType);
			int rbLength = bodyData.getLength();
			byte[] rbLengthByte = DataTools.getLength2(2, rbLength);
			String reedback = bodyData.getReedback();
			byte[] reedbackByte = reedback.getBytes(IProcessCore.CHARSET_NAME);
			baos.write(messageIdByte);
			baos.write(rbMessageidByte);
			baos.write(rbDateTimeByte);
			baos.write(rbTypeByte);
			baos.write(rbLengthByte);
			baos.write(reedbackByte);
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
	
	//业务报文编码
	private byte[] packServiceBody(ProcessBody body, int type) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bodyByte = null;
		ProcessServiceBody bodyData = (ProcessServiceBody)body;
		try {
			//报文编号
			int messageid = bodyData.getMessageId();
			byte[] messageIdByte = DataTools.getLength2(4, messageid);
			baos.write(messageIdByte);
			//业务代码
			String serviceCode = bodyData.getServiceCode();
			String hexStr = DataTools.str2Hex(serviceCode);
			if(hexStr.length() > 16){
				log.error("业务代码["+serviceCode+"]超长！");
				return null;
			}
			byte[] serviceCodeByte = new byte[8];
			byte[] temp = DataTools.hesStrToByte(hexStr);
			int tl = temp.length;
			if(tl == 8){
				System.arraycopy(temp, 0, serviceCodeByte, 0, 8);
			}else{
				//位数不够左补0
				int index = 8-tl;
				System.arraycopy(temp, 0, serviceCodeByte, index, tl);
				for(int i=0;i<index;i++){
					serviceCodeByte[i]=0;
				}
			}
			baos.write(serviceCodeByte);
			//航天器编号
			//int satNum = bodyData.getSatNum();
			//byte[] satNumByte = DataTools.getLength2(4, satNum);
			//baos.write(satNumByte);
			
			//发送次数
			int sendNum = bodyData.getSendNum();
			byte[] sendNumByte = DataTools.getLength2(2, sendNum);
			baos.write(sendNumByte);
			if(type == 4){
				//附加内容
				String content = bodyData.getContent();
				byte[] contentByte = content.getBytes(IProcessCore.CHARSET_NAME);
				//附加内容长度
				int length = contentByte.length;
				byte[] lengthByte = DataTools.getLength2(2, length);
				baos.write(lengthByte);
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
	
	public static void main(String[] arg){
		
		String hex = DataTools.str2Hex("23");
		System.out.println(hex);
		byte[] a = new byte[8];
		byte[] b = DataTools.hesStrToByte(hex);
		int index = 8-b.length;
		System.arraycopy(b, 0, a, index, b.length);
		for(int i=0;i<index;i++){
			a[i]=0;
		}
		System.out.println(a.length);
		System.out.println(DataTools.hex2Str(a).trim());
	}
}

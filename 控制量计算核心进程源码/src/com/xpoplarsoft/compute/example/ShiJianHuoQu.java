/**
 * com.xpoplarsoft.compute.example
 */
package com.xpoplarsoft.compute.example;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.compute.IComputeExecuter;
import com.xpoplarsoft.compute.log.ComputeLogFactory;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.bean.ParamData;
import com.xpoplarsoft.packages.pack.PackConstant;
import com.xpoplarsoft.packages.pack.PackFactory;

/**
 * 时间参数工程值获取
 * 传入卫星任务号mid
 * @author zhouxignlu
 * 2017年4月26日
 */
public class ShiJianHuoQu implements IComputeExecuter {
	private static Log log = LogFactory.getLog(ShiJianHuoQu.class);

	private int mid=-1;
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		String satPort = "select a.pk_id, a.sat_id, a.address, a.port, a.content, a.create_time, a.type from sat_address_table a,satellite s "+
							" where a.sat_id = s.sat_id and s.mid = "+mid;
		String address = "";
		String port = "";
		DBResult rs = SQLFactory.getSqlComponent().queryInfo(satPort);
		if (rs != null && rs.getRows() > 0) {
			for (int j = 0; j < rs.getRows(); j++) {
				if(rs.get(j, "type").equals("1")){
					address = rs.get(j, "address");
					port = rs.get(j, "port");
				}
			}
		}else{
			//异常日志信息处理示例
			ComputeLogFactory.getIComputeLog().err("ShiJianHuoQu", "未能在数据库中获取到卫星["+mid+"]的数据收发地址信息。");
			return null;
		}
		InetAddress group = null;
		MulticastSocket ms = null;
		Map<String,Long> data = new HashMap<String,Long>();
		try {
			group = InetAddress.getByName(address);
			ms = new MulticastSocket(Integer.parseInt(port));
			ms.joinGroup(group);
			ms.setNetworkInterface(NetworkInterface
					.getByInetAddress(InetAddress.getLocalHost()));
			byte[] buf = new byte[50000];
			DatagramPacket dp = new DatagramPacket(buf, 50000);
			ms.receive(dp);
			int dataLen = dp.getLength();
			byte[] dataBuf = new byte[dataLen];
			System.arraycopy(buf, 0, dataBuf, 0, dataLen);
			
			byte[] bodyData = checkData(dataBuf);
			if(bodyData != null){
				//解包数据，获取时间数据
				// 包体长度
				byte[] bodyLenByte = new byte[4];
				System.arraycopy(dataBuf, 4, bodyLenByte, 0, 4);
				int bodyLen = DataTools.getLenth2(0, bodyLenByte);
				// 包体
				byte[] bodyByte = new byte[bodyLen];
				System.arraycopy(dataBuf, 10, bodyByte, 0, bodyLen);
				ParamData paramData = (ParamData) PackFactory.getInstance()
						.getPackComponent(PackConstant.PACK_PARAM).unpack(bodyByte);
				SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				
				data.put("time1",sp.parse(paramData.getHead().getDateTime()).getTime());
				//模拟参数中无星上时间，使用系统时间代替
				data.put("time2",System.currentTimeMillis());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ComputeLogFactory.getIComputeLog().err("ShiJianHuoQu", "获取实时时间数据数据出现异常："+e.getMessage());
		}
		try {
			ms.leaveGroup(group);
			ms.close();
			ms = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#setParameters(java.lang.Object[])
	 */
	@Override
	public void setParameters(Object... param) {
		this.mid = Integer.parseInt(param[0].toString());
	}
	
	private byte[] checkData(byte[] data){
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
			return null;
		}

		// 获取数据类型1：采样数据 2：非采样数据
		byte[] dataTypeByte = new byte[1];
		System.arraycopy(data2, 0, dataTypeByte, 0, 1);
		int dataType = DataTools.getLenth(0, dataTypeByte);

		if (log.isDebugEnabled()) {
			log.debug("dataType =[" + dataType + "]");
		}

		if (dataType != 1) {
			return null;
		}
		
		// 判断是实遥还是延遥
		byte[] tmTypeByte = new byte[1];
		System.arraycopy(data2, 18, tmTypeByte, 0, 1);
		int tmType = DataTools.getLenth(0, tmTypeByte);

		if (log.isDebugEnabled()) {
			log.debug("tmType =[" + tmType + "]");
		}

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);
		
		return bodyByte;
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

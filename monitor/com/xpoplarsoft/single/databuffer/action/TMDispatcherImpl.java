package com.xpoplarsoft.single.databuffer.action;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.dispatcher.IDispatcher;
import com.bydz.fltp.connector.param.Device;
import com.bydz.packages.util.PackagesUtil;
import com.dataSource.model.DataPackage;
import com.dataSource.model.ShowParam;
import com.jianshi.cache.ParamCache;
import com.jianshi.cache.SatCache;
import com.jianshi.websocket.DataManager;
import com.xpoplarsoft.framework.utils.DataTools;
import com.xpoplarsoft.packages.bean.Param;
import com.xpoplarsoft.packages.bean.ParamData;
import com.xpoplarsoft.packages.pack.IPack;
import com.xpoplarsoft.packages.pack.PackFactory;


/**
 * 类功能: 遥测工程值存储调度类
 * 
 * @author mxc
 * @date 2016-01-25
 */
public class TMDispatcherImpl implements IDispatcher {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(TMDispatcherImpl.class);


	/**
	 * 调度处理
	 * 
	 * @param data2
	 */
	public void dispatch(String connectorName, byte[] data) {

		if (log.isInfoEnabled()) {
			log.info("组件[TMDispatcherImpl]开始执行,connectorName=[" + connectorName
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

		if(dataType != 1){
			return;
		}

		// 获取拆包组件
		IPack pack = PackFactory.getInstance().getPackComponent(dataType);

		if (null == pack) {
			if (log.isErrorEnabled()) {
				log.error("获取拆包组件为null");
			}
			return;
		}

		byte[] bodyByte = new byte[data2.length - 1];
		System.arraycopy(data2, 1, bodyByte, 0, data2.length - 1);

		ParamData paramData = (ParamData)pack.unpack(bodyByte);
		DataPackage dataPack = new DataPackage();
		//测站
		dataPack.setSid(String.valueOf(paramData.getHead().getDeviceId()));
		dataPack.setDataSource(0);
		dataPack.setDataTime(paramData.getHead().getDateTime());
		//卫星
		int mid = paramData.getHead().getSatId();
		Device dev = SatCache.getInstance().getSat(mid);
		dataPack.setDeviceId(String.valueOf(dev.getId()));
		
		Map<Integer, Param> params = paramData.getBody().getParamContain();
		String value = "";

		List<ShowParam> pList = new ArrayList<ShowParam>();
		ShowParam sp = null;
		String code = null;
		for(Param p : params.values()){
			if(p.getDataType() == 0){
				//浮点型数据
				value = remainDeci(p.getContent().toString());
			}else{
				value = p.getContent().toString();
			}
//			dataPack.addVal(String.valueOf(p.getParamId()), value);
			sp = ShowParam.getParamObj(p);
			sp.setParamValue(value);
			code = ParamCache.getInstance().getParamCode(p.getParamId());
			if(code == null){
				continue;
			}
			sp.setParamCode(code);
			pList.add(sp);
		}
		dataPack.setParamList(pList);
		
		DataManager.put(dataPack);
	}
	
	/**
	 * 保留3位小数，四舍五入
	 * @param value
	 * @return
	 */
	private static String remainDeci(String value){
		if(value != null && value.contains(".")){
			String[] vArr = value.split("\\.");
			if(vArr[1].length() < 4){
				return value;
			}
		}
		BigDecimal decimal = new BigDecimal(value);
		decimal = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
		return decimal.toString();
	}
	
}

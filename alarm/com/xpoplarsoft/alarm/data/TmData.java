package com.xpoplarsoft.alarm.data;

import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;

/**
 * 参与计算的缓存数据,遥测数据
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class TmData implements IData {

	private AlarmParam value = null;


	/**
	 * 使用报警参数数据初始化计算用数据
	 * @param value 遥测报警数据
	 */
	public TmData(AlarmParam value) {
		this.value = value;
	}
	
	/**
	 * 根据设备id和参数code初始化计算用数据
	 * @param deviceId
	 * @param paramCode
	 */
	public TmData(String deviceId, String paramCode){
		String dcode = AlarmCacheUtil.getDeviceById(Integer.parseInt(deviceId)).getCode();
		this.value = AlarmCacheUtil.getParamByCode(dcode, paramCode);
	}

	@Override
	public int getType() {

		return value.getData_type();
	}

	@Override
	public Object getValue() {
		
		return value.getValue();
	}
	public void setValue(Object value){
		this.value.setValue(value);
	}
}

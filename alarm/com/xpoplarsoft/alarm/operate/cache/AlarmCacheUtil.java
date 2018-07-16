package com.xpoplarsoft.alarm.operate.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;

/**
 * 报警缓存管理工具
 * 
 * @author zhouxignlu 2015年9月8日
 */
public class AlarmCacheUtil {
	private static Log log = LogFactory.getLog(AlarmCacheUtil.class);
	private static boolean isInit = false;
	private static DeviceCache dc = new DeviceCache();
	private static ParamCache pc = new ParamCache();
	private static AlarmRuleCache rc = new AlarmRuleCache();

	/**
	 * 缓存初始化，可输入设备编号，初始化对应的设备
	 * 
	 * @param deviceID
	 */
	public static String initAlarmCache(String deviceID) {
		dc = new DeviceCache();
		pc = new ParamCache();
		rc = new AlarmRuleCache();
		String satid = putDeviceByDB(deviceID);
		if (satid != null)
			isInit = true;
		return satid;
	}
	
	/**
	 * 缓存初始化，可输入设备编号，初始化对应的设备
	 * 
	 * @param deviceID
	 */
	public static void initAlarmCache() {
		dc = new DeviceCache();
		pc = new ParamCache();
		rc = new AlarmRuleCache();
		putDeviceByDB(null);
		isInit = true;
	}

	public static String initAlarmCache(int mid) {
		dc = new DeviceCache();
		pc = new ParamCache();
		rc = new AlarmRuleCache();
		String satid = putDeviceByDB(mid);
		if (satid != null)
			isInit = true;
		return satid;
	}

	public static void setDeviceCache(DeviceCache dc) {
		AlarmCacheUtil.dc = dc;
		if (AlarmCacheUtil.dc == null) {
			AlarmCacheUtil.dc = new DeviceCache();
		}
	}

	public static void setParamCache(ParamCache pc) {
		AlarmCacheUtil.pc = pc;
		if (AlarmCacheUtil.pc == null) {
			AlarmCacheUtil.pc = new ParamCache();
		}
	}

	public static void setAlarmRuleCache(AlarmRuleCache rc) {
		AlarmCacheUtil.rc = rc;
		if (AlarmCacheUtil.rc == null) {
			AlarmCacheUtil.rc = new AlarmRuleCache();
		}
	}

	/**
	 * 根据mid获取航天器
	 * 
	 * @param mid
	 * @return
	 */
	public static Device geiDeviceByMid(int mid) {
		return AlarmCacheUtil.dc.geiDeviceByMid(mid);
	}

	/**
	 * 根据id获取航天器信息
	 * 
	 * @param id
	 * @return
	 */
	public static Device getDeviceById(int id) {
		return AlarmCacheUtil.dc.getDeviceById(id);
	}

	/**
	 * 向缓存中添加参数
	 * 
	 * @param param
	 */
	public static void putParam(AlarmParam param) {
		AlarmCacheUtil.pc.putParam(param);
	}

	/**
	 * 根据航天器id和遥测参数id获取遥测参数信息
	 * 
	 * @param device_id
	 * @param param_id
	 * @return
	 */
	public static AlarmParam getParamById(String device_id, String param_id) {
		return AlarmCacheUtil.pc.getParamById(device_id, param_id, 0);
	}

	/**
	 * 获取最新的遥测工程值
	 * 
	 * @param device_id
	 * @param param_id
	 * @param times
	 *            上一次获取遥测工程值的时间戳
	 * @return 如果时间戳大于等于遥测被赋值的时间戳，则认为没有最新的遥测工程值，返回null
	 */
	public static AlarmParam getParamById(String device_id, String param_id,
			long times) {
		return AlarmCacheUtil.pc.getParamById(device_id, param_id, times);
	}

	/**
	 * 根据航天器code和遥测参数code获取遥测参数信息
	 * 
	 * @param device_code
	 * @param param_code
	 * @return
	 */
	public static AlarmParam getParamByCode(String device_code,
			String param_code) {
		return AlarmCacheUtil.pc.getParamByCode(device_code, param_code, 0);
	}

	/**
	 * 获取最新的遥测工程值
	 * 
	 * @param device_code
	 * @param param_code
	 * @param times
	 *            上一次获取遥测工程值的时间戳
	 * @return 如果时间戳大于等于遥测被赋值的时间戳，则认为没有最新的遥测工程值，返回null
	 */
	public static AlarmParam getParamByCode(String device_code,
			String param_code, long times) {
		return AlarmCacheUtil.pc.getParamByCode(device_code, param_code, times);
	}

	/**
	 * 根据航天器mid和遥测参数num获取遥测参数信息
	 * 
	 * @param device_mid
	 * @param param_num
	 * @return
	 */
	public static AlarmParam getParamByMid(String device_mid, String param_num) {
		return AlarmCacheUtil.pc.getParamByMid(device_mid, param_num, 0);
	}

	/**
	 * 获取最新的遥测工程值
	 * 
	 * @param device_mid
	 * @param param_num
	 * @param times
	 *            上一次获取遥测工程值的时间戳
	 * @return 如果时间戳大于等于遥测被赋值的时间戳，则认为没有最新的遥测工程值，返回null
	 */
	public static AlarmParam getParamByMid(String device_mid, String param_num,
			long times) {
		return AlarmCacheUtil.pc.getParamByMid(device_mid, param_num, times);
	}

	/**
	 * 根据航天器code和遥测code的索引获取遥测参数
	 * 
	 * @param index
	 * @return
	 */
	public static AlarmParam getParamByIndex(int index) {
		return AlarmCacheUtil.pc.getParamByIndex(index);
	}

	/**
	 * 向缓存中添加报警规则
	 * 
	 * @param rule
	 */
	public static void putAlarmRule(AlarmRuleInfo rule) {
		AlarmCacheUtil.rc.putAlarmRule(rule);
	}

	/**
	 * 根据设备id和参数id获取报警规则
	 * 
	 * @param device_id
	 * @param param_id
	 * @return
	 */
	public static AlarmRuleInfo getAlarmRuleInfo(String device_id,
			String param_id) {
		return AlarmCacheUtil.rc.getAlarmRuleInfo(device_id, param_id);
	}

	/**
	 * 读取数据库中的设备信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备
	 */
	public static String putDeviceByDB(String deviceID) {
		String deviceid = AlarmCacheUtil.dc.putDeviceByDB(deviceID);
		if(deviceid != null){
			putAlarmParamByDB(deviceID);
			return deviceid;
		}
		return null;
	}

	public static String putDeviceByDB(int mid) {
		String deviceID = AlarmCacheUtil.dc.putDeviceByDB(mid);
		if (deviceID == null) {
			return null;
		}
		putAlarmParamByDB(deviceID);
		return deviceID;
	}

	/**
	 * 读取数据库中的遥测信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备下的遥测
	 */
	public static void putAlarmParamByDB(String deviceID) {
		try {
			AlarmCacheUtil.pc.putAlarmParamByDB(deviceID);
		} catch (Exception e) {
			log.error("航天器【"+deviceID+"】初始化参数失败！");
			log.error(e);
		}
		putAlarmRuleByDB(deviceID);
	}

	/**
	 * 读取数据库中的报警规则信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备的报警规则
	 */
	public static void putAlarmRuleByDB(String deviceID) {
		try {
			AlarmCacheUtil.rc.putAlarmRuleByDB(deviceID);
		} catch (Exception e) {
			log.error("航天器【"+deviceID+"】初始化报警规则失败！");
			log.error(e);
		}
	}

	/**
	 * 清除全部缓存
	 * 
	 * @return
	 */
	public static boolean cleanCache() {
		AlarmCacheUtil.dc.clean();
		AlarmCacheUtil.pc.clean();
		AlarmCacheUtil.rc.clean();
		AlarmCacheUtil.dc = new DeviceCache();
		AlarmCacheUtil.pc = new ParamCache();
		AlarmCacheUtil.rc = new AlarmRuleCache();
		isInit = false;
		return true;
	}

	public static DeviceCache getDc() {
		return dc;
	}

	public static ParamCache getPc() {
		return pc;
	}

	public static AlarmRuleCache getRc() {
		return rc;
	}

	public static boolean isInit() {
		return isInit;
	}

	public static boolean isInit(int mid) {
		Device device = dc.geiDeviceByMid(mid);
		if (device != null) {
			return true;
		}
		return false;
	}
	
	public static boolean isInit(String deviceid) {
		Device device = dc.getDeviceById(Integer.valueOf(deviceid));
		if (device != null) {
			return true;
		}
		return false;
	}

	public static void setInit(boolean isInit) {
		AlarmCacheUtil.isInit = isInit;
	}

	public static boolean isRuleChangeed(String device_id, String param_id) {
		return AlarmCacheUtil.rc.isChange(device_id, param_id);
	}

	/**
	 * 取出内存中的并，修改为不报警
	 * @param device_id
	 * @param param_id
	 */
	public static void queryThenUpdate(String device_id, String param_id) {
		//获取内存中的规则
		AlarmRuleInfo ruleInfo = getAlarmRuleInfo(device_id, param_id);
		if(ruleInfo != null){
			//修改为不报警
			ruleInfo.setCanalarm("1");
		}
	}
}

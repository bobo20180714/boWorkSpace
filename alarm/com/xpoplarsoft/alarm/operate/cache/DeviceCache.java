package com.xpoplarsoft.alarm.operate.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 航天器数据缓存
 * 
 * @author zhouxignlu 2015年9月8日
 */
public class DeviceCache {
	private static Log log = LogFactory.getLog(DeviceCache.class);
	/**
	 * 航天器信息缓存
	 */
	private Map<Integer, Device> deviceList = new HashMap<Integer, Device>();
	/**
	 * 航天器id与mid关系缓存
	 */
	private Map<Integer, Integer> midMap = new HashMap<Integer, Integer>();

	/**
	 * 根据mid获取航天器
	 * 
	 * @param mid
	 * @return
	 */
	public Device geiDeviceByMid(int mid) {
		return deviceList.get(mid);
	}

	/**
	 * 向缓存中添加航天器信息
	 * 
	 * @param device
	 */
	public void setDevice(Device device) {
		deviceList.put(device.getMd(), device);
		midMap.put(device.getId(), device.getMd());
	}

	/**
	 * 根据id获取航天器信息
	 * 
	 * @param id
	 * @return
	 */
	public Device getDeviceById(int id) {
		return deviceList.get(midMap.get(id));
	}

	public Map<Integer, Device> getList() {
		return deviceList;
	}

	public int getDeviceCount() {
		return deviceList.size();
	}

	/**
	 * 读取数据库中的设备信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备
	 */
	public String putDeviceByDB(String deviceID) {
		String sql = AlarmCacheConst.DEVICE_SQL;
		if (null != deviceID && !"".equals(deviceID)) {
			sql = sql + " and sat_id = '" + deviceID + "'";
		}

		DBResult dbrs = SQLFactory.getSqlComponent().queryInfo(sql);
		if (dbrs != null && dbrs.getRows() > 0) {
			for (int i = 0; i < dbrs.getRows(); i++) {
				Device device = new Device();
				device.setCode(dbrs.get(i, "sat_code"));
				device.setId(Integer.valueOf(dbrs.get(i, "sat_id")));
				device.setIp(dbrs.get(i, "multicast_address"));
				device.setMd(Integer.valueOf(dbrs.get(i, "mid")));
				device.setName(dbrs.get(i, "sat_name"));
				setDevice(device);
				AlarmCacheUtil.putAlarmParamByDB(dbrs.get(i, "sat_id"));
//				return deviceID;
			}
		}else{
			log.error("初始化航天器异常，系统中未查询到航天器id[" + deviceID + "]");
		}
		return null;
	}

	public String putDeviceByDB(int mid) {
		String sql = AlarmCacheConst.DEVICE_SQL;
		sql = sql + " and mid = '" + mid + "'";
		DBResult dbrs = SQLFactory.getSqlComponent().queryInfo(sql);
		if (dbrs != null && dbrs.getRows() > 0) {
			for (int i = 0; i < dbrs.getRows(); i++) {
				Device device = new Device();
				String sat_id = dbrs.get(i, "sat_id");
				device.setCode(dbrs.get(i, "sat_code"));
				device.setId(Integer.valueOf(sat_id));
				device.setIp(dbrs.get(i, "multicast_address"));
				device.setMd(Integer.valueOf(dbrs.get(i, "mid")));
				device.setName(dbrs.get(i, "sat_name"));
				setDevice(device);
				return sat_id;
			}
		}
		log.error("初始化航天器异常，系统中未查询到航天器mid[" + mid + "]");
		return null;
	}

	public boolean clean() {
		midMap.clear();
		deviceList.clear();
		return true;
	}
}

package com.xpoplarsoft.alarm.operate.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bydz.fltp.connector.param.Device;
import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

/**
 * 采样参数数据缓存类，存储所有被监视设备的最新采样数据
 * 
 * @author zhouxignlu 2015年9月2日
 */
public class ParamCache {
	private static Log log = LogFactory.getLog(ParamCache.class);
	/**
	 * 参数缓存，以航天器id拼接&&&拼接参数id为key
	 */
	private Map<String, AlarmParam> paramList = new HashMap<String, AlarmParam>();
	/**
	 * 编码与id的关系，航天器code拼接&&&拼接参数code为key
	 */
	private Map<String, String> codeMap = new HashMap<String, String>();
	/**
	 * num与id的关系，航天器mid拼接&&&拼接参数num为key
	 */
	private Map<String, String> numMap = new HashMap<String, String>();

	/**
	 * 航天器code拼接&&&拼接参数code为key，提供地址
	 */
	private List<String> codeList = new ArrayList<String>();

	/**
	 * 向缓存中添加参数
	 * 
	 * @param param
	 */
	public void putParam(AlarmParam param) {
		String pkey = String.valueOf(param.getDevice_id()) + AlarmConst.SPLIT
				+ String.valueOf(param.getId());
		paramList.put(pkey, param);

		String deviceCode = AlarmCacheUtil.getDeviceById(param.getDevice_id())
				.getCode();
		String deviceMid = String.valueOf(AlarmCacheUtil.getDeviceById(
				param.getDevice_id()).getMd());

		String ckey = deviceCode + AlarmConst.SPLIT + param.getCode();
		codeMap.put(ckey, pkey);
		codeList.add(ckey);
		String nkey = deviceMid + AlarmConst.SPLIT
				+ String.valueOf(param.getNum());
		numMap.put(nkey, pkey);
	}

	/**
	 * 根据航天器id和遥测参数id获取遥测参数信息
	 * 
	 * @param device_id
	 * @param param_id
	 * @return
	 */
	public AlarmParam getParamById(String device_id, String param_id, long times) {
		String key = device_id + AlarmConst.SPLIT + param_id;
		if (!paramList.containsKey(key)) {
			log.error("参数处理错误：航天器id[" + device_id + "]中未找到id[" + param_id
					+ "]遥测参数!");
		} else {
			AlarmParam p = paramList.get(key);
			if (p.getPutTime() > times) {
				paramList.put(key, p);
				return p;
			}
		}
		return null;
	}

	/**
	 * 根据航天器code和遥测参数code获取遥测参数信息
	 * 
	 * @param device_code
	 * @param param_code
	 * @return
	 */
	public AlarmParam getParamByCode(String device_code, String param_code,
			long times) {
		String key = codeMap.get(device_code + AlarmConst.SPLIT + param_code);
		if (!paramList.containsKey(key)) {
			log.error("参数处理错误：航天器code[" + device_code + "]中未找到code[" + param_code
					+ "]遥测参数!");
		} else {
			AlarmParam p = paramList.get(key);
			if (p.getPutTime() > times) {
				paramList.put(key, p);
				return p;
			}
		}
		return null;
	}

	/**
	 * 根据航天器mid和遥测参数num获取遥测参数信息
	 * 
	 * @param device_mid
	 * @param param_num
	 * @return
	 */
	public AlarmParam getParamByMid(String device_mid, String param_num,
			long times) {
		String key = numMap.get(device_mid + AlarmConst.SPLIT + param_num);
		if (!paramList.containsKey(key)) {
			log.error("参数处理错误：航天器mid[" + device_mid + "]中未找到num[" + param_num
					+ "]遥测参数!");
		} else {
			AlarmParam p = paramList.get(key);
			if (p.getPutTime() > times) {
				paramList.put(key, p);
				return p;
			}
		}
		return null;
	}

	/**
	 * 根据航天器code和遥测code的索引获取遥测参数
	 * 
	 * @param index
	 * @return
	 */
	public AlarmParam getParamByIndex(int index) {
		return paramList.get(codeMap.get(codeList.get(index)));
	}

	public int getParamCount() {
		return paramList.size();
	}

	/**
	 * 读取数据库中的遥测信息，并添加到缓存中
	 * 
	 * @param deviceID
	 *            设备id为空时添加所有设备下的遥测
	 */
	public void putAlarmParamByDB(String deviceID) {
		String sql = AlarmCacheConst.PARAM_SQL;
		List<DBResult> l = new ArrayList<DBResult>();
		if (null != deviceID && !"".equals(deviceID)) {
			sql = sql + " and sat_id = '" + deviceID + "'";
			DBResult dbrs = SQLFactory.getSqlComponent().queryInfo(sql);
			l.add(dbrs);
		} else {
			if (AlarmCacheUtil.getDc().getDeviceCount() > 0) {
				for (int mid : AlarmCacheUtil.getDc().getList().keySet()) {
					Device device = AlarmCacheUtil.geiDeviceByMid(mid);
					sql = AlarmCacheConst.PARAM_SQL + " where sat_id = '"
							+ device.getId() + "'";
					DBResult dbrs = SQLFactory.getSqlComponent().queryInfo(sql);
					l.add(dbrs);
				}
			} else {
				return;
			}
		}
		for (DBResult dbrs : l) {
			for (int i = 0; i < dbrs.getRows(); i++) {
				AlarmParam p = new AlarmParam();
				p.setCode(dbrs.get(i, "tm_param_code"));
				p.setData_type(Integer.valueOf(dbrs.get(i, "tm_param_type")));
				p.setDevice_id(Integer.valueOf(dbrs.get(i, "sat_id")));
				p.setId(Integer.valueOf(dbrs.get(i, "tm_param_id")));
				p.setJudgetype(Integer.valueOf(dbrs.get(i, "judgetype")));
				p.setName(dbrs.get(i, "tm_param_name"));
				p.setNum(Integer.valueOf(dbrs.get(i, "tm_param_num")));
				p.setPutTime(new Date().getTime());
				putParam(p);
			}
		}
	}

	public boolean clean() {
		codeList.clear();
		numMap.clear();
		codeMap.clear();
		paramList.clear();
		return true;
	}
}

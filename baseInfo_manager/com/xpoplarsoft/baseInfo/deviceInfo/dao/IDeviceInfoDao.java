package com.xpoplarsoft.baseInfo.deviceInfo.dao;

import com.xpoplarsoft.baseInfo.deviceInfo.bean.DeviceInfoBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

/**
 * 设备管理dao层接口
 * @author mxc
 *
 */
public interface IDeviceInfoDao {
	
	/**
	 * 根据设备编号查询设备详情
	 * @param devCode
	 * @return DeviceInfoBean
	 */
	DBResult getDevByCode(String devCode);

	/**
	 * 根据设备ID查询设备详情
	 * @param devId
	 * @return DeviceInfoBean
	 */
	DBResult getDevById(String devId);

	/**
	 * 设备列表
	 * @return List<DeviceInfoBean>
	 */
	DBResult getDevList();

	/**
	 * 分页查询设备列表(关键字查询)
	 * @param commBean
	 * @param param
	 * @return
	 */
	DBResult querydevList(CommonBean commBean, DBParameter param);

	/**
	 * 分页查询地面站列表（基本信息模糊查询）
	 * @param device_code
	 * @param device_name
	 * @param device_type
	 * @param commBean
	 * @return
	 */
	DBResult queryDeviceList(String device_code, String device_name,
			String device_type, CommonBean commBean);

	/**
	 * 删除设备信息
	 * @param device_id
	 * @return
	 */
	boolean deleteDevice(String device_id);

	/**
	 * 根据编号和设备ID查询设备
	 * @param code
	 * @param deviceId
	 * @return
	 */
	DBResult queryDeviceByCodeAndDev(String code, String deviceId);

	/**
	 * 根据sid和设备ID查询设备
	 * @param code
	 * @param deviceId
	 * @return
	 */
	DBResult queryDeviceBySidAndDev(String device_sid, String deviceId);

	/**
	 * 新增设备信息
	 * @param bean
	 * @return
	 */
	boolean addDevice(DeviceInfoBean bean);

	/**
	 * 修改地面站信息
	 * @param bean
	 * @return
	 */
	boolean alterDevice(DeviceInfoBean bean);

}

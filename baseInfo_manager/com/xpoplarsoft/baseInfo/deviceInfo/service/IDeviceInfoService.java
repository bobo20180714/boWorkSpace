package com.xpoplarsoft.baseInfo.deviceInfo.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.baseInfo.deviceInfo.bean.DeviceInfoBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 设备管理服务层接口
 * @author mxc
 *
 */
public interface IDeviceInfoService {

	/**
	 * 设备列表
	 * @return List<DeviceInfoBean>
	 */
	public List<DeviceInfoBean> getDevList();
	
	/**
	 * 根据设备编号查询设备详情
	 * @param devCode
	 * @return DeviceInfoBean
	 */
	public DeviceInfoBean getDevByCode(String devCode);
	
	/**
	 * 根据设备ID查询设备详情
	 * @param devId
	 * @return DeviceInfoBean
	 */
	public DeviceInfoBean getDevById(String devId);

	/**
	 * 分页查询设备列表
	 * @param key
	 * @param commBean
	 * @return
	 */
	public Map<String, Object> querydevList(String key, CommonBean commBean);

	/**
	 * 分页查询地面站列表
	 * @param device_code
	 * @param device_name
	 * @param device_type
	 * @param commBean
	 * @return
	 */
	public Map<String, Object> queryDeviceList(String device_code,
			String device_name, String device_type, CommonBean commBean);

	/**
	 * 删除地面站信息
	 * @param deviceIdArr
	 * @return
	 */
	public boolean deleteDevices(String[] deviceIdArr);

	/**
	 * 判断编号是否已经存在
	 * @param code
	 * @param deviceId
	 * @return
	 */
	public boolean judgeCode(String code, String deviceId);

	/**
	 * 判断sid是否已经存在
	 * @param device_sid
	 * @param deviceId
	 * @return
	 */
	public boolean judgeSid(String device_sid, String deviceId);

	/**
	 * 添加设备信息
	 * @param bean
	 * @return
	 */
	public boolean addDevice(DeviceInfoBean bean);

	/**
	 * 修改地面站信息
	 * @param bean
	 * @return
	 */
	public boolean alterDevice(DeviceInfoBean bean);
	
}

package com.xpoplarsoft.baseInfo.deviceInfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.common.BaseInfoCommon;
import com.xpoplarsoft.baseInfo.deviceInfo.bean.DeviceInfoBean;
import com.xpoplarsoft.baseInfo.deviceInfo.dao.IDeviceInfoDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
/**
 * 设备管理服务层实现类
 * @author mxc
 *
 */
@Service
public class DeviceInfoService implements IDeviceInfoService {

	@Autowired
	private IDeviceInfoDao deviceInfoDao;
	
	@Override
	public List<DeviceInfoBean> getDevList() {
		List<DeviceInfoBean> rsList = new ArrayList<DeviceInfoBean>();
		DBResult dbResult = deviceInfoDao.getDevList();
		DeviceInfoBean devInfo = null;
		for (int i = 0; dbResult != null && i < dbResult.getRows(); i++) {
			devInfo = new DeviceInfoBean();
			devInfo.setAddress(dbResult.get(i, "ADDRESS"));
			devInfo.setDevice_sid(dbResult.getObject(i, "DEVICE_SID")==null?0:Integer.parseInt(dbResult.get(i, "DEVICE_SID")));
			devInfo.setName(dbResult.get(i, "NAME"));
			devInfo.setParent_id(dbResult.getObject(i, "PARENT_ID")==null?0:Integer.parseInt(dbResult.get(i, "PARENT_ID")));
			devInfo.setPk_id(dbResult.getObject(i, "PK_ID")==null?0:Integer.parseInt(dbResult.get(i, "pk_id")));
			devInfo.setPort(dbResult.getObject(i, "PORT")==null?0:Integer.parseInt(dbResult.get(i, "PORT")));
			devInfo.setRemark(dbResult.get(i, "REMARK"));
			devInfo.setCode(dbResult.get(i, "DEVICE_CODE"));
			rsList.add(devInfo);
		}
		return rsList;
	}

	@Override
	public DeviceInfoBean getDevByCode(String devCode) {
		DeviceInfoBean devInfo = new DeviceInfoBean();
		DBResult dbResult = deviceInfoDao.getDevByCode(devCode);
		if(dbResult != null && dbResult.getRows() > 0) {
			devInfo = new DeviceInfoBean();
			devInfo.setAddress(dbResult.get(0, "ADDRESS"));
			devInfo.setDevice_sid(dbResult.getObject(0, "DEVICE_SID")==null?0:Integer.parseInt(dbResult.get(0, "DEVICE_SID")));
			devInfo.setName(dbResult.get(0, "NAME"));
			devInfo.setParent_id(dbResult.getObject(0, "PARENT_ID")==null?0:Integer.parseInt(dbResult.get(0, "PARENT_ID")));
			devInfo.setPk_id(dbResult.getObject(0, "PK_ID")==null?0:Integer.parseInt(dbResult.get(0, "pk_id")));
			devInfo.setPort(dbResult.getObject(0, "PORT")==null?0:Integer.parseInt(dbResult.get(0, "PORT")));
			devInfo.setRemark(dbResult.get(0, "REMARK"));
			devInfo.setCode(dbResult.get(0, "DEVICE_CODE"));
		}
		return devInfo;
	}

	@Override
	public DeviceInfoBean getDevById(String devId) {
		DeviceInfoBean devInfo = new DeviceInfoBean();
		DBResult dbResult = deviceInfoDao.getDevById(devId);
		if(dbResult != null && dbResult.getRows() > 0) {
			devInfo = new DeviceInfoBean();
			devInfo.setAddress(dbResult.get(0, "ADDRESS"));
			devInfo.setDevice_sid(dbResult.getObject(0, "DEVICE_SID")==null?0:Integer.parseInt(dbResult.get(0, "DEVICE_SID")));
			devInfo.setName(dbResult.get(0, "NAME"));
			devInfo.setParent_id(dbResult.getObject(0, "PARENT_ID")==null?0:Integer.parseInt(dbResult.get(0, "PARENT_ID")));
			devInfo.setPk_id(dbResult.getObject(0, "PK_ID")==null?0:Integer.parseInt(dbResult.get(0, "pk_id")));
			devInfo.setPort(dbResult.getObject(0, "PORT")==null?0:Integer.parseInt(dbResult.get(0, "PORT")));
			devInfo.setRemark(dbResult.get(0, "REMARK"));
			devInfo.setCode(dbResult.get(0, "DEVICE_CODE"));
		}
		return devInfo;
	}

	@Override
	public Map<String, Object> querydevList(String key, CommonBean commBean) {
		DBParameter param = new DBParameter();
		param.setObject("key",key);
		DBResult dbResult = deviceInfoDao.querydevList(commBean,param);
		
		Map<String, Object> pageData = DBResultUtil.dbResultToPageData(dbResult);
		
		return pageData;
	}

	@Override
	public Map<String, Object> queryDeviceList(String device_code,
			String device_name, String device_type, CommonBean commBean) {
		DBResult dbr = deviceInfoDao.queryDeviceList(device_code,device_name,device_type,commBean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public boolean deleteDevices(String[] deviceIdArr) {
		boolean flag = false;
		for (int i = 0; i < deviceIdArr.length; i++) {
			flag = deviceInfoDao.deleteDevice(deviceIdArr[i]);
			if(!flag){
				break;
			}
		}
		return flag;
	}

	@Override
	public boolean judgeCode(String code, String deviceId) {
		DBResult dbr = deviceInfoDao.queryDeviceByCodeAndDev(code,deviceId);
		if(dbr!= null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean judgeSid(String device_sid, String deviceId) {
		DBResult dbr = deviceInfoDao.queryDeviceBySidAndDev(device_sid,deviceId);
		if(dbr!= null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean addDevice(DeviceInfoBean bean) {
		//获取设备主键
		String dev_id = BaseInfoCommon.getSatOrTmId();
		bean.setPk_id(Integer.parseInt(dev_id));
		return deviceInfoDao.addDevice(bean);
	}

	@Override
	public boolean alterDevice(DeviceInfoBean bean) {
		return deviceInfoDao.alterDevice(bean);
	}

}

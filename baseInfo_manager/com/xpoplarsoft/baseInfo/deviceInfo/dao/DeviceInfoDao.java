package com.xpoplarsoft.baseInfo.deviceInfo.dao;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.baseInfo.deviceInfo.bean.DeviceInfoBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
/**
 * 设备管理dao层实现类
 * @author mxc
 *
 */
@Repository
public class DeviceInfoDao implements IDeviceInfoDao {

	//sql所属组名
	private static final String GROUP_NAME = "deviceInfo";
	
	@Override
	public DBResult getDevByCode(String devCode) {
		DBParameter param = new DBParameter();
		param.setObject("code", devCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				GROUP_NAME, "getDevByCode", param);
		return result;
	}

	@Override
	public DBResult getDevById(String devId) {
		DBParameter param = new DBParameter();
		param.setObject("pk_id", devId);
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				GROUP_NAME, "getDevById", param);
		return result;
	}

	@Override
	public DBResult getDevList() {
		DBResult result = SQLFactory.getSqlComponent().queryInfo(
				GROUP_NAME, "dev_info_list", new DBParameter());
		return result;
	}

	@Override
	public DBResult querydevList(CommonBean commBean, DBParameter param) {
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				GROUP_NAME, "pagingQuerydevList", param,commBean.getPage(),commBean.getPagesize());
		return result;
	}

	@Override
	public DBResult queryDeviceList(String device_code, String device_name,
			String device_type, CommonBean commBean) {
		DBParameter param = new DBParameter();
		param.setObject("device_code", device_code);
		param.setObject("device_name", device_name);
		param.setObject("device_type", device_type == null?-1:Integer.parseInt(device_type));
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				GROUP_NAME, "queryDeviceList", param,commBean.getPage(),commBean.getPagesize());
		return result;
	}

	@Override
	public boolean deleteDevice(String device_id) {
		DBParameter param = new DBParameter();
		param.setObject("device_id", device_id);
		return SQLFactory.getSqlComponent().updateInfo(GROUP_NAME, "deleteDevice", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryDeviceByCodeAndDev(String code, String deviceId) {
		String sql = "select t.* from STATION_INFO t where STATUS = 0 and "
				+ "t.DEVICE_CODE = '"+code+"' ";
		if(deviceId != null){
			sql = sql + " and t.pk_id != '"+deviceId+"'";
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult queryDeviceBySidAndDev(String device_sid, String deviceId) {
		String sql = "select t.* from STATION_INFO t where STATUS = 0 and "
				+ "t.device_sid = '"+device_sid+"' ";
		if(deviceId != null){
			sql = sql + " and t.pk_id != '"+deviceId+"'";
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}

	@Override
	public boolean addDevice(DeviceInfoBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("pk_id", bean.getPk_id());
		param.setObject("code", bean.getCode());
		param.setObject("name", bean.getName());
		param.setObject("device_sid", bean.getDevice_sid());
		param.setObject("parent_id", bean.getParent_id());
		param.setObject("remark", bean.getRemark());
		return SQLFactory.getSqlComponent().updateInfo(GROUP_NAME, "addDevice", param);
	}

	@Override
	public boolean alterDevice(DeviceInfoBean bean) {
		DBParameter param = new DBParameter();
		param.setObject("pk_id", bean.getPk_id());
		param.setObject("code", bean.getCode());
		param.setObject("name", bean.getName());
		param.setObject("device_sid", bean.getDevice_sid());
		param.setObject("parent_id", bean.getParent_id());
		param.setObject("remark", bean.getRemark());
		return SQLFactory.getSqlComponent().updateInfo(GROUP_NAME, "alterDevice", param);
	}

}

package com.xpoplarsoft.baseInfo.deviceInfo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.common.FileUploadUtil;
import com.xpoplarsoft.baseInfo.deviceInfo.bean.DeviceInfoBean;
import com.xpoplarsoft.baseInfo.deviceInfo.service.IDeviceInfoService;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.PageView;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.startup.FrameStartup;

/**
 * 设备(地面站)管理连接层
 * @author mxc
 *
 */
@Controller
@RequestMapping("/deviceInfo")
public class DeviceInfoController {
	
	private static final Gson GSON = new Gson();
	
	public static final Log log = LogFactory.getLog(DeviceInfoController.class);
	
	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	
	/**
	 * 查询设备列表（根据关键字查询）
	 * 
	 * @author 孟祥超
	 * @param request
	 * @param key 关键字
	 * @param commBean
	 * @return
	 */
	@RequestMapping(value="/queryDeviceList")
	public @ResponseBody
	String queryDeviceList(HttpServletRequest request,String device_code,
			String device_name,String device_type,CommonBean commBean) {
		device_type = ("".equals(device_type) || "null".equals(device_type))?null:device_type;
		Map<String,Object> rsMap = deviceInfoService.queryDeviceList(device_code,
				device_name,device_type,commBean);
		return GSON.toJson(rsMap);
	}
	
	/**
	 * 查看设备列表（ext使用，用PageView接收分页信息）
	 * @param request
	 * @param page
	 * @param key
	 * @return
	 */
	@RequestMapping(value="/findExtDeviceList")
	public @ResponseBody String findExtDeviceList(HttpServletRequest request,
			PageView page, String key) {
		if (log.isInfoEnabled()) {
			log.info("组件[deviceInfo][findExtDeviceList]开始执行");
		}
		CommonBean bean = new CommonBean();
		bean.setPage(page.getPage());
		bean.setPagesize(page.getLimit());
		Map<String,Object> rsMap = deviceInfoService.querydevList(key,bean);
		return GSON.toJson(rsMap.get("Rows"));
	}
	
	
	/**
	 * 查看地面站信息
	 * @param request
	 * @param deivceId
	 * @return
	 */
	@RequestMapping(value="/queryDeviceInfo")
	public @ResponseBody
	String queryDeviceInfo(HttpServletRequest request,String deviceId) {
		DeviceInfoBean rsMap = deviceInfoService.getDevById(deviceId);
		return GSON.toJson(rsMap);
	}
	
	/**
	 * 删除地面站（地面站ID多个用“;”分割）
	 * @param request
	 * @param deviceIds
	 * @return
	 */
	@RequestMapping(value="/deleteDevices")
	public @ResponseBody
	String deleteDevices(HttpServletRequest request,String deviceIds) {
		ResultBean rb = new ResultBean();
		String[] deviceIdArr = deviceIds.split(";");
		boolean flag = deviceInfoService.deleteDevices(deviceIdArr);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}
	
	/**
	 * 判断编号是否已经存在
	 * @param request
	 * @param code
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value="/judgeCode")
	public @ResponseBody
	String judgeCode(HttpServletRequest request,String code,String deviceId) {
		ResultBean rb = new ResultBean();
		deviceId = ("null".equals(deviceId) || "".equals(deviceId))?null:deviceId;
		boolean flag = deviceInfoService.judgeCode(code,deviceId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}

	/**
	 * 判断sid是否已经存在
	 * @param request
	 * @param code
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value="/judgeSid")
	public @ResponseBody
	String judgeSid(HttpServletRequest request,String device_sid,String deviceId) {
		ResultBean rb = new ResultBean();
		deviceId = ("null".equals(deviceId) || "".equals(deviceId))?null:deviceId;
		boolean flag = deviceInfoService.judgeSid(device_sid,deviceId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}
	
	/**
	 * 新增地面站信息
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/addDevice")
	public @ResponseBody
	String addDevice(HttpServletRequest request,DeviceInfoBean bean) {
		ResultBean rb = new ResultBean();
		boolean flag = deviceInfoService.addDevice(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}
	
	/**
	 * 修改地面站信息
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/alterDevice")
	public @ResponseBody
	String alterDevice(HttpServletRequest request,DeviceInfoBean bean) {
		ResultBean rb = new ResultBean();
		boolean flag = deviceInfoService.alterDevice(bean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return GSON.toJson(rb);
	}

	/**
	 * 查询设备列表（根据关键字查询）
	 * 
	 * @author 孟祥超
	 * @param request
	 * @param key 关键字
	 * @param commBean
	 * @return
	 */
	@RequestMapping(value="/querydevList")
	public @ResponseBody
	String satList(HttpServletRequest request,
			String key,CommonBean commBean) {
		Map<String,Object> rsMap = deviceInfoService.querydevList(key,commBean);
		return GSON.toJson(rsMap);
	}
	

	@RequestMapping(value = "/importDevice")
	public @ResponseBody
	String importDevice(HttpServletRequest request,String filePath) {
		String filePathTemp = FrameStartup.PROJECT_PATH + filePath;
		String[][] dataArr;
		try {
			dataArr = FileUploadUtil.getDataByFilePath(filePathTemp, 1);
			return saveData(dataArr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "导入失败！请联系管理员！";
	}
	
	public String saveData(String[][] records) {
		int r=0;
		try {
			List<DeviceInfoBean> deviceBeanList = new ArrayList<DeviceInfoBean>();
			DeviceInfoBean bean= null;
			for (int i = 0; i < records.length; i++) {
				if(records[i][0] == null || records[i][0].equals("")){
					break;
				}
				bean = new DeviceInfoBean();
				bean.setCode(records[i][0]);
				bean.setName(records[i][1]);
				if (records[i][2] != null && !"".equals(records[i][2])) {
					bean.setDevice_sid(Integer.parseInt(records[i][2]));
				}
				if (records[i][3] != null && !"".equals(records[i][3])) {
					bean.setParent_id(Integer.parseInt(records[i][3]));
				}
				bean.setRemark(records[i][4]);
				deviceBeanList.add(bean);
			}
			r = saveDevice(deviceBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (r == 0) {
			return "导入成功";
		} else if (r == 1) {
			return "导入失败";
		} else {
			return "部分数据导入失败，请查看错误日志!";
		}
	}
	
	/**
	 * 保存地面站信息
	 * @param deviceBeanList
	 * @return
	 */
	private int saveDevice(List<DeviceInfoBean> deviceBeanList) {
		int r=0;
		boolean flag = true;
		for(DeviceInfoBean bean : deviceBeanList){
			boolean iscontinue = false;
			
			//判断编号是否存在
			if(deviceInfoService.judgeCode(bean.getCode(), null)){
				log.error("地面站编号【"+bean.getCode()+"】重复");
				iscontinue = true;
			}
			//判断sid是否已存在
			if(deviceInfoService.judgeSid(String.valueOf(bean.getDevice_sid()), null)){
				log.error("地面站识别码【"+bean.getDevice_sid()+"】重复");
				iscontinue = true;
			}
			
			if(iscontinue){
				r=2;
				continue;
			}
			flag = deviceInfoService.addDevice(bean);
		}
		if(r==0 && !flag){
			r=1;
		}
		return r;
	}
}

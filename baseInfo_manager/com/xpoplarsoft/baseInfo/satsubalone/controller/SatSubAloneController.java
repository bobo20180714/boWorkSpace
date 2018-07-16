package com.xpoplarsoft.baseInfo.satsubalone.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;
import com.xpoplarsoft.baseInfo.satsubalone.service.ISatSubAloneService;

@Controller
@RequestMapping("/satsubalone")
public class SatSubAloneController{
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(SatSubAloneController.class);
	
	@Autowired
	private ISatSubAloneService satSubAloneService;
	
	/**
	 * @Title: findGrantUserGroupEquipmentTree
	 * @Description: 获取当前登陆人已拥有的设备信息树
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findgrantusergroupequipmenttree")
	public @ResponseBody String findGrantUserGroupEquipmentTree(HttpServletRequest request,String sys_resource_id,String key){
		if (log.isInfoEnabled()) {
			log.info("组件[SatSubAloneController][findGrantUserGroupEquipmentTree]开始执行");
		}
		String json = "";
		if(sys_resource_id==null) return null;
		List<SatSubAlone> result = satSubAloneService.findGrantUserGroupEquipmentTree(sys_resource_id,key);
		json = JSONObject.toJSONString(result);
		return json;
	}
	
	/**
	 * @Description: 获取航天器树
	 * @author 孟祥超
	 * @param key 关键字
	 * @throws
	 */
	@RequestMapping("findSatTree")
	public @ResponseBody String findSatTree(HttpServletRequest request,String key){
		if (log.isInfoEnabled()) {
			log.info("组件[SatSubAloneController][findSatTree]开始执行");
		}
		String json = "";
		List<Map<String, String>> result = satSubAloneService.findSatTree(key);
		json = JSONObject.toJSONString(result);
		return json;
	}
	
}

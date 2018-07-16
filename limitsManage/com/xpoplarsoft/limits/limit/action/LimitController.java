package com.xpoplarsoft.limits.limit.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.limits.limit.bean.LimitBean;
import com.xpoplarsoft.limits.limit.service.ILimitService;

/**
 * 权限统一处理接口
 * 
 * @author mxc
 */
@Controller
@RequestMapping("/LimitController")
public class LimitController {

	private static Log log = LogFactory.getLog(LimitController.class);
	
	public static Gson gson = new Gson();
	
	@Autowired
	private ILimitService limitService;

	/**
	 * 统一添加授权接口
	 * 
	 * @param request
	 * @param sys_resource_id  批量操作授权时‘,’分隔拼接为字符串
	 * @param ug_id  机构主键或用户主键
	 * @param grant_manage_type: 0全部 1编辑 2只读 3预留 
	 * @param grant_type: 0 设备 1 遥测参数 2计算结果 3 关键参数包 4 空间环境 5 预留 6预留 ; 批量操作授权时‘,’分隔拼接为字符串
	 * @param end_time  有效截止日
	 * 
	 * @return
	 */
	@RequestMapping("resourceAuthorizationAdd")
	public @ResponseBody
	String resourceAuthorizationAdd(HttpServletRequest request,HttpSession session,
			String sys_resource_id, String ug_id, int grant_manage_type,
			String grant_type, String end_time) {
		if (log.isInfoEnabled()) {
			log.info("组件[LimitController][resourceAuthorization]开始执行");
		}

		ResultBean bean = new ResultBean();
		
		if (sys_resource_id == null || ug_id == null
				|| sys_resource_id.length() == 0 || ug_id.length() == 0){
			bean.setSuccess("false");
			return gson.toJson(bean);
		}
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		
		List<LimitBean> beanList = new ArrayList<LimitBean>();
		String[] sysResourceIdArr = sys_resource_id.split(",");
		String[] grant_typeArr = grant_type.split(",");
		if(sysResourceIdArr.length != grant_typeArr.length){
			bean.setSuccess("false");
			return gson.toJson(bean);
		}
		LimitBean limitBean = null;
		for (int i = 0; i < sysResourceIdArr.length; i++) {
			if("".equals(sysResourceIdArr[i])){
				continue;
			}
			limitBean = new LimitBean();
			limitBean.setSys_resource_id(sysResourceIdArr[i]);
			limitBean.setUg_id(ug_id);
			limitBean.setGrant_type(grant_typeArr[i]);
			limitBean.setGrant_manage_type(grant_manage_type);
			limitBean.setEnd_time(end_time);
			limitBean.setCreate_user(loginUser.getUserId());
			beanList.add(limitBean);
		}
		boolean flag = limitService.resourceAuthorizationAdd(beanList);
		if (flag) {
			bean.setSuccess("true");
			bean.setMessage("数据授权成功！");
		} else {
			bean.setSuccess("false");
			bean.setMessage("数据授权失败！");
		}
		return gson.toJson(bean);
	}

	/**
	 * 统一删除授权接口
	 * 
	 * @param request
	 * @param sys_resource_id  批量操作时sys_resource_id已‘,’分隔拼接为字符串
	 * @param ug_id
	 * @return
	 */
	@RequestMapping("resourceAuthorizationDelete")
	public @ResponseBody
	String resourceAuthorizationDelete(HttpServletRequest request,HttpSession session,
			String sys_resource_id, String ug_id,String grant_type) {
		if (log.isInfoEnabled()) {
			log.info("组件[LimitController][resourceAuthorizationDelete]开始执行");
		}

		ResultBean bean = new ResultBean();
		if (sys_resource_id == null || sys_resource_id.length() == 0){
			bean.setSuccess("false");
			return gson.toJson(bean);
		}
		List<LimitBean> beanList = new ArrayList<LimitBean>();
		String[] sysResourceIdArr = sys_resource_id.split(",");
		String[] grant_typeArr = grant_type.split(",");
		if(sysResourceIdArr.length != grant_typeArr.length){
			bean.setSuccess("false");
			return gson.toJson(bean);
		}
		LimitBean limitBean = null;
		for (int i = 0; i < sysResourceIdArr.length; i++) {
			if("".equals(sysResourceIdArr[i])){
				continue;
			}
			limitBean = new LimitBean();
			limitBean.setSys_resource_id(sysResourceIdArr[i]);
			limitBean.setUg_id(ug_id);
			limitBean.setGrant_type(grant_typeArr[i]);
			beanList.add(limitBean);
		}
		boolean flag = limitService.resourceAuthorizationDelete(beanList);
		if (flag) {
			bean.setSuccess("true");
			bean.setMessage("撤销授权成功！");
		} else {
			bean.setSuccess("false");
			bean.setMessage("撤销授权失败！");
		}
		return gson.toJson(bean);
	}

	/**
	 * 获取未授权的卫星
	 * @param request
	 * @return
	 */
	@RequestMapping("satList")
	public @ResponseBody
	String satList(HttpServletRequest request,HttpSession session,CommonBean bean,
			String ug_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[LimitController][satList]开始执行");
		}

		Map<String,Object> rsMap = limitService.satList(bean,ug_id);
		return gson.toJson(rsMap);
	}
	
	/**
	 * 获取所有卫星
	 * @param request
	 * @return
	 */
	@RequestMapping("queryAlreadyGrantSat")
	public @ResponseBody
	String queryAlreadyGrantSat(HttpServletRequest request,String ug_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[LimitController][queryAlreadyGrantSat]开始执行");
		}
		Map<String,Object> rsMap = new HashMap<String, Object>();
		List<Map<String,Object>> rsList = limitService.queryAlreadyGrantSat(ug_id,0);
		rsMap.put("satList", rsList);
		return gson.toJson(rsMap);
	}
}

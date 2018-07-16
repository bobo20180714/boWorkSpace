package com.xpoplarsoft.limits.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.satInfo.service.ISatInfoService;
import com.xpoplarsoft.baseInfo.satsubalone.bean.SatSubAlone;
import com.xpoplarsoft.baseInfo.satsubalone.service.ISatSubAloneService;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.queryBylimit.bean.SatBean;
import com.xpoplarsoft.queryBylimit.bean.SatSubAloneBean;
import com.xpoplarsoft.queryBylimit.service.IQueryResourceService;
import com.xpoplarsoft.queryBylimit.service.QueryResourceService;


@Controller
@RequestMapping("/satinfoLimit")
public class SatInfoLimitController {
	
	private IQueryResourceService service = new QueryResourceService();
	
	private static Gson gson = new Gson();
	
	@Autowired
	private ISatInfoService satInfoService;
	@Autowired
	private ISatSubAloneService satSubAloneService;
	
	/**
	 * 查询航天器列表
	 * 
	 * @author 孟祥超
	 * @param request
	 * @param commBean
	 * @return
	 */
	@RequestMapping("satList")
	public @ResponseBody
	String satList(HttpServletRequest request,
			HttpSession session,String key,CommonBean commBean) {
		key = key == null?"":key;
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		if(managerAccount.equals(userAccount)){
			//查询所有
			return satInfoService.satList("-1",key,commBean);
		}else{
			// 获取当前登录用户信息
			LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
			List<SatBean> satList = service.querySatByPage(loginUser.getUserId(), key, commBean.getPage(), commBean.getPagesize());
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			Map<String,Object> satMap = null;
			for (SatBean satBean : satList) {
				satMap = new HashMap<String, Object>();
				satMap.put("sys_resource_id", satBean.getSat_id());
				satMap.put("code", satBean.getSat_code());
				satMap.put("name", satBean.getSat_name());
				satMap.put("mid", satBean.getMid());
				dataList.add(satMap);
			}
			int total = service.querySatTotal(loginUser.getUserId(), key);
			Map<String,Object> rsMap = new HashMap<String,Object>();
			rsMap.put("total", total);
			rsMap.put("Rows", dataList);
			return gson.toJson(rsMap);
		}
		
	}
	
	/**
	 * @Description: 获取航天器树
	 * @author 孟祥超
	 * @param key 关键字
	 * @throws
	 */
	@RequestMapping("findSatTree")
	public @ResponseBody String findSatTree(HttpServletRequest request,
			HttpSession session,String key){
		key = key == null?"":key;
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		if(managerAccount.equals(userAccount)){
			List<Map<String, String>> result = satSubAloneService.findSatTree(key);
			return gson.toJson(result);
		}else{
			// 获取当前登录用户信息
			LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
			List<SatSubAloneBean> result = service.findSatTree(loginUser.getUserId(),key);
			return gson.toJson(result);
		}
		
	}
	
	/**
	 * ext界面获取航天器树形数据
	 * @param request
	 * @param sys_resource_id
	 * @param key
	 * @return
	 */
	@RequestMapping("findgrantusergroupequipmenttree")
	public @ResponseBody String findGrantUserGroupEquipmentTree(HttpServletRequest request,
			HttpSession session,String sys_resource_id,String key){
		key = key == null?"":key;
		if(sys_resource_id==null) return null;
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		if(managerAccount.equals(userAccount)){
			List<SatSubAlone> result = satSubAloneService.findGrantUserGroupEquipmentTree(sys_resource_id,key);
			return JSONObject.toJSONString(result);
		}else{
			// 获取当前登录用户信息
			LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
			List<SatSubAloneBean> result = service.findGrantUserGroupEquipmentTree(loginUser.getUserId(),sys_resource_id,key);
			System.out.println("121");
			return JSONObject.toJSONString(result);
		}
	}
}

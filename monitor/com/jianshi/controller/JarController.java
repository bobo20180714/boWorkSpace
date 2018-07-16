package com.jianshi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.jianshi.service.IJarService;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;
import com.xpoplarsoft.queryBylimit.bean.SatBean;
import com.xpoplarsoft.queryBylimit.service.IQueryResourceService;
import com.xpoplarsoft.queryBylimit.service.QueryResourceService;

@Controller
@RequestMapping("/jar")
public class JarController {
	@Autowired
	private IJarService jarService;

	private IQueryResourceService service = new QueryResourceService();
	
	private static Gson gson = new Gson();
	
	@RequestMapping("registJar")
	public @ResponseBody String registJar(@RequestParam MultipartFile upfile){		
		return jarService.registJar(upfile)?"T":"F";
	}
	
	@RequestMapping("registBaseData")
	public @ResponseBody String registBaseData(@RequestParam(value="data[]") String[] data){
		return jarService.registBaseData(data)?"T":"F";	
	}
	
	@RequestMapping("getDevices")
	public @ResponseBody String getDevices(HttpSession session,String key,CommonBean commBean){
		key = key == null?"":key;
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		List<SatBean> satList = service.querySatByPage(loginUser.getUserId(), key, commBean.getPage(), commBean.getPagesize());
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String,Object> satMap = null;
		for (SatBean satBean : satList) {
			satMap = new HashMap<String, Object>();
			satMap.put("id", satBean.getSat_id());
			satMap.put("name", satBean.getSat_name());
			satMap.put("ver", "1.0");
			satMap.put("desc", "");
			dataList.add(satMap);
		}
		int total = service.querySatTotal(loginUser.getUserId(), key);
		Map<String,Object> rsMap = new HashMap<String,Object>();
		rsMap.put("total", total);
		rsMap.put("Rows", dataList);
		return gson.toJson(rsMap);
	}
	
	@RequestMapping("getSats")
	public @ResponseBody String getSats(HttpSession session,String key){		
		key = key == null?"":key;
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		if(managerAccount.equals(userAccount)){
			return gson.toJson(jarService.getSats(""));
		}
		List<SatBean> satList = service.querySatList(loginUser.getUserId(), key);
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String,Object> satMap = null;
		for (SatBean satBean : satList) {
			satMap = new HashMap<String, Object>();
			satMap.put("id", satBean.getSat_id());
			satMap.put("text", satBean.getSat_name());
			satMap.put("ver", "1.0");
			satMap.put("desc", "");
			dataList.add(satMap);
		}
		return gson.toJson(dataList);
	}
	
	@RequestMapping("getParams")
	public @ResponseBody String getParams(String jarId,CommonBean bean,String key){		
		return jarService.getParams(jarId,bean,key);
	}
	
	@RequestMapping("getJsjg")
	public @ResponseBody String getJsjg(String satId,String key){		
		return jarService.getJsjg(satId,key);
	}
	
	@RequestMapping("getJsjgField")
	public @ResponseBody String getJsjgField(String jsjgId){		
		return jarService.getJsjgField(jsjgId);
	}
	
	@RequestMapping("getCezhan")
	public @ResponseBody String getCezhan(){		
		return jarService.getCezhan();
	}
	
	@RequestMapping("getGraphId")
	public @ResponseBody String getGraphId(String devId,String dataSource,String parIds){		
		return jarService.getGraphId(devId,dataSource,parIds);
	}
	
	@RequestMapping("getParIds")
	public @ResponseBody String getParIds(String jarId){		
		return jarService.getParIds(jarId);
	}
	
	@RequestMapping("getSelectJar")
	public @ResponseBody String getSelectJar(String proId){		
		return jarService.getSelectJar(proId);
	}
	
	@RequestMapping("addProjJar")
	public @ResponseBody String addProjJar(String proId,@RequestParam(value="jarIds[]") String[] jarIds){
		return jarService.addProjJar(proId,jarIds)?"T":"F";
	}
	
	@RequestMapping("delProjJar")
	public @ResponseBody String delProjJar(String proId,String jarId){
		return jarService.delProjJar(proId,jarId)?"T":"F";
	}
	
	@RequestMapping("getSelectedJar")
	public @ResponseBody String getSelectedJar(String proId,String key){
		key = key == null?"":key;
		return jarService.getSelectedJar(proId,key);
	}
	
	@RequestMapping("delJar")
	public @ResponseBody String delJar(String id){
		return jarService.delJar(id)?"T":"F";
	}
	
	@RequestMapping("getJarGraph")
	public @ResponseBody String getJarGraph(String jarId,String plugId){
		return jarService.getJarGraph(jarId,plugId);
	}
}

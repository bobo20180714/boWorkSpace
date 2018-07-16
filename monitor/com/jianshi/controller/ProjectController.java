package com.jianshi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.jianshi.service.IProjectService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private IProjectService projectService;
	
	private static Gson gson = new Gson();
	
	@RequestMapping("addProj")
	public @ResponseBody String addProj(HttpSession session,String name,String isleaf){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return projectService.addNode(name,-1,user.getUserId(),isleaf);
	}
	
	@RequestMapping("getProj")
	public @ResponseBody String getProj(HttpSession session){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		if(managerAccount.equals(userAccount)){
			return user!=null?JSONArray.toJSONString(projectService.getAllProj()):"[]";
		}else{
			return user!=null?JSONArray.toJSONString(projectService.getProj(user.getUserId())):"[]";
		}
	}
	
	@RequestMapping("getAllProj")
	public @ResponseBody String getAllProj(HttpSession session){		
		return JSONArray.toJSONString(projectService.getAllProj());
	}

	/**
	 * 获取文件夹树
	 * @param session
	 * @return
	 */
	@RequestMapping("getFloadTree")
	public @ResponseBody String getFloadTree(HttpSession session){		
		return gson.toJson(projectService.getFloadTree());
	}
	
	//检查是否名称重复
	@RequestMapping("getNode")
	public @ResponseBody String getProj1(HttpSession session,int owner,String name){	
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return projectService.getProj(user.getUserId(),owner,name)?"T":"F";
	}
	
	@RequestMapping("addNode")
	public @ResponseBody String addNode(HttpSession session,String name,int owner,String isleaf){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return projectService.addNode(name,owner,user.getUserId(),isleaf);
	}

	@RequestMapping("addNode1")
	public @ResponseBody String addNode1(HttpSession session,String name,int owner,String isleaf,String data){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return projectService.addNode(name,owner,user.getUserId(),isleaf,data);
	}
	
	@RequestMapping("editNode")
	public @ResponseBody String editNode(String id,String name){
		return projectService.editNode(id,name)?"T":"F";
	}
	
	@RequestMapping("delNode")
	public @ResponseBody String delNode(HttpSession session,String id){
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		int owner=Integer.parseInt(id);
		boolean flag = projectService.getProj(user.getUserId(), owner);
		if(flag){
			return projectService.delNode(id)?"T":"F";
		}else{
			return "";
		}
	}
	
	@RequestMapping("addProjectLib")
	public @ResponseBody String addProjectLib(String values){		
		return projectService.addProjectLib(values)?"T":"F";
	}
	
	@RequestMapping("getProjLib")
	public @ResponseBody String getProjLib(String proId){		
		return projectService.getProjLib(proId);
	}
	
	@RequestMapping("delProjLib")
	public @ResponseBody String delProjLib(String id){		
		return projectService.delProjLib(id)?"T":"F";
	}
	@RequestMapping("addFload")
	public @ResponseBody String addFload(String name,String owner,String isleaf){
		ResultBean rb = projectService.addFload(name,owner,isleaf);
		return gson.toJson(rb);
	}
}

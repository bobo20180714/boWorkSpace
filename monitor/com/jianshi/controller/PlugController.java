package com.jianshi.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jianshi.service.IPlugService;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;

@Controller
@RequestMapping("/plug")
public class PlugController {
	
	@Autowired
	private IPlugService plugService;
	
	@RequestMapping("addPlug")
	public @ResponseBody String addPlug(HttpSession session,String name,String comment,String icon,@RequestParam MultipartFile upfile){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		String path=Common.getConfigVal("jarPath")+"IMG";
		try {
			upfile.transferTo(new File(path,icon+".png"));
			return plugService.addPlug(name,0,comment,user.getUserId(),icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("editPlug")
	public @ResponseBody String editPlug(String id,String name,String comment,String icon,@RequestParam MultipartFile upfile){		
		try {
			if(!upfile.isEmpty()){
				String path=Common.getConfigVal("jarPath")+"IMG";
				upfile.transferTo(new File(path,icon+".png"));
			}
			return plugService.editPlug(id,name,comment,icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("getPlug")
	public @ResponseBody String getPlug(HttpSession session,String key){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		return plugService.getPlug(user.getUserId(),key!=null?key:"");
	}
	
	@RequestMapping("addStatic")
	public @ResponseBody String addStatic(String name,String comment,String type,String exp,String plug_id,String img,@RequestParam MultipartFile upfile){		
		String path=Common.getConfigVal("jarPath","IMG");
		try {
			upfile.transferTo(new File(path, img+".png"));
			return plugService.addStatic(name,comment,type,exp,plug_id,img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("editStatic")
	public @ResponseBody String editStatic(String id,String name,String comment,String type,String exp,String img,@RequestParam MultipartFile upfile){		
		try {
			if(!upfile.isEmpty()){
				String path=Common.getConfigVal("jarPath","IMG");
				upfile.transferTo(new File(path, img+".png"));
			}
			return plugService.editStatic(id,name,comment,type,exp,img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("delPlug")
	public @ResponseBody String delPlug(String id,String icon){	
		new File(Common.getConfigVal("jarPath","IMG"),icon+".png").delete();
		return plugService.delPlug(id)?"T":"F";
	}
	
	@RequestMapping("delGraph")
	public @ResponseBody String delGraph(String id,String img){	
		new File(Common.getConfigVal("jarPath","IMG"),img+".png").delete();
		return plugService.delGraph(id)?"T":"F";
	}
	
	@RequestMapping("setComplete")
	public @ResponseBody String setComplete(String id){		
		return plugService.setComplete(id)?"T":"F";
	}
	
	@RequestMapping("getStatic")
	public @ResponseBody String getStatic(String plugId){		
		return plugService.getStatic(plugId);
	}
	
	@RequestMapping("getPlugByLibId")
	public @ResponseBody String getPlugByLibId(String libId){		
		return plugService.getPlugByLibId(libId);
	}
	
	@RequestMapping("getSelectPlug")
	public @ResponseBody String getSelectPlug(String libId){		
		return plugService.getSelectPlug(libId);
	}
}

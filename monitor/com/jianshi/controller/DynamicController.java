package com.jianshi.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.jianshi.service.IDynamicService;
import com.jianshi.util.Common;
import com.jianshi.util.JarTool;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;

@Controller
@RequestMapping("/dynamic")
public class DynamicController {
	
	@Autowired
	private IDynamicService dynamicService;
	
	@RequestMapping("getDynamic")
	public @ResponseBody String getDynamic(){		
		return dynamicService.getDynamic();
	}
	
	@RequestMapping("addDynamic")
	public @ResponseBody String addDynamic(HttpSession session,String name,String comment,String icon,@RequestParam MultipartFile img,@RequestParam MultipartFile jar){		
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		try {
			String path=Common.getConfigVal("jarPath","IMG");
			img.transferTo(new File(path,icon+".png"));
			String id=dynamicService.addDynamic(name,comment,icon,user.getUserId());
			if(id!=null){
				path=Common.getConfigVal("jarPath")+"JS";
				Common.mkdir(path);
				jar.transferTo(new File(path,id+".jar"));
				return "T";
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "F";
	}
	
	@RequestMapping("editDynamic")
	public @ResponseBody String editDynamic(String id,String name,String comment,String icon,@RequestParam MultipartFile img,@RequestParam MultipartFile jar){		
		try {
			if(!img.isEmpty()){
				String path=Common.getConfigVal("jarPath","IMG");
				img.transferTo(new File(path,icon+".png"));
			}
			if(dynamicService.editDynamic(id,name,comment,icon)){
				if(!jar.isEmpty()){
					String path=Common.getConfigVal("jarPath")+"JS";
					jar.transferTo(new File(path,id+".jar"));
				}
			}
			return "T";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "F";
	}
	
	@RequestMapping("delDynamic")
	public @ResponseBody String delDynamic(String id,String icon){
		if(dynamicService.delDynamic(id)){
			new File(Common.getConfigVal("jarPath","IMG"),icon+".png").delete();
			String path=Common.getConfigVal("jarPath")+"JS";
			File file=new File(path,id+".jar");
			if(file.exists())file.delete();
		}
		return dynamicService.delDynamic(id)?"T":"F";
	}
	
	@RequestMapping("readParams")
	public @ResponseBody String readParams(String id,@RequestParam MultipartFile file){
		try {
			InputStream is=file.getInputStream();
			String fileName=file.getOriginalFilename();
			return JSONArray.toJSONString(Common.parseDoc(fileName,is));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("getDebugFiles")
	public @ResponseBody String getDebugFiles(HttpSession session){
		String debug=session.getServletContext().getRealPath("debug");
		return getFiles(debug);
	}
	
	@RequestMapping("getRunFiles")
	public @ResponseBody String getRunFiles(HttpSession session,String pid){
		String src=Common.getConfigVal("jarPath")+"JS/"+pid+".jar";
		String run=session.getServletContext().getRealPath("run/"+pid).replace("\\", "/");		
		try {
			JarTool.decompress(src,run);
			return getFiles(run);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("exportJar")
	public @ResponseBody String exportJar(HttpSession session){
		String webRoot=session.getServletContext().getRealPath("/").replace("\\", "/");
		Common.deldir(webRoot+"/tmp");
		try {
			JarTool.generateJar(webRoot+"debug/",webRoot+"tmp/myjs.jar");
			//Process process = Runtime.getRuntime().exec("java -jar \""+webRoot+"tool/jartool.jar"+"\" \""+webRoot+"debug/\" \""+webRoot+"tmp/myjs.jar\"");
			//process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return "F";
		}
		return "T";
	}
	
	public static String getFiles(String path) {
		File[] files = new File(path+"/css").listFiles();
		List<String> css=new ArrayList<String>();
		for (File file : files) {
			if(!file.isDirectory()){
				String name=file.getName();
				if(name.endsWith(".css"))
					css.add(name);
			}
		}
		files = new File(path+"/js").listFiles();
		List<String> js=new ArrayList<String>();
		for (File file : files) {
			if(!file.isDirectory()){
				String name=file.getName();
				if(name.endsWith(".js"))
					js.add(name);
			}
		}
		return "{css:"+JSONArray.toJSONString(css)+",js:"+JSONArray.toJSONString(js)+"}";
	}
}

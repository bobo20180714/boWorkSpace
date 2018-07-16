package com.jianshi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.jianshi.model.FilesData;
import com.jianshi.service.IFileService;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;

@Controller
@RequestMapping("/file")
public class fileController {
	@Autowired
	private IFileService fileService;
	
	private Gson gson = new Gson();
	
	@RequestMapping("getLastFile")
	public @ResponseBody String getLastFile(String proId){
		return fileService.getLastFile(proId);
	}
	
	@RequestMapping("addFile")
	public @ResponseBody String addFile(HttpSession session,String proId,
			String name,String data,FilesData files,String[] dels){
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		String path=Common.getConfigVal("jarPath","IMG");
		Map<String, MultipartFile> files1=files.getFiles();
		if(files1!=null){
			for(String file : files1.keySet()){
				try {
					files1.get(file).transferTo(new File(path,file.lastIndexOf(".svg")>-1?file:file+".png"));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(dels!=null){
			for (String del : dels) {
				new File(path,del+".png").delete();
			}
		}
		return fileService.addFile(proId,name,data,user.getUserId());
	}
	
	@RequestMapping("copyFile")
	public @ResponseBody String copyFile(HttpSession session,String proId
			,String name,String data,FilesData files,String[] dels,String srcProjId){
		if(addFile(session, proId, name, data, files, dels).equals("T")){
			if(fileService.addLibJar(proId,srcProjId))
				return "T";
		}
		return "F";
	}
	
	@RequestMapping("getFileByProId")
	public @ResponseBody String getFileByProId(String proId){
		return fileService.getFileByProId(proId);
	}
	
	@RequestMapping("getFileById")
	public @ResponseBody String getFileById(String id){
		return fileService.getFileById(id);
	}
	
	@RequestMapping("delFile")
	public @ResponseBody String delFile(String id){
		return fileService.delFile(id)?"T":"F";
	}
	
	@RequestMapping("upload")
	public @ResponseBody String upload(HttpSession session,@RequestParam MultipartFile upfile){
		String path=session.getServletContext().getRealPath("dat/plug");
		String fileName=UUID.randomUUID().toString()+".png";
		File file = new File(path,fileName); 
		try {
			upfile.transferTo(file);
			session.setAttribute("fileName", fileName);
			//int id=Integer.parseInt(session.getAttribute("id").toString());
			//user.addFilename(id, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:../page/home.jsp";	
	}
	
	@RequestMapping("getImg")
	public void getImg(HttpServletResponse response,String id){
		OutputStream out;
		FileInputStream in;
		try {			
			String path=Common.getConfigVal("jarPath","IMG",id+".png");
			in = new FileInputStream(path);
			response.setContentType("image/png");
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			for (int i; (i = in.read(buf)) > 0;) {
				out.write(buf, 0, i);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getSvg")
	public @ResponseBody String getSvg(HttpSession session,String id){
		String src=Common.getConfigVal("jarPath","IMG",id);
		String target=session.getServletContext().getRealPath("svg")+"/"+id;
		Common.copy(src, target);
		return "T";
	}
	
	/**
	 * 根据文件夹ID，查询其下的文件夹及文件
	 * @param session
	 * @param bean
	 * @param key
	 * @param ownerId  卫星或文件夹ID
	 * @param proId  文件夹ID
	 * @return
	 */
	@RequestMapping("getFileListByFload")
	public @ResponseBody String getFileListByFload(HttpSession session,String key,String ownerId,String proId){
		
		Map<String,Object> fileMap = fileService.getFileListByFload(key,ownerId,proId);
		
		return gson.toJson(fileMap);
	}
	
	
	@RequestMapping("getAllFileList")
	public @ResponseBody String getAllFileList(HttpSession session,CommonBean bean,String key){
		
		Map<String,Object> fileMap = fileService.getAllFileList(bean,key);
		
		return gson.toJson(fileMap);
	}
}

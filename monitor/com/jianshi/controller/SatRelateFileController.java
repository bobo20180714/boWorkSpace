package com.jianshi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jianshi.model.PagTreeConfBean;
import com.jianshi.service.ISatRelateFileService;
import com.jianshi.util.Common;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;

@Controller
@RequestMapping("/SatRelateFile")
public class SatRelateFileController {

	private Gson gson = new Gson();
	
	@Autowired
	private ISatRelateFileService satRelateFileService;
	
	@RequestMapping("queryMonitorTree")
	public @ResponseBody String queryMonitorTree(HttpSession session,String ownerId){	
		LoginUserBean user=(LoginUserBean)session.getAttribute("LoginUser");
		String userAccount = session.getAttribute("userAccount").toString();
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		managerAccount = managerAccount == null?"admin":managerAccount;
		int isAdmin = 1;
		if(managerAccount.equals(userAccount)){
			isAdmin = 0;
		}
		List<Map<String,String>> treeData = satRelateFileService.queryMonitorTree(ownerId,user.getUserId(),isAdmin);
		return gson.toJson(treeData);
	}
	
	/**
	 * 查询文件树结构，
	 * 	已经关联的节点，设置 map.put("ischecked", "true");
	 * @param session
	 * @param ownerId
	 * @return
	 */
	@RequestMapping("queryFileTree")
	public @ResponseBody String queryFileTree(HttpSession session,String ownerId){	
		System.out.println(ownerId);
		List<Map<String,String>> treeData = satRelateFileService.queryFileTree(ownerId);
		return gson.toJson(treeData);
	}

	@RequestMapping("queryRelateFileList")
	public @ResponseBody String queryRelateFileList(HttpSession session,String ownerId,String ownerType,CommonBean bean){	
		Map<String,Object> rsData = satRelateFileService.queryRelateFileList(ownerId,ownerType,bean);
		return gson.toJson(rsData);
	}
	
	@RequestMapping("addSatFileRelate")
	public @ResponseBody String addSatFileRelate(HttpSession session,String ownerId,
			String fileListStr){		
		
		JSONArray jArr = JSONArray.parseArray(fileListStr);
		
		ResultBean rb = new ResultBean();
		
		int isroot = 1;//不是根节点
		
		List<PagTreeConfBean> beanList = new ArrayList<PagTreeConfBean>();
		PagTreeConfBean bean = null;
		for (int j = 0;jArr != null && j < jArr.size(); j++) {
			JSONObject jo = jArr.getJSONObject(j);
			
			//判断是否已经关联
			if(satRelateFileService.judgeHaveRelate(ownerId,jo.getString("obj_id"))){
				continue;
			}
			
			bean = new PagTreeConfBean();
			String id=Common.getId("SAT_TM_ID_SEQ");
			bean.setPk_id(id);
			bean.setSuper_id(ownerId);
			bean.setType(Integer.parseInt(jo.getString("type")));
			bean.setIsroot(isroot);
			bean.setIsleaf(Integer.parseInt(jo.getString("isleaf")));
			bean.setPage_name(jo.getString("page_name"));
			bean.setPage_url(jo.getString("page_url"));
			bean.setOpen_mode(jo.get("open_mode")==null?1:Integer.parseInt(jo.getString("open_mode")));
			bean.setIsalias(jo.get("isalias")==null?1:Integer.parseInt(jo.getString("isalias")));
			bean.setObj_id(jo.getString("obj_id"));
			beanList.add(bean);
		}
		
		if(beanList.size() == 0){
			rb.setSuccess("true_s");
			return gson.toJson(rb);
		}
		
		boolean flag = satRelateFileService.addSatFileRelate(beanList);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("关联成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("关联失败！");
		}
		return gson.toJson(rb);
	}

	@RequestMapping("addSingleSatFileRelate")
	public @ResponseBody String addSingleSatFileRelate(HttpSession session,String satId,
			String fileId){		
		
		ResultBean rb = new ResultBean();
		
		//查询ownerId
		Map<String,Object> dataMap = satRelateFileService.getOwnerIdBySatId(satId);
		
		String ownerId = dataMap.get("pk_id").toString();
		
		boolean flag1 = satRelateFileService.judgeHaveRelate(ownerId,fileId);
		if(flag1){
			rb.setSuccess("true");
			rb.setMessage("已经关联该卫星！");
			return gson.toJson(rb);
		}
		
		int isroot = 1;//不是根节点
		int isleaf = 0;//叶子节点
		int open_mode = 1;
		int isalias = 1;
		int type = 5;
		
		List<PagTreeConfBean> beanList = new ArrayList<PagTreeConfBean>();
		PagTreeConfBean bean = new PagTreeConfBean();
		String id=Common.getId("SAT_TM_ID_SEQ");
		bean.setPk_id(id);
		bean.setSuper_id(ownerId);
		bean.setType(type);
		bean.setIsroot(isroot);
		bean.setIsleaf(isleaf);
		bean.setOpen_mode(open_mode);
		bean.setIsalias(isalias);
		bean.setObj_id(fileId);
		beanList.add(bean);
		boolean flag = satRelateFileService.addSatFileRelate(beanList);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("关联成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("关联失败！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 删除关联关系
	 * @param session
	 * @param ownerId
	 * @param fileIds
	 * @return
	 */
	@RequestMapping("deleteRelate")
	public @ResponseBody String deleteRelate(HttpSession session,String ownerId,
			String fileIds){		
		
		String[] fileIdArr = fileIds.split(";");
		
		ResultBean rb = new ResultBean();
		
		boolean flag = satRelateFileService.deleteRelate(ownerId,fileIdArr);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("删除关联成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("删除关联失败！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 移除多个关联关系
	 * @param session
	 * @param ownerId
	 * @param fileIds
	 * @return
	 */
	@RequestMapping("removeAllSatFileRelate")
	public @ResponseBody String removeAllSatFileRelate(HttpSession session,String ownerId,
			String fileListStr){		
		
		String[] fileIdArr = fileListStr.split(";");
		
		ResultBean rb = new ResultBean();
		
		boolean flag = satRelateFileService.deleteRelate(ownerId,fileIdArr);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("删除关联成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("删除关联失败！");
		}
		return gson.toJson(rb);
	}
	
	/**
	 * 新增自定义文件
	 * @param session
	 * @param page_name
	 * @param page_url
	 * @param open_mode
	 * @return
	 */
	@RequestMapping("addUserFile")
	public @ResponseBody String addUserFile(HttpSession session,String ownerId,String page_name,
			String page_url,String open_mode){		
		
		ResultBean rb = new ResultBean();

		List<PagTreeConfBean> beanList = new ArrayList<PagTreeConfBean>();
		PagTreeConfBean bean = new PagTreeConfBean();
		String id=Common.getId("SAT_TM_ID_SEQ");
		bean.setPk_id(id);
		bean.setSuper_id(ownerId);
		bean.setType(3);
		bean.setIsroot(1);
		bean.setIsleaf(0);
		bean.setPage_name(page_name);
		bean.setPage_url(page_url);
		bean.setOpen_mode(Integer.parseInt(open_mode));
		bean.setIsalias(1);
		bean.setObj_id(id);
		beanList.add(bean);
		boolean flag = satRelateFileService.addSatFileRelate(beanList);
		if(flag){
			rb.setData(id);
			rb.setSuccess("true");
			rb.setMessage("新增自定义文件成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("新增自定义文件失败！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("updateUserFile")
	public @ResponseBody String updateUserFile(HttpSession session,String pkId,String page_name,
			String page_url,String open_mode){		
		
		ResultBean rb = new ResultBean();

		boolean flag = satRelateFileService.updateUserFile(pkId,page_name,page_url,open_mode);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("修改自定义文件成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("修改自定义文件失败！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("deleteUserFile")
	public @ResponseBody String deleteUserFile(HttpSession session,String pkId){		
		
		ResultBean rb = new ResultBean();

		boolean flag = satRelateFileService.deleteUserFile(pkId);
		if(flag){
			rb.setSuccess("true");
			rb.setMessage("删除自定义文件成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("删除自定义文件失败！");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("queryUserFile")
	public @ResponseBody String queryUserFile(HttpSession session,String pkId){		
		
		Map<String,Object> flag = satRelateFileService.queryUserFile(pkId);
		return gson.toJson(flag);
	}
	
	/**
	 * 查询卫星及其下文件夹
	 * @param session
	 * @return
	 */
	@RequestMapping("queryFloadTree")
	public @ResponseBody String queryFloadTree(HttpSession session){		
		
		List<Map<String,Object>> floadTree = satRelateFileService.queryFloadTree();
		return gson.toJson(floadTree);
	}
	

	@RequestMapping("judgeHaveRelateBySat")
	public @ResponseBody String judgeHaveRelateBySat(HttpSession session,String satId,
			String fileId){		


		//查询ownerId
		Map<String,Object> dataMap = satRelateFileService.getOwnerIdBySatId(satId);
		
		String ownerId = dataMap.get("pk_id").toString();
		
		ResultBean rb = new ResultBean();
		boolean flag = satRelateFileService.judgeHaveRelate(ownerId,fileId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
	@RequestMapping("judgeHaveRelate")
	public @ResponseBody String judgeHaveRelate(HttpSession session,String ownerId,
			String fileId){		

		ResultBean rb = new ResultBean();
		boolean flag = satRelateFileService.judgeHaveRelate(ownerId,fileId);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return gson.toJson(rb);
	}
	
}

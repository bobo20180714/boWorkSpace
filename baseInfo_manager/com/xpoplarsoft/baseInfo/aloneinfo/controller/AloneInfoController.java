package com.xpoplarsoft.baseInfo.aloneinfo.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.aloneinfo.bean.StandAloneInfo;
import com.xpoplarsoft.baseInfo.aloneinfo.service.IAloneInfoService;
import com.xpoplarsoft.framework.bean.ResultBean;

@Controller
@RequestMapping("/aloneinfo")
public class AloneInfoController{
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(AloneInfoController.class);
	@Autowired
	private IAloneInfoService aloneInfoService;
	
	private Gson gson = new Gson();
	
	/**
	 * @Title: standAloneInfoAdd
	 * @Description: 单机信息新增
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("standaloneinfoadd")
	public @ResponseBody String standAloneInfoAdd(HttpServletRequest request,/*StandAloneInfo standAloneInfo,*/String sub_system_id){
		if (log.isInfoEnabled()) {
			log.info("组件[AloneInfoController][standAloneInfoAdd]开始执行");
		}
//		UserInfoView userInfoView=(UserInfoView) request.getSession().getAttribute("LoginUser");
//		standAloneInfo.setCreate_user(userInfoView.getUser_id());
		String stand_alone_name=(request.getParameter("stand_alone_name"));
		String stand_alone_code=(request.getParameter("stand_alone_code"));
		ResultBean rb = new ResultBean();
		//判断分系统编号是否存在
		if(aloneInfoService.judgeCodeExit(sub_system_id,stand_alone_code,null)){
			rb.setSuccess("false");
			rb.setMessage("单机编号已经存在！");
			return gson.toJson(rb);
		}
		//判断分系统名称是否已存在
		if(aloneInfoService.judgeNameExit(sub_system_id,stand_alone_name,null)){
			rb.setSuccess("false");
			rb.setMessage("单机名称已经存在！");
			return gson.toJson(rb);
		}
		String result = aloneInfoService.standAloneInfoAdd(stand_alone_name,stand_alone_code,sub_system_id);
		return result;
//		Boolean flag=aloneInfoService.standAloneInfoAdd(stand_alone_name,stand_alone_code,sub_system_id);
//		if(flag){
//			return "{success:true,message:'添加成功'}";
//		}else{
//			return "{success:false,message:'添加失败'}";
//		}
	}
	/**
	 * @Title: standAloneInfoById
	 * @Description: 根据id获取单机信息
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("standaloneinfobyid")
	public @ResponseBody String standAloneInfoById(HttpServletRequest request,String stand_alone_id){
		String json = "";
		if (log.isInfoEnabled()) {
			log.info("组件[AloneInfoController][standAloneInfoById]开始执行");
		}
		StandAloneInfo standAloneInfo = aloneInfoService.standAloneInfoById(stand_alone_id);
		json = JSONObject.toJSONString(standAloneInfo);
		return json;
//		return "{success:true,message:'查询成功'}";
	}
	/**
	 * @Title: standAloneInfoUpdate
	 * @Description: 单机信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("standaloneinfoupdate")
	public @ResponseBody String standAloneInfoUpdate(HttpServletRequest request,StandAloneInfo standAloneInfo,String sub_system_id){
		if (log.isInfoEnabled()) {
			log.info("组件[AloneInfoController][standAloneInfoUpdate]开始执行");
		}
		ResultBean rb = new ResultBean();
		/*//判断分系统编号是否存在
		if(aloneInfoService.judgeCodeExit(sub_system_id,standAloneInfo.getStand_alone_code(),
				String.valueOf(standAloneInfo.getStand_alone_id()))){
			rb.setSuccess("false");
			rb.setMessage("分系统编号已经存在！");
			return gson.toJson(rb);
		}*/
		//判断分系统名称是否已存在
		if(aloneInfoService.judgeNameExit(sub_system_id,standAloneInfo.getStand_alone_name(),
				String.valueOf(standAloneInfo.getStand_alone_id()))){
			rb.setSuccess("false");
			rb.setMessage("单机名称已经存在！");
			return gson.toJson(rb);
		}
		Boolean flag = aloneInfoService.standAloneInfoUpdate(standAloneInfo);
		if(flag){
			return "{success:'true',message:'修改成功'}";
		}else{
			return "{success:'false',message:'修改失败'}";
		}
	}
	/**
	 * @Title: standAloneInfoDeleteById
	 * @Description: 单机信息删除（实质：修改状态）
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("standaloneinfodeletebyid")
	public @ResponseBody String standAloneInfoDeleteById(HttpServletRequest request,String stand_alone_id){
		if (log.isInfoEnabled()) {
			log.info("组件[AloneInfoController][standAloneInfoDeleteById]开始执行");
		}
		Boolean flag=aloneInfoService.standAloneInfoDeleteById(stand_alone_id);
		if(flag){
			return "{success:'true',message:'移除成功'}";
		}else{
			return "{success:'false',message:'移除失败'}";
		}
	}
}

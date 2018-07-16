package com.xpoplarsoft.baseInfo.subinfo.controller;

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
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.subinfo.bean.SubSystemInfo;
import com.xpoplarsoft.baseInfo.subinfo.service.ISubInfoService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.query.logger.LoggerFactory;
import com.xpoplarsoft.query.logger.bean.OperateLogBean;

@Controller
@RequestMapping("/subinfo")
public class SubInfoController{
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(SubInfoController.class);
	@Autowired
	private ISubInfoService subInfoService;
	
	private Gson gson = new Gson();
	
	/**
	 * @Title: subSystemInfoAdd
	 * @Description: 分系统信息新增
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("subsysteminfoadd")
	public @ResponseBody String subSystemInfoAdd(HttpServletRequest request,/*SubSystemInfo subSystemInfo,*/String sat_id){
		if (log.isInfoEnabled()) {
			log.info("组件[SubInfoController][subSystemInfoAdd]开始执行");
		}
		SubSystemInfo subSystemInfo = new SubSystemInfo();
		subSystemInfo.setSub_system_name(request.getParameter("sub_system_name"));
		subSystemInfo.setSub_system_code(request.getParameter("sub_system_code"));
		
		ResultBean rb = new ResultBean();
		//判断分系统编号是否存在
		if(subInfoService.judgeCodeExit(sat_id,subSystemInfo.getSub_system_code(),null)){
			rb.setSuccess("false");
			rb.setMessage("分系统编号已经存在！");
			return gson.toJson(rb);
		}
		//判断分系统名称是否已存在
		if(subInfoService.judgeNameExit(sat_id,subSystemInfo.getSub_system_name(),null)){
			rb.setSuccess("false");
			rb.setMessage("分系统名称已经存在！");
			return gson.toJson(rb);
		}
	String result=subInfoService.subSystemInfoAdd(subSystemInfo,sat_id);
	return result;
	}
	
	/**
	 * @Title: subSystemInfoById
	 * @Description: 根据id获取分系统信息
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("subsysteminfobyid")
	public @ResponseBody String subSystemInfoById(HttpServletRequest request,String sub_system_id){
		if (log.isInfoEnabled()) {
			log.info("组件[SubInfoController][subSystemInfoById]开始执行");
		}
		String json = "";
		SubSystemInfo subSystemInfo = subInfoService.subSystemInfoById(sub_system_id);
		json = JSONObject.toJSONString(subSystemInfo);
		return json;
//		return "{success:true,message:'查询成功',reData:"+ json +"}";
	}
	
	/**
	 * @Title: subSystemInfoUpdate
	 * @Description: 分系统信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("subsysteminfoupdate")
	public @ResponseBody String subSystemInfoUpdate(HttpServletRequest request,SubSystemInfo subSystemInfo,String sat_id){
		if (log.isInfoEnabled()) {
			log.info("组件[SubInfoController][subSystemInfoUpdate]开始执行");
		}
		ResultBean rb = new ResultBean();
		//判断分系统编号是否存在
		if(subInfoService.judgeCodeExit(sat_id,subSystemInfo.getSub_system_code(),
				String.valueOf(subSystemInfo.getSub_system_id()))){
			rb.setSuccess("false");
			rb.setMessage("分系统编号已经存在！");
			return gson.toJson(rb);
		}
		//判断分系统名称是否已存在
		if(subInfoService.judgeNameExit(sat_id,subSystemInfo.getSub_system_name(),
				String.valueOf(subSystemInfo.getSub_system_id()))){
			rb.setSuccess("false");
			rb.setMessage("分系统名称已经存在！");
			return gson.toJson(rb);
		}
		return subInfoService.subSystemInfoUpdate(subSystemInfo);
//		if(row>0){
//			return "{success:true,message:'修改成功'}";
//		}else{
//			return "{success:false,message:'修改失败'}";
//		}
	}
	/**
	 * @Title: subSystemInfoDeleteById
	 * @Description: 分系统信息删除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("subsysteminfodeletebyid")
	public @ResponseBody String subSystemInfoDeleteById(HttpServletRequest request,String sub_system_id){
		if (log.isInfoEnabled()) {
			log.info("组件[SubInfoController][subSystemInfoDeleteById]开始执行");
		}
		
		//判断其是否有单机 孟祥超 add
		List<Map<String,Object>> listChild = subInfoService.queryChildInfo(sub_system_id);
		if(listChild != null && listChild.size() > 0){
			return "{success:'false',message:'该分系统有单机,无法删除！'}";
		}
		
		Boolean flag=subInfoService.subSystemInfoDeleteById(sub_system_id);
		if(flag){
			return "{success:'true',message:'删除成功'}";
		}else{
			return "{success:'false',message:'删除失败'}";
		}
	}
	
}

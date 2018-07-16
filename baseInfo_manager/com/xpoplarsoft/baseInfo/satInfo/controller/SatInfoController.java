package com.xpoplarsoft.baseInfo.satInfo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.baseInfo.satInfo.service.ISatInfoService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.parameter.SystemParameter;

@Controller
@RequestMapping("/satinfo")
public class SatInfoController {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(SatInfoController.class);
	@Autowired
	private ISatInfoService satInfoService;
	

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
	String satList(HttpServletRequest request,String key,CommonBean commBean) {

		String rs = satInfoService.satList("-1",key,commBean);
		
		return rs;
	}

	/**
	 * @Title: findSatBasicInfoQueryPage
	 * @Description: 飞行器信息列表（分页）
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findsatbasicinfoquerypage")
	public @ResponseBody
	String findSatBasicInfoQueryPage(HttpSession session,HttpServletRequest request, String page,
			SatInfoDetail satInfoDetail) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][findsatbasicinfoquerypage]开始执行");
		}
		String sat_name = request.getParameter("sat_name");
		String design_org = request.getParameter("design_org");
		String user_org = request.getParameter("user_org");
		String design_life = request.getParameter("design_life");
		String pageSize = request.getParameter("pagesize");
		String pageIndex = request.getParameter("page");
		String over_life = request.getParameter("over_life");
		String multicast_address = request.getParameter("multicast_address");
		String udp_port = request.getParameter("udp_port");
		String launch_time_start = request.getParameter("launch_time_start");
		String launch_time_end = request.getParameter("launch_time_end");
		String sat_id = request.getParameter("sat_id");

		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		String userAccount = session.getAttribute("userAccount").toString();
		
		//超级管理员账号
		String managerAccount = SystemParameter.getInstance().getParameter("systemAccount");
		if(managerAccount.equals(userAccount)){
			//超级管理员satInfoService
			return satInfoService.findSatBasicInfoQueryPage(sat_name,design_org,user_org, design_life, pageSize, pageIndex, over_life, multicast_address, udp_port, launch_time_start,launch_time_end, sat_id );
		}else{
			return satInfoService.findSatBasicInfoQueryPageLimit(loginUser.getUserId(),sat_name,design_org,user_org,pageSize, pageIndex,launch_time_start,launch_time_end);
		}
	}

	
	
	@RequestMapping("getSatBasicInfoById")
	public @ResponseBody
	String getDeviceParamById(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][getSatBasicInfoById]开始执行");
		}
		String ids = request.getParameter("sat_id");
		SatInfoDetail result = satInfoService.getSatBasicInfoById(ids);
		return new Gson().toJson(result);
	}

	/**
	 * @Title: satBasicInfoUpdate
	 * @Description: 飞行器信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("satbasicinfoupdate")
	public @ResponseBody
	String satBasicInfoUpdate(HttpServletRequest request,
			SatInfoDetail satInfoDetail) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][satBasicInfoUpdate]开始执行");
		}
		String result = satInfoService.satBasicInfoUpdate(satInfoDetail);

		return result;
	}
	
	/**
	 * 新增卫星
	 * @param request
	 * @param satInfoDetail
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	String add(HttpServletRequest request,SatInfoDetail satInfoDetail) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][add]开始执行");
		}
		ResultBean rb = new ResultBean();
		boolean result = satInfoService.add(satInfoDetail);
		if(result){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
	}

	/**
	 * @Title: satBasicInfoDelete
	 * @Description: 飞行器信息删除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("satbasicinfodelete")
	public @ResponseBody
	String satBasicInfoDelete(HttpServletRequest request, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][satBasicInfoDelete]开始执行");
		}
		ResultBean rb = new ResultBean();
		boolean result = satInfoService.satBasicInfoDelete(ids);
		if(result){
			rb.setSuccess("true");
			rb.setMessage("删除成功！");
		}else{
			rb.setSuccess("false");
			rb.setMessage("删除失败！");
		}
		return new Gson().toJson(rb);
	}
	/**
	 * 判断卫星编号是否重复
	 * @param request
	 * @param satCode
	 * @return
	 */
	@RequestMapping("judgeSatCode")
	public @ResponseBody
	String judgeSatCode(HttpServletRequest request, String satCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][judgeSatCode]开始执行");
		}
		ResultBean rb = new ResultBean();
		Map<String,Object> result = satInfoService.querySatByCode(satCode);
		if(result.size() != 0){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
	}
	/**
	 * 判断任务代号是否重复
	 * @param request
	 * @param mid
	 * @return
	 */
	@RequestMapping("judgeMid")
	public @ResponseBody
	String judgeMid(HttpServletRequest request, String mid) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][judgeMid]开始执行");
		}
		ResultBean rb = new ResultBean();
		Map<String,Object> result = satInfoService.querySatByMid(mid);
		if(result.size() != 0){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
	}

}

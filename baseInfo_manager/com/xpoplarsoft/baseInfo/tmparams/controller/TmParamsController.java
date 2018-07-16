package com.xpoplarsoft.baseInfo.tmparams.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.common.FileUploadUtil;
import com.xpoplarsoft.baseInfo.tmparams.bean.SatSubAloneParam;
import com.xpoplarsoft.baseInfo.tmparams.bean.TmParamView;
import com.xpoplarsoft.baseInfo.tmparams.service.ITmParamsService;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.startup.FrameStartup;

@Controller
@RequestMapping("/tmparams")
public class TmParamsController{
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(TmParamsController.class);
	
	@Autowired
	private ITmParamsService tmParamsService;
	
	/**
	 * @Description: 获取遥测参数
	 * @author 孟祥超
	 * @date 2015.08.06
	 */
	@RequestMapping("queryParamsInfo")
	public @ResponseBody String queryParamsInfo(HttpServletRequest request,CommonBean commonBean){
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][queryParamsInfo]开始执行");
		}
		String json = "";
		String sat_id = request.getParameter("sat_id");
		//关键字
		String param_code = request.getParameter("param_code");
		Map<String,Object> paramsInfo = tmParamsService.queryParamsInfo(sat_id,param_code,commonBean);
		//返回结果
		json = JSONObject.toJSONString(paramsInfo);
		log.debug("result:["+json+"]");
		return json;
	}
		
	/**
	 * @Title: findGrantUserGroupTmParamQueryPage
	 * @Description: 根据主键获取参数
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findgrantusergrouptmparamquerypage")
	public @ResponseBody
	String findGrantUserGroupTmParamQueryPage(HttpServletRequest request, String page, String id, String key, String pagesize) {
		if (log.isInfoEnabled()) {
			log
					.info("组件[TmParamsController][findGrantUserGroupTmParamQueryPage]开始执行");
		}
		String json = "";
		String sat_id = request.getParameter("id");
		//关键字
		String param_code = request.getParameter("key");
		
		String limit = request.getParameter("limit")==null?"50":request.getParameter("limit");
		
		CommonBean commonBean = new CommonBean();
		commonBean.setPage(Integer.parseInt(page));
		commonBean.setPagesize(Integer.parseInt(limit));
		Map<String,Object> paramsInfo = tmParamsService.queryParamsInfo(sat_id,param_code,commonBean);
		//返回结果
		json = JSONObject.toJSONString(paramsInfo);
		log.debug("result:["+json+"]");
		return json;
	}
	
	@RequestMapping("findgrantusergrouptmparambyid")
	public @ResponseBody
	String findGrantUserGroupTmParamById(HttpServletRequest resquest, String sat_id,String page, String pagesize) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][findgrantusergrouptmparambyid]开始执行");
		}
		DBParameter param = new DBParameter();
		param.setObject("sat_id", sat_id);
		param.setObject("page", page);
		param.setObject("pageSize", pagesize);
		param.setObject("tm_param_id", resquest.getParameter("tm_param_id"));
		param.setObject("tm_param_name", resquest.getParameter("tm_param_name"));
		param.setObject("tm_param_code", resquest.getParameter("tm_param_code"));
		param.setObject("tm_param_type", resquest.getParameter("tm_param_type"));
		//是否根据关键字查询（true,false）
		param.setObject("querybykey", resquest.getParameter("querybykey")==null?"":resquest.getParameter("querybykey"));
		return tmParamsService.findGrantUserGroupTmParamById(param);
	}
	
	
	
	@RequestMapping("gettmparamsinfobyid")
	public @ResponseBody
	String getTmParamsInfoById(HttpServletRequest resquest, String tm_param_id,HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log
					.info("组件[TmParamsController][getTmParamsInfoById]开始执行");
		}
		DBParameter param = new DBParameter();
		param.setObject("tm_param_id", tm_param_id);
		return tmParamsService.getTmParamsInfoById(param);
	}
	/**
	 * 参数信息新增
	 * @param request
	 * @param tmParamView
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody
	String add(HttpServletRequest request, TmParamView tmParamView) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][add]开始执行");
		}
		ResultBean rb = new ResultBean();
		boolean result = tmParamsService.add(tmParamView);
		if(result){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
		
	}
	/**
	 * @Title: tmParamUpdate
	 * @Description: 参数信息修改
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("tmparamupdate")
	public @ResponseBody
	String tmParamUpdate(HttpServletRequest request, TmParamView tmParamView) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][tmParamUpdate]开始执行");
		}
		DBParameter param = new DBParameter();
		param.setObject("tm_param_name",tmParamView.getTm_param_name());
		param.setObject("tm_param_id",tmParamView.getTm_param_id());
		param.setObject("tm_param_code",tmParamView.getTm_param_code());
		param.setObject("tm_param_bdh",tmParamView.getTm_param_bdh());
		param.setObject("tm_param_num",tmParamView.getTm_param_num());
		param.setObject("tm_param_type",tmParamView.getTm_param_type());
		
		return tmParamsService.tmParamUpdate(param);

	}
	/**
	 * @Title: tmParamDelete
	 * @Description: 参数信息删除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("tmparamdelete")
	public @ResponseBody
	String tmParamDelete(HttpServletRequest request, String ids) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][tmParamDelete]开始执行");
		}
		return tmParamsService.tmParamDelete(ids);
		
	}
	/**
	 * @Title: findSubNoParamBySubQueryPage
	 * @Description: 分系统获取未拥有的参数
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findsubnoparambysubquerypage")
	public @ResponseBody
	String findSubNoParamBySubQueryPage(HttpServletRequest request,int page, String id, 
			int pagesize, HttpServletResponse response
			) {
		if (log.isInfoEnabled()) {
			log
					.info("组件[TmParamsController][findSubNoParamBySubQueryPage]开始执行");
		}
		String owner_id = request.getParameter("owner_id");
		String key=request.getParameter("key");
		String json = "";
		if (id == null)
			return null;
		Map<String,Object> result = tmParamsService
				.findSubNoParamBySubQueryPage(page, id, key, pagesize,owner_id);
		json = JSONObject.toJSONString(result);
		return json;
	}
	
	/**
	 * @Title: findStandNoParamByStandQueryPage
	 * @Description: 单机获取未拥有的参数
	 * @author jingkewen
	 * @throws
	 */
/*	@SuppressWarnings( { "unchecked", "rawtypes" })
	@RequestMapping("findstandnoparambystandquerypage")
	public @ResponseBody
	String findStandNoParamByStandQueryPage(HttpServletRequest request,
			String page, String id ,HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][findStandNoParamByStand]开始执行");
		}
		String json = "";
		if (id == null)
			return null;
		String key;
		List<SatSubAloneParam> result = tmParamsService
				.findStandNoParamByStandQueryPage(page, id, key);
		json = JSONObject.toJSONString(result);
		return json;
	}*/
	/**
	 * @Title: findSubHasParamBySubQueryPage
	 * @Description: 分系统获取已拥有的参数
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("findsubhasparambysubquerypage")
	public @ResponseBody
	
	/*String findSubHasParamBySubQueryPage(HttpServletRequest request,
			String page, String id, String key) {
		if (log.isInfoEnabled()) {
			log
					.info("组件[TmParamsController][findSubHasParamBySubQueryPage]开始执行");
		}
		String json = "";
		if (id == null)
			return null;
		Map condition = new HashMap();
		List<SatSubAloneParam> result = tmParamsService
				.findSubHasParamBySubQueryPage(page,id,key);
//		page.setRecords(result);
		json = JSONObject.toJSONString(result);
		return json;
	}*/
	String findSubHasParamBySubQueryPage(HttpServletRequest request,String page, String id, 
			String pagesize, HttpServletResponse response
			) {
		if (log.isInfoEnabled()) {
			log
					.info("组件[TmParamsController][findSubHasParamBySubQueryPage]开始执行");
		}
		String key=request.getParameter("key1");
		if (id == null)
			return null;
		String total = tmParamsService.getSubHasParamCount(page, id, key);
		int cou = Integer.parseInt(total);
		List<SatSubAloneParam> result = tmParamsService
				.findSubHasParamBySubQueryPage(page, id, key, pagesize);
		
		Map<String,Object> rsMap = new HashMap<String, Object>();
		rsMap.put("Rows", result);
		rsMap.put("Total", cou);
		return new Gson().toJson(rsMap);
	}
	/**
	 * @Title: findStandHasParamByStandQueryPage
	 * @Description: 单机获取已拥有的参数
	 * @author jingkewen
	 * @throws
	 */
/*	@SuppressWarnings( { "rawtypes", "unchecked" })
	@RequestMapping("findstandhasparambystandquerypage")
	public @ResponseBody
	String findStandHasParamByStandQueryPage(HttpServletRequest request,
			String page, String id, String key) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][findStandHasParamByStand]开始执行");
		}
		String json = "";
		if (id == null)
			return null;
		String total = tmParamsService.getStandHasParamCount(page, id, key);
		int cou = Integer.parseInt(total);
		DBParameter param = new DBParameter();
		param.setObject("id", id);
		param.setObject("key", key);
		param.setObject("page", page);
		List<SatSubAloneParam> result = tmParamsService
				.findStandHasParamByStandQueryPage(param);
		json = JSONObject.toJSONString(result);
		//json= "[" + json + "]";
		return "{\"Rows\":"+json+",Total:"+cou+"}";
	}*/

	/**
	 * @Title: paramDrop
	 * @Description:参数移动 公有方法
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("paramdrop")
	public @ResponseBody
	String paramDrop(HttpServletRequest request, String ids, String owner_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][paramDrop]开始执行");
		}
		if (ids == null || owner_id == null)
			return null;
		Boolean flag=true;
//		ids = ids.substring(0, ids.length()-1);
//		ids = ids.replaceAll("\'", "");
		String []str =new String[50];
		str = ids.split(",");
		for(int i=0;i<str.length;i++){
			String id=str[i];
			flag = flag && tmParamsService.paramDrop(id, owner_id);
		}
		if (flag) {
			return "{success:true,message:'操作成功'}";
		} else {
			return "{success:false,message:'操作失败'}";
		}
	}

	/**
	 * @Title: paramDropToRightAll
	 * @Description: 全部授权
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("paramdroptorightall")
	public @ResponseBody
	String paramDropToRightAll(HttpServletRequest request, String id,
			String key, String owner_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][paramDropToRightAll]开始执行");
		}
		if (id == null)
			return null;
		Boolean flag = tmParamsService.paramDropToRightAll(id, key, owner_id);
		if (flag) {
			return "{success:true,message:'操作成功'}";
		} else {
			return "{success:false,message:'操作失败'}";
		}
	}

	/**
	 * @Title: aloneParamDropToRightAll
	 * @Description: 单机全部授权
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("aloneparamdroptorightall")
	public @ResponseBody
	String aloneParamDropToRightAll(HttpServletRequest request, String id,
			String key, String owner_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][aloneParamDropToRightAll]开始执行");
		}
		Boolean flag = tmParamsService.aloneParamDropToRightAll(id, key, owner_id);
		if (flag) {
			return "{success:true,message:'操作成功'}";
		} else {
			return "{success:false,message:'操作失败'}";
		}
	}

	/**
	 * @Title: paramDropToLeftAll
	 * @Description: 全部移除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("paramdroptoleftall")
	public @ResponseBody
	String paramDropToLeftAll(HttpServletRequest request, String id,
			String key, String owner_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][paramDropToLeftAll]开始执行");
		}
		Boolean flag = tmParamsService.paramDropToLeftAll(id, key, owner_id);
		if (flag) {
			return "{success:true,message:'操作成功'}";
		} else {
			return "{success:false,message:'操作失败'}";
		}
	}

	/**
	 * @Title: ParamDropToLeft
	 * @Description: 参数移除
	 * @author jingkewen
	 * @throws
	 */
	@RequestMapping("paramdroptoleft")
	public @ResponseBody
	String ParamDropToLeft(HttpServletRequest request, String ids,
			String key, String owner_id) {
		if (log.isInfoEnabled()) {
			log.info("组件[TmParamsController][ParamDropToLeft]开始执行");
		}
		if (ids == null)
			return null;
		ids = ids.replaceAll("\'", "");
		ids = ids.substring(0, ids.length()-1);
		Boolean flag = tmParamsService.ParamDropToLeft(ids, key, owner_id);
		if (flag) {
			return "{success:true,message:'操作成功'}";
		} else {
			return "{success:false,message:'操作失败'}";
		}
	}
	
	/**
	 * 判断参数编号是否重复
	 * @param request
	 * @param tmCode
	 * @return
	 */
	@RequestMapping("judgeTmCode")
	public @ResponseBody
	String judgeTmCode(HttpServletRequest request, String satId,String tmCode,String tmId) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][judgeTmCode]开始执行");
		}
		ResultBean rb = new ResultBean();
		Map<String,Object> result = tmParamsService.queryTmByCode(satId,tmCode,tmId);
		if(result.size() != 0){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
	}
	/**
	 * 判断序号是否重复
	 * @param request
	 * @param tmNum
	 * @return
	 */
	@RequestMapping("judgeTmNum")
	public @ResponseBody
	String judgeTmNum(HttpServletRequest request,String satId, String tmNum,String tmId) {
		if (log.isInfoEnabled()) {
			log.info("组件[SatInfoController][judgeTmNum]开始执行");
		}
		ResultBean rb = new ResultBean();
		Map<String,Object> result = tmParamsService.queryTmByNum(satId,tmNum,tmId);
		if(result.size() != 0){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		return new Gson().toJson(rb);
	}
	
	/**
	 * 参数信息导入
	 * @param request
	 * @param sat_id
	 * @param version_id
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/tmparaminput")
	public @ResponseBody
	String tmparaminput(HttpServletRequest request, String sat_id,String filePath) {
		String filePathTemp = FrameStartup.PROJECT_PATH + filePath;
		String[][] dataArr;
		try {
			dataArr = FileUploadUtil.getDataByFilePath(filePathTemp, 1);
			return saveData(sat_id,dataArr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "导入失败！请联系管理员！";
	}
	
	public String saveData (String sat_id,String[][] records) {
		int r=1;
		try {
			if (sat_id == null)
				return null;
			List<TmParamView> tms = new ArrayList<TmParamView>();
			for(int i=0;records != null && i<records.length;i++){
				if(records[i][0] == null || records[i][0].equals("")){
					break;
				}
				TmParamView tm = new TmParamView();
				tm.setSat_id(sat_id);
				tm.setOwner_id(sat_id);
				tm.setTm_param_name(records[i][0]);
				tm.setTm_param_code(records[i][1]);
//				tm.setTm_param_num(records[i][2]);
				tm.setTm_param_type(records[i][2]);
				tm.setCreate_user("");
				tms.add(tm);
			}
			r = tmParamsService.tmParamInput(tms);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		if (r == 0) {
			return "导入成功";
		} else if (r == 1) {
			return "导入失败，请查看错误日志!";
		} else {
			return "部分数据导入失败，请查看错误日志!";
		}
	}
	
}

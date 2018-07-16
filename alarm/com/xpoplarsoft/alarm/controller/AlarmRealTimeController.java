package com.xpoplarsoft.alarm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.modal.ReqParamBean;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.result.AlarmResult;
import com.xpoplarsoft.alarm.result.AlarmResultManager;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.utils.BeanTools;
import com.xpoplarsoft.framework.utils.StringTools;

@Controller
@RequestMapping("/alarmRealTimeAction")
public class AlarmRealTimeController {
	private static Log log = LogFactory.getLog(AlarmRealTimeController.class);
 
	private static final Gson GSON = new Gson();
	
	private static final String FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	/**
	 * 获取当前时间
	 * @param request
	 * @return
	 */
	@RequestMapping("getServerDate")
	public @ResponseBody
	String getServerDate(HttpServletRequest request){
		
		Map<String,String> map = new HashMap<String, String>();
		
		String nowDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		map.put("nowDate", nowDate);
		
		return GSON.toJson(map);
	}
	
	/**
	 * 获取实时报警信息
	 * @param request
	 * @param satsid(逗号分隔)
	 * @return
	 */
	@RequestMapping("getRealTimePageAlam")
	public @ResponseBody
	String getRealTimePageAlam(HttpServletRequest request,String satsid){
		
		if(log.isDebugEnabled()){
			log.debug("组件[alarmRealTimeAction][getRealTimePageAlam]开始执行");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		if(satsid == null || "".equals(satsid)){
			map.put("Rows", rsList);
			return GSON.toJson(map);
		}
		//获取卫星id
		String[] satIdArr = satsid.split(",");
		//循环卫星ID
		for (int i = 0;satIdArr != null && i < satIdArr.length; i++) {
			AlarmResultManager arsm = new AlarmResultManager();
			List<AlarmResult> alarmList = arsm.getAlarmResult(satIdArr[i]);
			//循环结果
			for (int j = 0;alarmList != null && j < alarmList.size(); j++) {
				AlarmResult alarmRs = alarmList.get(j);
				if(alarmRs.getSatCode() == null || "".equals(alarmRs.getSatCode())){
					//若卫星编号为空，不再显示。
					continue;
				}
				//开始时间
				Long startTime = alarmRs.getTime();
				String startTimeStr = "";
				if(startTime != null){
					startTimeStr = DateFormatUtils.format(startTime, FORMAT_STR);
				}
				//结束时间
				Long alarmEndTime = alarmRs.getEndTime();
				String endTime = "";
				if(alarmEndTime != null){
					endTime = DateFormatUtils.format(alarmEndTime, FORMAT_STR);
				}
				//响应时间
				Long alarmEnsuredTime = alarmRs.getResponseTime();
				String responseTime = "";
				if(alarmEnsuredTime != null){
					responseTime = DateFormatUtils.format(alarmEnsuredTime,FORMAT_STR);
				}
				//如果报警结束时间和确认时间都不为空，不再显示
				if(alarmEndTime != null && alarmEnsuredTime != null){
					continue;
				}
				@SuppressWarnings("unchecked")
				Map<String,Object> alarmmap = BeanTools.beanToMap(alarmRs);
				alarmmap.put("startTimeStr", startTimeStr);
				alarmmap.put("endTimeStr", endTime);
				alarmmap.put("responseTimeStr", responseTime);
				//设备名称+code
				String satNameCode = alarmRs.getSatName()==null?"":alarmRs.getSatName()+"("+alarmRs.getSatCode()+")";
				alarmmap.put("satNameCode", satNameCode);
				//参数名称+code
				String paramNameCode = alarmRs.getParamName()==null?"":alarmRs.getParamName()+"("+alarmRs.getParamCode()+")";
				alarmmap.put("paramNameCode",paramNameCode);
				//当前值，默认是""
				alarmmap.put("currentValue","");
				//添加到list
				rsList.add(alarmmap);
			}
		}
		map.put("Rows", rsList);
		String rs = GSON.toJson(map);
		if(log.isDebugEnabled()){
			log.debug("组件[alarmRealTimeAction][getRealTimePageAlam]执行结束");
		}
		return rs;
	}
	
	/**
	 * 获取当前值
	 * @param request
	 * @param reqParamsStr(ReqParamBean对象)
	 * @return
	 */
	@RequestMapping("getNowValue")
	public @ResponseBody
	String getNowValue(HttpServletRequest request,String reqParamsStr){
		
		//返回结果list
		List<Map<String, String>> rsList = new ArrayList<Map<String,String>>();
		//初始化参数bean
		ReqParamBean paramBean = new ReqParamBean();
		if (!StringTools.isNull(reqParamsStr)) {
			//转化为bean
			paramBean = BeanTools.jsonToBean(reqParamsStr, ReqParamBean.class);
			//获取请求参数
			List<Map<String, String>> paramList = paramBean.getParamlist();
			//获取最新当前值
			for (int i = 0;paramList != null && i < paramList.size(); i++) {
				Map<String, String> map = paramList.get(i);
				//获取上一次获取当前值时间
				long lastGetValueTime = 1;
				if(map.get("lastGetValueTime") != null){
					try {
						lastGetValueTime = SDF.parse(map.get("lastGetValueTime")).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				AlarmParam param = AlarmCacheUtil.getParamById(map.get("sat_id"), map.get("tm_id")
						,lastGetValueTime);
				//获取当前值
				String value = "";
				if(param!=null && param.getValue() != null){
					/*if (param.getData_type() == 0) {
						value = param.getValue().toString();
					}
					if (param.getData_type() == 1 || param.getData_type() == 2) {
						value = ((Long)param.getValue()).toString();
					}*/
					value = param.getValue().toString();
				}
				map.put("newValue", value);
				//当前值变更时，修改获取变更值的时间
				map.put("lastGetValueTime", DateFormatUtils.format(new Date().getTime(), FORMAT_STR));
			}
			
			rsList = paramList;
		}
		
		String rs = GSON.toJson(rsList);
		return rs;
	}
	
	/**
	 * 确认
	 * @param deviceid
	 * @param tmid
	 * @return
	 */
	@RequestMapping("toEnsure")
	public @ResponseBody String toEnsure(HttpServletRequest request,String deviceid,String tmid,String response){
		
		if(log.isDebugEnabled()){
			log.debug("组件[alarmRealTimeAction][toEnsure]开始执行");
		}
		
		ResultBean bean = new ResultBean();
		//当前用户信息
		LoginUserBean loginBean = request.getSession().getAttribute("LoginUser")==null?null:(LoginUserBean)request.getSession().getAttribute("LoginUser");
		if(loginBean == null){
			bean.setSuccess("false");
			bean.setMessage("获取当前用户信息出错！");
			return GSON.toJson(bean);
		}
		//修改响应信息
		AlarmResultManager manager = new AlarmResultManager();
		manager.setRespone(deviceid, tmid, 
				loginBean.getUserId(), loginBean.getUserName(),response);
		bean.setMessage("响应成功！");
		bean.setSuccess("true");
		String rs = GSON.toJson(bean);
		if(log.isInfoEnabled()){
			log.info("返回结果["+rs+"]");
		}
		if(log.isDebugEnabled()){
			log.debug("组件[alarmRealTimeAction][toEnsure]执行结束");
		}
		return rs;
	}
	
}

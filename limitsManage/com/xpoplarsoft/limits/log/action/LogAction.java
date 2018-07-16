package com.xpoplarsoft.limits.log.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.action.Action;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.limits.log.service.ILogService;

/**
 * 类功能: 日志管理action类
 * 
 * @author admin
 * @date 2014-04-11
 */
@Controller
@RequestMapping("/LogAction")
public class LogAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(LogAction.class);

	@Autowired
	@Qualifier("LogService")
	private ILogService service;


	/**
	 * 查询多条数据
	 * 
	 * @param bean
	 * @param user_name
	 * @param begin_time
	 * @param end_time(开始时间的时间区间，和表里的end_time无关)
	 * @return
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody
	String list(HttpServletRequest request, HttpServletResponse response,
			CommonBean bean,String user_name,
			String begin_time,String end_time) {

		if (log.isInfoEnabled()) {
			log.info("组件[LogAction][list]开始执行");
		}

		Map<String, Object> result = service.listInfo(bean,user_name,
				begin_time,end_time);

		String json = new Gson().toJson(result);

		if (log.isInfoEnabled()) {
			log.info("组件[LogAction][list]执行结束");
		}
		return json;
	}
}

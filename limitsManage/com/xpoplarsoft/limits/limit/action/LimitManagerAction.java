package com.xpoplarsoft.limits.limit.action;

import java.util.HashMap;
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

import com.xpoplarsoft.framework.action.Action;
import com.xpoplarsoft.limits.limit.service.ILimitManagerService;
import com.xpoplarsoft.limits.login.bean.LoginUser;

/**
 * 类功能: 权限管理action类
 * 
 * @author
 * @date 2014-08-05
 */
@Controller
@RequestMapping("/LimitManagerAction")
public class LimitManagerAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(LimitManagerAction.class);

	@Autowired
	@Qualifier("LimitManagerService")
	private ILimitManagerService service;

	/**
	 * 查询页面元素权限
	 * 
	 * @param request
	 *            请求 包含页面路径
	 * @param response
	 *            返回
	 * @return
	 */
	@RequestMapping(value = "/limits")
	public @ResponseBody
	String limits(HttpServletRequest request, HttpServletResponse response) {

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerAction][limits]开始执行");
		}

		Map<String, String> beanMap = new HashMap<String, String>();

		beanMap.put("pagePath", request.getParameter("pagePath"));
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(
				"LoginUser");
		String operator = "";
		if (loginUser != null) {
			operator = loginUser.getUserId();
		}
		beanMap.put("operator", operator);
		String result = service.limits(beanMap);

		if (log.isInfoEnabled()) {
			log.info("组件[LimitManagerAction][limits]执行结束");
		}
		return result;
	}

}

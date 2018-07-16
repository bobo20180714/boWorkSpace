package com.xpoplarsoft.limits.login.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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
import com.xpoplarsoft.framework.action.Action;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.limits.login.bean.LoginBean;
import com.xpoplarsoft.limits.login.service.ILoginService;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.user.service.IUserService;

/**
 * 类功能: 登陆action类
 * 
 * @author chenjie
 * @date 2013-6-28
 */
@Controller
@RequestMapping("/poplarLogin")
public class PoplarLoginAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(PoplarLoginAction.class);
	private static final Gson GSON = new Gson();

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IUserService userService;

	/**
	 * 登陆验证
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            返回
	 * @param bean
	 *            实体bean
	 * @return
	 */
	@RequestMapping(value = "/loginUser")
	public @ResponseBody
	String loginUser(HttpServletRequest request, HttpServletResponse response,
			LoginBean loginBean) {
		
		if (log.isInfoEnabled()) {
			log.info("登陆验证开始");
		}

		boolean state = false;
		
		try {
			state = loginService.loginUser(loginBean);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("用户登录验证发生异常", e);
			}
		}
		ResultBean result = new ResultBean();
		if (state) {
			if (log.isDebugEnabled()) {
				log.debug("登陆验证通过");
			}
			LoginUserBean user = this.getLoginUser(loginBean);
			String clientIP = request.getRemoteAddr();
			user.setClientIp(clientIP);
			request.getSession().setAttribute("LoginUser", user);
			//登录账号
			request.getSession().setAttribute("userAccount", loginBean.getUserAccount());
			result.setSuccess("true");
			
		} else {
			request.getSession().removeAttribute("username");
			if (log.isDebugEnabled()) {
				log.debug("登陆验证失败");
			}
			result.setSuccess("false");
			result.setMessage("用户名或密码错误！");
		}
		return GSON.toJson(result);
	}
	
	/**
	 * 获取权限菜单
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getLimit")
	public @ResponseBody
	String getLimit(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		
			// 获取当前登录用户信息
			LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
			//获取一级、二级权限
			Map<String, List<Resources>> roleResList = loginService.getLimitMenu(loginUser);
			
			String result = GSON.toJson(roleResList);
			
			return result;
	}
	

	/**
	 * 生成session保存的用户信息
	 * 
	 * @param loginBean
	 * @return LoginUser
	 */
	private LoginUserBean getLoginUser(LoginBean loginBean) {
		
		LoginUserBean user = null;
		
		try {
			user = loginService.getUserInfo(loginBean);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("用户信息获取发生异常", e);
			}
		}

		return user;
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/loginOut")
	public @ResponseBody
	void loginOut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		//判断session里的LoginUser是否为null
		if (request.getSession().getAttribute("LoginUser") != null) {
			request.getSession().removeAttribute("LoginUser");
		}
		request.getSession().invalidate();
		response.sendRedirect("http://" + request.getHeader("Host")
				+ request.getContextPath() + "/login/login.jsp");

	}

}

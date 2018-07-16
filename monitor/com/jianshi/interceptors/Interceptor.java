package com.jianshi.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: Interceptor
 * @Description: 自定义拦截器
 * @author xjt
 */
public class Interceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
		// TODO 自动生成的方法存根		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
		// TODO 自动生成的方法存根		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object obj) throws Exception {
		String url=request.getRequestURL().toString();
		if(url.indexOf("rest/login/login")>-1)
			return true;
		Object userObject=request.getSession().getAttribute("LoginUser");
		if(userObject!=null){
			return true;
		}			
		response.sendError(408);
		return false;
	}

}

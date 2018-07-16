package com.xpoplarsoft.limits.resources.action;

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
import com.xpoplarsoft.framework.flowno.FlowNoFactory;
import com.xpoplarsoft.framework.interfaces.bean.LoginUserBean;
import com.xpoplarsoft.framework.utils.DateTools;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.resources.service.IResourcesService;

/**
 * 类功能: 资源连接器类
 * 
 * @author chen.jie
 * @date 2014-3-20
 */
@Controller
@RequestMapping("/ResourcesAction")
public class ResourcesAction extends Action {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ResourcesAction.class);

	@Autowired
	private IResourcesService resService;

	/**
	 * 新增
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param bean
	 * @return string
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody
	String add(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Resources bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][add]开始执行");
		}
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		bean.setUpdateUserCode(loginUser == null ? "" :loginUser.getUserId());
		bean.setPkId(FlowNoFactory.getFlowNoComponent().getFlowNo());
		String updateTime = DateTools.getCurryDateTime().substring(0, 19);
		bean.setUpdateTime(updateTime);

		ResultBean tempResult = resService.add(bean);
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param bean
	 * @return string
	 */
	@RequestMapping(value = "/alter")
	public @ResponseBody
	String alter(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,Resources bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][alter]开始执行");
		}
		// 获取当前登录用户信息
		LoginUserBean loginUser = (LoginUserBean) session.getAttribute("LoginUser");
		bean.setUpdateUserCode(loginUser == null ? "" :loginUser.getUserId());
		String updateTime = DateTools.getCurryDateTime().substring(0, 19);
		bean.setUpdateTime(updateTime);
		ResultBean tempResult = resService.alter(bean);
		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @param resCodes
	 *            资源编号集合
	 * @return string
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody
	String delete(HttpServletRequest request, HttpServletResponse response,
			String resCodes) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][delete]开始执行");
		}

		// 资源编号集合
		String[] resList = resCodes.split(";");

		Gson json = new Gson();

		ResultBean tempResult = resService.delete(resList);

		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 启用
	 * 
	 * @param resCodes
	 * @return
	 */
	@RequestMapping(value = "/start")
	public @ResponseBody
	String start(String resCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][start]开始执行");
		}
		// 资源编号集合
		String[] resList = resCodes.split(";");

		ResultBean tempResult = resService.start(resList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 禁用
	 * 
	 * @param resCodes
	 * @return
	 */
	@RequestMapping(value = "/stop")
	public @ResponseBody
	String stop(String resCodes) {

		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][stop]开始执行");
		}
		// 资源编号集合
		String[] resList = resCodes.split(";");

		ResultBean tempResult = resService.stop(resList);

		Gson json = new Gson();
		String result = json.toJson(tempResult);
		if (log.isDebugEnabled()) {
			log.debug("返回数据为[" + result + "]");
		}
		return result;
	}

	/**
	 * 获取列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getList")
	public @ResponseBody
	String getList(HttpServletRequest request, HttpServletResponse response,
			ResWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][getList]开始执行");
		}
		ResultBean tempResult = resService.getList(where);
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}
	
	/**
	 * 获取列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/byIdGetList")
	public @ResponseBody
	String byIdGetList(HttpServletRequest request, HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][byIdGetList]开始执行");
		}
		String resCode = request.getParameter("resCode");
		ResultBean tempResult = resService.byIdGetList(resCode);
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}

	/**
	 * 获取单条记录
	 * 
	 * @param request
	 * @param response
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value = "/getRowRecord")
	public @ResponseBody
	String getRowRecord(HttpServletRequest request,
			HttpServletResponse response, String resCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][getRowRecord]开始执行");
		}
		String result = resService.getRowRecord(resCode);
		return result;
	}
	
	/**
	 * 获取单条记录
	 * 
	 * @param request
	 * @param response
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value = "/checkCode")
	public @ResponseBody
	String checkCode(HttpServletRequest request,
			HttpServletResponse response, String resCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][checkCode]开始执行");
		}
		String result = resService.checkCode(resCode);
		return result;
	}

	/**
	 * 获取资源树数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getResourcesTree")
	public @ResponseBody
	String getResourcesTree(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesAction][getResourcesTree]开始执行");
		}
		// 从数据库得到列表
		ResultBean tempResult = resService.getResourcesTree();
		// 将数据转为json
		String result = null;
		if (tempResult != null) {
			result = tempResult.toJson();
		}
		return result;
	}

}

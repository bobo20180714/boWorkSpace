package com.xpoplarsoft.limits.resources.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;
import com.xpoplarsoft.limits.resources.dao.IResourcesDao;

/**
 * 类功能: 资源业务处理类
 * 
 * @author chen.jie
 * @date 2014-3-15
 */
@Service
public class ResourcesService implements IResourcesService {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ResourcesService.class);

	@Autowired
	private IResourcesDao resDao;

	/**
	 * 新增
	 * 
	 * @param bean
	 *            资源信息
	 * @return 返回显示数据类
	 */
	public ResultBean add(Resources bean) {

		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesService][add]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = resDao.add(bean);;
		if (status) {
			result.setSuccess("true");
			result.setMessage("新增资源成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("新增资源失败！");
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param bean
	 *            资源信息
	 * @return
	 */
	public ResultBean alter(Resources bean) {

		ResultBean result = new ResultBean();
		boolean status = resDao.alter(bean);
		if (status) {
			result.setSuccess("true");
			result.setMessage("修改资源信息成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("修改资源信息失败！");
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param resourcesList
	 * @return
	 */
	public ResultBean delete(String[] resourcesList) {

		ResultBean result = new ResultBean();
		boolean delStatus = resDao.delete(resourcesList);
		if (delStatus) {
			result.setSuccess("true");
			result.setMessage("删除资源成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("删除资源失败！");
		}
		return result;
	}

	/**
	 * 启用
	 * 
	 * @param resourcesList
	 *            资源编码集合
	 * @return 是否成功
	 */
	public ResultBean start(String[] resourcesList) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesService][start]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = resDao.updateState(resourcesList, "1");
		if (status) {
			result.setSuccess("true");
			result.setMessage("启用资源成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("启用资源失败！");
		}

		return result;
	}

	/**
	 * 禁用
	 * 
	 * @param ResourcesService
	 *            角色编码集合
	 * @return 是否成功
	 */
	public ResultBean stop(String[] ResourcesList) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesService][stop]开始执行");
		}
		ResultBean result = new ResultBean();
		boolean status = resDao.updateState(ResourcesList, "2");
		if (status) {
			result.setSuccess("true");
			result.setMessage("禁用资源成功！");
		} else {
			result.setSuccess("false");
			result.setMessage("禁用资源失败！");
		}

		return result;
	}

	/**
	 * 获取列表
	 * 
	 * @return List<AutRole>
	 */
	public ResultBean getList(ResWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesService][getList]开始执行");
		}

		ResultBean resultBean = new ResultBean();

		DBResult result = resDao.getList(where);
		
		// 页面数据
		Map<String,Object> pageInfo = new HashMap<String,Object>();
		// 一行数据
		Map<String,String> cellMap = null;

		List<Map<String,String>> infoList = new ArrayList<Map<String,String>>();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				cellMap = new HashMap<String,String>();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("res_code", result.getValue(i, "res_code"));
				cellMap.put("res_name", result.getValue(i, "res_name"));
				cellMap.put("res_type", result.getValue(i, "res_type"));
				cellMap.put("res_value", result.getValue(i, "res_value"));
				cellMap.put("res_father", result.getValue(i, "res_father"));
				cellMap.put("create_user_code", result.getValue(i,
						"create_user_code"));
				cellMap.put("create_time", result.getValue(i, "create_time"));
				cellMap.put("state", result.getValue(i, "state"));
				cellMap.put("order_num", result.getValue(i, "order_num"));
				cellMap.put("show_type", result.getValue(i, "show_type"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);

		resultBean.setSuccess("true");
		resultBean.setMessage("查询成功！");
		resultBean.setData(pageInfo);

		resultBean.setSuccess("true");
		resultBean.setMessage("查询成功！");
		resultBean.setData(pageInfo);

		return resultBean;
	}

	/**
	 * 获取列表
	 * 
	 * @return List<AutRole>
	 */
	public ResultBean byIdGetList(String resCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesService][byIdGetList]开始执行");
		}

		ResultBean resultBean = new ResultBean();

		DBResult result = resDao.byIdGetList(resCode);
		// 页面数据
		Map<String,Object> pageInfo = new HashMap<String,Object>();
		// 一行数据
		Map<String,String> cellMap = null;

		List<Map<String,String>> infoList = new ArrayList<Map<String,String>>();

		pageInfo.put("Total", result.getTotal());
		if (result != null && result.getRows() > 0) {
			for (int i = 0; i < result.getRows(); i++) {
				cellMap = new HashMap<String,String>();

				cellMap.put("pk_id", result.getValue(i, "pk_id"));
				cellMap.put("res_code", result.getValue(i, "res_code"));
				cellMap.put("res_name", result.getValue(i, "res_name"));
				cellMap.put("res_type", result.getValue(i, "res_type"));
				cellMap.put("res_value", result.getValue(i, "res_value"));
				cellMap.put("res_father", result.getValue(i, "res_father"));
				cellMap.put("create_user_code", result.getValue(i,
						"create_user_code"));
				cellMap.put("create_time", result.getValue(i, "create_time"));
				cellMap.put("state", result.getValue(i, "state"));
				cellMap.put("order_num", result.getValue(i, "order_num"));
				cellMap.put("show_type", result.getValue(i, "show_type"));
				infoList.add(cellMap);
			}
		}
		pageInfo.put("Rows", infoList);

		resultBean.setSuccess("true");
		resultBean.setMessage("查询成功！");
		resultBean.setData(pageInfo);

		return resultBean;
	}

	/**
	 * 获取资源树
	 * 
	 * @return
	 */
	public ResultBean getResourcesTree() {

		ResultBean result = new ResultBean();
		ResWhere where = new ResWhere();
		DBResult dbResult = resDao.getList(where);

		List<Resources> list = new ArrayList<Resources>();
		for (int i = 0; dbResult != null && i < dbResult.getRows(); i++) {
			Resources res = new Resources();
			res.setResName(dbResult.getValue(i, "res_name"));
			res.setResFather(dbResult.getValue(i, "res_father"));
			res.setResType(dbResult.getValue(i, "res_type"));
			res.setResValue(dbResult.getValue(i, "res_value"));
			res.setPkId(dbResult.getValue(i, "pk_id"));
			list.add(res);
		}

		result.setData(list);

		return result;
	}

	/**
	 * 获取单条记录
	 * 
	 * @param res_code
	 *            资源编码集合
	 * @return PoplarResources
	 */
	public String getRowRecord(String res_code) {

		Map<String, String> cellMap = new HashMap<String, String>();
		Gson json = new Gson();
		DBResult dbResult = resDao.view(res_code);
		if (dbResult != null && dbResult.getRows() > 0) {
			String[] resName = dbResult.getColName();

			for (int j = 0; j < resName.length; j++) {
				// 查询列对应的值
				String resValue = dbResult.getValue(0, resName[j]);
				// 放入map中
				cellMap.put(resName[j].toLowerCase(), resValue);
			}
		}
		String retJson = json.toJson(cellMap);
		if (log.isDebugEnabled()) {
			log.debug("返回json数据为[" + retJson + "]");
		}
		return retJson;

	}
	
	public String checkCode(String res_code) {

		ResultBean result = new ResultBean();
		Gson json = new Gson();
		DBResult dbResult = resDao.checkCode(res_code);
		if (dbResult != null && dbResult.getRows() > 0) {
			result.setSuccess("true");
		}else {
			result.setSuccess("false");
		}
		String retJson = json.toJson(result);
		if (log.isDebugEnabled()) {
			log.debug("返回json数据为[" + retJson + "]");
		}
		return retJson;

	}

}

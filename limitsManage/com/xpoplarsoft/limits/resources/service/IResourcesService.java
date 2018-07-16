package com.xpoplarsoft.limits.resources.service;

import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;

/**
 * 类功能: 资源业务处理接口类
 * 
 * @author chen.jie
 * @date 2014-3-20
 */
public interface IResourcesService {

	/**
	 * 新增
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean add(Resources bean);

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean alter(Resources bean);

	/**
	 * 删除
	 * 
	 * @param resourcesList
	 * @return
	 */
	public ResultBean delete(String[] resourcesList);

	/**
	 * 启用
	 * 
	 * @param resourcesList
	 *            资源编码集合
	 * @return 是否成功
	 */
	public ResultBean start(String[] resourcesList);

	/**
	 * 禁用
	 * 
	 * @param resourcesList
	 *            资源编码集合
	 * @return 是否成功
	 */
	public ResultBean stop(String[] resourcesList);

	/**
	 * 获取列表
	 * 
	 * @param bean
	 * @return
	 */
	public ResultBean getList(ResWhere where);

	/**
	 * 获取资源树
	 * 
	 * @return
	 */
	public ResultBean getResourcesTree();
	
	/**
	 * 获取单条记录
	 * 
	 * @param res_code
	 *            资源编码集合
	 * @return PoplarResources
	 */
	public String getRowRecord(String res_code);
	
	/**通过资源编码查询子资源集合
	 * 
	 * @param resCode
	 * @return
	 */
	ResultBean byIdGetList(String resCode);
	
	/**检查资源编码是否重复
	 * 
	 * @param res_code
	 * @return
	 */
	String checkCode(String res_code);

}

package com.xpoplarsoft.limits.resources.dao;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;

/**
 * 类功能: 资源dao接口类
 * 
 * @author chen.jie
 * @date 2014-3-20
 */
public interface IResourcesDao {

	/**
	 * 新增
	 * 
	 * @param bean
	 * @return
	 */
	public boolean add(Resources bean);

	/**
	 * 修改
	 * 
	 * @param bean
	 * @return
	 */
	public boolean alter(Resources bean);
	
	DBResult view(String id);

	/**
	 * 删除
	 * 
	 * @param resourcesCodes
	 * @return
	 */
	public boolean delete(String[] resourcesCodes);

	/**
	 * 更新角色状态
	 * 
	 * @param resourcesList
	 * @param state
	 * 
	 * @return 成功标志
	 */
	public boolean updateState(String[] resourcesList, String state);

	/**
	 * 根据表格条件查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return 数据库执行语句结果
	 */
	public DBResult getList(ResWhere where);
	
	
	/**
	 * 判断资源编号是否存在
	 * 
	 * @param resourcesCode
	 * @return
	 */
	public boolean checkCodeExist(String resourcesCode);
	
	/**
	 * 根据用户编码获取权限资源列表
	 * 
	 * @param userCode
	 * @return DBResult
	 */
	public DBResult getResTreeByUserCode(String userCode);
	
	/**通过资源编码查询子资源集合
	 * 
	 * @param resCode
	 * @return
	 */
	DBResult byIdGetList(String resCode);
	
	/**检查资源编码是否重复
	 * 
	 * @param res_code
	 * @return
	 */
	DBResult checkCode(String res_code);

}

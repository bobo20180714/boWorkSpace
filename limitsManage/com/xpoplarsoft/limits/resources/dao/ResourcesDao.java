package com.xpoplarsoft.limits.resources.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.limits.resources.bean.ResWhere;
import com.xpoplarsoft.limits.resources.bean.Resources;

/**
 * 类功能: 角色dao实现类
 * 
 * @author chen.jie
 * @date 2014-3-14
 */
@Repository
public class ResourcesDao implements IResourcesDao {

	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ResourcesDao.class);



	/**
	 * 新增
	 * 
	 * @param bean
	 * @return
	 */
	public boolean add(Resources bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesDao][add]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("pk_id", bean.getPkId());
		para.setObject("res_name", bean.getResName());
		para.setObject("res_code", bean.getResCode());
		para.setObject("res_type", bean.getResType());
		para.setObject("state", bean.getState());
		para.setObject("res_value", bean.getResValue());
		para.setObject("res_father", bean.getResFather());
		para.setObject("update_user_code", bean.getUpdateUserCode());
		para.setObject("update_time", bean.getUpdateTime());
		para.setObject("order_num", bean.getOrderNum());
		para.setObject("show_type", bean.getShowType());

		boolean result = SQLFactory.getSqlComponent().updateInfo("resourcesInfo","add_resources", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("新增资源成功");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("新增资源失败");
			}
			return false;
		}
	}

	/**
	 * 修改
	 * 
	 * @param role
	 * @return
	 */
	public boolean alter(Resources bean) {
		if (log.isInfoEnabled()) {
			log.info("组件[RoleDao][update]开始执行");
		}

		DBParameter para = new DBParameter();
		para.setObject("pk_id", bean.getPkId());
		para.setObject("res_name", bean.getResName());
		para.setObject("res_code", bean.getResCode());
		para.setObject("res_type", bean.getResType());
		para.setObject("res_value", bean.getResValue());
		para.setObject("res_father", bean.getResFather());
		para.setObject("update_user_code", bean.getUpdateUserCode());
		para.setObject("update_time", bean.getUpdateTime());
		para.setObject("order_num", bean.getOrderNum());
		para.setObject("show_type", bean.getShowType());
		boolean result = SQLFactory.getSqlComponent().updateInfo("resourcesInfo","alter_resources", para);
		if (result) {
			if (log.isDebugEnabled()) {
				log.debug("修改资源信息成功");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("修改资源信息失败");
			}
			return false;
		}

	}

	/**
	 * 删除
	 * 
	 * @param resourcesList
	 * @return
	 */
	public boolean delete(String[] resourcesList) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesDao][delete]开始执行");
		}
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> roleResList = new ArrayList<DBParameter>();
		for (int i = 0; i < resourcesList.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("res_id", resourcesList[i]);
			roleResList.add(para);
		}
		map.put("delete_role_res", roleResList);
		boolean result = SQLFactory.getSqlComponent().batchUpdate("resourcesInfo", map);
		if(result){
			List<DBParameter> list = new ArrayList<DBParameter>();
			for (int i = 0; i < resourcesList.length; i++) {
				DBParameter para = new DBParameter();
				para.setObject("pk_id", resourcesList[i]);
				list.add(para);
			}
			map.put("delete_resources", list);
			result = SQLFactory.getSqlComponent().batchUpdate("resourcesInfo", map);
		}
		return result;
	}

	/**
	 * 更新资源状态
	 * 
	 * @param resourcesList
	 * @param state
	 * 
	 * @return 成功标志
	 */
	public boolean updateState(String[] resourcesList, String state) {
		List<DBParameter> list = new ArrayList<DBParameter>();
		for (int i = 0; i < resourcesList.length; i++) {
			DBParameter para = new DBParameter();
			para.setObject("pk_id", resourcesList[i]);
			para.setObject("state", state);
			list.add(para);
		}
		boolean result = SQLFactory.getSqlComponent().batchUpdate("resourcesInfo", "update_resources_state", list);
		return result;
	}

	/**
	 * 根据表格条件查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return
	 */
	public DBResult getList(ResWhere where) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesDao][getList]开始执行");
		}
		if (where.getRes_name() == null) {
			where.setRes_name("");
		}
		if (where.getState() == null) {
			where.setState("");
		}
		DBParameter para = new DBParameter();
		para.setObject("res_name", where.getRes_name() == null? "" : where.getRes_name());
		para.setObject("state", "".equals(where.getState())? "1" : where.getState());
		
		DBResult result = null;
		if (!"".equals(where.getResCode()) && where.getResCode() != null && !"-1".equals(where.getResCode())) {
			para.setObject("res_code", where.getResCode());
			result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo","query_resources_byidlist", para);
		}else if ("-1".equals(where.getResCode())) {
			result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo","query_first_resources_byidlist", para);
		} else if (where.getPage() == -1 || where.getPagesize() == -1) {
			result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo","query_resources_list", para);
		} else {
			result = SQLFactory.getSqlComponent().pagingQueryInfo("resourcesInfo","query_resources_list", para, where.getPage(), where.getPagesize());
		}
		return result;
	}
	
	/**
	 * 根资源id查询
	 * 
	 * @param GridViewBean
	 *            grid查询显示bean
	 * @return
	 */
	public DBResult byIdGetList(String resCode) {
		if (log.isInfoEnabled()) {
			log.info("组件[ResourcesDao][getList]开始执行");
		}
		DBParameter para = new DBParameter();
		para.setObject("res_code", resCode == "" ? null : resCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo","query_resources_byid", para);
		return result;
	}

	
	/**
	 * 判断资源编号是否存在
	 * 
	 * @param resourcesCode
	 * @return
	 */
	public boolean checkCodeExist(String resourcesCode) {
		if (resourcesCode == null) {
			return false;
		}
		String where = " where res_code='" + resourcesCode + "'";

		DBParameter para = new DBParameter();
		para.setObject("where", where);
		@SuppressWarnings("deprecation")
		DBResult result = SQLFactory.getSqlComponent().queryInfo("query_xpoplar_resources_list", para);
		if (result == null || result.getTotal() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 根据用户编码获取权限资源列表
	 * 
	 * @param userCode
	 * @return DBResult
	 */
	public DBResult getResTreeByUserCode(String userCode) {
		
		DBParameter para = new DBParameter();
		para.setObject("user_account", userCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("login","query_reslist_usercode", para);
		
		return result;
	}

	@Override
	public DBResult view(String id) {
		DBParameter para = new DBParameter();
		para.setObject("pk_id", id);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo", "view_resources", para);
		return result;
	}
	
	@Override
	public DBResult checkCode(String res_code) {
		DBParameter para = new DBParameter();
		para.setObject("res_code", res_code);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("resourcesInfo", "check_res_code", para);
		return result;
	}

}

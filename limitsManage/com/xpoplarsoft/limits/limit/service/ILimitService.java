package com.xpoplarsoft.limits.limit.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.limits.limit.bean.LimitBean;

/**
 * 系统数据授权服务接口
 * @author mxc
 *
 */
public interface ILimitService {

	/**
	 * 新增授权
	 * @param List<LimitBean>
	 * @return
	 */
	boolean resourceAuthorizationAdd(List<LimitBean> beanList);

	/**
	 * 移除授权
	 * @param List<LimitBean>
	 * @return
	 */
	boolean resourceAuthorizationDelete(List<LimitBean> beanList);

	/**
	 *  获取未授权的卫星
	 * @return
	 */
	Map<String, Object> satList(CommonBean bean,
			String ug_id);

	/**
	 * 获取已经授权的卫星数据
	 * @param ug_id
	 * @param grant_type
	 * @return
	 */
	List<Map<String, Object>> queryAlreadyGrantSat(String ug_id, int grant_type);

}

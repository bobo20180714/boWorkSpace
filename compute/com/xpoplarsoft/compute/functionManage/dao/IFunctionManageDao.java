package com.xpoplarsoft.compute.functionManage.dao;

import java.util.List;

import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionParam;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;

public interface IFunctionManageDao {

	/**
	 * 分页查询函数列表
	 * @param functionName
	 * @param functionCode
	 * @param className
	 * @param bean
	 * @return
	 */
	DBResult functionList(String functionName, String functionCode,
			String className, CommonBean bean);

	/**
	 * 分页查询函数的参数列表
	 * @param functionId
	 * @param bean
	 * @return
	 */
	DBResult paramList(String functionId, CommonBean bean);

	/**
	 * 移除函数
	 * @param functionId
	 * @return
	 */
	boolean deleteFunction(String functionId);

	/**
	 * 注册函数
	 * @param FunctionBean
	 * @return
	 */
	boolean addFunction(FunctionBean bean);
	
	/**
	 * 新增参数信息
	 * @param List<FunctionParam>
	 * @return
	 */
	boolean addParam(List<FunctionParam> beanList);

	/**
	 * 修改参数信息
	 * @param FunctionParam
	 * @return
	 */
	boolean updateParam(FunctionParam bean);

	/**
	 * 查询参数信息
	 * @param FunctionParam
	 * @return
	 */
	DBResult getFunctionParam(String fieldId);

	/**
	 * 修改参数序号
	 * @param lastFieldId
	 * @param paramOrder
	 * @return
	 */
	boolean updateParamOrder(int fieldId, int paramOrder);

	/**
	 * 根据函数id和参数序号获取参数信息
	 * @param fid
	 * @param paramOrder
	 * @return
	 */
	DBResult queryParamByOrder(int fid, int paramOrder);

	/**
	 * 查询寻列获取唯一标识
	 * @return
	 */
	DBResult getPkId();
	
	/**
	 * 移除参数
	 * @param functionId
	 * @return
	 */
	boolean deleteParamByFid(String functionId);

	boolean updateSql(String sql);

	DBResult queryFunction(String fid);

	boolean updateFunction(FunctionBean bean);

}

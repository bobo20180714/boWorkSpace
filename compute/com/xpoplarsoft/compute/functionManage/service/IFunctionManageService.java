package com.xpoplarsoft.compute.functionManage.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.functionManage.bean.FieldBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionParam;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 注册计算模块管理
 * @author mengxiangchao
 *
 */
public interface IFunctionManageService {

	/**
	 * 分页查询函数列表
	 * @param functionName
	 * @param functionCode
	 * @param className
	 * @param bean
	 * @return
	 */
	Map<String, Object> functionList(String functionName, String functionCode,
			String className, CommonBean bean);
	
	/**
	 * 分页查询函数的参数列表
	 * @param functionId
	 * @param bean
	 * @return
	 */
	Map<String, Object> paramList(String functionId, CommonBean bean);

	/**
	 * 移除函数
	 * @param functionId
	 * @return
	 */
	boolean deleteFunction(String functionId);

	/**
	 * 注册函数
	 * @param functionId
	 * @return
	 */
	int addFunction(FunctionBean bean);

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
	FunctionParam getFunctionParam(String fieldId);

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
	FunctionParam queryParamByOrder(int fid, int paramOrder);

	/**
	 * 获取主键
	 * @return
	 */
	int getPkId();

	/**
	 * 创建表结构
	 * @param tableName
	 * @param fieldBeanlist
	 */
	boolean createTable(String tableName, List<FieldBean> fieldBeanlist);

	/**
	 * 获取计算模块信息
	 * @param fid
	 * @return
	 */
	FunctionBean queryFunction(String fid);

	/**
	 * 修改计算模块信息
	 * @param bean
	 * @return
	 */
	boolean updateFunction(FunctionBean bean);
	

}

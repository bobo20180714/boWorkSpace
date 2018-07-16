package com.xpoplarsoft.compute.computeFunc.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.compute.computeFunc.bean.ComputeFuncBean;
import com.xpoplarsoft.compute.functionManage.bean.FunctionBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;

/**
 * 控制量计算功能管理
 * @author mengxiangchao
 *
 */
public interface IComputeFuncService {

	/**
	 * 新增
	 * @param bean
	 * @return
	 */
	boolean addCompute(ComputeFuncBean bean);
	
	/**
	 * 修改
	 * @param bean
	 * @return
	 */
	boolean updateCompute(ComputeFuncBean bean);

	/**
	 * 分页查询
	 * @param computeName
	 * @param className
	 * @param bean
	 * @return
	 */
	Map<String, Object> computeList(String computeName,String className, CommonBean bean);

	/**
	 * 删除
	 * @param computeId
	 * @return
	 */
	boolean deleteCompute(String computeId);
	
	/**
	 * 单条信息查询
	 * @param computeId
	 * @return
	 */
	ComputeFuncBean view(String computeId);

	/**
	 * 查询所有计算功能
	 * @param computeName
	 * @return
	 */
	List<Map<String, Object>> queryAllComputeList(String computeName);

	/**
	 * 根据控制计算id获取参数信息
	 * @param computeId
	 * @param bean
	 * @return
	 */
	FunctionBean getFunctionInfo(String computeId);

}

package com.xpoplarsoft.monitor.service;

import java.util.List;
import java.util.Map;

import com.xpoplarsoft.monitor.bean.ColumnBean;

/**
 * 进程相关信息 运行参数、输入结果
 * @author mengxiangchao
 *
 */
public interface IRelationService {

	/**
	 * 查询日志表格列属性集合
	 * @param processCode
	 * @return
	 */
	List<ColumnBean> queryLogGridColumns(String processCode);

	/**
	 * 查询日志数据
	 * @param processCode
	 * @return
	 */
	List<Map<String,String>> processLogList(String processCode);

	/**
	 * 查询运行参数
	 * @param processCode
	 * @return
	 */
	List<Map<String, String>> queryRunParam(String processCode);
	
	/**
	 * 查询输出结果
	 * @param processCode
	 * @return
	 */
	List<Map<String, String>> queryResult(String processCode);

}

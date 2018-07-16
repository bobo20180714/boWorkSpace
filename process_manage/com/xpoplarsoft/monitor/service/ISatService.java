package com.xpoplarsoft.monitor.service;

import com.xpoplarsoft.baseinfoquery.bean.SatInfoDetail;

/**
 * 卫星信息查询 服务层
 * @author mengxiangchao
 *
 */
public interface ISatService {

	/**
	 * 根据任务代号查询卫星信息
	 * @param satMid
	 * @return
	 */
	SatInfoDetail getSatInfoByMid(String satMid);

}

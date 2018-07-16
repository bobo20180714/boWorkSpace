/**
 * com.xpoplarsoft.compute.example
 */
package com.xpoplarsoft.compute.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xpoplarsoft.compute.IComputeExecuter;
import com.xpoplarsoft.compute.result.ResultManager;

/**
 * 例子，剩余燃料计算模块
 * @author zhouxignlu
 * 2017年4月27日
 */
public class ShenYuRanLiaoJiSuan implements IComputeExecuter {
	private int mid=-1;
	private Map<String,Double> param;
	
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		ShenYuRanLiaoTMhuoqu shenYuRanLiaoTMhuoqu = new ShenYuRanLiaoTMhuoqu();
		shenYuRanLiaoTMhuoqu.setParameters(mid);
		this.param = (Map<String,Double>)shenYuRanLiaoTMhuoqu.execute();
		Map<String,String> rsData = new HashMap<String, String>();
		rsData.put("type", "SHENGYU");
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		rsData.put("time", sp.format(new Date()));//计算完成时间
		rsData.put("value", String.valueOf(param.get("tm1") + param.get("tm2")));
		
		ResultManager resultManager= new ResultManager();
		resultManager.setParameters(new Object[]{mid,rsData});
		resultManager.execute();
		return rsData;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#setParameters(java.lang.Object[])
	 */
	@Override
	public void setParameters(Object... param) {
		Map<String, Object> map = (Map<String, Object>)param[0];
		this.mid = Integer.parseInt(map.get("satMid").toString());
	}

}

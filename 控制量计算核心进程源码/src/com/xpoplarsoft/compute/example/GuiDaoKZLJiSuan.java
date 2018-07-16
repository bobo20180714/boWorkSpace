/**
 * com.xpoplarsoft.compute.example
 */
package com.xpoplarsoft.compute.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.xpoplarsoft.compute.IComputeExecuter;
import com.xpoplarsoft.compute.result.ResultManager;

/**
 * 例子，轨道控制量计算模块
 * @author zhouxignlu
 * 2017年4月27日
 */
public class GuiDaoKZLJiSuan implements IComputeExecuter {
	private int mid=-1;
	
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		Map<String,String> rsData = new HashMap<String, String>();
		rsData.put("type", "GUIDAO");
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		rsData.put("time", sp.format(new Date()));//计算完成时间
		rsData.put("value", String.valueOf(new Random().nextDouble()));
		
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

/**
 * com.xpoplarsoft.compute.example
 */
package com.xpoplarsoft.compute.example;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.joda.time.DateTime;

import com.cast.tmdb.clidriver.datastru.Value;
import com.xpoplarsoft.compute.IComputeExecuter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.query.auth.User;
import com.xpoplarsoft.query.dispatcher.DispatcherFactory;
import com.xpoplarsoft.query.dispatcher.IDispatcher;
import com.xpoplarsoft.query.engine.TMEngine;
import com.xpoplarsoft.query.engine.db.DBSourceConfigLoad;
import com.xpoplarsoft.query.engine.register.RegisterManager;
import com.xpoplarsoft.query.statement.StatementRegister;

/**
 * 剩余燃料计算用参数数据获取
 * 传入卫星任务号mid
 * 在数据库中获取参数工程值
 * @author zhouxignlu
 * 2017年4月27日
 */
public class ShenYuRanLiaoTMhuoqu implements IComputeExecuter {
	private int mid=-1;
	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#execute()
	 */
	@Override
	public Object execute() {
		//已知参数任务号
		int param1Num = 681;
		int param2Num = 682;
		String sql = "select tm.tm_param_num from tm,satellite s where s.sat_id = tm.sat_id and s.mid = "+mid;
		DBResult dbr = SQLFactory.getSqlComponent().queryInfo(sql);
		if(dbr != null && dbr.getRows() > 1){
			param1Num = Integer.parseInt(dbr.get(0, "tm_param_num"));
			param2Num = Integer.parseInt(dbr.get(1, "tm_param_num"));
		}
		Map<String,Double> rs = new HashMap<String,Double>(); 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			DateTime time1 = new DateTime(sf.parse("2016-11-18 17:26:23.935").getTime());
			DateTime time2 = new DateTime(sf.parse("2017-11-18 17:26:24.935").getTime());
			
			DBSourceConfigLoad.loadProxoolFileconfig();
			TMEngine e = new TMEngine();
			RegisterManager.getRegisterComponent().engineRegister(e);
			SortedMap<DateTime, Value> value1 = e.queryFull(mid, param1Num, time1, time2, 10, null);
			if(value1 != null){
				Set<DateTime> timeSet = value1.keySet();
				DateTime timeFirst = null;
				int num = 0;
				for (DateTime dateTime : timeSet) {
					if(num == 0){
						timeFirst = dateTime;
						break;
					}
					num++;
				}
				rs.put("tm1", value1.get(timeFirst).getDouble());
				SortedMap<DateTime, Value> value2 = e.queryFull(mid, param2Num, time1, time2, 10, null);
				num = 0;
				for (DateTime dateTime : timeSet) {
					if(num == 0){
						timeFirst = dateTime;
						break;
					}
					num++;
				}
				rs.put("tm2", value2.get(timeFirst).getDouble());
			}else{
				rs.put("tm1", 0.0);
				rs.put("tm2", 0.0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/* (non-Javadoc)
	 * @see com.xpoplarsoft.compute.IComputeExecuter#setParameters(java.lang.Object[])
	 */
	@Override
	public void setParameters(Object... param) {
		this.mid = (Integer)param[0];

	}
	
	public static void main(String[] args) {
		ShenYuRanLiaoTMhuoqu p = new ShenYuRanLiaoTMhuoqu();
		p.setParameters(17);
		System.out.println(p.execute());
	}
}

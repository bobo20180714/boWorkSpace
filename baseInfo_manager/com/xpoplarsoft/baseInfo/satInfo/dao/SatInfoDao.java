package com.xpoplarsoft.baseInfo.satInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class SatInfoDao implements ISatInfoDao{
		

	@Override
	public DBResult satList(CommonBean commBean, DBParameter dbParameter) {
		if(commBean.getPage() == -1){
			return SQLFactory.getSqlComponent().queryInfo("satsubalonemapper","satList",dbParameter);
		}
		return SQLFactory.getSqlComponent().pagingQueryInfo("satsubalonemapper","satList",dbParameter,
				commBean.getPage(),commBean.getPagesize());
	}
	
		public DBResult getSatBasicInfoById(DBParameter param){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("satinfomapper",
					"getSatBasicInfoById", param);
			return result;
		}
			
		public DBResult findSatBasicInfoQueryPage(DBParameter param){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("satinfomapper",
					"findSatBasicInfoQueryPage", param);
			return result;	
		}
		
		
		public DBResult findSeqId(){
			DBResult result = SQLFactory.getSqlComponent().queryInfo("satinfomapper",
					"findSeqId",null);
			return result;	
		}
		
		public String getSatBasicInfoCount(DBParameter param) {
			return SQLFactory.getSqlComponent().queryInfo("satinfomapper",
					"getSatBasicInfoCount",param).get(0, 0);
		}
				


	
	public Boolean satBasicInfoUpdate(DBParameter param){
		Boolean flag = SQLFactory.getSqlComponent().updateInfo("satinfomapper",
				"satBasicInfoUpdateSatDetail", param); 
				if(flag){
					flag = SQLFactory.getSqlComponent().updateInfo("satinfomapper",
							"satBasicInfoUpdateSat", param);
				}
				return flag;
	}
	
	public Boolean satBasicInfoDelete(List<DBParameter> paramList){
		return SQLFactory.getSqlComponent().batchUpdate("satinfomapper",
				"satBasicInfoDelete", paramList);
	}

	@Override
	public boolean add(SatInfoDetail satInfoDetail) {
		DBParameter param = new DBParameter();
		param.setObject("sat_name", satInfoDetail.getSat_name());
		param.setObject("mid", satInfoDetail.getMid());
		param.setObject("sat_id", satInfoDetail.getSat_id());
		param.setObject("sat_code", satInfoDetail.getSat_code());
		boolean flag = SQLFactory.getSqlComponent().updateInfo("satinfomapper",
				"addSat", param); 
		if(flag){
			param = new DBParameter();
			param.setObject("sat_id", satInfoDetail.getSat_id());
			param.setObject("design_org", satInfoDetail.getDesign_org());
			param.setObject("user_org", satInfoDetail.getUser_org());
			param.setObject("design_life", satInfoDetail.getDesign_life());
			param.setObject("launch_time", satInfoDetail.getLaunch_time());
			param.setObject("over_life", satInfoDetail.getOver_life());
			param.setObject("location", satInfoDetail.getLocation());
			param.setObject("domain", satInfoDetail.getDomain());
			param.setObject("platform", satInfoDetail.getPlatform());
			param.setObject("duty_officer", satInfoDetail.getDuty_officer());
			param.setObject("first_designer", satInfoDetail.getFirst_designer());
			param.setObject("team", satInfoDetail.getTeam());
			param.setObject("sat_ftm", satInfoDetail.getSat_ftm().isEmpty()?null:satInfoDetail.getSat_ftm());
			param.setObject("sat_ftc", satInfoDetail.getSat_ftc().isEmpty()?null:satInfoDetail.getSat_ftc());
			param.setObject("sat_orbit_height", satInfoDetail.getSat_orbit_height().isEmpty()?null:satInfoDetail.getSat_orbit_height());
			param.setObject("sat_cycle", satInfoDetail.getSat_cycle().isEmpty()?null:satInfoDetail.getSat_cycle());
			param.setObject("sat_longtitude", satInfoDetail.getSat_longtitude());
			flag = SQLFactory.getSqlComponent().updateInfo("satinfomapper",
					"addSatDetail", param);
		}
		return flag;
	}

	@Override
	public DBResult querySatByCode(String satCode) {
		DBParameter p = new DBParameter();
		p.setObject("satCode", satCode);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("satinfomapper",
				"querySatByCode", p);
		return result;
	}

	@Override
	public DBResult querySatByMid(String mid) {
		DBParameter p = new DBParameter();
		p.setObject("mid", mid);
		DBResult result = SQLFactory.getSqlComponent().queryInfo("satinfomapper",
				"querySatByMid", p);
		return result;
	}

	@Override
	public DBResult findSatBasicInfoQueryPageLimit(String userId,String sat_name,
			String design_org, String user_org, String pageSize,
			String pageIndex, String launch_time_start, String launch_time_end){
		
			DBParameter param = new DBParameter();
			param.setObject("user_id", userId);
			param.setObject("sat_name", sat_name);
			param.setObject("design_org", design_org);
			param.setObject("user_org", user_org);
			param.setObject("pageSize", pageSize);
			param.setObject("pageIndex", pageIndex);
			param.setObject("launch_time_start", launch_time_start);
			param.setObject("launch_time_end", launch_time_end);
			DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo("satinfomapper",
					"findSatBasicInfoQueryPageLimit", param, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
			return result;	
	}

}
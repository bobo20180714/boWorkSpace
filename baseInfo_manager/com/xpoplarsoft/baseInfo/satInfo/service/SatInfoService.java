package com.xpoplarsoft.baseInfo.satInfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.baseInfo.common.BaseInfoCommon;
import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.baseInfo.satInfo.dao.ISatInfoDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class SatInfoService implements ISatInfoService {
	@Autowired
	private ISatInfoDao satInfoDao;

	@Override
	public String satList(String ownerId,String key,CommonBean commBean) {
		DBParameter param = new DBParameter();
		param.setObject("owner_id",ownerId);
		param.setObject("key",key);
		DBResult dbResult = satInfoDao.satList(commBean,param);
		
		Map<String, Object> pageData = DBResultUtil.dbResultToPageData(dbResult);
		
		Gson gson = new Gson();
		String rs = gson.toJson(pageData);
		return rs;
	}
	
	@Override
	public String findSatBasicInfoQueryPage(String sat_name,String design_org,String user_org,String design_life, String pageSize, String pageIndex, String over_life, String multicast_address, 
			String udp_port, String launch_time_start,String launch_time_end, String sat_id) {
		DBParameter param = new DBParameter();
		param.setObject("sat_name", sat_name);
		param.setObject("design_org", design_org);
		param.setObject("user_org", user_org);
		param.setObject("design_life", design_life);
		param.setObject("pageSize", pageSize);
		param.setObject("pageIndex", pageIndex);
		param.setObject("over_life", over_life);
		param.setObject("multicast_address", multicast_address);
		param.setObject("udp_port", udp_port);
		param.setObject("launch_time_start", launch_time_start);
		param.setObject("launch_time_end", launch_time_end);
		param.setObject("sat_id", sat_id);
		
		DBResult result = satInfoDao.findSatBasicInfoQueryPage(param);
		String total = satInfoDao.getSatBasicInfoCount(param);
		int cou = Integer.parseInt(total);
		List<SatInfoDetail> satInfoDetails = new ArrayList<SatInfoDetail>();
		if(result!=null&&result.getRows()>0){
			for(int i = 0; i<result.getRows();i++){
				SatInfoDetail alone = new SatInfoDetail();
				alone.setMid(Integer.parseInt(result.get(i, "MID")));
				alone.setSat_id(Integer.parseInt(result.get(i, "SAT_ID")));
				alone.setSat_name((result.get(i, "SAT_NAME")));
				alone.setSat_code((result.get(i, "SAT_CODE")));
				alone.setStatus((result.get(i, "STATUS")));
				alone.setDesign_org(result.get(i, "DESIGN_ORG"));
				alone.setUser_org(result.get(i, "USER_ORG"));
				alone.setDesign_life(result.get(i, "life"));
				alone.setOver_life(result.get(i, "OVER_LIFE"));
				alone.setMulticast_address((result.get(i, "MULTICAST_ADDRESS")));
				alone.setUdp_port(result.get(i, "UDP_PORT"));
				alone.setLaunch_time(result.get(i, "TIME"));
				satInfoDetails.add(alone);
			}
		}
		Map<String,Object> pageData = new HashMap<String, Object>();
		pageData.put("Rows", satInfoDetails);
		pageData.put("Total", cou);
		return new Gson().toJson(pageData);
		
		
	
	}

	@Override
	public String satBasicInfoUpdate(SatInfoDetail satInfoDetail) {
		DBParameter param = new DBParameter();
		param.setObject("sat_name", satInfoDetail.getSat_name());
		param.setObject("status", satInfoDetail.getStatus());
		param.setObject("sat_code", satInfoDetail.getSat_code());
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
		param.setObject("mid", satInfoDetail.getMid());
		param.setObject("multicast_address", satInfoDetail.getMulticast_address());
		param.setObject("udp_port", satInfoDetail.getUdp_port());
		param.setObject("sat_id", satInfoDetail.getSat_id());
		param.setObject("sat_ftm", satInfoDetail.getSat_ftm().isEmpty()?null:satInfoDetail.getSat_ftm());
		param.setObject("sat_ftc", satInfoDetail.getSat_ftc().isEmpty()?null:satInfoDetail.getSat_ftc());
		param.setObject("sat_orbit_height", satInfoDetail.getSat_orbit_height().isEmpty()?null:satInfoDetail.getSat_orbit_height());
		param.setObject("sat_cycle", satInfoDetail.getSat_cycle().isEmpty()?null:satInfoDetail.getSat_cycle());
		param.setObject("sat_longtitude", satInfoDetail.getSat_longtitude());
		
		boolean flag =  satInfoDao.satBasicInfoUpdate(param);
		if(flag){
			return "{success:true,message:'修改成功！'}";
		}
		return "{success:false,message:'修改失败！'}";
	}

	@Override
	public boolean satBasicInfoDelete(String ids) {
		List<DBParameter> paramList = new ArrayList<DBParameter>();
		if(ids!=null){
			String aa[] = ids.split(",");
			for(int i=0;i<aa.length;i++){
				DBParameter param = new DBParameter();
				param.setObject("id", aa[i]);
				paramList.add(param);
			}
		}
		return satInfoDao.satBasicInfoDelete(paramList);
	}
	@Override
	public SatInfoDetail getSatBasicInfoById(String ids){
		DBParameter param = new DBParameter();
		param.setObject("sat_id", ids!=null?Integer.parseInt(ids):0);
		DBResult rst = satInfoDao.getSatBasicInfoById(param);
		SatInfoDetail alone = new SatInfoDetail();
		if(rst.getRows()>0){
			alone.setSat_id(Integer.parseInt(rst.get(0, "SAT_ID")));
			alone.setSat_code(rst.get(0, "SAT_CODE"));
			alone.setStatus(rst.get(0, "STATUS"));
			alone.setSat_name((rst.get(0, "SAT_NAME")));
			alone.setDesign_org(rst.get(0, "DESIGN_ORG"));
			alone.setUser_org(rst.get(0, "USER_ORG"));
			alone.setDesign_life(rst.get(0, "DESIGN_LIFE"));
			alone.setOver_life(rst.get(0, "OVER_LIFE"));
			alone.setMulticast_address((rst.get(0, "MULTICAST_ADDRESS")));
			alone.setUdp_port(rst.get(0, "UDP_PORT"));
			alone.setLaunch_time(rst.get(0, "time"));
			
			alone.setMid(Integer.parseInt(rst.get(0, "MID")));
			alone.setLocation(rst.get(0, "LOCATION"));
			alone.setDomain(rst.get(0, "DOMAIN"));
			alone.setPlatform(rst.get(0, "PLATFORM"));
			alone.setTeam(rst.get(0, "TEAM"));
			alone.setDuty_officer(rst.get(0, "DUTY_OFFICER"));
			alone.setSat_ftm(rst.get(0, "SAT_FTM"));
			alone.setSat_ftc(rst.get(0, "SAT_FTC"));
			alone.setSat_orbit_height(rst.get(0, "SAT_ORBIT_HEIGHT"));
			alone.setSat_cycle(rst.get(0, "SAT_CYCLE"));
			alone.setSat_longtitude(rst.get(0, "SAT_LONGTITUDE"));
			alone.setFirst_designer(rst.get(0, "FIRST_DESIGNER"));
		}
		return alone;
	}

	@Override
	public boolean add(SatInfoDetail satInfoDetail) {
		satInfoDetail.setSat_id(Integer.parseInt(BaseInfoCommon.getSatOrTmId()));
		return satInfoDao.add(satInfoDetail);
	}

	@Override
	public Map<String, Object> querySatByCode(String satCode) {
		DBResult dbr = satInfoDao.querySatByCode(satCode);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public Map<String, Object> querySatByMid(String mid) {
		DBResult dbr = satInfoDao.querySatByMid(mid);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public String findSatBasicInfoQueryPageLimit(String userId,String sat_name,
			String design_org, String user_org, String pageSize,
			String pageIndex, String launch_time_start, String launch_time_end) {
		DBResult result = satInfoDao.findSatBasicInfoQueryPageLimit(userId,sat_name,
				design_org, user_org, pageSize,
				pageIndex, launch_time_start, launch_time_end);
		List<SatInfoDetail> satInfoDetails = new ArrayList<SatInfoDetail>();
		Map<String,Object> pageData = new HashMap<String, Object>();
		if(result == null || result.getRows()==0){
			pageData.put("Rows", satInfoDetails);
			pageData.put("Total", 0);
		}else{
			for(int i = 0; i<result.getRows();i++){
				SatInfoDetail alone = new SatInfoDetail();
				alone.setMid(Integer.parseInt(result.get(i, "MID")));
				alone.setSat_id(Integer.parseInt(result.get(i, "SAT_ID")));
				alone.setSat_name((result.get(i, "SAT_NAME")));
				alone.setSat_code((result.get(i, "SAT_CODE")));
				alone.setStatus((result.get(i, "STATUS")));
				alone.setDesign_org(result.get(i, "DESIGN_ORG"));
				alone.setUser_org(result.get(i, "USER_ORG"));
				alone.setDesign_life(result.get(i, "life"));
				alone.setOver_life(result.get(i, "OVER_LIFE"));
				alone.setMulticast_address((result.get(i, "MULTICAST_ADDRESS")));
				alone.setUdp_port(result.get(i, "UDP_PORT"));
				alone.setLaunch_time(result.get(i, "TIME"));
				satInfoDetails.add(alone);
			}
			pageData.put("Rows", satInfoDetails);
			pageData.put("Total", result.getTotal());
		}
		return new Gson().toJson(pageData);
	}
}

package com.xpoplarsoft.baseInfo.aloneinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.aloneinfo.bean.StandAloneInfo;
import com.xpoplarsoft.baseInfo.aloneinfo.dao.IAloneInfoDao;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class AloneInfoService implements IAloneInfoService{
	@Autowired
	private IAloneInfoDao aloneInfoDao;
	@Override
	public String standAloneInfoAdd(String stand_alone_name, String stand_alone_code,String sub_system_id) {
		int id=aloneInfoDao.findSeqId();
		DBParameter param = new DBParameter();
		param.setObject("sub_system_id", sub_system_id);
		param.setObject("stand_alone_id", id);
		param.setObject("stand_alone_name", stand_alone_name);
		param.setObject("stand_alone_code", stand_alone_code);
//		int resAdd=aloneInfoDao.resourceAdd(param);
//		standAloneInfo.setStand_alone_id(id);
		Boolean flag = aloneInfoDao.subSystemInfoAdd(param);
		if(flag) return "{success:'true',message:'新增单机成功'}";
		else return "{success:'false',message:'新增单机失败'}";
//		if(resAdd!=0&&aloneAdd!=0) return id;
//		return 0;
	}
	@Override
	public StandAloneInfo standAloneInfoById(String stand_alone_id) {
		DBParameter param = new DBParameter();
		param.setObject("stand_alone_id", stand_alone_id);
		DBResult result= aloneInfoDao.standAloneInfoById(param);
		StandAloneInfo standAloneInfo = new StandAloneInfo();
		standAloneInfo.setStand_alone_name(result.get(0, "stand_alone_name"));
		standAloneInfo.setStand_alone_code(result.get(0, "stand_alone_code"));
//		standAloneInfo.setStand_alone_id(Integer.parseInt(result.get(0, "stand_alone_id")));
		return standAloneInfo;
	}
	@Override
	public Boolean standAloneInfoUpdate(StandAloneInfo standAloneInfo) {
		DBParameter param = new DBParameter();
		param.setObject("stand_alone_id", standAloneInfo.getStand_alone_id());
		param.setObject("stand_alone_name", standAloneInfo.getStand_alone_name());
		param.setObject("stand_alone_code", standAloneInfo.getStand_alone_code());
		return aloneInfoDao.standAloneInfoUpdate(param);
	}
	@Override
	public Boolean standAloneInfoDeleteById(String stand_alone_id) {
		DBParameter param = new DBParameter();
		param.setObject("stand_alone_id", stand_alone_id);
		return aloneInfoDao.standAloneInfoDeleteById(param);
	}
	@Override
	public boolean judgeCodeExit(String sub_system_id, String stand_alone_code,
			String stand_alone_id) {
		DBResult dbr = aloneInfoDao.judgeCodeExit(sub_system_id,stand_alone_code,stand_alone_id);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}
	@Override
	public boolean judgeNameExit(String sub_system_id, String stand_alone_name,
			String stand_alone_id) {
		DBResult dbr = aloneInfoDao.judgeNameExit(sub_system_id,stand_alone_name,stand_alone_id);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}
}

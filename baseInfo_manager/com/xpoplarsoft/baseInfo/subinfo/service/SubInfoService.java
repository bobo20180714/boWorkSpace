package com.xpoplarsoft.baseInfo.subinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.subinfo.bean.SubSystemInfo;
import com.xpoplarsoft.baseInfo.subinfo.dao.ISubInfoDao;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;

@Service
public class SubInfoService implements ISubInfoService{
	@Autowired
	private ISubInfoDao subInfoDao;

//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String subSystemInfoAdd(SubSystemInfo subSystemInfo, String sat_id) {
		int id=subInfoDao.findSeqId();
		DBParameter param1 = new DBParameter();
		param1.setObject("sys_resource_id", id);
		param1.setObject("owner_id", sat_id);
//		Boolean resAdd=subInfoDao.resourceAdd(param1);
		subSystemInfo.setSub_system_id(id);
		DBParameter param = new DBParameter();
		param.setObject("sat_id", sat_id);
		param.setObject("sub_system_id", subSystemInfo.getSub_system_id());
		param.setObject("sub_system_name", subSystemInfo.getSub_system_name());
		param.setObject("sub_system_code", subSystemInfo.getSub_system_code());
		param.setObject("sub_system_type", subSystemInfo.getSub_system_type());
		param.setObject("status", subSystemInfo.getStatus());
		param.setObject("create_user", subSystemInfo.getCreate_user());
		param.setObject("create_time", subSystemInfo.getCreate_time());
		Boolean subAdd=subInfoDao.subSystemInfoAdd(param);
		if(subAdd)  return "{success:'true',message:'新增分系统成功'}";
		else return "{success:'false',message:'新增分系统失败'}";
	}

	@Override
	public SubSystemInfo subSystemInfoById(String sub_system_id) {
		DBParameter param = new DBParameter();
		param.setObject("sub_system_id", sub_system_id);
		DBResult rusult = subInfoDao.subSystemInfoById(param);
		SubSystemInfo subSystemInfo = new SubSystemInfo();
//		sub_system_id,name sub_system_name,
//		code sub_system_code,system_type_id sub_system_type,system_type_name 
//		sub_system_type_name,
//		status,create_user,create_time from sat_sub_alone_view
		subSystemInfo.setSub_system_id(Integer.parseInt(rusult.get(0, "sub_system_id")));
		subSystemInfo.setSub_system_name(rusult.get(0, "sub_system_name"));
		subSystemInfo.setSub_system_code(rusult.get(0, "sub_system_code"));
		subSystemInfo.setSub_system_type(rusult.get(0, "sub_system_type"));
		subSystemInfo.setSub_system_type_name(rusult.get(0, "sub_system_type_name"));
		subSystemInfo.setStatus(rusult.get(0, "status"));
		subSystemInfo.setCreate_time(rusult.get(0, "create_time"));
		subSystemInfo.setCreate_user(rusult.get(0, "create_user"));
		return subSystemInfo;
	}

	@Override
	public String subSystemInfoUpdate(SubSystemInfo subSystemInfo) {
		DBParameter param = new DBParameter();
		param.setObject("sub_system_name", subSystemInfo.getSub_system_name());
		param.setObject("sub_system_code", subSystemInfo.getSub_system_code());
		param.setObject("sub_system_id", subSystemInfo.getSub_system_id());
		Boolean result = subInfoDao.subSystemInfoUpdate(param);
		if(result){
			return "{success:'true',message:'修改成功'}";
		}else
		return "{success:'false',message:'修改失败'}";
	}

	@Override
	public Boolean subSystemInfoDeleteById(String sub_system_id) {
		DBParameter param = new DBParameter();
		param.setObject("sub_system_id", sub_system_id);
		return subInfoDao.subSystemInfoDeleteById(param);
	}

	
	@Override
	public List<Map<String, Object>> queryChildInfo(String owner_id) {
		DBResult dbr = subInfoDao.queryChildInfo(owner_id);
		List<Map<String, Object>> list = DBResultUtil.resultToList(dbr);
		return list;
	}

	@Override
	public boolean judgeCodeExit(String sat_id,String sub_system_code, String sub_system_id) {
		DBResult dbr = subInfoDao.judgeCodeExit(sat_id,sub_system_code,sub_system_id);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean judgeNameExit(String sat_id,String sub_system_name, String sub_system_id) {
		DBResult dbr = subInfoDao.judgeNameExit(sat_id,sub_system_name,sub_system_id);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}
	
}

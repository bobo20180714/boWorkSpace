package com.xpoplarsoft.queryBylimit.service;

import java.util.ArrayList;
import java.util.List;

import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.queryBylimit.bean.SatBean;
import com.xpoplarsoft.queryBylimit.bean.SatSubAloneBean;
import com.xpoplarsoft.queryBylimit.dao.IQueryResourceDao;
import com.xpoplarsoft.queryBylimit.dao.QueryResourceDao;

public class QueryResourceService implements IQueryResourceService {

	public IQueryResourceDao dao = new QueryResourceDao();
	
	@Override
	public List<SatBean> querySatList(String ownerId, String key) {
		DBResult dbResult = dao.querySatList(ownerId,key);
		return dbresultToSatBean(dbResult);
	}

	@Override
	public int querySatTotal(String ownerId,String key) {
		DBResult dbResult = dao.querySatList(ownerId,key);
		if (dbResult != null && dbResult.getRows() > 0) {
			return dbResult.getTotal();
		}
		return 0;
	}

	@Override
	public List<SatBean> querySatByPage(String ownerId, String key, int page,
			int pageSize) {
		DBResult dbResult = dao.querySatByPage(ownerId,key,page,pageSize);
		return dbresultToSatBean(dbResult);
	}
	
	
	@Override
	public List<SatSubAloneBean> findSatTree(String userId, String key) {
		DBResult dbResult = dao.findSatTree(userId,key);
		//列表数据
		List<SatSubAloneBean> infoList = new ArrayList<SatSubAloneBean>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			SatSubAloneBean satbean = null;
			for (int i = 0; i < rows; i++) {
				satbean = new SatSubAloneBean();
				satbean.setSys_resource_id(dbResult.getObject(i, "sys_resource_id").toString());
				satbean.setName(dbResult.getObject(i, "name").toString());
				satbean.setCode(dbResult.getObject(i, "code").toString());
				satbean.setOwner_id(dbResult.getObject(i, "owner_id").toString());
				satbean.setType(Integer.parseInt(dbResult.getObject(i, "type").toString()));
				satbean.setLeaf(dbResult.getObject(i, "leaf").toString());
				satbean.setSat_id(Integer.parseInt(dbResult.getObject(i, "sat_id").toString()));
				satbean.setSat_code(dbResult.getObject(i, "sat_code").toString());
				satbean.setSat_name(dbResult.getObject(i, "sat_name").toString());
				satbean.setMid(Integer.parseInt(dbResult.getObject(i, "mid").toString()));
				infoList.add(satbean);
			}
		}
		return infoList;
	}
	
	/**
	 * 将结果转为卫星对象集合
	 * @param dbResult
	 * @return
	 */
	private List<SatBean> dbresultToSatBean(DBResult dbResult){
		//列表数据
		List<SatBean> infoList = new ArrayList<SatBean>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			SatBean satbean = null;
			for (int i = 0; i < rows; i++) {
				satbean = new SatBean();
				satbean.setSat_id(Integer.parseInt(dbResult.getObject(i, "sat_id").toString()));
				satbean.setSat_code(dbResult.getObject(i, "sat_code").toString());
				satbean.setSat_name(dbResult.getObject(i, "sat_name").toString());
				satbean.setMid(Integer.parseInt(dbResult.getObject(i, "mid").toString()));
				infoList.add(satbean);
			}
		}
		return infoList;
	}

	@Override
	public List<SatSubAloneBean> findGrantUserGroupEquipmentTree(String userId,
			String sys_resource_id, String key) {
		DBResult dbResult = dao.findGrantUserGroupEquipmentTree(userId,sys_resource_id,key);
		//列表数据
		List<SatSubAloneBean> infoList = new ArrayList<SatSubAloneBean>();
		if (dbResult != null && dbResult.getRows() > 0) {
			//获取总条数
			int rows = dbResult.getRows();
			SatSubAloneBean satbean = null;
			for (int i = 0; i < rows; i++) {
				satbean = new SatSubAloneBean();
				satbean.setSys_resource_id(dbResult.getObject(i, "sys_resource_id").toString());
				satbean.setName(dbResult.getObject(i, "name").toString());
				satbean.setCode(dbResult.getObject(i, "code").toString());
				satbean.setOwner_id(dbResult.getObject(i, "owner_id").toString());
				satbean.setType(Integer.parseInt(dbResult.getObject(i, "type").toString()));
				satbean.setLeaf(dbResult.getObject(i, "leaf").toString());
				satbean.setSat_id(Integer.parseInt(dbResult.getObject(i, "sat_id").toString()));
				satbean.setSat_code(dbResult.getObject(i, "sat_code").toString());
				satbean.setSat_name(dbResult.getObject(i, "sat_name").toString());
				satbean.setMid(Integer.parseInt(dbResult.getObject(i, "mid").toString()));
				infoList.add(satbean);
			}
		}
		return infoList;
	}

	@Override
	public boolean judgeHaveSatLimit(String userAccount, String satMid) {
		return dao.judgeHaveSatLimit(userAccount,satMid);
	}

}

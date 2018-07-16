package com.xpoplarsoft.baseInfo.orbitrelated.service;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedBean;
import com.xpoplarsoft.baseInfo.orbitrelated.bean.OrbitRelatedFieldBean;
import com.xpoplarsoft.baseInfo.orbitrelated.dao.IOrbitRelatedDao;
import com.xpoplarsoft.baseInfo.satInfo.bean.SatInfoDetail;
import com.xpoplarsoft.baseInfo.satInfo.service.ISatInfoService;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;
import com.xpoplarsoft.framework.db.impl.DBConnect;

@Service
public class OrbitRelatedService implements IOrbitRelatedService {
	

	private static Log log = LogFactory.getLog(OrbitRelatedService.class);
	
	@Autowired
	private IOrbitRelatedDao orbitRelatedDao;
	@Autowired
	private ISatInfoService satInfoService;
	
	@Override
	public Map<String, Object> queryOrbitRelatedByPage(String satId,
			CommonBean bean){
		DBResult dbr = orbitRelatedDao.queryOrbitRelatedByPage(satId,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}
	
	@Override
	public Map<String, Object> queryStartOrbitRelatedByPage(String satId,
			CommonBean bean){
		DBResult dbr = orbitRelatedDao.queryStartOrbitRelatedByPage(satId,bean);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public List<OrbitRelatedFieldBean> findOrbitRelatedFieldList(String jsjg_id) {
		List<OrbitRelatedFieldBean> list = new ArrayList<OrbitRelatedFieldBean>();
		DBResult dbr = orbitRelatedDao.findOrbitRelatedFieldList(jsjg_id);
		OrbitRelatedFieldBean bean = null;
		for (int i = 0; i < dbr.getRows(); i++) {
			bean = new OrbitRelatedFieldBean();
			bean.setField_id(dbr.get(i, "field_id"));
			bean.setJsjg_id(Integer.parseInt(dbr.get(i, "jsjg_id")));
			bean.setField_name(dbr.get(i, "field_name"));
			bean.setField_code(dbr.get(i, "field_code"));
			bean.setField_type(Integer.parseInt(dbr.get(i, "field_type")));
			bean.setField_comment(dbr.get(i, "field_comment"));
			bean.setIs_display_flag(dbr.get(i, "is_display_flag"));
			bean.setField_length(dbr.get(i, "field_length"));
			bean.setFiel_dscale(dbr.get(i, "fiel_dscale"));
			list.add(bean);
		}
		return list;
	}

	@Override
	public 
	Map<String, Object> queryOrbitFieldByPage(CommonBean bean, String jsjg_id){
		DBResult dbr = orbitRelatedDao.queryOrbitFieldByPage(bean,jsjg_id);
		return DBResultUtil.dbResultToPageData(dbr);
	}

	@Override
	public Map<String, Object> queryOrbitRelatedByCode(String satId,
			String jsjg_code) {
		DBResult dbr = orbitRelatedDao.queryOrbitRelatedByCode(satId,jsjg_code);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public boolean addRelated(OrbitRelatedBean bean) {
		orbitRelatedDao.addRelated(bean);
		//默认新增两个字段pk_id,time
		return addDefaultField(bean.getJsjg_id());
	}

	private boolean addDefaultField(int jsjg_id) {
		//默认新增一个时间字段
		OrbitRelatedFieldBean fieldBean = new OrbitRelatedFieldBean();
		fieldBean.setJsjg_id(jsjg_id);
		fieldBean.setField_code("pk_id");
		fieldBean.setField_name("主键");
		fieldBean.setField_comment("主键");
		fieldBean.setField_length("50");
		fieldBean.setFiel_dscale("0");
		fieldBean.setField_type(3);
		fieldBean.setField_order("1");
		addField(fieldBean);
		//默认新增一个时间字段
		fieldBean = new OrbitRelatedFieldBean();
		fieldBean.setJsjg_id(jsjg_id);
		fieldBean.setField_code("time");
		fieldBean.setField_name("时间");
		fieldBean.setField_comment("时间");
		fieldBean.setField_length("50");
		fieldBean.setFiel_dscale("0");
		fieldBean.setField_type(5);
		fieldBean.setField_order("2");
		return addField(fieldBean);
		
	}

	@Override
	public boolean updateRelatedStatus(String[] jsjgArr, String status) {
		for (int i = 0; i < jsjgArr.length; i++) {
			if("".equals(jsjgArr[i])){
				continue;
			}
			boolean flag = orbitRelatedDao.updateRelatedStatus(jsjgArr[i],status);
			if(!flag){
				return flag;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> viewRelated(String jsjg_id) {
		DBResult dbr = orbitRelatedDao.viewRelated(jsjg_id);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public boolean updateRelated(OrbitRelatedBean bean) {
		return orbitRelatedDao.updateRelated(bean);
	}

	@Override
	public boolean addField(OrbitRelatedFieldBean bean) {
		return orbitRelatedDao.addField(bean);
	}

	@Override
	public boolean updateFieldStatus(String[] fieldIdArr, String status) {
		for (int i = 0; i < fieldIdArr.length; i++) {
			if("".equals(fieldIdArr[i])){
				continue;
			}
			boolean flag = orbitRelatedDao.updateFieldStatus(fieldIdArr[i],status);
			if(!flag){
				return flag;
			}
		}
		return true;
	}

	@Override
	public boolean updateField(OrbitRelatedFieldBean bean) {
		return orbitRelatedDao.updateField(bean);
		}

	@Override
	public Map<String, Object> viewField(String field_id) {
		DBResult dbr = orbitRelatedDao.viewField(field_id);
		return DBResultUtil.dbResultToMap(dbr);
	}

	@Override
	public List<OrbitRelatedBean> findOrbitrelatedList(String satId,String key) {
		List<OrbitRelatedBean> beanList = new ArrayList<OrbitRelatedBean>();
		DBResult dbr = orbitRelatedDao.findOrbitrelatedList(satId,key);
		OrbitRelatedBean bean = null;
		for (int i = 0; dbr != null && i < dbr.getRows(); i++) {
			bean = new OrbitRelatedBean();
			bean.setJsjg_id(Integer.parseInt(dbr.get(i, "jsjg_id")));
			bean.setJsjg_code(dbr.get(i, "jsjg_code"));
			bean.setJsjg_name(dbr.get(i, "jsjg_name"));
			
			bean.setStart_time(dbr.get(i, "start_time"));
			bean.setEnd_time(dbr.get(i, "end_time"));
			bean.setView_col(dbr.get(i, "view_col"));
			beanList.add(bean);
		}
		return beanList;
	}

	@Override
	public boolean startRelated(String jsjgId) {
		//修改状态，为2
		boolean flag = orbitRelatedDao.updateRelatedStatus(jsjgId,"2");
		return flag;
	}

	@Override
	public boolean createTabel(String jsjgId,String satId) {
		Map<String, Object> relateMap = viewRelated(jsjgId);
		SatInfoDetail sat = satInfoService.getSatBasicInfoById(satId);
		String tableName = "" + relateMap.get("jsjg_code") + "_" +sat.getMid();
		tableName = tableName.toUpperCase();
		@SuppressWarnings("deprecation")
		DBResult fieldResult = SQLFactory.getSqlComponent()
				.queryInfo("select * from user_tables where table_name = '"
						+ tableName + "'");
		if (fieldResult != null && fieldResult.getRows() > 0) {
			//表已经存在
			return false;
		}
		List<OrbitRelatedFieldBean> collection = findOrbitRelatedFieldList(jsjgId);
		if(collection == null || collection.size() == 0){
			//没有字段信息，不创建 表结构
			return false;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("create table ");
		sql.append(" ");
		sql.append(tableName);
		sql.append(" ");
		sql.append("(");
		for (OrbitRelatedFieldBean cf : collection) {
			String cName = cf.getField_code();
			String cType = "VARCHAR2";
			int l = cf.getField_length()==null||"".equals(cf.getField_length())?0:Integer.parseInt(cf.getField_length());
			int s = cf.getFiel_dscale()==null||"".equals(cf.getFiel_dscale())?0:Integer.parseInt(cf.getFiel_dscale());
			String cScale = s == 0 ? "" : "," + s;
			String cLength = l == 0 ? "" : "(" + l + cScale + ")";
			if("pk_id".equals(cName.toLowerCase())){
				sql.append(cName);
				sql.append(" ");
				sql.append(cType);
				sql.append(l == 0 ? "(50)":"("+l+")");
				sql.append(" primary key,");
				continue;
			}
			switch (cf.getField_type()) {
			case 5:
				cType = "TIMESTAMP";
				sql.append(cName);
				sql.append(" ");
				sql.append(cType);
				break;
			case 0:
			case 2:
				cType = "NUMBER";
				sql.append(cName);
				sql.append(" ");
				sql.append(cType);
				sql.append(cLength);
				break;
			case 1:
				cType = "NUMBER";
				sql.append(cName);
				sql.append(" ");
				sql.append(cType);
				sql.append(cLength);
				break;
			case 3:
				cType = "VARCHAR2";
				sql.append(cName);
				sql.append(" ");
				sql.append(cType);
				sql.append("("+l+")");
				break;
			default:
				cType = "VARCHAR2(50)";
				sql.append(cName);
				break;
			};
			sql.append(",");
		}
		if (sql.charAt(sql.length() - 1) == ',') {
			sql = sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(")");
		Statement sqlStmt;
		try {
			sqlStmt = DBConnect.getConnect().createStatement();
			sqlStmt.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("在轨相关数据表【"+tableName+"】创建失败："+e);
			log.error("在轨相关数据表【"+tableName+"】创建语句为："+sql.toString());
		}
		return true;
	
	}

	@Override
	public boolean judgeCodeIsExit(int jsjg_id, String jsjg_code) {
		DBResult dbr = orbitRelatedDao.queryRelateByIdAndCode(jsjg_id,jsjg_code);
		if(dbr != null && dbr.getRows() > 0){
			//已经存在
			return true;
		}
		return false;
	}

	@Override
	public boolean judgeFieldName(int jsjg_id, String field_code, String field_id) {
		DBResult dbr = orbitRelatedDao.queryFieldByIdAndCode(jsjg_id,field_code,field_id);
		if(dbr != null && dbr.getRows() > 0){
			//已经存在
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findAllOrbitrelatedList() {
		DBResult dbr = orbitRelatedDao.findAllOrbitrelatedList();
		return DBResultUtil.resultToList(dbr);
	}

	@Override
	public boolean addSatRelatedInfo(String satId, String jsjgId) {
		return orbitRelatedDao.addSatRelatedInfo(satId,jsjgId);
	}

	@Override
	public boolean removeSatRelatedInfo(String satId, String jsjgId) {
		return orbitRelatedDao.removeSatRelatedInfo(satId,jsjgId);
	}
}

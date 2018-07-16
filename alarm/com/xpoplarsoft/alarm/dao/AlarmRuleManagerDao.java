package com.xpoplarsoft.alarm.dao;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.modal.PageBean;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.alarm.util.AlarmUtil;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.db.SQLFactory;

@Repository
public class AlarmRuleManagerDao implements IAlarmRuleManagerDao {
	XStream x = new XStream();

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public Map<String,Object> findLimitRuleInfos(String satid, String key,
			 CommonBean commonBean) {
		
		Map<String,Object> pageData = new HashMap<String, Object>();
		
		List<LimitRuleInfo> rs = new ArrayList<LimitRuleInfo>();
		DBParameter jParameter = new DBParameter();
		//孟祥超 修改 ;原因： satid没有赋值
		jParameter.setObject("satid", satid);
		jParameter.setObject("key", key);
		jParameter.setObject("judgetype", 0);
		DBResult dbs = SQLFactory.getSqlComponent().pagingQueryInfo("alarm",
				"selectLimitRuleBySat", jParameter,commonBean.getPage(),commonBean.getPagesize());
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				//孟祥超 修改 处理规则内容
				Clob ruleContentClob = (Clob)dbs.getObject(i, "rulecontent");
				//转化为字符串
				String ruleContent = AlarmUtil.clobToString(ruleContentClob);
				x.processAnnotations(LimitRuleInfo.class);
				LimitRuleInfo lr = new LimitRuleInfo();
				try {
					if(!"".equals(ruleContent)){
						lr = (LimitRuleInfo) x.fromXML(ruleContent);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//内容解析错误
					log.error("规则内容解析错误！");
				}
				lr.setRuleid(dbs.get(i, "ruleid"));
				lr.setSatid(dbs.get(i, "sat_id"));
				lr.setTmcode(dbs.get(i, "tm_param_code"));
				lr.setTmid(dbs.get(i, "tm_param_id"));
				lr.setTmname(dbs.get(i, "tm_param_name"));
				lr.setJudgetype(dbs.get(i, "judgetype"));
				lr.setCanalarm(dbs.get(i,"canalarm"));
				lr.setRulecontent(ruleContent);
				lr.setTmtype(dbs.get(i, "tm_param_type"));
				rs.add(lr);
			}
			pageData.put("Total", dbs.getTotal());
		}else{
			pageData.put("Total", 0);
		}
		pageData.put("Rows", rs);
		return pageData;
	}

	@Override
	public List<StateRuleInfo> findStateRuleInfos(String satid, String key,
			int start, int limit) {
		List<StateRuleInfo> rs = new ArrayList<StateRuleInfo>();
		DBParameter jParameter = new DBParameter();
		//孟祥超 修改
		jParameter.setObject("satid", satid);
		jParameter.setObject("key", key);
		jParameter.setObject("start", start);
		jParameter.setObject("limit", limit);
		//孟祥超 修改 ;原因： sqlID重复
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"selectStateRuleBySatid", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				//孟祥超 修改 处理规则内容
				Clob ruleContentClob = (Clob)dbs.getObject(i, "rulecontent");
				//转化为字符串
				String ruleContent = AlarmUtil.clobToString(ruleContentClob);
				x.processAnnotations(StateRuleInfo.class);
				StateRuleInfo lr = new StateRuleInfo();
				try {
					if(!"".equals(ruleContent)){
						lr = (StateRuleInfo) x.fromXML(ruleContent);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//内容解析错误
					log.error("规则内容解析错误！");
				}
				lr.setRuleid(dbs.get(i, "ruleid"));
				lr.setSatid(dbs.get(i, "sat_id"));
				lr.setTmcode(dbs.get(i, "tm_param_code"));
				lr.setTmid(dbs.get(i, "tm_param_id"));
				lr.setTmname(dbs.get(i, "tm_param_name"));
				lr.setJudgetype(dbs.get(i, "judgetype"));
				lr.setRulecontent(ruleContent);
				rs.add(lr);
			}
		}
		return rs;
	}

	@Override
	public List<AlarmRuleInfo> getAlarmRuleInfosForTmId(String satId,
			String tmId) {
		List<AlarmRuleInfo> rs = new ArrayList<AlarmRuleInfo>();
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("satId", satId);
		jParameter.setObject("tmId", tmId);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"selectStateRuleBySat", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				AlarmRuleInfo lr = null;
				String judgetype = dbs.get(0, "judgetype");
				String ruleContent = dbs.get(0, "rulecontent");
				if (judgetype.equals("0")) {
					x.processAnnotations(LimitRuleInfo.class);
					lr = (LimitRuleInfo) x.fromXML(ruleContent);
				} else if (judgetype.equals("2")) {
					x.processAnnotations(StateRuleInfo.class);
					lr = (StateRuleInfo) x.fromXML(ruleContent);
				} else {
					lr = new AlarmRuleInfo();
				}

				lr.setRuleid(dbs.get(0, "ruleid"));
				lr.setSatid(dbs.get(0, "sat_id"));
				lr.setTmcode(dbs.get(0, "tm_param_code"));
				lr.setTmid(dbs.get(0, "tm_param_id"));
				lr.setTmname(dbs.get(0, "tm_param_name"));
				lr.setJudgetype(judgetype);
				lr.setRulecontent(ruleContent);
				rs.add(lr);
			}
		}
		return rs;
	}

	@Override
	public boolean addAlarmTmInfo(AlarmRuleInfo alarmRuleInfo) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("satid", alarmRuleInfo.getSatid());
		jParameter.setObject("tmid", alarmRuleInfo.getTmid());
		jParameter.setObject("tmcode", alarmRuleInfo.getTmcode());
		jParameter.setObject("tmname", alarmRuleInfo.getTmname());
		jParameter.setObject("judgetype", alarmRuleInfo.getJudgetype());
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"insertTmAlarmInfo", jParameter);
	}

	@Override
	public boolean addLimitRuleInfo(LimitRuleInfo limitRuleInfo) {

		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		x.processAnnotations(LimitRuleInfo.class);
		String rulecontent = x.toXML(limitRuleInfo);
		DBParameter jParameter = new DBParameter();
		//获取规则id
		String ruleid = querySequences();
		//孟祥超 add 将规则id赋值到对象中
		limitRuleInfo.setRuleid(ruleid);
		//孟祥超 add 将规则内容赋值到对象中
		limitRuleInfo.setRulecontent(rulecontent);
		jParameter.setObject("tmid", limitRuleInfo.getTmid());
		jParameter.setObject("ruleid", ruleid);
		jParameter.setObject("canalarm", limitRuleInfo.getCanalarm());
		jParameter.setObject("judgetype", limitRuleInfo.getJudgetype());
		jParameter.setObject("rulecontent", rulecontent);
		jParameter.setObject("canalarm", limitRuleInfo.getCanalarm());
		jParameter.setObject("judgecount", limitRuleInfo.getJudgecount());
		List<DBParameter> paramList1 = new ArrayList<DBParameter>();
		paramList1.add(jParameter);
		
		//若该报警有效0，根据tmid将其他规则修改为无效1
		if("0".equals(limitRuleInfo.getCanalarm())){
			DBParameter param2 = new DBParameter();
			param2.setObject("tmid", limitRuleInfo.getTmid());
			param2.setObject("ruleid", ruleid);
			param2.setObject("canalarm", "1");
			List<DBParameter> paramList2 = new ArrayList<DBParameter>();
			paramList2.add(param2);
			//修改规则为无效
			map.put("updateTmCanAlarm", paramList2);
		}
		//修改规则信息
		map.put("insertTmRuleInfo", paramList1);
		return SQLFactory.getSqlComponent().batchUpdate("alarm", map);
		
//		return SQLFactory.getSqlComponent().updateInfo("alarm",
//				"insertTmRuleInfo", jParameter);
	}

	@Override
	public boolean updateLimitRuleInfo(LimitRuleInfo limitRuleInfo) {
		
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		
		x.processAnnotations(LimitRuleInfo.class);
		//去掉规则内容里面的rulecontent节点
		limitRuleInfo.setRulecontent("");
		String rulecontent = x.toXML(limitRuleInfo);
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", limitRuleInfo.getRuleid());
		jParameter.setObject("judgecount", limitRuleInfo.getJudgecount());
		jParameter.setObject("rulecontent", rulecontent);
		jParameter.setObject("canalarm", limitRuleInfo.getCanalarm());
		List<DBParameter> paramList1 = new ArrayList<DBParameter>();
		paramList1.add(jParameter);
		//修改规则信息
		map.put("updateTmRuleInfo", paramList1);
		
		//若该报警有效0，根据tmid将其他规则修改为无效1
		if("0".equals(limitRuleInfo.getCanalarm())){
			DBParameter param2 = new DBParameter();
			param2.setObject("tmid", limitRuleInfo.getTmid());
			param2.setObject("ruleid", limitRuleInfo.getRuleid());
			param2.setObject("canalarm", "1");
			List<DBParameter> paramList2 = new ArrayList<DBParameter>();
			paramList2.add(param2);
			//修改规则为无效
			map.put("updateTmCanAlarm", paramList2);
		}
		return SQLFactory.getSqlComponent().batchUpdate("alarm", map);
//		return SQLFactory.getSqlComponent().updateInfo("alarm",
//				"updateTmRuleInfo", jParameter);
	}

	@Override
	public boolean addTmStateAlarmInfo(StateRuleInfo stateRuleInfo) {
		x.processAnnotations(StateRuleInfo.class);
		String rulecontent = x.toXML(stateRuleInfo);
		DBParameter jParameter = new DBParameter();
		/** 孟祥超 add start*/
		//获取规则id
		String ruleid = querySequences();
		//孟祥超 add 将规则id赋值到对象中
		stateRuleInfo.setRuleid(ruleid);
		//孟祥超 add 将规则内容赋值到对象中
		stateRuleInfo.setRulecontent(rulecontent);
		//规则id赋值
		jParameter.setObject("ruleid", ruleid);
		/** 孟祥超 add end*/
		
		jParameter.setObject("tmid", Integer.parseInt(stateRuleInfo.getTmid()));
		jParameter.setObject("canalarm", stateRuleInfo.getCanalarm());
		jParameter.setObject("judgetype", Integer.parseInt(stateRuleInfo.getJudgetype()));
		jParameter.setObject("rulecontent", rulecontent);
		jParameter.setObject("judgecount", stateRuleInfo.getJudgecount());
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"insertTmRuleInfo", jParameter);
	}

	@Override
	public boolean updateTmStateAlarmInfo(StateRuleInfo stateRuleInfo) {
		x.processAnnotations(StateRuleInfo.class);
		String rulecontent = x.toXML(stateRuleInfo);
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", stateRuleInfo.getRuleid());
		jParameter.setObject("judgecount", stateRuleInfo.getJudgecount());
		jParameter.setObject("rulecontent", rulecontent);
		jParameter.setObject("canalarm", stateRuleInfo.getCanalarm());
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"updateTmRuleInfo", jParameter);
	}

	@Override
	public boolean deleteRuleInfo(String ruleid) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", ruleid);
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"deleteTmRuleInfo", jParameter);
	}

	@Override
	public boolean updateTmAlarmInfoCanalarm(String tmid, String ruleid,
			int canAlarm) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", ruleid);
		jParameter.setObject("tmid", tmid);
		jParameter.setObject("canalarm", canAlarm);
		SQLFactory.getSqlComponent().updateInfo("alarm",
				"updateTmRuleCanalarmByTm", jParameter);
		return SQLFactory.getSqlComponent().updateInfo("alarm",
				"updateTmRuleCanalarmById", jParameter);
	}

	@Override
	public AlarmRuleInfo getAlarmRuleInfo(String ruleid) {
		AlarmRuleInfo ar = new AlarmRuleInfo();
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", ruleid);
		String sqlId = "getRuleById";
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				sqlId, jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			//孟祥超 修改 处理规则内容
			Clob ruleContentClob = (Clob)dbs.getObject(0, "rulecontent");
			//转化为字符串
			String ruleContent = AlarmUtil.clobToString(ruleContentClob);
			
			ar.setRuleid(dbs.get(0, "ruleid"));
			ar.setSatid(dbs.get(0, "sat_id"));
			ar.setTmcode(dbs.get(0, "tm_param_code"));
			ar.setTmid(dbs.get(0, "tm_param_id"));
			ar.setTmname(dbs.get(0, "tm_param_name"));
			ar.setJudgetype(dbs.get(0, "judgetype"));
			ar.setRulecontent(ruleContent);
		}
		return ar;
	}

	@Override
	public LimitRuleInfo getLimitRuleInfo(String tmid,String ruleid) {
		LimitRuleInfo lr = new LimitRuleInfo();
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("tmid", tmid);
		jParameter.setObject("ruleid", ruleid);
		String sqlId = "getRuleById";
		if("".equals(ruleid)){
			sqlId = "getRuleByTmId";
		}
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				sqlId, jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			//孟祥超 修改 处理规则内容
			Clob ruleContentClob = (Clob)dbs.getObject(0, "rulecontent");
			//转化为字符串
			String ruleContent = AlarmUtil.clobToString(ruleContentClob);
			x.processAnnotations(LimitRuleInfo.class);
			try {
				if(!"".equals(ruleContent)){
					lr = (LimitRuleInfo) x.fromXML(ruleContent);
				}
			} catch (Exception e) {
				//内容解析错误
				log.error("规则内容解析错误！");
				e.printStackTrace();
			}
			lr.setRuleid(dbs.get(0, "ruleid"));
			lr.setSatid(dbs.get(0, "sat_id"));
			lr.setTmcode(dbs.get(0, "tm_param_code"));
			lr.setTmid(dbs.get(0, "tm_param_id"));
			lr.setTmname(dbs.get(0, "tm_param_name"));
			lr.setJudgetype(dbs.get(0, "judgetype"));
			lr.setCanalarm(dbs.get(0,"canalarm"));
			lr.setRulecontent(ruleContent);
		}
		return lr;
	}
	
	@Override
	public boolean updateTmAlarmForLimit(String tmid) {
		LinkedHashMap<String, List<DBParameter>> map = 
				new LinkedHashMap<String, List<DBParameter>>();
		
		List<DBParameter> list1 = new ArrayList<DBParameter>();
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("tmid", tmid);
		jParameter.setObject("judgetype", 0);
		list1.add(jParameter);
		map.put("updateTmAlarmJudgetype", list1);
		
		//根据tmid和judgetype将对应的规则改为不报警
		List<DBParameter> list2 = new ArrayList<DBParameter>();
		DBParameter param = new DBParameter();
		param.setObject("tmid", tmid);
		param.setObject("judgetype", 2);
		list2.add(param);
		map.put("updateToNoAlarm", list2);
		
		return SQLFactory.getSqlComponent().batchUpdate("alarm", map);
		
//		return SQLFactory.getSqlComponent().updateInfo("alarm",
//				"updateTmAlarmJudgetype", jParameter);
	}

	@Override
	public boolean updateTmAlarmForState(String tmid) {
		LinkedHashMap<String, List<DBParameter>> map = 
					new LinkedHashMap<String, List<DBParameter>>();
		
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("tmid", tmid);
		jParameter.setObject("judgetype", 2);
		List<DBParameter> list1 = new ArrayList<DBParameter>();
		list1.add(jParameter);
		map.put("updateTmAlarmJudgetype", list1);
		
		//根据tmid和judgetype将对应的规则改为不报警
		List<DBParameter> list2 = new ArrayList<DBParameter>();
		DBParameter param = new DBParameter();
		param.setObject("tmid", tmid);
		param.setObject("judgetype", 0);
		list2.add(param);
		map.put("updateToNoAlarm", list2);
		
		return SQLFactory.getSqlComponent().batchUpdate("alarm", map);
//		return SQLFactory.getSqlComponent().updateInfo("alarm",
//				"updateTmAlarmJudgetype", jParameter);
	}

	@Override
	public int getLimitRuleCount(String satid, String key) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("key", key);
		jParameter.setObject("satid", satid);
		jParameter.setObject("judgetype", 0);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"getLimitRuleCount", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			return Integer.valueOf(dbs.getValue(0, "count"));
		}
		return 0;
	}

	@Override
	public int getStateRuleCount(String satid, String key) {
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("key", key);
		jParameter.setObject("satid", satid);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"getStateRuleCount", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			return Integer.valueOf(dbs.getValue(0, "count"));
		}
		return 0;
	}

	@Override
	public StateRuleInfo getStateRuleInfo(String ruleid) {
		StateRuleInfo lr = null;
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("ruleid", ruleid);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"getRuleById", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			if (!dbs.get(0, "judgetype").equals("2")) {
				return null;
			}
			String ruleContent = dbs.get(0, "rulecontent");
			x.processAnnotations(StateRuleInfo.class);
			lr = (StateRuleInfo) x.fromXML(ruleContent);
			lr.setRuleid(dbs.get(0, "ruleid"));
			lr.setSatid(dbs.get(0, "sat_id"));
			lr.setTmcode(dbs.get(0, "tm_param_code"));
			lr.setTmid(dbs.get(0, "tm_param_id"));
			lr.setTmname(dbs.get(0, "tm_param_name"));
			lr.setJudgetype(dbs.get(0, "judgetype"));
			lr.setRulecontent(ruleContent);
		}
		return lr;
	}

	@Override
	public AlarmRuleInfo getCanAlarmRuleInfosForTmId(String satId, String tmId) {
		AlarmRuleInfo lr = null;
		DBParameter jParameter = new DBParameter();
		jParameter.setObject("satId", satId);
		jParameter.setObject("tmId", tmId);
		jParameter.setObject("canalarm", 0);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm",
				"selectStateRuleBySat", jParameter);
		if (dbs != null && dbs.getRows() > 0) {
			String judgetype = dbs.get(0, "judgetype");
			String ruleContent = dbs.get(0, "rulecontent");
			if (judgetype.equals("0")) {
				x.processAnnotations(LimitRuleInfo.class);
				lr = (LimitRuleInfo) x.fromXML(ruleContent);
			} else if (judgetype.equals("2")) {
				x.processAnnotations(StateRuleInfo.class);
				lr = (StateRuleInfo) x.fromXML(ruleContent);
			} else {
				lr = new AlarmRuleInfo();
			}

			lr.setRuleid(dbs.get(0, "ruleid"));
			lr.setSatid(dbs.get(0, "sat_id"));
			lr.setTmcode(dbs.get(0, "tm_param_code"));
			lr.setTmid(dbs.get(0, "tm_param_id"));
			lr.setTmname(dbs.get(0, "tm_param_name"));
			lr.setJudgetype(judgetype);
			lr.setRulecontent(ruleContent);
		}
		return lr;
	}

	/**
	 * 查询航天器组列表
	 * @author 孟祥超
	 * @param CommonBean bean
	 * @param DBParameter param
	 * @return
	 */
	@Override
	public DBResult queryAlarmPageList(CommonBean bean,DBParameter param) {

		DBResult result = null;
		if (bean.getPage() == -1 && bean.getPagesize() == -1) {
			//根据条件查询数据
			result = SQLFactory.getSqlComponent().queryInfo(
					bean.getTableSpace(), bean.getSqlId(), param);
		} else {
			//查询分页数据
			result = SQLFactory.getSqlComponent().pagingQueryInfo(
					bean.getTableSpace(), bean.getSqlId(), param,
					bean.getPage(), bean.getPagesize());
		}
		return result;
	}

	@Override
	public DBResult findStateTmList(String satid, String key,String judgetype,
			CommonBean bean) {
		 DBParameter param = new DBParameter();
		 param.setObject("satid", satid);
		 param.setObject("key", key);
		 param.setObject("judgetype", judgetype);
		DBResult result = SQLFactory.getSqlComponent().pagingQueryInfo(
				"alarm", "selectStateRuleBySatid", param,
				bean.getPage(), bean.getPagesize());
		return result;
	}

	/**
	 * 修改航天器组信息
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	@Override
	public boolean updatePageAlam(PageBean pageBean) {
		
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> parm1List = new ArrayList<DBParameter>();
		DBParameter parm1 = new DBParameter();
		parm1.setObject("ALARMPAGEID", pageBean.getPageId());
		parm1.setObject("PAGENAME", pageBean.getPageName());
		parm1List.add(parm1);
		map.put("updatePage", parm1List);
		
		//删除原来的组
		List<DBParameter> parm2List = new ArrayList<DBParameter>();
		DBParameter parm2 = new DBParameter();
		parm2.setObject("ALARMPAGEID", pageBean.getPageId());
		parm2List.add(parm2);
		map.put("deletePageSat", parm2List);
		
		//重新添加
		List<DBParameter> parm3List = new ArrayList<DBParameter>();
		DBParameter parm3 = null;
		List<String> satIdArr = pageBean.getSatIdArr();
		for (int i = 0;satIdArr!=null && i < satIdArr.size(); i++) {
			if(satIdArr.get(i) == null || "".equals(satIdArr.get(i))){
				continue;
			}
			parm3 = new DBParameter();
			parm3.setObject("ALARMPAGEID", pageBean.getPageId());
			parm3.setObject("SAT_ID", satIdArr.get(i));
			parm3List.add(parm3);
		}
		map.put("addPageSat", parm3List);
		
		boolean result = SQLFactory.getSqlComponent().batchUpdate("alarm", map);
		return result;
	}

	/**
	 * 查询序列DATASEQ
	 * @return
	 */
	@Override
	public String querySequences() {
		String pkid = "0";
	    DBResult rs = SQLFactory.getSqlComponent().queryInfo("alarm", "query_dataseq", new DBParameter());
	    if(rs!=null && rs.getRows()>0){
	    	pkid = rs.getObject(0,"pkid").toString();
	    }
		return pkid;
	}

	/**
	 * 保存航天器组
	 * @param pageBean
	 * @return
	 */
	@Override
	public boolean addPageAlam(PageBean pageBean) {
		
		LinkedHashMap<String, List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> parm1List = new ArrayList<DBParameter>();
		DBParameter parm1 = new DBParameter();
		parm1.setObject("ALARMPAGEID", pageBean.getPageId());
		parm1.setObject("PAGENAME", pageBean.getPageName());
		parm1List.add(parm1);
		map.put("addPage", parm1List);
		
		//关系表添加数据
		List<DBParameter> parm3List = new ArrayList<DBParameter>();
		DBParameter parm3 = null;
		List<String> satIdArr = pageBean.getSatIdArr();
		for (int i = 0;satIdArr!=null && i < satIdArr.size(); i++) {
			if(satIdArr.get(i) == null || "".equals(satIdArr.get(i))){
				continue;
			}
			parm3 = new DBParameter();
			parm3.setObject("ALARMPAGEID", pageBean.getPageId());
			parm3.setObject("SAT_ID", satIdArr.get(i));
			parm3List.add(parm3);
		}
		map.put("addPageSat", parm3List);
		
		boolean result = SQLFactory.getSqlComponent().batchUpdate("alarm", map);
		return result;
	}

	/**
	 * 查询规则内容
	 * @param tmid
	 * @return
	 */
	@Override
	public List<StateRuleInfo> queryStateRuleListByTM(String tmid) {
		
		List<StateRuleInfo> ruleList = new ArrayList<StateRuleInfo>();
		
		DBParameter param = new DBParameter();
		param.setObject("tmid", tmid);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm", "queryStateRuleListByTM", 
				param);
		if (dbs != null && dbs.getRows() > 0) {
			for (int i = 0; i < dbs.getRows(); i++) {
				Clob ruleContentClob = (Clob)dbs.getObject(i, "rulecontent");
				//转化为字符串
				String ruleContent = AlarmUtil.clobToString(ruleContentClob);
				x.processAnnotations(StateRuleInfo.class);
				StateRuleInfo stateRule = new StateRuleInfo();
				try {
					stateRule = (StateRuleInfo) x.fromXML(ruleContent);
				} catch (Exception e) {
					//内容解析错误
					log.error("规则内容解析错误！");
					e.printStackTrace();
				}
				stateRule.setRuleid(dbs.get(i, "ruleid"));
				stateRule.setTmid(dbs.get(i, "tmid"));
				stateRule.setCanalarm(dbs.get(i, "canalarm"));
				stateRule.setJudgetype(dbs.get(i, "judgetype"));
				stateRule.setJudgecount(dbs.get(i, "judgecount"));
				stateRule.setRulecontent(ruleContent);
				ruleList.add(stateRule);
			}
		}
		return ruleList;
	}

	/**
	 * 查询拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@Override
	public StateRuleInfo querySonStateRule(String ruleId) {
		
		DBParameter param = new DBParameter();
		param.setObject("ruleId", ruleId);
		DBResult dbs = SQLFactory.getSqlComponent().queryInfo("alarm", "querySonStateRule", 
				param);
		StateRuleInfo stateRule = new StateRuleInfo();
		if (dbs != null && dbs.getRows() > 0) {
			Clob ruleContentClob = (Clob)dbs.getObject(0, "rulecontent");
			//转化为字符串
			String ruleContent = AlarmUtil.clobToString(ruleContentClob);
			x.processAnnotations(StateRuleInfo.class);
			try {
				stateRule = (StateRuleInfo) x.fromXML(ruleContent);
			} catch (Exception e) {
				//内容解析错误
				log.error("规则内容解析错误！");
				e.printStackTrace();
			}
			stateRule.setRuleid(dbs.get(0, "ruleid"));
			stateRule.setTmid(dbs.get(0, "tmid"));
			stateRule.setTmcode(dbs.get(0, "tm_param_code"));
			stateRule.setTmname(dbs.get(0, "tm_param_name"));
			stateRule.setCanalarm(dbs.get(0, "canalarm"));
			stateRule.setJudgetype(dbs.get(0, "judgetype"));
			stateRule.setJudgecount(dbs.get(0, "judgecount"));
			stateRule.setRulecontent(ruleContent);
		}
		return stateRule;
	}

	/**
	 * 删除拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@Override
	public boolean deleteStateRule(String ruleId) {
		DBParameter param = new DBParameter();
		param.setObject("ruleId", ruleId);
		return SQLFactory.getSqlComponent().updateInfo("alarm", "deleteStateRule", param);
	}

	/**
	 * 查询对应的所有航天器
	 * @param alarmpageid
	 * @return
	 */
	@Override
	public DBResult queryAlarmPageSatsList(String alarmpageid) {
		DBParameter param = new DBParameter();
		param.setObject("alarmpageid", alarmpageid);
		return SQLFactory.getSqlComponent().queryInfo("alarm", "queryAlarmPageSatsList", param);
	}

	@Override
	public boolean deletePage(String[] pageArr) {
		LinkedHashMap<String,List<DBParameter>> map = new LinkedHashMap<String, List<DBParameter>>();
		List<DBParameter> paramList1 = new ArrayList<DBParameter>();
		for (int i = 0;pageArr!=null && i < pageArr.length; i++) {
			DBParameter param = new DBParameter();
			param.setObject("alarmpageid", pageArr[i]);
			paramList1.add(param);
		}
		map.put("delete_sat_page", paramList1);
		map.put("delete_alarmpage", paramList1);
		return SQLFactory.getSqlComponent().batchUpdate("alarm", map);
	}

	@Override
	public DBResult getAlarmType(String tmId) {
		DBParameter param = new DBParameter();
		param.setObject("tmId", tmId);
		return SQLFactory.getSqlComponent().queryInfo("alarm", "getAlarmType", param);
	}

	@SuppressWarnings("deprecation")
	@Override
	public DBResult judgePageIsexit(String pageName, String pageId) {
		String sql = "select * from ALARM_PAGE where pagename = '"+pageName+"' ";
		if(pageId != null && !"".equals(pageId)){
			sql = sql + " and ALARMPAGEID != "+pageId;
		}
		return SQLFactory.getSqlComponent().queryInfo(sql);
	}
}

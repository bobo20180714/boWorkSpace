package com.xpoplarsoft.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xpoplarsoft.alarm.dao.IAlarmRuleManagerDao;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.modal.PageBean;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.alarm.data.Unit;
import com.xpoplarsoft.common.util.DBResultUtil;
import com.xpoplarsoft.framework.bean.ResultBean;
import com.xpoplarsoft.framework.common.bean.CommonBean;
import com.xpoplarsoft.framework.db.DBParameter;
import com.xpoplarsoft.framework.db.DBResult;
import com.xpoplarsoft.framework.utils.BeanTools;

import compiler.AlarmAnalyse;
import compiler.CompilerInput;
import compiler.CompilerOutput;
import compiler.DataCheck;
import compiler.IDataCheck;

/**
 * 报警配置管理service实现
 * 
 * @author zhouxignlu 2015年7月21日
 */
@Service
public class AlarmInfoService implements IAlarmInfoService {
	
	private Log log = LogFactory.getLog(getClass());
	
	private static final Gson GSON = new Gson();
	
	@Autowired
	private IAlarmRuleManagerDao dao;

	@Override
	public Map<String, Object> findLimitRuleInfos(String satid, String key, CommonBean commonBean) {
		Map<String,Object> rsMap = dao.findLimitRuleInfos(satid,key,commonBean);
		return rsMap;
	}

	@Override
	public Map<String, Object> findStateRuleInfos(String satid, String key, String judgetype,CommonBean commonBean) {
//		Map rs = new HashMap();
		//查询状态参数列表
		DBResult dbResult = dao.findStateTmList(satid,key,judgetype,commonBean);
		Map<String, Object> rsMap = DBResultUtil.dbResultToPageData(dbResult);
		
//		rs.put("count", dao.getStateRuleCount(satid, key));
//		rs.put("list", dao.findStateRuleInfos(satid, key, start, limit));
		return rsMap;
	}

	@Override
	public List<AlarmRuleInfo> getAlarmRuleInfosForTmId(String satId,
			String tmId) {

		return dao.getAlarmRuleInfosForTmId(satId, tmId);
	}

	@Override
	public boolean addLimitRuleInfo(LimitRuleInfo limitRuleInfo) {
	
		return dao.addLimitRuleInfo(limitRuleInfo);
	}

	@Override
	public boolean updateLimitRuleInfo(LimitRuleInfo limitRuleInfo) {
		
		return dao.updateLimitRuleInfo(limitRuleInfo);
	}

	@Override
	public boolean addTmStateAlarmInfo(StateRuleInfo sonStateInfo) {
		
		return dao.addTmStateAlarmInfo(sonStateInfo);
	}

	@Override
	public boolean updateTmStateAlarmInfo(StateRuleInfo stateRuleInfo) {
		
		return dao.updateTmStateAlarmInfo(stateRuleInfo);
	}

	@Override
	public boolean deleteRuleInfo(String ruleid) {
		
		return dao.deleteRuleInfo(ruleid);
	}

	@Override
	public boolean updateTmAlarmInfoCanalarm(String tmid, String ruleid, int canAlarm) {
		
		return dao.updateTmAlarmInfoCanalarm(tmid, ruleid, canAlarm);
	}

	@Override
	public AlarmRuleInfo getAlarmRuleInfo(String ruleid) {
		
		return dao.getAlarmRuleInfo(ruleid);
	}
	
	@Override
	public LimitRuleInfo getLimitRuleInfo(String tmid,String ruleid) {
		
		return dao.getLimitRuleInfo(tmid,ruleid);
	}

	@Override
	public boolean updateTmAlarmForLimit(String tmid) {
		
		return dao.updateTmAlarmForLimit(tmid);
	}

	@Override
	public boolean updateTmAlarmForState(String tmid) {
		
		return dao.updateTmAlarmForState(tmid);
	}

	/**
	 * 查询航天器组列表
	 * @author 孟祥超
	 * @param request
	 * @param CommonBean bean
	 * @return
	 */
	@Override
	public String queryAlarmPageList(CommonBean bean) {
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][queryAlarmPageList]开始执行");
		}
		
		DBResult dbResult = dao.queryAlarmPageList(bean,new DBParameter());
		//列表数据
		Map<String,Object> pageData = new HashMap<String, Object>();

		//列表数据
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if (dbResult == null || dbResult.getRows() <= 0) {
			pageData.put("Total",0);
		} else {
			//获取总条数
			int rows = dbResult.getRows();
			pageData.put("Total",dbResult.getTotal());
			// 获取列名称
			String[] columnName = dbResult.getColName();
			Map<String,Object> cellMap = null;
			for (int i = 0; i < rows; i++) {
				cellMap = new HashMap<String,Object>();
				//该条记录的alarmpageid值
				String alarmpageid = "";
				for (int j = 0; j < columnName.length; j++) {
					String value = dbResult.getValue(i, columnName[j]);
					cellMap.put(columnName[j].toLowerCase(), value);
					if("alarmpageid".equals(columnName[j].toLowerCase())){
						alarmpageid = value;
					}
				}
				//根据alarmpageid查询航天器，并拼接成字符串
				cellMap.putAll(getSatsNamesAndIds(alarmpageid));
				infoList.add(cellMap);
			}
		}
		pageData.put("Rows",infoList);
		
		String rs = GSON.toJson(pageData);
		
		if(log.isInfoEnabled()){
			log.info("返回结果：["+rs+"]");
		}
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][queryAlarmPageList]执行结束");
		}
		return rs;
	}

	/**
	 * 根据alarmpageid查询航天器，并拼接成字符串
	 * @param alarmpageid
	 */
	private Map<String,String> getSatsNamesAndIds(String alarmpageid) {
		Map<String,String> map = new HashMap<String, String>();
		String names = "";
		String satIDs = "";
		//查询对应的所有航天器
		DBResult dbResult = dao.queryAlarmPageSatsList(alarmpageid);
		for (int i = 0;dbResult != null && i < dbResult.getRows(); i++) {
			String satName = dbResult.getValue(i, "sat_name");
			String satID = dbResult.getValue(i, "sat_id");
			if(i == 0){
				names = satName;
				satIDs = satID;
				continue;
			}
			names = names + "," + satName;
			satIDs = satIDs + "," + satID;
		}
		map.put("satsname", names);
		map.put("satsid", satIDs);
		return map;
	}

	/**
	 * 查询航天器组单条信息
	 * @author 孟祥超
	 * @param String pageId
	 * @return
	 */
	@Override
	public Map<String,Object> queryAlarmInfo(String pageId) {
		
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][queryAlarmInfo]开始执行");
		}
		
		DBParameter param = new DBParameter();
		param.setObject("pageId", pageId);
		CommonBean cbean = new CommonBean();
		cbean.setTableSpace("alarm");
		cbean.setSqlId("queryAlarmInfo");
		DBResult dbr = dao.queryAlarmPageList(cbean,param);

		//查询结果数据
		Map<String, Object> map = new HashMap<String, Object>();
		
		//存放已经选择的设备
		List<Map<String, Object>> satList = new ArrayList<Map<String,Object>>();
		if (dbr != null && dbr.getRows() > 0) {
			//分组名称
			map.put("pageName",dbr.getObject(0, "pagename"));
			map.put("pageId",dbr.getObject(0, "alarmpageid"));
			String[] colName = dbr.getColName();
			Map<String, Object> satMap = null;
			for (int i = 0; i < dbr.getRows(); i++) {
				satMap = new HashMap<String, Object>();
				for (String col : colName) {
					satMap.put(col.toLowerCase(), dbr.getObject(i, col));
				}
				satList.add(satMap);
			}
		}
		map.put("satList", satList);
		if(log.isInfoEnabled()){
			log.info("返回结果：["+map+"]");
		}
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][queryAlarmInfo]执行结束");
		}
		return map;
	}
	

	/**
	 * 新增航天器组信息（需返回pageId）
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	@Override
	public ResultBean addPageAlam(PageBean pageBean){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][addPageAlam]开始执行");
		}
		ResultBean rb = new ResultBean();
		//查询序列生成PAGEID
		String pageId = dao.querySequences();
		pageBean.setPageId(pageId);
		//保存航天器组
		boolean flag = dao.addPageAlam(pageBean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		rb.setData(pageId);
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][addPageAlam]执行结束");
		}
		return rb;
	}

	/**
	 * 修改航天器组信息
	 * @author 孟祥超
	 * @param PageBean pageBean
	 * @return
	 */
	@Override
	public ResultBean updatePageAlam(PageBean pageBean){
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][updatePageAlam]开始执行");
		}
		ResultBean rb = new ResultBean();
		
		boolean flag = dao.updatePageAlam(pageBean);
		if(flag){
			rb.setSuccess("true");
		}else{
			rb.setSuccess("false");
		}
		if(log.isDebugEnabled()){
			log.debug("组件[AlarmInfoService][updatePageAlam]执行结束");
		}
		return rb;
	}

	/**
	 * 根据tm获取拆分状态列表
	 * @author 孟祥超
	 * @param tmid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
		//返回结果数据
	public Map<String, Object> getStateRuleListByTM(String tmid) {
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		//查询拆分状态列表数据
		List<StateRuleInfo> list = dao.queryStateRuleListByTM(tmid);
		
		for (int i = 0;list!=null && i < list.size(); i++) {
			Map<String,Object> rsMap = new HashMap<String, Object>();
			//获取每一条结果
			StateRuleInfo stateRule = list.get(i);
			//放到返回结果中
			rsMap.putAll(BeanTools.beanToMap(stateRule));
			//返回规则内容
			List<Map<String,Object>> newContentList = new ArrayList<Map<String,Object>>(); 
			//获取原来规则内容
			List<Unit> oldContentList = new ArrayList<Unit>(); 
			if(stateRule.getUnit()!=null){
				oldContentList = stateRule.getUnit();
			}
			//新的规则内容
			Map<String,Object> newContentMap = null;
			for (int j = 0; oldContentList!=null && j < oldContentList.size();) {
				//获取旧规则内容
				Unit oldContentMap = oldContentList.get(j);
				//新的返回结果
				newContentMap = new HashMap<String, Object>();
				for (int k = 0; k < 4; k++) {
					if(j < oldContentList.size()){
						oldContentMap = oldContentList.get(j);
						//状态字
						newContentMap.put("data_"+k, oldContentMap.getData());
						//描述
						newContentMap.put("text_"+k, oldContentMap.getText());
						//级别
						newContentMap.put("alarmLevel_"+k, oldContentMap.getAlarmLevel());
					}
					j++;
				}
				newContentList.add(newContentMap);
			}
			//放到规则内容中
			rsMap.put("RULECONTENT", newContentList);
			rsList.add(rsMap);
		}
		//列表数据
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("Rows", rsList);
		return map;
	}

	/**
	 * 查询拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> querySonStateRule(String ruleId) {
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		//查询拆分状态列表数据
		StateRuleInfo stateRule = dao.querySonStateRule(ruleId);
		
		Map<String,Object> rsMap = new HashMap<String, Object>();
		if(stateRule != null) {
			//放到返回结果中
			rsMap.putAll(BeanTools.beanToMap(stateRule));
			//获取原来规则内容
			List<Unit> oldContentList = new ArrayList<Unit>(); 
			if(stateRule.getUnit()!=null){
				oldContentList = stateRule.getUnit();
			}
			//放到规则内容中
			rsMap.put("RULECONTENT", oldContentList);
			rsList.add(rsMap);
		}
		return rsMap;
	}

	/**
	 * 删除拆分状态
	 * @param request
	 * @param ruleId
	 * @return
	 */
	@Override
	public boolean deleteStateRule(String ruleId) {
		boolean flag =  dao.deleteStateRule(ruleId);
		return flag;
	}

	@Override
	public CompilerOutput checkRelationRule(String relation) {
		AlarmAnalyse analyse=new AlarmAnalyse();
		IDataCheck dc = new DataCheck();
		//以下1条语句为使用控制台输入测试接口
		CompilerInput input=new CompilerInput();
		input.setDataCheckService(dc);
		input.setSourceCode(relation);
		return analyse.ScriptAnalyse(input);
	}

	/**
	 * 根据tm和mask查询规则是否已存在
	 * @param tmid
	 * @param mask
	 * @param ruleId
	 * @return true:已存在
	 */
	@Override
	public boolean queryRuleByTmAndMask(String tmid, String mask, String ruleId) {
		boolean flag = false;
		//根据规则查询对应的所有掩码
		List<StateRuleInfo> listMask = dao.queryStateRuleListByTM(tmid);
		for (int i = 0;listMask != null && i < listMask.size(); i++) {
			StateRuleInfo ruleInfo = listMask.get(i);
			if(mask.equals(ruleInfo.getMask()) && "".equals(ruleId)){
				//新增拆分状态字时
				flag = true;
				break;
			}
			if(mask.equals(ruleInfo.getMask()) 
					&& !"".equals(ruleId)
					&& !ruleId.equals(ruleInfo.getRuleid())){
				//编辑拆分状态字时
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 删除航天器组
	 * @author 孟祥超
	 * @param request
	 * @param pageIds
	 * @return
	 */
	@Override
	public ResultBean deletePage(String[] pageArr) {
		ResultBean rb = new ResultBean();
		boolean flag = dao.deletePage(pageArr);
		rb.setSuccess(String.valueOf(flag));
		return rb;
	}

	@Override
	public boolean queryRuleByTmAndCanalarm(String tmid,
			String ruleId) {
		boolean flag = false;
		//根据参数查询对应的所有规则
		List<StateRuleInfo> listRule = dao.queryStateRuleListByTM(tmid);
		for (int i = 0;listRule != null && i < listRule.size(); i++) {
			StateRuleInfo ruleInfo = listRule.get(i);
			if("0".equals(ruleInfo.getCanalarm()) && "".equals(ruleId)){
				//新增拆分状态字时,已存在报警的规则
				flag = true;
				break;
			}
			if("0".equals(ruleInfo.getCanalarm()) 
					&& !"".equals(ruleId)
					&& !ruleId.equals(ruleInfo.getRuleid())){
				//编辑拆分状态字时,已存在报警的规则
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public Map<String, Object> getAlarmType(String tmId) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		DBResult dbr = dao.getAlarmType(tmId);
		if(dbr != null && dbr.getRows() > 0){
			rsMap.put("alarmType", dbr.get(0, "JUDGETYPE"));
			rsMap.put("ruleId", dbr.get(0, "RULEID"));
		}
		return rsMap;
	}

	@Override
	public boolean judgePageIsexit(String pageName, String pageId) {
		DBResult dbr = dao.judgePageIsexit(pageName,pageId);
		if(dbr != null && dbr.getRows() > 0){
			return true;
		}
		return false;
	}
	
	
}

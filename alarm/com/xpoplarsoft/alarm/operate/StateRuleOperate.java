package com.xpoplarsoft.alarm.operate;

import java.util.List;

import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.StateRuleInfo;
import com.xpoplarsoft.alarm.data.Unit;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheConst;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.operate.cache.AlarmOperateCache;

import compiler.AlarmAnalyse;
import compiler.CompilerInput;
import compiler.CompilerOutput;
import compiler.DataCheck;

/**
 * 状态字规则计算
 * 
 * @author zhouxignlu 2015年10月8日
 */
public class StateRuleOperate {
	private String deviceId;
	private long tmIntData;
	private AlarmParam param;
	private long maskCode;// 掩码，通过掩码来分解遥测参数

	private String[] rsArray = new String[5];// 判定结果数组
	// 关联条件
	private String relation;
	// 关联条件是否有效
	private boolean relationValid;

	private StateRuleInfo srule;

	public StateRuleOperate(AlarmParam param, AlarmRuleInfo rule) {
		deviceId = rule.getSatid();
		this.param = param;
		tmIntData = 0x5fffffffffffffffl;
		srule = (StateRuleInfo) rule;
	}

	public boolean operate() {
		if (srule.getCanalarm() != null && srule.getCanalarm().equals("0")) {
			int judgeCount = 3;
			try {
				judgeCount = Integer.parseInt(srule.getJudgecount());
			} catch (Exception e) {
				judgeCount = 3;
			}
			relation = srule.getRelation();
			// 关联条件是否有效
			relationValid = srule.getRelationValid().equals("0");
			maskCode = Long.parseLong(srule.getMask());
			List<Unit> stateUnitArray = srule.getUnit();

			tmIntData = (Long) AlarmCacheUtil.getParamById(deviceId,
					String.valueOf(param.getId())).getValue();
			// 判断是否进行关联条件验证
			if (relationValid) {
				// 启动关联条件判定
				boolean success = true;
				// 编译关联语句
				AlarmAnalyse analyse = new AlarmAnalyse();
				DataCheck dc = new DataCheck();
				CompilerInput c3input = new CompilerInput();
				c3input.setDataCheckService(dc);
				c3input.setSourceCode(relation);
				CompilerOutput c3output = analyse.ScriptAnalyse(c3input);
				if (c3output.getCheckflag()) {
					// 启动关联条件判定
					RelationRuleOperate relation = new RelationRuleOperate(
							deviceId, c3output.getStatementList());
					relation.operate();
					success = relation.getRs();
				}
				if (!success) {
					return false;
				}
			}

			// 进行报警判断
			long tmdata = tmIntData & maskCode;
			String paramId = String.valueOf(param.getId());
			String tmState = "";
			int level = 0;
			long unitData = 0;
			for (Unit unit : stateUnitArray) {
				unitData = Long.parseLong(unit.getData());
				if (unitData == tmdata) {
					// 规则计算符合报警要求,进行计数
					level = Integer.parseInt(unit.getAlarmLevel());
					tmState = unit.getText();
					//待实现报警日志记录
					return createAlarmReault(unitData, paramId, level, tmState,
							judgeCount);
				}
			}
		}
		return false;
	}

	/**
	 * 创建状态字报警信息
	 * 
	 * @param paramId
	 * @param ruleId
	 * @param level
	 * @param judgeCount
	 */
	private boolean createAlarmReault(long unitData, String paramId, int level,
			String tmState, int judgeCount) {
		String key = paramId + AlarmConst.SPLIT + srule.getRuleid();
		String str = AlarmOperateCache.setStateOperateResult(unitData, key,
				tmState, judgeCount, srule.getTmname());
		if (str.equals("")) {
			// 无报警
			return false;
		}
		// 以下有报警
		rsArray[0] = paramId;
		rsArray[1] = srule.getRuleid();
		rsArray[2] = str;
		rsArray[3] = String.valueOf(level);
		rsArray[4] = AlarmCacheConst.NORMAL;
		return true;
	}

	/**
	 * 获取报警结果
	 * 
	 * @return String[] index：[0]参数id，[1]规则id，[2]报警信息，[3]报警级别1-3
	 */
	public String[] getRsArray() {
		return rsArray;
	}
}

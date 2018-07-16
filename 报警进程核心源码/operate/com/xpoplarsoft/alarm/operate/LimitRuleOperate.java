package com.xpoplarsoft.alarm.operate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xpoplarsoft.alarm.AlarmConst;
import com.xpoplarsoft.alarm.data.AlarmParam;
import com.xpoplarsoft.alarm.data.AlarmRuleInfo;
import com.xpoplarsoft.alarm.data.LimitRuleInfo;
import com.xpoplarsoft.alarm.data.RangeAlarm;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheConst;
import com.xpoplarsoft.alarm.operate.cache.AlarmCacheUtil;
import com.xpoplarsoft.alarm.operate.cache.AlarmOperateCache;

import compiler.AlarmAnalyse;
import compiler.CompilerInput;
import compiler.CompilerOutput;
import compiler.DataCheck;

/**
 * 门限规则运算
 * 
 * @author zhouxignlu 2015年9月10日
 */
public class LimitRuleOperate {
	private static Log log = LogFactory.getLog(LimitRuleOperate.class);

	private String deviceId;

	private AlarmParam param;

	private long tmIntData;

	private double tmFloatData;

	private boolean isFloat;

	private RangeAlarm[] rangeAlarmArray;// 门限数组，用下标表示报警级别，０：不报警，１：一级，２：二级，３：三级。

	private String[] rsArray;// 判定结果数组index：0，无报警；1，重度报警；2，中度报警；3，轻度报警；value："L"超下限,"U"超上限
	// 关联条件
	private String relation;
	// 关联条件是否有效
	private boolean relationValid;
	// 各级报警是否有效0：都无效；1：轻度有效；2：中度有效；3：轻度、中度有效；4：重度有效；5：轻度、重度有效；6：中度、重度有效；7：全有效
	private int rangevalidity;
	// 有效值下限
	private double rangevaluelower;
	// 有效值上限
	private double rangevalueupper;
	// 是否报警0：是；1：否
	private boolean canalarm;

	private LimitRuleInfo lrule;

	public LimitRuleOperate(AlarmParam param, AlarmRuleInfo rule) {
		if (log.isDebugEnabled()) {
			log.debug("初始化门限报警运算器！待处理的参数为：" + param.getName() + "("
					+ param.getCode() + ")当前值为：" + param.getValue());
		}
		deviceId = rule.getSatid();
		this.param = param;
		tmFloatData = 10E100;
		tmIntData = 0x5fffffffffffffffl;

		lrule = (LimitRuleInfo) rule;
		relation = lrule.getRelation();
		// 关联条件是否有效
		relationValid = lrule.getRelationValid() == null ? false : lrule
				.getRelationValid().equals(AlarmConst.CAN_RELATION);
		// 有效值下限
		try {
			rangevaluelower = Double.parseDouble(lrule.getRangevaluelower()
					.trim());
		} catch (Exception e) {
			rangevaluelower = -0x5fffffffffffffffl;
		}
		// 有效值上限
		try {
			rangevalueupper = Double.parseDouble(lrule.getRangevalueupper()
					.trim());
		} catch (Exception e) {
			rangevaluelower = 0x5fffffffffffffffl;
		}
		// 是否报警0：是；1：否
		canalarm = lrule.getCanalarm() == null ? false : lrule.getCanalarm()
				.equals(AlarmConst.CAN_ALARM);
		try {
			// 各级报警是否有效0：都无效；1：轻度有效；2：中度有效；3：轻度、中度有效；4：重度有效；5：轻度、重度有效；6：中度、重度有效；7：全有效
			rangevalidity = Integer.parseInt(lrule.getRangevalidity().trim());
		} catch (Exception e) {
			rangevalidity = 0;
		}
		rangeAlarmArray = new RangeAlarm[4];
		for (int i = 1; i < 4; i++) {
			rangeAlarmArray[i] = new RangeAlarm();
		}
		rangeAlarmArray[1].validity = (rangevalidity & 4) > 0;
		// 重度下限
		rangeAlarmArray[1].lower = Double.parseDouble(lrule.getLowerfirst()
				.trim());
		// 重度上限
		rangeAlarmArray[1].upper = Double.parseDouble(lrule.getUpperfirst()
				.trim());
		rangeAlarmArray[2].validity = (rangevalidity & 2) > 0;
		// 中度下限
		rangeAlarmArray[2].lower = Double.parseDouble(lrule.getLowersecond()
				.trim());
		// 重度上限
		rangeAlarmArray[2].upper = Double.parseDouble(lrule.getUppersecond()
				.trim());
		rangeAlarmArray[3].validity = (rangevalidity & 1) > 0;
		// 轻度下限
		rangeAlarmArray[3].lower = Double.parseDouble(lrule.getLowerthrid()
				.trim());
		// 轻度上限
		rangeAlarmArray[3].upper = Double.parseDouble(lrule.getUpperthrid()
				.trim());
		rsArray = new String[4];
		for (int i = 1; i < 4; i++) {
			rsArray[i] = "";
		}
	}

	public boolean operate() {
		if (log.isDebugEnabled()) {
			log.debug( param.getName() + "("
					+ param.getCode() + ")门限报警计算开始！");
		}
		if(!canalarm){
			if (log.isDebugEnabled()) {
				log.debug( param.getName() + "("
						+ param.getCode() + ")不报警,结束计算！");
			}
			return false;
		}
		if (param.getData_type() == 0) {
			isFloat = true;
			tmFloatData = (Double) AlarmCacheUtil.getParamById(deviceId,
					String.valueOf(param.getId())).getValue();
		}
		if (param.getData_type() == 1 || param.getData_type() == 2) {
			isFloat = false;
			tmIntData = (Long) AlarmCacheUtil.getParamById(deviceId,
					String.valueOf(param.getId())).getValue();
		}
		// 判断是否进行关联条件验证
		if (relationValid) {
			if(log.isDebugEnabled()){
				log.debug(param.getName() + "("
						+ param.getCode() + ")开始判定关联条件规则！");
			}
			boolean success = false;
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
				if(log.isDebugEnabled()){
					log.debug(param.getName() + "("
							+ param.getCode() + ")关联条件判定失败！");
				}
				return false;
			}
		}
		if (isFloat) {
			if (canalarm && tmFloatData >= rangevaluelower
					&& tmFloatData <= rangevalueupper) {
				JudgeData(tmFloatData, 3);
				JudgeData(tmFloatData, 2);
				JudgeData(tmFloatData, 1);
			}
		} else {
			if (canalarm && tmIntData >= rangevaluelower
					&& tmIntData <= rangevalueupper) {
				JudgeData(tmIntData, 3);
				JudgeData(tmIntData, 2);
				JudgeData(tmIntData, 1);
			}
		}
		String key = String.valueOf(param.getId()) + AlarmConst.SPLIT
				+ lrule.getRuleid();
		int judgecount = Integer.parseInt(lrule.getJudgecount());
		if (log.isDebugEnabled()) {
			log.debug(param.getName() + "("
					+ param.getCode() + ")门限报警计算结束！");
		}
		return AlarmOperateCache
				.setLimitOperateResult(key, rsArray, judgecount);
	}

	/**
	 * 超限判断
	 * 
	 * @param tmFloatDataVal
	 *            双精度遥测工程值
	 * @param levelVal
	 *            报警级别
	 */
	private void JudgeData(double tmFloatDataVal, int levelVal) {
		if (!rangeAlarmArray[levelVal].validity) {
			rsArray[levelVal] = null;
			return;
		}
		if (rangeAlarmArray[levelVal].upper < tmFloatDataVal) {
			rsArray[levelVal] = AlarmCacheConst.UPPER;
			rsArray[0] = null;
			// 待实现报警日志记录
		} else if (rangeAlarmArray[levelVal].lower > tmFloatDataVal) {
			rsArray[levelVal] = AlarmCacheConst.LOWER;
			rsArray[0] = null;
			// 待实现报警日志记录
		} else {
			rsArray[levelVal] = AlarmCacheConst.NORMAL;
			for (int i = 3; i >= levelVal; i--) {
				if(rsArray[i] != null && !AlarmCacheConst.NORMAL.equals(rsArray[i])){
					rsArray[0] = null;
					break;
				}
				rsArray[0] = AlarmCacheConst.NORMAL;
			}
		}
	}

	/**
	 * 超限判断
	 * 
	 * @param tmIntDataVal
	 *            长整形遥测工程值
	 * @param levelVal
	 *            报警级别
	 */
	private void JudgeData(long tmIntDataVal, int levelVal) {
		if (!rangeAlarmArray[levelVal].validity) {
			rsArray[levelVal] = null;
			return;
		}
		if (rangeAlarmArray[levelVal].upper < tmIntDataVal) {
			rsArray[levelVal] = AlarmCacheConst.UPPER;
			rsArray[0] = null;
			// 待实现报警日志记录
		} else if (rangeAlarmArray[levelVal].lower > tmIntDataVal) {
			rsArray[levelVal] = AlarmCacheConst.LOWER;
			rsArray[0] = null;
			// 待实现报警日志记录
		} else {
			rsArray[levelVal] = AlarmCacheConst.NORMAL;
			for (int i = 3; i >= levelVal; i--) {
				if(rsArray[i] != null && !AlarmCacheConst.NORMAL.equals(rsArray[i])){
					break;
				}
				rsArray[0] = AlarmCacheConst.NORMAL;
			}
		}
	}

	/**
	 * 获取报警结果
	 * 
	 * @return String[] index：[0]参数id，[1]规则id，[2]报警信息，[3]报警级别1-3，[4]超上限U/下限L
	 */
	public String[] getRsArray() {
		if(log.isDebugEnabled()){
			log.debug(param.getName() + "("
					+ param.getCode() + ")组装门限报警结果信息！");
		}
		String[] rs = new String[5];
		rs[0] = String.valueOf(param.getId());
		rs[1] = lrule.getRuleid();
		rs[3] = "0";
		rs[4] = AlarmCacheConst.NORMAL;
		String str = "";
		if (rsArray[0] != null && !rsArray[0].equals("")) {
			str = param.getName() + "(" + param.getCode() + ")超限恢复";
		} else {
			for (int i = 1; i < 4; i++) {
				if (rsArray[i] != null && !rsArray[i].equals("")) {
					rs[3] = String.valueOf(i);
					// 报警发生改变
					if (rsArray[i].equals(AlarmCacheConst.UPPER)) {
						str = param.getName() + "(" + param.getCode() + ")超过"
								+ AlarmConst.level2Text(i) + "报警上限["
								+ rangeAlarmArray[i].lower + ","
								+ rangeAlarmArray[i].upper + "]";
						rs[4] = AlarmCacheConst.UPPER;
						break;
					} else if (rsArray[i].equals(AlarmCacheConst.LOWER)) {
						str = param.getName() + "(" + param.getCode() + ")低于"
								+ AlarmConst.level2Text(i) + "报警下限["
								+ rangeAlarmArray[i].lower + ","
								+ rangeAlarmArray[i].upper + "]";
						rs[4] = AlarmCacheConst.LOWER;
						break;
					}
				}
			}
		}
		rs[2] = str;

		return rs;
	}
}

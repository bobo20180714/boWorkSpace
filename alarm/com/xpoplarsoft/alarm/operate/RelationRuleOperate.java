package com.xpoplarsoft.alarm.operate;

import java.util.ArrayList;

import statement.ICompilerStatement;
import statement.Statement_Const;
import statement.Statement_Define;
import statement.Statement_Expression;
import statement.Statement_TMRef;

import com.xpoplarsoft.alarm.data.BooleanData;
import com.xpoplarsoft.alarm.data.DoubleData;
import com.xpoplarsoft.alarm.data.IData;
import com.xpoplarsoft.alarm.data.LongData;
import com.xpoplarsoft.alarm.data.StringData;
import com.xpoplarsoft.alarm.data.TmData;

import compiler.AlarmParser;

/**
 * 关联条件规则执行类
 * 
 * @author zhouxignlu 2015年9月11日
 */
public class RelationRuleOperate {

	private boolean rs = false;
	/**
	 * 表达式缓存区
	 */
	private ArrayList<ICompilerStatement> relationRuleList = new ArrayList<ICompilerStatement>();
	/**
	 * 计算数据变量缓存区
	 */
	private ArrayList<Object> dataList = new ArrayList<Object>();

	private String deviceId;

	/**
	 * @param deviceId
	 *            航天器设备编号
	 * @param statementList
	 *            解析后的关联条件表达式
	 */
	public RelationRuleOperate(String deviceId,
			ArrayList<ICompilerStatement> statementList) {
		this.deviceId = deviceId;
		for (int i = 0; i < statementList.size(); i++) {
			ICompilerStatement st = statementList.get(i);
			if (st.getType() == ICompilerStatement.STAT_DEFINE
					|| st.getType() == ICompilerStatement.STAT_CONST
					|| st.getType() == ICompilerStatement.STAT_TMREF) {
				int index = dataList.size();
				st = addVariable(st, index);// 添加计算用的的数据变量，并确定数据在缓存区的位置

			}
			relationRuleList.add(i, st);
		}
	}

	private ICompilerStatement addVariable(ICompilerStatement st, int index) {
		if (st.getType() == ICompilerStatement.STAT_DEFINE) {
			Statement_Define define = (Statement_Define) st;
			define.setIndex(index);
			dataList.add(index,
					createDate(define.getDataType(), define.getVarCode()));
			return define;
		} else if (st.getType() == ICompilerStatement.STAT_TMREF) {
			Statement_TMRef define = (Statement_TMRef) st;
			// 获取遥测缓存中的参数工程值
			TmData param = new TmData(deviceId, define.getParamCode());
			define.setIndex(index);
			dataList.add(index,
					createDate(define.getDataType(), param.getValue()));
			return define;
		} else {
			Statement_Const condt = (Statement_Const) st;
			condt.setIndex(index);
			dataList.add(index,
					createDate(condt.getDataType(), condt.getNameString()));
			return condt;
		}
	}

	private Object createDate(int type, Object value) {
		if (type == AlarmParser.FLOAT) {
			return new DoubleData(value);
		} else if (type == AlarmParser.INT) {
			return new LongData(value);
		} else if (type == AlarmParser.STRING) {
			return new StringData(value);
		} else if (type == AlarmParser.BOOL) {
			return new BooleanData(value);
		} else if (type == AlarmParser.TM) {
			return new TmData(deviceId, value.toString());
		} else if (type == AlarmParser.TOKEN_ALL_OBJ) {
			return value;
		}
		return null;
	}

	public void operate() {
		for (int i = 0; i < relationRuleList.size(); i++) {
			ICompilerStatement st = relationRuleList.get(i);
			// 为表达式语句进行计算
			if (st.getType() == ICompilerStatement.STAT_EXP) {
				Statement_Expression ste = (Statement_Expression) st;
				if (ste.getOperator() == AlarmParser.ADD
						|| // 加法运算//减法运算//乘法运算//除法运算
						ste.getOperator() == AlarmParser.SUB
						|| ste.getOperator() == AlarmParser.MUL
						|| ste.getOperator() == AlarmParser.DIV) {
					simpleCalc(ste);
				} else if (ste.getOperator() == AlarmParser.EQ
						|| ste.getOperator() == AlarmParser.NE
						|| ste.getOperator() == AlarmParser.GT
						|| ste.getOperator() == AlarmParser.GE
						|| ste.getOperator() == AlarmParser.LT
						|| ste.getOperator() == AlarmParser.LE) {
					// 逻辑运算
					logic(ste);
				}
			}
			if (i == relationRuleList.size() - 1) {
				rs = (Boolean) ((IData) dataList.get(i - 1)).getValue();
			}
		}
	}

	public boolean getRs() {
		return rs;
	}

	/**
	 * 四则运算
	 * 
	 * @param ste
	 */
	private void simpleCalc(Statement_Expression ste) {
		double leftParam = 0;
		double rightParam = 0;
		IData data1 = (IData) dataList.get(ste.getData1() - 1);
		IData data2 = (IData) dataList.get(ste.getData2() - 1);
		if (data1.getType() == AlarmParser.TM) {
			leftParam = (Double) data1.getValue();
		} else if (data1.getType() == AlarmParser.INT) {
			leftParam = (Long) data1.getValue();
		} else {
			leftParam = (Double) data1.getValue();
		}

		if (data2.getType() == AlarmParser.TM) {
			rightParam = (Double) data2.getValue();
		} else if (data2.getType() == AlarmParser.INT) {
			rightParam = (Long) data2.getValue();
		} else {
			rightParam = (Double) data2.getValue();
		}
		int rsIndex = ste.getResult() - 1;
		DoubleData rs = (DoubleData) dataList.get(rsIndex);
		dataList.remove(rsIndex);
		if (ste.getOperator() == AlarmParser.ADD) {// 加法运算
			rs.setValue(leftParam + rightParam);
		} else if (ste.getOperator() == AlarmParser.SUB) {// 减法运算
			rs.setValue(leftParam - rightParam);
		} else if (ste.getOperator() == AlarmParser.MUL) {// 乘法运算
			rs.setValue(leftParam * rightParam);
		} else if (ste.getOperator() == AlarmParser.DIV) {// 除法运算
			rs.setValue(leftParam / rightParam);
		}

		dataList.add(rsIndex, rs);
	}

	/**
	 * 逻辑运算
	 * 
	 * @param ste
	 */
	private void logic(Statement_Expression ste) {
		IData data1 = (IData) dataList.get(ste.getData1() - 1);
		IData data2 = (IData) dataList.get(ste.getData2() - 1);
		int rsIndex = ste.getResult() - 1;
		BooleanData rs = (BooleanData) dataList.get(rsIndex);
		dataList.remove(rsIndex);
		if (ste.getOperator() == AlarmParser.EQ) {// 等于
			rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
					.compareTo((String) getDataValue(data2, AlarmParser.STRING)) == 0);
		} else if (ste.getOperator() == AlarmParser.NE) {// 不等于
			rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
					.compareTo((String) getDataValue(data2, AlarmParser.STRING)) != 0);
		} else if (ste.getOperator() == AlarmParser.GT) {// 大于
			if (data1.getType() == AlarmParser.STRING) {
				rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
						.compareTo((String) getDataValue(data2,
								AlarmParser.STRING)) > 0);
			} else {
				rs.setValue(((Double) getDataValue(data1, AlarmParser.FLOAT))
						.doubleValue() > ((Double) getDataValue(data2,
						AlarmParser.FLOAT)).doubleValue());
			}
		} else if (ste.getOperator() == AlarmParser.GE) {// 大于等于
			if (data1.getType() == AlarmParser.STRING) {
				rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
						.compareTo((String) getDataValue(data2,
								AlarmParser.STRING)) >= 0);
			} else {
				rs.setValue(((Double) getDataValue(data1, AlarmParser.FLOAT))
						.doubleValue() >= ((Double) getDataValue(data2,
						AlarmParser.FLOAT)).doubleValue());
			}
		} else if (ste.getOperator() == AlarmParser.LT) {// 小于
			if (data1.getType() == AlarmParser.STRING) {
				rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
						.compareTo((String) getDataValue(data2,
								AlarmParser.STRING)) < 0);
			} else {
				rs.setValue(((Double) getDataValue(data1, AlarmParser.FLOAT))
						.doubleValue() < ((Double) getDataValue(data2,
						AlarmParser.FLOAT)).doubleValue());
			}
		} else if (ste.getOperator() == AlarmParser.LE) {// 小于等于
			if (data1.getType() == AlarmParser.STRING) {
				rs.setValue(((String) getDataValue(data1, AlarmParser.STRING))
						.compareTo((String) getDataValue(data2,
								AlarmParser.STRING)) <= 0);
			} else {
				rs.setValue(((Double) getDataValue(data1, AlarmParser.FLOAT))
						.doubleValue() <= ((Double) getDataValue(data2,
						AlarmParser.FLOAT)).doubleValue());
			}
		}

		dataList.add(rsIndex, rs);
	}

	private Object getDataValue(IData data, int targetType) {
		if (data.getType() == targetType) {
			return data.getValue();
		} else {
			String tmpValue = "0";
			try {
				if (data.getType() == AlarmParser.STRING) {
					tmpValue = (String) data.getValue();
				} else if (data.getType() == AlarmParser.INT) {
					tmpValue = String.valueOf((Long) data.getValue());
				} else if (data.getType() == AlarmParser.FLOAT) {
					tmpValue = String.valueOf((Double) data.getValue());
				} else if (data.getType() == AlarmParser.TM) {
					TmData t = (TmData) data;
					if (t.getType() == AlarmParser.INT) {
						tmpValue = String.valueOf((Long) t.getValue());
					} else if (t.getType() == AlarmParser.FLOAT) {
						tmpValue = String.valueOf((Double) t.getValue());
					} else if (t.getType() == AlarmParser.STRING) {
						tmpValue = (String) t.getValue();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (targetType == AlarmParser.STRING) {
				return tmpValue;
			} else if (targetType == AlarmParser.INT) {
				return Integer.valueOf(tmpValue);
			} else if (targetType == AlarmParser.FLOAT) {
				return Double.valueOf(tmpValue);
			}
		}
		return data.getValue();
	}
}

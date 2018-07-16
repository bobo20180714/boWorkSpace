package compiler;


public class ExpressionAnalyseResult extends ResultObj {//表达式语句分析结果
	//表达式类型枚举值
	public static final int EXP_TYPE_NULL = 0;
	public static final int EXP_TYPE_ITEM = 1;		//单个表达式项
	public static final int EXP_TYPE_ONE = 2;		//单目运算表达式
	public static final int EXP_TYPE_TWO = 3;		//双目运算表达式
	public static final int EXP_TYPE_FUN = 9;		//函数表达式
	
	private int expType=EXP_TYPE_NULL;		//表达式类型
	private int resultDataType=AlarmParser.NULL;	//表达式计算结果数据类型

	public int getResultDataType() {
		return resultDataType;
	}

	public void setResultDataType(int resultDataType) {
		this.resultDataType = resultDataType;
	}

	public int getExpType() {
		return expType;
	}

	public void setExpType(int expType) {
		this.expType = expType;
	}
}

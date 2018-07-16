package statement;


public class Statement_Expression extends CompilerStatementObj{//表达式语句基类
	private int Data1;		//操作数1（地址）
	private int Data2;		//操作数2（地址）
	private int Operator;	//操作符
	private int Result;		//操作结果（地址）
	
	public Statement_Expression(){
		setType(STAT_EXP);
	}

	public int getOperator() {
		return Operator;
	}

	public void setOperator(int operator) {
		Operator = operator;
	}

	public int getResult() {
		return Result;
	}

	public void setResult(int result) {
		Result = result;
	}

	public int getData1() {
		return Data1;
	}

	public void setData1(int data1) {
		Data1 = data1;
	}

	public int getData2() {
		return Data2;
	}

	public void setData2(int data2) {
		Data2 = data2;
	}	
}

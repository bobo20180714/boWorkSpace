package compiler;


public class ExpressionGenerateResult extends ResultObj {//表达式语句代码生成结果	
	private int ResultAddress=0;		//结果对象地址
	private int StatementIndex=0;	//表达式所在语句行号
	private String ResultVarCode=null;	//结果对象变量名称(仅对复合表达式有效)
	
	public int getResultAddress() {
		return ResultAddress;
	}
	public void setResultAddress(int resultAddress) {
		ResultAddress = resultAddress;
	}
	public int getStatementIndex() {
		return StatementIndex;
	}
	public void setStatementIndex(int statementIndex) {
		StatementIndex = statementIndex;
	}
	public String getResultVarCode() {
		return ResultVarCode;
	}
	public void setResultVarCode(String resultVarCode) {
		ResultVarCode = resultVarCode;
	}
	

}

package statement;

public class Statement_Define extends CompilerStatementObj{//变量定义语句
	private int DataType;	//变量数据类型
	private String VarCode;	//变量代号
	
	public int getDataType() {
		return DataType;
	}

	public void setDataType(int dataType) {
		DataType = dataType;
	}

	public String getVarCode() {
		return VarCode;
	}

	public void setVarCode(String varCode) {
		VarCode = varCode;
	}

	public int getAddress() {
		return getIndex();	//变量地址就是语句序号
	}

	public Statement_Define(){
		setType(STAT_DEFINE);
	}	

}

package statement;

public class Statement_Const extends CompilerStatementObj {//常量定义语句
	private int DataType;	//常量数据类型
	private String NameString;	//常量对应字符串内容
	
	public int getDataType() {
		return DataType;
	}
	public void setDataType(int dataType) {
		DataType = dataType;
	}
	public String getNameString() {
		return NameString;
	}
	public void setNameString(String nameString) {
		NameString = nameString;
	}
	public int getAddress() {
		return getIndex();	//常量地址就是语句序号
	}
	
	public Statement_Const(){
		setType(STAT_CONST);
	}	
}

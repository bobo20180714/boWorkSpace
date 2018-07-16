package statement;

public class Statement_TMRef extends CompilerStatementObj {//遥测参数引用语句
	private int DataType;	//参数数据类型
	private String Code;	//参数代号（唯一标识）
	
	public int getDataType() {
		return DataType;
	}
	public void setDataType(int dataType) {
		DataType = dataType;
	}
	public String getParamCode() {
		return Code;
	}
	public void setParamCode(String code) {
		Code = code;
	}
	public int getAddress() {
		return getIndex();	//参数引用地址就是语句序号
	}
	
	public Statement_TMRef(){
		setType(STAT_TMREF);
	}
}

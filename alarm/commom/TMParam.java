package commom;

import compiler.AlarmParser;

public class TMParam {		//遥测参数对象类
	private int ParamNo;		//参数序号
	private String ParamCode;	//参数代号
	private String ParamName;	//参数名称
	private int ParamType=AlarmParser.FLOAT;	//参数数据类型（缺省设置为浮点型）
	
	public int getParamNo() {
		return ParamNo;
	}
	public void setParamNo(int paramNo) {
		ParamNo = paramNo;
	}
	public String getParamCode() {
		return ParamCode;
	}
	public void setParamCode(String paramCode) {
		ParamCode = paramCode;
	}
	public String getParamName() {
		return ParamName;
	}
	public void setParamName(String paramName) {
		ParamName = paramName;
	}
	public int getDataType() {
		return ParamType;
	}
	public void setDataType(int dataType) {
		ParamType = dataType;
	}
}

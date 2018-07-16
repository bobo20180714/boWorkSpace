package commom;

import compiler.AlarmParser;

public class IntObj extends DataObj {	//整型类
	private long value;
	public IntObj(){
		setObjType(DataObj.ENUM_TYPE_CONST);
		setDataType(AlarmParser.INT);
		value=0;
	}
	public IntObj(long i) {
		setObjType(DataObj.ENUM_TYPE_CONST);
		setDataType(AlarmParser.INT);
		value=i;
	}
	public String toString() {
		return "IntObj："+String.valueOf(value);
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public void setValue(String text) {
		value=Long.parseLong(text);
	}
}

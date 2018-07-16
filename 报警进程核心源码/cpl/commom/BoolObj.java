package commom;

import compiler.AlarmParser;


public class BoolObj extends DataObj {		//布尔型数据类
	private boolean value;
	public BoolObj(){
		setObjType(DataObj.ENUM_TYPE_CONST);
		setDataType(AlarmParser.BOOL);
		setValue(false);
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public String toString() {
		return "BoolObj："+String.valueOf(value);
	}
}

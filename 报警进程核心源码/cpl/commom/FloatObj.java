package commom;

import compiler.AlarmParser;

public class FloatObj extends DataObj {		//浮点数类
	private double value;
	public FloatObj(){
		setObjType(DataObj.ENUM_TYPE_CONST);
		setDataType(AlarmParser.FLOAT);
		value=(double) 0.0;
	}
	public FloatObj(double i) {
		setObjType(DataObj.ENUM_TYPE_CONST);
		setDataType(AlarmParser.FLOAT);
		value=i;
	}
	public String toString() {
		return "FloatObj："+String.valueOf(value);
	}
	public double getValue() {
		return value;
	}
	public void setValue(String text){
		this.value = Double.parseDouble(text);
	}
	public void setValue(double value) {
		this.value =value;
	}
}

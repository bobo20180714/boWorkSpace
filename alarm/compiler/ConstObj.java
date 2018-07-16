package compiler;

public class ConstObj {	//常量对象类
	private int DataType;	//常量数据类型
	private int Address=0;	//常量地址
	
	public int getAddress() {
		return Address;
	}
	public void setAddress(int address) {
		Address = address;
	}
	public int getDataType() {
		return DataType;
	}
	public void setDataType(int dataType) {
		DataType = dataType;
	}
}

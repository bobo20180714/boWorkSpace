package compiler;

public class VarObj {	//变量对象
	private int DataType;	///变量数据类型
	private int Address=0;	///变量地址
	private boolean lockFlag=false;	///变量锁定标志
	
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
	public boolean isLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}
	

}

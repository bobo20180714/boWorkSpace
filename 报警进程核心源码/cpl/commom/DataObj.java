package commom;


public abstract class DataObj { // 通用数据对象类
	// 对象类型枚举值
	public static final int ENUM_TYPE_NULL = 0;
	public static final int ENUM_TYPE_CONST = 1;
	public static final int ENUM_TYPE_VAR = 2;
	public static final int ENUM_TYPE_TMPARAM = 3;

	private int ObjType;		// 对象类型
	private int DataType;		// 数据类型
	
	public int getObjType() {
		return ObjType;
	}
	public void setObjType(int objType) {
		ObjType = objType;
	}
	public int getDataType() {
		return DataType;
	}
	public void setDataType(int dataType) {
		DataType = dataType;
	}
	public String toString() {
		return "DataObj：DataType="+String.valueOf(DataType);
	}
}

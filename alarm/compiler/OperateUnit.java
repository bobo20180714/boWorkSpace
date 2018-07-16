package compiler;



public class OperateUnit {/// �ո��信息单元
	  /// <remarks>
	  /// �ո���?
	  /// </remarks>
	private int OP;
	  /// <remarks>
	  /// �ո���?数据类型
	  /// </remarks>
	private int DataType1;
	  /// <remarks>
	  /// �ո���?数据类型
	  /// </remarks>
	private int DataType2;
	  /// <remarks>
	  /// 运算结果数据类型
	  /// </remarks>
	private int ResultDataType;
	
	public OperateUnit(){
		DataType1=AlarmParser.NULL;
		DataType2=AlarmParser.NULL;
		ResultDataType=AlarmParser.NULL;
	}
	public int getOP() {
		return OP;
	}
	public void setOP(int oP) {
		OP = oP;
	}
	public int getDataType1() {
		return DataType1;
	}
	public void setDataType1(int dataType1) {
		DataType1 = dataType1;
	}
	public int getDataType2() {
		return DataType2;
	}
	public void setDataType2(int dataType2) {
		DataType2 = dataType2;
	}
	public int getResultDataType() {
		return ResultDataType;
	}
	public void setResultDataType(int resultDataType) {
		ResultDataType = resultDataType;
	}

}

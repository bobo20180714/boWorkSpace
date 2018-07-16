package statement;

public class CompilerStatementObj implements ICompilerStatement{// 编译结果语句对象类
	private int index; // 语句序号
	private int StatementType; // 语句类型

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getType() {
		return StatementType;
	}

	@Override
	public void setType(int type) {
		this.StatementType = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}

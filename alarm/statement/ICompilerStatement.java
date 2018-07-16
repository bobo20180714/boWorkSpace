package statement;

public interface ICompilerStatement {			//编译结果语句接口
	// 语句类型枚举
	public static final int STAT_NULL =0;		// 无类型语句
	public static final int STAT_CONST =1;				// 常量定义语句
	public static final int STAT_DEFINE =2;				// 变量定义语句
	public static final int STAT_TMREF =3;				// 遥测参数引用语句
	public static final int STAT_EXP =4;			// 表达式语句
	public static final int STAT_CALL =5;		// 函数调用语句

	/**
	 * 清理(释放语句对象占用的空间)
	 */
	public void clear();
	/**
	 * 获取语句类型
	 */
	public int getType();
	/**
	 * 设置语句类型
	 */
	public void setType(int type);

}

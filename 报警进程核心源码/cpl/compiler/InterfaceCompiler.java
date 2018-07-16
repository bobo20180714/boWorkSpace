package compiler;

public interface InterfaceCompiler {	//脚本代码编译接口
	//编译错误枚举类型
	public static final int ENUM_ERROR_COMMON= 0;
	public static final int ENUM_ERROR_SCAN = 1;
	public static final int ENUM_ERROR_PARSE = 2;
	public static final int ENUM_ERROR_ANALYSE = 3;
	public static final int ENUM_ERROR_CHECK = 4; // 信息检查错误
	public static final int ENUM_ERROR_GENERATE = 5; // 代码生成错误

	public CompilerOutput ScriptAnalyse(CompilerInput input);		//代码分析（含编译及权限检查）
}


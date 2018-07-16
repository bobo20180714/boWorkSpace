package compiler;

public class CompilerInput_3Level implements ICompilerInput{	//3级门限报警知识编译过程输入对象
	private String SourceCode;			//源代码
	private IDataCheck DataCheckService;	//数据检查接口
	
	public String getSourceCode() {
		return SourceCode;
	}
	public void setSourceCode(String sourceCode) {
		SourceCode = sourceCode;
	}

	public IDataCheck getDataCheckService() {
		return DataCheckService;
	}
	public void setDataCheckService(IDataCheck AService) {
		DataCheckService = AService;
	}
	
}

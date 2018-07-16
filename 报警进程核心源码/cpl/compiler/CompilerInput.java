package compiler;

public class CompilerInput {			//编译过程输入对象
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

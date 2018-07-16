package compiler;

//编译过程输入信息接口
public interface ICompilerInput {		
	//获取源代码
	public String getSourceCode();	
	//获取数据检查服务接口
	public IDataCheck getDataCheckService();	
}

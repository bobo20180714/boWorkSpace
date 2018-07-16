package compiler;

import java.util.ArrayList;

import statement.ICompilerStatement;

//编译过程输出信息接口
public interface ICompilerOutput {	
	//获取编译是否成功的标志，当该标志为True时编译结果语句列表才有效
	public boolean getResultflag();	
	//获取编译信息列表
	public ArrayList<CompilerInfoUnit> getInfoList();
	//获取编译结果语句列表
	public ArrayList<ICompilerStatement> getStatementList();
}

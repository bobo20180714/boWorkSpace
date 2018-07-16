package compiler;

//脚本代码编译接口
public interface IScriptCompiler {	
	//代码分析（含编译及相关检查）
    public ICompilerOutput ScriptAnalyse(ICompilerInput input);		
}


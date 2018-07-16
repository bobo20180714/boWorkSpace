package compiler;

import java.util.ArrayList;

public class AlarmAnalyse implements InterfaceCompiler{		//代码编译分析类
	@Override
	//代码编译分析函数，用于外部接口调用，含代码编译及外部信息有效性检查全过程
	public CompilerOutput ScriptAnalyse(CompilerInput input) {
		CompilerOutput ret;
		CompilerInfoUnit temp;
		ArrayList<CompilerInfoUnit> InfoList=new ArrayList<CompilerInfoUnit>();
		//执行输入对象有效性检查
		if(input==null){
			//生成输出信息列表对象
			temp=new CompilerInfoUnit();
			temp.setLine(0);
			temp.setRow(0);
			temp.setType(InterfaceCompiler.ENUM_ERROR_COMMON);
			temp.setText("编译模块输入对象不能为空!");
			InfoList.add(temp);
			//生成输出对象
			ret = new CompilerOutput();
			ret.setCheckflag(false);
			ret.setInfoList(InfoList);		
		}else{
			if(input.getDataCheckService()==null){
				//生成输出信息列表对象
				temp=new CompilerInfoUnit();
				temp.setLine(0);
				temp.setRow(0);
				temp.setType(InterfaceCompiler.ENUM_ERROR_COMMON);
				temp.setText("编译模块输入对象的数据检查接口不能为空!");
				InfoList.add(temp);
				//生成输出对象
				ret = new CompilerOutput();
				ret.setCheckflag(false);
				ret.setInfoList(InfoList);		
			}else if(input.getSourceCode()==null){
				//生成输出信息列表对象
				temp=new CompilerInfoUnit();
				temp.setLine(0);
				temp.setRow(0);
				temp.setType(InterfaceCompiler.ENUM_ERROR_COMMON);
				temp.setText("编译模块输入对象的源代码内容不能为空!");
				InfoList.add(temp);
				//生成输出对象
				ret = new CompilerOutput();
				ret.setCheckflag(false);
				ret.setInfoList(InfoList);		
			}else{
				AlarmCompiler ACompiler = new AlarmCompiler();
				ret=ACompiler.CodeAnalyse(input);
				ACompiler.Clear();
				ACompiler=null;
			}
		}
		InfoList.clear();
		InfoList=null;
		return ret;
	}
	//代码编译分析测试函数，用于控制台测试时调用
	public CompilerOutput Test(CompilerInput input) {
		CompilerOutput ret;
		AlarmCompiler ACompiler = new AlarmCompiler();
		ret=ACompiler.CodeAnalyse(input);
		ACompiler.Clear();
		ACompiler=null;
		return ret;
	}
}

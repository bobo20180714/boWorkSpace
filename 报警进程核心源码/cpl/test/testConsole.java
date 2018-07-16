package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import compiler.AlarmAnalyse;
import compiler.CompilerInput;
import compiler.CompilerOutput;

public class testConsole {
	public static void main(String[] args) throws Exception
	{
		try
		{
			System.out.println("请输入表达式");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			AlarmAnalyse testAnalyse=new AlarmAnalyse();
			//以下1条语句为使用控制台输入测试接口
			CompilerInput input=new CompilerInput();
			input.setDataCheckService(null);
			input.setSourceCode(br.readLine());
			//input.setSourceCode("9.9>=temp && !(4=5)");
			CompilerOutput Output=testAnalyse.Test(input);

			if(Output!=null)
			{
				if(Output.getCheckflag()==true)
				{
					System.out.println("****** OK *******");
					System.out.println(Output.getStatementList());
					//System.out.println(Output.getCheckParamList().toString());
				}
				else
				{
					System.out.println("****** Error *******");
					System.out.println(Output.getInfoList());
				}			
			}
			else
			{
				System.out.println("____Exception_____");
			}
		}
		catch(Exception e)
		{
			System.out.println("main Error:"+e.toString());
		}
	}
}

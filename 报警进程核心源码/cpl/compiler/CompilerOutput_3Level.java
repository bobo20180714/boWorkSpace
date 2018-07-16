package compiler;

import java.util.ArrayList;

import statement.ICompilerStatement;

public class CompilerOutput_3Level implements ICompilerOutput{		//3级门限报警知识编译过程输出结果
	private boolean Resultflag;					//编译是否成功标志，当该标志为True时下列内容才有效
	private ArrayList<CompilerInfoUnit> InfoList;		//编译信息列表
	private ArrayList<ICompilerStatement> StatementList;		//编译结果语句列表
	
	public CompilerOutput_3Level(){
		Resultflag=false;
	}
	public boolean getResultflag() {
		return Resultflag;
	}

	public void setResultflag(boolean resultflag) {
		Resultflag = resultflag;
	}
	
	public ArrayList<CompilerInfoUnit> getInfoList() {
		return InfoList;
	}
	public void setInfoList(ArrayList<CompilerInfoUnit> InfoList) {
		if(InfoList!=null){
			CompilerInfoUnit unit;
			this.InfoList=new ArrayList<CompilerInfoUnit>();
			//将信息列表内容复制到输出结果对象中（因为编译结束后要清除信息列表）
			for(int i=0;i<InfoList.size();i++){
				unit=new CompilerInfoUnit();
				unit.setType(InfoList.get(i).getType());
				unit.setLine(InfoList.get(i).getLine());
				unit.setRow(InfoList.get(i).getRow());
				unit.setText(InfoList.get(i).getText());
				this.InfoList.add(unit);
			}
		}else{
			this.InfoList=null;
		}
	}
	public ArrayList<ICompilerStatement> getStatementList() {
		return StatementList;
	}
}

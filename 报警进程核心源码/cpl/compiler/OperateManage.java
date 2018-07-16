package compiler;

import java.util.ArrayList;

public class OperateManage {		//运算�ո��管理�?
	private ArrayList<OperateUnit> OperateList;
	public void clear(){
		if(OperateList!=null){
			OperateList.clear();
			OperateList=null;
		}
	}
	
	public OperateManage(){
		OperateList=new ArrayList<OperateUnit>();

	  //初始化操���列表
	  //算术运算
	  AddOperate(AlarmParser.ADD,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.ADD,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.ADD,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.ADD,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.SUB,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.SUB,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.SUB,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.SUB,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.MUL,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.MUL,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.MUL,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.MUL,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.DIV,AlarmParser.INT,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.DIV,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.DIV,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.DIV,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.MOD,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.IDV,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  //位运�?
	  AddOperate(AlarmParser.BAND,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.BNOT,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.BOR,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.BXOR,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.LMOV,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  AddOperate(AlarmParser.RMOV,AlarmParser.INT,AlarmParser.INT,AlarmParser.INT);
	  //关系运算�����数据对象�?
	  AddOperate(AlarmParser.LT,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LT,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LT,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LT,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LE,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LE,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LE,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.LE,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GT,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GT,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GT,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GT,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GE,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GE,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GE,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.GE,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.EQ,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.EQ,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.EQ,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.EQ,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.NE,AlarmParser.INT,AlarmParser.INT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.NE,AlarmParser.FLOAT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.NE,AlarmParser.INT,AlarmParser.FLOAT,AlarmParser.BOOL);
	  AddOperate(AlarmParser.NE,AlarmParser.FLOAT,AlarmParser.INT,AlarmParser.BOOL);
	  //�ͻ辑运算�����数据对象�?
	  AddOperate(AlarmParser.KW_NOT,AlarmParser.BOOL,AlarmParser.BOOL);
	  AddOperate(AlarmParser.KW_AND,AlarmParser.BOOL,AlarmParser.BOOL,AlarmParser.BOOL);
	  AddOperate(AlarmParser.KW_OR,AlarmParser.BOOL,AlarmParser.BOOL,AlarmParser.BOOL);
	  //
	  AddOperate(AlarmParser.SUB,AlarmParser.INT,AlarmParser.FLOAT);
	  AddOperate(AlarmParser.SUB,AlarmParser.FLOAT,AlarmParser.FLOAT);
	}
	public void AddOperate(int OP,int Data1,int Data2,int Result){		//双目运算
		OperateUnit AUnit=new OperateUnit();
		AUnit.setOP(OP);
		AUnit.setDataType1(Data1);
		AUnit.setDataType2(Data2);
		AUnit.setResultDataType(Result);
		OperateList.add(AUnit);
	}
	public void AddOperate(int OP,int Data1,int Result){		//卿���运算
		OperateUnit AUnit=new OperateUnit();
		AUnit.setOP(OP);
		AUnit.setDataType1(Data1);
		AUnit.setDataType2(AlarmParser.NULL);
		AUnit.setResultDataType(Result);
		OperateList.add(AUnit);
	}
	public int OperateCheck(int OP,int Data1){		//卿���运算
		int ret=AlarmParser.NULL;
		OperateUnit TempUnit;
		
		for(int i=0;i<OperateList.size();i++){
			TempUnit=OperateList.get(i);
			if(TempUnit.getOP()==OP){
				if(TempUnit.getDataType1()==Data1){
					if(TempUnit.getDataType2()==AlarmParser.NULL){
						ret=TempUnit.getResultDataType();
						break;
					}
				}
			}
		}
		return ret;
	}
	public int OperateCheck(int OP,int Data1,int Data2){		//双目运算
		int ret=AlarmParser.NULL;
		OperateUnit TempUnit;
		
		if(Data2==AlarmParser.NULL){		//如果是单目运�?
			return OperateCheck(OP,Data1);
		}
		for(int i=0;i<OperateList.size();i++){
			TempUnit=OperateList.get(i);
			if(TempUnit.getOP()==OP){
				if(TempUnit.getDataType1()==Data1){
					if(TempUnit.getDataType2()==Data2){
						ret=TempUnit.getResultDataType();
						break;
					}
				}
			}
		}
		return ret;
	}
}

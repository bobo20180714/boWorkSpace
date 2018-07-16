package compiler;

import java.lang.reflect.Method;

public class FunctionInfo {			//函数基本信息
	//函数类型��⸾类型
	public static final int ENUM_FUN_GROUP = 1;		//��合函数
	public static final int ENUM_FUN_TOGETHER = 2;	//�벐�函数
	
	private String engineName;		//��?��引擎�ݴѯ������引擎�᫧�，对于内部函数该�᫧�为FDML�?
	private String FunName;		//函数�ݴѯ������函数�᫧��?
	private boolean IsBuiltIn;		//是否为内置函数�ֽ�ߠ需注册，语�?��函数�?
	private int FunType;		//函数类型（集������?�벐�函数�?
	private int ReturnType;		//返回���数据类�?
	private int[] ParamTypeList;		//��셥参数数据类型列表
	private String ExecModel;		//�ا行模块对应类名称�ֽ对于内部函数其执行模块名称是确定�ЄＶ

	private Method ExecMethod;	//�ا行模块对应方法对象
	private Object ExecObject;	//�ا行模块对应类对�?
	
	public FunctionInfo(){
		ExecMethod=null;
		ExecObject=null;
	}
	public int getParamCount() {
		return ParamTypeList.length;
	}
	public String getFunName() {
		return FunName;
	}
	public void setFunName(String funName) {
		FunName = funName;
	}
	public boolean isIsBuiltIn() {
		return IsBuiltIn;
	}
	public void setIsBuiltIn(boolean isBuiltIn) {
		IsBuiltIn = isBuiltIn;
	}
	public int getFunType() {
		return FunType;
	}
	public void setFunType(int funType) {
		FunType = funType;
	}
	public int getReturnType() {
		return ReturnType;
	}
	public void setReturnType(int returnType) {
		ReturnType = returnType;
	}
	public int[] getParamTypeList() {
		return ParamTypeList;
	}
	public void setParamTypeList(int[] paramList) {
		ParamTypeList= new int[paramList.length];
		for(int i=0;i<paramList.length;i++){
			ParamTypeList[i] = paramList[i];
		}
	}
	public String getExecModel() {
		return ExecModel;
	}
	public void setExecModel(String execModel) {
		ExecModel = execModel;
	}
	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}	
	public Object getExecObject() {
		return ExecObject;
	}
	public void setExecObject(Object execObject) {
		ExecObject = execObject;
	}
	public Method getExecMethod() {
		return ExecMethod;
	}
	public void setExecMethod(Method execMethod) {
		ExecMethod = execMethod;
	}
}

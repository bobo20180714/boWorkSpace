package compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.BaseTree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import statement.CompilerStatementObj;
import statement.ICompilerStatement;
import statement.Statement_Call;
import statement.Statement_Const;
import statement.Statement_Define;
import statement.Statement_Expression;
import statement.Statement_TMRef;

import commom.TMParam;


public class AlarmCompiler {		//报警条件表达式编译类

	//全局日志对象
	private static Log GlobalLog = LogFactory.getLog(AlarmCompiler.class);
	public static Log getLog() {
		return GlobalLog;
	}
	//词法和语法分析对象
	private MyAlarmLexer GlobalLexer;
	private MyAlarmParser GlobalParser;
	
	// 以下为输入接口全局指针
	private IDataCheck GlobleExternalService; //外部检查接口
	// 以下为全局静态变量(只此一份，仅在对象初始化时创建)
	private HashMap<String, FunctionInfo> GlobalFunctionList; // 系统函数列表
	private OperateManage GlobalOperateManage; // 运算操作管理对象
	// 以下为全局变量(每次执行编译均需重新清零或初始化)
	private int GlobleTMRefCount;			//参数引用次数
	private int GlobleStatementIndex;			//语句序号变量
	private HashMap<String,ExpressionAnalyseResult> GlobalAnalyseResultList;//表达式分析结果对象
	private HashMap<String,ConstObj> GlobalConstList;//常量对象集合
	private HashMap<String,VarObj> GlobalVarList;//变量对象集合
	private ArrayList<CompilerStatementObj> GlobalStatementList; // 编译结果语句列表
	
	private void Init(){	// 初始化函数	
		//初始化语句序号变量
		GlobleStatementIndex=0;
		GlobleTMRefCount=0;
		// 初始化内部静态全局变量
		GlobalOperateManage = new OperateManage();
		// 初始化内部变量(一次创建，反复清理及使用)
		GlobalConstList=new HashMap<String,ConstObj>();
		GlobalVarList=new HashMap<String,VarObj>();
		GlobalAnalyseResultList=new HashMap<String,ExpressionAnalyseResult>();
		GlobalStatementList=new ArrayList<CompilerStatementObj>(); 
		//初始化函数列表
		InitFunctionList();
		// 外部接口初始化
		GlobleExternalService = null;	
	}
	public AlarmCompiler() {
		Init();
	}
	// 函数初始化过程(含内部及外部)
	private void InitFunctionList() {
		GlobalFunctionList = new HashMap<String, FunctionInfo>();
		InitBulitInFunction();
		if (GlobleExternalService != null) {
			InitExternalFunction();
		}
	}
	// 内部函数初始化过程，可以根据实际需要更新内部函数列表
	private void InitBulitInFunction() {
	}

	// 外部函数初始化过程
	private void InitExternalFunction() {
	}
	// 代码正确性分析（含代码编译及外部信息有效性检查）
	public CompilerOutput CodeAnalyse(CompilerInput input) {
		// 外部接口初始化
		GlobleExternalService = input.getDataCheckService();
		// 代码编译
		CompilerOutput Output = CodeCompiler(input.getSourceCode()); 
		if (Output.getCheckflag() == true) { // 在代码编译通过的情况下才进行外部信息有效性检查
			if (ExternalInfoCheck() == false) {// 外部信息有效性检查
				Output.setCheckflag(false);
			}
		}
		return Output;
	}
	// 外部信息有效性检查(利用外部信息检查接口进行)
	private boolean ExternalInfoCheck() {
		// TODO Auto-generated method stub
		return true;
	}
	// 代码编译（输入为代码字符串）
	public CompilerOutput CodeCompiler(String CodeText) {
		if(CodeText==null){
			CompilerOutput Output = CreateOutputObject(false);
			return Output;
		}
		ANTLRStringStream inputStream = new ANTLRStringStream(CodeText);
		// 初始化编译运行环境
		Reset();
		return ReScan(inputStream);
	}
	// 代码编译（输入为字节流）
	public CompilerOutput CodeCompiler(InputStream input) throws IOException {
		ANTLRInputStream inputStream = new ANTLRInputStream(input);
		// 初始化编译运行环境
		Reset();
		return ReScan(inputStream);
	}
	// 重置编译过程使用的内部对象(用于每次编译操作开始之前)
	private void Reset() {
		// 清除临时变量内容
		FreeTempObject();
		// 初始化内部变量
		GlobleStatementIndex = 0;
		GlobleTMRefCount=0;
	}
	// 释放编译过程使用的临时对象占用的内存空间
	private void FreeTempObject(){
		//每次调用编译过程都会清理已有的对象内容	
		int i;
		if (GlobalAnalyseResultList != null) {
			GlobalAnalyseResultList.clear();
		}
		if (GlobalConstList != null) {
			GlobalConstList.clear();
		}	
		if (GlobalVarList != null) {
			GlobalVarList.clear();
		}	
		if (GlobalStatementList != null) {
			for(i=0;i<GlobalStatementList.size();i++){
				GlobalStatementList.get(i).clear();//逐个清理语句对象
			}
			GlobalStatementList.clear();
		}	
		//清空全局变量内容
		if (GlobalLexer != null) {
			GlobalLexer.clear();
		}
		if (GlobalParser != null) {
			GlobalParser.clear();
		}
	}
	private void AddStatement(CompilerStatementObj aObj){		//添加编译结果语句
		GlobalStatementList.add(aObj);
	}
	private String getNodeKey(BaseTree ExpressionTree){		//获取树节点对应的唯一索引
		return ExpressionTree.getLine() + "_"
				+ ExpressionTree.getCharPositionInLine() + "_"
				+ ExpressionTree.getText();
	}
	//获取节点文本合并内容（不同节点的内容可能一样，此外无法获取括号等隐含符号）
	private String GetNodeText(BaseTree TreeNode) {	
		String ret = "";

		if (TreeNode.getChildCount() > 0) {
			int NodeType = TreeNode.getType();
			switch (NodeType) {
//			case AlarmParser.TOKEN_OBJ_REF: // 对象引用处理
//				// 将树节点的所有子节点内容合并起来
//				ret = TreeNode.getChild(0).getText();
//				for (int i = 1; i < TreeNode.getChildCount(); i++) {
//					ret = ret + "." + TreeNode.getChild(i).getText();
//				}
//				break;
			default: // 缺省作为表达式处理
				ret = TreeNode.getText();
				if (TreeNode.getChildCount() == 1) {
					ret = ret + GetNodeText((BaseTree) TreeNode.getChild(0));
				} else {
					ret = GetNodeText((BaseTree) TreeNode.getChild(0)) + ret;
					ret = ret + GetNodeText((BaseTree) TreeNode.getChild(1));
				}
				break;
			}
		} else {
			ret = TreeNode.getText();
		}
		return ret;
	}
	private void AddAnalyseResult(BaseTree expressionTree,ExpressionAnalyseResult aObj){	//添加表达式分析结果对象
		String TreeNodeKey=getNodeKey(expressionTree);
		GlobalAnalyseResultList.put(TreeNodeKey, aObj);
	}
	private ExpressionAnalyseResult getAnalyseResult(BaseTree expressionTree){	//获取表达式分析结果对象
		String TreeNodeKey=getNodeKey(expressionTree);
		return GlobalAnalyseResultList.get(TreeNodeKey);
	}
	private void AddConstObj(String ConstName,ConstObj aObj){	//添加常数对象
		GlobalConstList.put(ConstName.toUpperCase(), aObj);// 统一转换成大写字母
	}
	private int getAddressOfConstObj(String ConstName){	//获取常数对象地址
		ConstName=ConstName.toUpperCase();// 统一转换成大写字母
		return GlobalConstList.get(ConstName).getAddress();
	}	
	private void AddVarObj(String VarCode,VarObj aObj){	//添加变量对象
		GlobalVarList.put(VarCode.toUpperCase(), aObj);
	}
	private int getAddressOfVarObj(String VarCode){	//获取变量对象地址
		VarCode=VarCode.toUpperCase();// 统一转换成大写字母
		return GlobalVarList.get(VarCode).getAddress();
	}	
	private void SetLockFlagOfVarObj(String VarCode,boolean Lockflag){	//设置变量锁定状态
		if(VarCode!=null){		//当输入参数为空时保证代码不出错
			VarCode=VarCode.toUpperCase();// 统一转换成大写字母
			VarObj temp=GlobalVarList.get(VarCode);
			temp.setLockFlag(Lockflag);
			GlobalVarList.put(VarCode, temp);
		}
	}
	private String getFreeVarObj(int dataType){	//获取未锁定的变量
		String ret=null;
		Set<String> key;
		String keyString,VarCode;
		VarObj temp;
		int objCount=0;
		
		//遍历HashMap
        key = GlobalVarList.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
        	keyString = ((String) it.next()).toUpperCase();
        	temp=GlobalVarList.get(keyString);
        	if(temp.getDataType()==dataType){
        		objCount++;		//同类型临时变量计数
        		if(temp.isLockFlag()==false){
        			ret=keyString;
        			break;
        		}
        	}
        }
        if(ret==null){		//如果不存在未锁定的变量
        	if(dataType==AlarmParser.INT){
        		VarCode="TempInteger";
        	}else if(dataType==AlarmParser.FLOAT){
        		VarCode="TempFloat";
        	}else if(dataType==AlarmParser.BOOL){
        		VarCode="TempBool";
        	}else if(dataType==AlarmParser.STRING){
        		VarCode="TempString";
        	}else if(dataType==AlarmParser.TIME){
        		VarCode="TempTime";
        	}else{
        		VarCode="TempVar";
        	}
        	//使用特殊符号及序号作为临时变量代号
        	VarCode="@"+VarCode+String.valueOf(objCount+1);
        	VarCode=VarCode.toUpperCase();
        	//创建临时变量
        	CreateNewVarObj(VarCode, dataType);
        	ret=VarCode;		//返回新创建的变量
        }
		return ret;
	}
	// 释放编译过程使用的内部对象(用于销毁编译对象时释放内存空间)
	public void Clear() {
		// 清除临时变量内容
		FreeTempObject();
		// 清除静态变量内容
		if (GlobalOperateManage != null) {
			GlobalOperateManage.clear();
		}		
		if (GlobalFunctionList != null) {
			GlobalFunctionList.clear();
		}
		//设置所有全局变量为空
		GlobalAnalyseResultList=null;
		GlobalConstList=null;
		GlobalVarList=null;
		GlobalOperateManage=null;
		GlobalFunctionList=null;
	}
	// 输出调试信息到控制台
	private void DebugOutPut(String FunName, String OutputMsg) {
		if (getLog().isDebugEnabled()) {
			getLog().debug(FunName + ":	" + OutputMsg);
		}
	}
	// 语义分析及代码生成
	private CompilerOutput ReScan(ANTLRStringStream inputStream) {
		CompilerOutput Output = null;		
		
		ParseResult parseResult=Parse(inputStream);	//语法解析
		if (parseResult.isResultFlag()){ 
			AnalyseResult analyseResult=Analyse(parseResult);//语义分析
			if (analyseResult.isResultFlag()){ 
				ResultObj ResultObj=Generate(analyseResult);//代码生成
				if (ResultObj.isResultFlag()){ 
					Output = CreateOutputObject(true);
					Output.AddStatementList(GlobalStatementList);
				} else {
					Output = CreateOutputObject(false);
				}
			} else {
				Output = CreateOutputObject(false);
			}
		}else {
			Output = CreateOutputObject(false);
		}
		return Output;
	}
	// 根据编译结果标志创建编译输出结果对象
	private CompilerOutput CreateOutputObject(boolean flag) {
		CompilerOutput ret = new CompilerOutput();
		ret.setCheckflag(flag);
		ret.setInfoList(GetScanInfoList());
		ret.setInfoList(GetParseInfoList());
		return ret;
	}
	// 获取语法分析错误信息列表
	private ArrayList<CompilerInfoUnit> GetParseInfoList() {
		if (GlobalParser.getInfoList().size() > 0) {
			return GlobalParser.getInfoList();
		} else
			return null;
	}
	// 获取词法分析错误信息列表 
	private ArrayList<CompilerInfoUnit> GetScanInfoList() {
		if (GlobalLexer.getInfoList().size() > 0) {
			return GlobalLexer.getInfoList();
		} else
			return null;
	}
	// 获取编译错误信息个数
	private int GetErrorInfoCount() {
		int ret=0;
		//词法分析与语法分析同时进行，因此需要对两类信息输出进行检查
		if (GlobalLexer.getInfoList()!=null) {
			ret=ret+GlobalLexer.getInfoList().size();
		}
		if (GlobalParser.getInfoList()!=null) {
			ret=ret+GlobalParser.getInfoList().size();
		}
		return ret;
	}
	// 添加信息单元到输出列表中
	private void AddInfo(int type, int line, int row, String text) {
		CompilerInfoUnit x;
		x = new CompilerInfoUnit();
		x.setType(type);
		x.setLine(line);
		x.setRow(row);
		x.setText(text);
		GlobalParser.AddInfo(x);
		if (getLog().isDebugEnabled()) {
			getLog().debug(text);
		}
	}
	//语法分析（含词法分析）
	private ParseResult Parse(ANTLRStringStream inputStream)
	{
		ParseResult ret=new ParseResult();
		try {
			GlobalLexer = new MyAlarmLexer(inputStream);
			CommonTokenStream tokens = new CommonTokenStream(GlobalLexer);
			GlobalParser = new MyAlarmParser(tokens);
			//获取语法树，即执行语法解析
			BaseTree ResultTree=(BaseTree) GlobalParser.expression_only().getTree();
			if (GetErrorInfoCount() == 0){ // 解析成功
				ret.setResultTree(ResultTree);
				ret.setResultFlag(true);
			}else{
				ret.setResultFlag(false);		//发现分析错误时设置返回结果为false
			}
			DebugOutPut("Parse", String.valueOf(ret.isResultFlag()));
		} catch(Exception e) {
			DebugOutPut("Parse", "语法分析过程发生错误:"+e.toString());
			ret.setResultFlag(false);		//发生异常时设置返回结果为false
		}
		return ret;
	}	
	// 语义分析
	private AnalyseResult Analyse(ParseResult parseResult) {
		AnalyseResult analyseResult=new AnalyseResult();
		analyseResult.setAnalyseTree(parseResult.getResultTree());
		
		BaseTree TreeNode=parseResult.getResultTree();
		try {
			if (TreeNode.getChildCount() == 0) {
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, 1, 1, "条件表达式内容不能为空");
				return analyseResult;
			}else if (TreeNode.getChildCount() != 1) {
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, 1, 1, "条件表达式必须为单一条件");
				return analyseResult;
			}
			// 以下为代码语义分析过程
			BaseTree StatementNode;
			analyseResult.setResultFlag(true);
			for(int i=0;i<TreeNode.getChildCount();i++){//条件表达式中只有一条表达式语句，仅循环一次
				StatementNode=(BaseTree)(TreeNode.getChild(i));
				analyseResult.MergeResult(AnalyseStatement(StatementNode));
			}
		} catch (Exception e) {
			AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, TreeNode.getLine(),
					TreeNode.getCharPositionInLine(),
					"语义分析过程发生错误:" + e.toString());
		}
		DebugOutPut("Analyse", String.valueOf(analyseResult.isResultFlag()));
		return analyseResult;
	}
	// 代码生成
	private ResultObj Generate(AnalyseResult analyseResult) {
		ResultObj ret = new ResultObj();

		// 循环生成语句代码
		BaseTree TreeNode=analyseResult.getAnalyseTree();
		BaseTree StatementNode;
		ret.setResultFlag(true);
		for(int i=0;i<TreeNode.getChildCount();i++){
			StatementNode=(BaseTree)(TreeNode.getChild(i));
			ret.MergeResult(GenerateStatement(StatementNode));
		}
		DebugOutPut("Generate", String.valueOf(ret.isResultFlag()));
		return ret;
	}
	// 语句单元语义分析
	private ResultObj AnalyseStatement(BaseTree ASTree) {
		ResultObj ret = new ResultObj();
		int StatementType;

		// 根据语句类型执行不同的分析过程
		StatementType = ASTree.getType();
		switch (StatementType) {
		case AlarmParser.TOKEN_STAT_EXPRESSION:
			ret = AnalyseExpressionStatement(ASTree);
			break;
		default:
			ret.setResultFlag(false);
			AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ASTree.getLine(),
					ASTree.getCharPositionInLine(),
					"不可识别的语句类型:" + GetNodeText(ASTree));
		}
		return ret;
	}
	// 语句单元代码生成
	private ResultObj GenerateStatement(BaseTree ASTree) {
		ResultObj ret = new ResultObj();
		int StatementType;

		// 根据语句类型执行不同的分析过程
		StatementType = ASTree.getType();
		switch (StatementType) {
		case AlarmParser.TOKEN_STAT_EXPRESSION:
			ret = GenerateExpressionStatement(ASTree);
			break;
		default:
			ret.setResultFlag(false);
			AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ASTree.getLine(),
					ASTree.getCharPositionInLine(),
					"不可识别的语句类型:" + GetNodeText(ASTree));
		}
		return ret;
	}
	//*************************以上内容为编译器通用代码结构，只需做适应性修改即可*******************
	//*************************以下内容为编译器实际处理业务，应当根据需要重新编写*******************
	private ExpressionAnalyseResult AnalyseExpressionStatement(BaseTree ASTree) {
		ExpressionAnalyseResult ret;
		
		BaseTree ExpTree = (BaseTree) ASTree.getChild(0);
		// 报警条件表达式的内容必须是逻辑表达式
		ret = AnalyseLogicExpression(ExpTree);
		return ret;
	}
	// 逻辑表达式语义分析
	private ExpressionAnalyseResult AnalyseLogicExpression(BaseTree ExpressionTree) {
		ExpressionAnalyseResult ret=AnalyseExpression(ExpressionTree);
		if(ret.isResultFlag()){		//分析正确后进行附加语义检查
			if(ret.getResultDataType() != AlarmParser.BOOL){	//检查表达式结果是否为布尔类型
				ret.setResultFlag(false);
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ExpressionTree.getLine(),
						ExpressionTree.getCharPositionInLine(),
						"条件表达式必须是逻辑表达式:" + GetNodeText(ExpressionTree));			
			}
			if(GlobleTMRefCount==0){	//检查表达式中是否包含参数引用
				ret.setResultFlag(false);
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ExpressionTree.getLine(),
						ExpressionTree.getCharPositionInLine(),
						"条件表达式中必须包含参数引用:" + GetNodeText(ExpressionTree));			
			}
		}
		return ret;
	}
	// 分析表达式
	private ExpressionAnalyseResult AnalyseExpression(BaseTree ExpressionTree) {
		BaseTree Node_ExpLeft, Node_ExpRight;
		ExpressionAnalyseResult temp1,temp2;
		ExpressionAnalyseResult ret=new ExpressionAnalyseResult();
		int count;
		int ExpOperateType;

		ExpOperateType = ExpressionTree.getType();
		count = ExpressionTree.getChildCount();
		ret.setResultFlag(false);
		
		if(count==0){	//单个变量或常量
			if (IsConstValue(ExpOperateType)) { // 常量
				ret = AnalyseConst(ExpressionTree);	
			} else if (ExpOperateType == AlarmParser.ID) { // 变量
				ret = AnalyseID(ExpressionTree);
			}else{		///不应该出现此情况
				ret.setResultFlag(false);
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ExpressionTree.getLine(),
						ExpressionTree.getCharPositionInLine(), "不可识别的表达式项:"
								+ ExpressionTree.getText());
			}
		}else if(count==1){	//一元运算
			Node_ExpLeft=(BaseTree)ExpressionTree.getChild(0);
			ret=AnalyseExpression(Node_ExpLeft);
			ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_ONE);
			if(ret.isResultFlag()){//子表达式代码分析正确时再继续进行合并表达式代码分析
				int ResultDataType = getOperateDataType(ExpOperateType,ret.getResultDataType(), AlarmParser.NULL);
				if (ResultDataType == AlarmParser.NULL) { // 如果表达式数据类型检查未通过
					ret.setResultFlag(false);
					AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ExpressionTree.getLine(),
							ExpressionTree.getCharPositionInLine(),
							"参与一元运算的数据类型不匹配:" + GetNodeText(Node_ExpLeft));
				}else{
					ret.setResultDataType(ResultDataType);
				}
			}
		}else{		//二元运算	
			Node_ExpLeft=(BaseTree)ExpressionTree.getChild(0);
			Node_ExpRight=(BaseTree)ExpressionTree.getChild(1);
			temp1=AnalyseExpression(Node_ExpLeft);
			temp2=AnalyseExpression(Node_ExpRight);
			ret=MergeExpressionAnalyseResult(ExpOperateType,temp1,temp2);
			if(ret.isResultFlag()){//子表达式代码分析正确时再继续进行合并表达式代码分析
				int ResultDataType = getOperateDataType(ExpOperateType,temp1.getResultDataType(), temp2.getResultDataType());
				if (ResultDataType == AlarmParser.NULL) { // 如果表达式数据类型检查未通过
					ret.setResultFlag(false);
					AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, ExpressionTree.getLine(),
							ExpressionTree.getCharPositionInLine(),
							"参与表达式的数据类型不匹配:" + GetNodeText(ExpressionTree));
				}else{
					ret.setResultDataType(ResultDataType);
				}
			}
		}
		if (ret.isResultFlag()) {// 表达式分析正确时将分析结果记入全局表以备后用			
			AddAnalyseResult(ExpressionTree, ret);
		}
		return ret;
	}
	//合并表达式分析结果
	private ExpressionAnalyseResult MergeExpressionAnalyseResult(
			int expOperateType, ExpressionAnalyseResult r1,ExpressionAnalyseResult r2) {
		ExpressionAnalyseResult ret = new ExpressionAnalyseResult();
		ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_TWO);
		ret.setResultFlag(r1.isResultFlag() && r2.isResultFlag());
		return ret;
	}
	private int getOperateDataType(int expOperateType, int DataType1,int DataType2){	//获取操作结果数据类型
		return GlobalOperateManage.OperateCheck(expOperateType,DataType1,DataType2);	
	}
	//分析变量
	private ExpressionAnalyseResult AnalyseID(BaseTree expressionTree) {
		// 此处的变量只能测参数，通过外部接口检查其合法性即可
		ExpressionAnalyseResult ret = new ExpressionAnalyseResult();
		if(GlobleExternalService==null){
			//外部接口不能为空，以下代码仅供测试时使用
			ret.setResultFlag(true);
			ret.setResultDataType(AlarmParser.FLOAT);
			ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_ITEM);
			GlobleTMRefCount++;
		}else{
			TMParam temp=GlobleExternalService.getParameter(expressionTree.getText());
			if(temp!=null){
				ret.setResultFlag(true);
				ret.setResultDataType(temp.getDataType());
				ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_ITEM);
				GlobleTMRefCount++;
			}else{
				ret.setResultFlag(false);
				AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, expressionTree.getLine(),
						expressionTree.getCharPositionInLine(),
						"不可识别的参数:" + expressionTree.getText());
			}
		}
		return ret;
	}

	private ResultObj GenerateExpressionStatement(BaseTree ASTree) {
		ResultObj ret;
		
		BaseTree ExpTree = (BaseTree) ASTree.getChild(0);
		ret = GenerateExpression(ExpTree);
		return ret;
	}
	private ExpressionGenerateResult GenerateExpression(BaseTree ExpressionTree) {
		BaseTree Node_ExpLeft, Node_ExpRight;
		ExpressionGenerateResult temp;
		ExpressionGenerateResult ret=new ExpressionGenerateResult();
		int count;
		int ExpOperateType;
		int tempAddress;
		String tempVarCode;
		Statement_Expression ExpStatement;

		ExpOperateType = ExpressionTree.getType();
		count = ExpressionTree.getChildCount();

		if(count==0){	//单个变量或常量
			if (IsConstValue(ExpOperateType)) { // 常量
				ret = CreateNewConstObj(ExpressionTree);
			} else if (ExpOperateType == AlarmParser.ID) { // 变量
				ret = CreateNewTMRef(ExpressionTree);	//表达式中的显式变量均为参数引用
			}else{		///不应该出现此情况
				ret.setResultFlag(false);
				AddInfo(InterfaceCompiler.ENUM_ERROR_GENERATE, ExpressionTree.getLine(),
						ExpressionTree.getCharPositionInLine(), "不可识别的表达式项:"
								+ ExpressionTree.getText());
			}
		}else if(count==1){	//一元运算
			Node_ExpLeft=(BaseTree)ExpressionTree.getChild(0);
			ret=GenerateExpression(Node_ExpLeft);
			if(ret.isResultFlag()){		//子表达式代码生成正确时再继续进行合并表达式代码生成
				// 根据参与运算的数据类型生成表达式语句
				if (ExpOperateType == AlarmParser.SUB) { // -运算符
					// 将-运算符处理为0减原表达式
					//解除子表达式对应临时变量锁定状态
					SetLockFlagOfVarObj(ret.getResultVarCode(), false);
					//获取空闲的临时变量
					tempVarCode=getFreeVarObj(getAnalyseResult(ExpressionTree).getResultDataType());		//获取空闲的临时变量
					SetLockFlagOfVarObj(tempVarCode,true);//锁定临时变量
					tempAddress=getAddressOfVarObj(tempVarCode);//获取变量地址
					//创建常量0
					temp=CreateNewConstObj("0",AlarmParser.FLOAT);
					//创建表达式语句
					ExpStatement = CreateExpStatement(ExpOperateType);
					ExpStatement.setData1(temp.getResultAddress());
					ExpStatement.setData2(ret.getResultAddress());
					ExpStatement.setResult(tempAddress);	//表达式结果保存至临时变量
					AddStatement(ExpStatement);//添加语句对象到全局语句列表中
					//设置返回结果
					ret.setResultAddress(tempAddress);//表达式结果变量地址
					ret.setResultVarCode(tempVarCode);//表达式结果临时变量名称
					ret.setStatementIndex(ExpStatement.getIndex());//所在语句行号
				} else if (ExpOperateType == AlarmParser.KW_NOT) { // 逻辑非运算
					// 将逻辑非运算处理为一元运算表达式
					//解除子表达式对应临时变量锁定状态
					SetLockFlagOfVarObj(ret.getResultVarCode(), false);
					//获取空闲的临时变量
					tempVarCode=getFreeVarObj(getAnalyseResult(ExpressionTree).getResultDataType());		
					SetLockFlagOfVarObj(tempVarCode,true);//锁定临时变量
					tempAddress=getAddressOfVarObj(tempVarCode);//获取变量地址
					//创建表达式语句
					ExpStatement = CreateExpStatement(ExpOperateType);
					ExpStatement.setData1(ret.getResultAddress());
					ExpStatement.setData2(0);		//一元运算，操作数2为空
					ExpStatement.setResult(tempAddress);	//表达式结果保存至临时变量
					AddStatement(ExpStatement);//添加语句对象到全局语句列表中
					//设置返回结果
					ret.setResultAddress(tempAddress);//表达式结果变量地址
					ret.setResultVarCode(tempVarCode);//表达式结果变量名称
					ret.setStatementIndex(ExpStatement.getIndex());//所在语句行号
				}else { // 不应该出现此情况
					ret.setResultFlag(false);
					AddInfo(InterfaceCompiler.ENUM_ERROR_GENERATE, ExpressionTree.getLine(),
							ExpressionTree.getCharPositionInLine(),
							"不可识别的运算类型:" + ExpressionTree.getText());
				}
			}
		}else{		//二元运算	
			Node_ExpLeft=(BaseTree)ExpressionTree.getChild(0);
			Node_ExpRight=(BaseTree)ExpressionTree.getChild(1);
			ret=GenerateExpression(Node_ExpLeft);
			temp=GenerateExpression(Node_ExpRight);
			ret.MergeResult(temp);	//合并结果标志
			if(ret.isResultFlag()){		//子表达式代码生成正确时再继续进行合并表达式代码生成
				//解除子表达式对应临时变量锁定状态
				SetLockFlagOfVarObj(ret.getResultVarCode(), false);
				SetLockFlagOfVarObj(temp.getResultVarCode(), false);
				//获取空闲的临时变量
				tempVarCode=getFreeVarObj(getAnalyseResult(ExpressionTree).getResultDataType());		
				SetLockFlagOfVarObj(tempVarCode,true);//锁定临时变量
				tempAddress=getAddressOfVarObj(tempVarCode);//获取变量地址
				// 生成表达式代码
				ExpStatement = CreateExpStatement(ExpOperateType);
				ExpStatement.setData1(ret.getResultAddress());
				ExpStatement.setData2(temp.getResultAddress());
				ExpStatement.setResult(tempAddress);	//表达式结果保存至临时变量
				AddStatement(ExpStatement);//添加语句对象到全局语句列表中		
				//设置返回结果
				ret.setResultAddress(tempAddress);//表达式结果变量地址
				ret.setResultVarCode(tempVarCode);//表达式结果变量名称
				ret.setStatementIndex(ExpStatement.getIndex());//所在语句行号
			}
		}
		return ret;
	}
	private ExpressionGenerateResult CreateNewTMRef(BaseTree expressionTree) {//创建参数引用
		ExpressionGenerateResult ret=new ExpressionGenerateResult();
		int ResultAddress;
		
		int DataType = getAnalyseResult(expressionTree).getResultDataType();
		String VarCode=expressionTree.getText().toUpperCase();	//转换为大写字母
		if(GlobalVarList.containsKey(VarCode)==false){		//如果不存在指定变量
			//语句对象处理
			Statement_TMRef NewStatement;
			NewStatement = CreateTMRefStatement();//创建语句对象（表达式中的显式变量均为参数引用）
			NewStatement.setDataType(DataType);
			NewStatement.setParamCode(VarCode);
			AddStatement(NewStatement);//添加语句对象到全局语句列表中
			ResultAddress=NewStatement.getIndex();//对象地址就是语句序号
			//全局变量对象处理
			VarObj temp=new VarObj();
			temp.setAddress(ResultAddress);
			temp.setDataType(DataType);// 设置对象数据类型
			temp.setLockFlag(true);	//参数引用不能再次使用，所以将其锁定标志置为true
			AddVarObj(VarCode,temp);//添加变量对象到全局列表中
		}else{
			ResultAddress=getAddressOfVarObj(VarCode);
		}
		//设置返回结果
		ret.setResultFlag(true);
		ret.setResultAddress(ResultAddress);
		ret.setStatementIndex(ResultAddress);
		return ret;
	}
	private ExpressionGenerateResult CreateNewVarObj(String VarCode,int DataType) {	//创建变量对象
		ExpressionGenerateResult ret=new ExpressionGenerateResult();
		int ResultAddress;
		VarCode=VarCode.toUpperCase();	//转换为大写字母
		if(GlobalVarList.containsKey(VarCode)==false){		//如果不存在指定变量
			//语句对象处理
			Statement_Define NewStatement;
			NewStatement = CreateDefineStatement();//创建语句对象
			NewStatement.setDataType(DataType);
			NewStatement.setVarCode(VarCode);
			AddStatement(NewStatement);//添加语句对象到全局语句列表中
			ResultAddress=NewStatement.getIndex();//对象地址就是语句序号
			//全局变量对象处理
			VarObj temp=new VarObj();
			temp.setAddress(ResultAddress);
			temp.setDataType(DataType);// 设置对象数据类型
			temp.setLockFlag(false);
			AddVarObj(VarCode,temp);//添加变量对象到全局列表中
		}else{
			ResultAddress=getAddressOfVarObj(VarCode);
		}
		//设置返回结果
		ret.setResultFlag(true);
		ret.setResultAddress(ResultAddress);
		ret.setStatementIndex(ResultAddress);
		return ret;
	}
	private ExpressionGenerateResult CreateNewConstObj(String ConstName,int DataType){	//新建常数对象
		ExpressionGenerateResult ret=new ExpressionGenerateResult();
		int ResultAddress;
		ConstName=ConstName.toUpperCase();	//转换为大写字母
		if(GlobalConstList.containsKey(ConstName)==false){		//如果不存在指定常数
			//语句对象处理
			Statement_Const NewStatement;
			NewStatement = CreateConstStatement();//创建语句对象
			NewStatement.setDataType(DataType);
			NewStatement.setNameString(ConstName);
			AddStatement(NewStatement);//添加语句对象到全局语句列表中
			ResultAddress=NewStatement.getIndex();//对象地址就是语句序号
			//全局常量对象处理
			ConstObj temp=new ConstObj();
			temp.setAddress(ResultAddress);
			temp.setDataType(DataType);// 设置对象数据类型
			AddConstObj(ConstName,temp);//添加常量对象到全局列表中
		}else{
			ResultAddress=getAddressOfConstObj(ConstName);
		}
		//设置返回结果
		ret.setResultFlag(true);
		ret.setResultAddress(ResultAddress);
		ret.setStatementIndex(ResultAddress);
		return ret;
	}
	private ExpressionGenerateResult CreateNewConstObj(BaseTree expressionTree) {	//创建常量对象
		int DataType = getAnalyseResult(expressionTree).getResultDataType();
		String ConstName=expressionTree.getText();
		return CreateNewConstObj(ConstName,DataType);
	}
	// 分析常数
	private ExpressionAnalyseResult AnalyseConst(BaseTree expressionTree) {
		ExpressionAnalyseResult ret= new ExpressionAnalyseResult();
		int Node_Type = expressionTree.getType();
		switch (Node_Type) {
		case AlarmParser.INT:
			// 设置表达式分析结果
			ret.setResultFlag(true);
			ret.setResultDataType(AlarmParser.INT);
			ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_ITEM);
			break;
		case AlarmParser.FLOAT:
			// 设置表达式分析结果
			ret.setResultFlag(true);
			ret.setResultDataType(AlarmParser.FLOAT);
			ret.setExpType(ExpressionAnalyseResult.EXP_TYPE_ITEM);
			break;
		default:
			ret.setResultFlag(false);
			AddInfo(InterfaceCompiler.ENUM_ERROR_ANALYSE, expressionTree.getLine(),
					expressionTree.getCharPositionInLine(), "不可识别的常数类型:"
							+ expressionTree.getText());
		}
		return ret;
	}
	// 判断是否为常数值
	private boolean IsConstValue(int type) {
		if (type == AlarmParser.INT || type == AlarmParser.FLOAT) {
			return true;
		} else {
			return false;
		}
	}
	// 创建语句对象
	private CompilerStatementObj CreateNewStatement(int StatementType) {
		CompilerStatementObj ret;
		switch (StatementType) {
		case ICompilerStatement.STAT_CONST:
			ret = new Statement_Const();
			break;
		case ICompilerStatement.STAT_DEFINE:
			ret = new Statement_Define();
			break;
		case ICompilerStatement.STAT_TMREF:
			ret = new Statement_TMRef();
			break;
		case ICompilerStatement.STAT_CALL:
			ret = new Statement_Call();
			break;
		case ICompilerStatement.STAT_EXP:
			ret = new Statement_Expression();
			break;
		default:
			ret = null;
		}
		ret.setIndex(newStatementIndex());
		return ret;
	}

	// 获取新的内部语句序号
	private int newStatementIndex() {
		GlobleStatementIndex++;
		return GlobleStatementIndex;
	}
	// 创建常量定义语句
	private Statement_Const CreateConstStatement() {
		Statement_Const AStatement;
		// 创建语句对象
		AStatement = (Statement_Const) CreateNewStatement(ICompilerStatement.STAT_CONST);
		return AStatement;
	}
	// 创建变量定义语句
	private Statement_Define CreateDefineStatement() {
		Statement_Define AStatement;
		// 创建语句对象
		AStatement = (Statement_Define) CreateNewStatement(ICompilerStatement.STAT_DEFINE);
		return AStatement;
	}
	// 创建参数引用语句
	private Statement_TMRef CreateTMRefStatement() {
		Statement_TMRef AStatement;
		// 创建语句对象
		AStatement = (Statement_TMRef) CreateNewStatement(ICompilerStatement.STAT_TMREF);
		return AStatement;
	}
	// 创建表达式语句
	private Statement_Expression CreateExpStatement(int OpType) {
		Statement_Expression AStatement;
		// 创建语句对象
		AStatement = (Statement_Expression) CreateNewStatement(ICompilerStatement.STAT_EXP);
		AStatement.setOperator(OpType);
		return AStatement;
	}
	// 创建函数调用语句
	private Statement_Call CreateCallStatement() {
		Statement_Call AStatement;
		// 创建语句对象
		AStatement = (Statement_Call) CreateNewStatement(ICompilerStatement.STAT_CALL);
		return AStatement;
	}
}

package compiler;

import java.util.ArrayList;

import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.MissingTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.UnwantedTokenException;


public class MyAlarmParser extends AlarmParser {	
	private ArrayList<CompilerInfoUnit> InfoList;	//错误信息列表
	public ArrayList<CompilerInfoUnit> getInfoList() {
		return InfoList;
	}

	// 清除错误信息列表内容
	public void clear() {
		if(InfoList!=null){
			InfoList.clear();
			InfoList=null;
		}
	}
	public void AddInfo(CompilerInfoUnit x) {
		InfoList.add(x);
	}

	public MyAlarmParser(TokenStream input) {
		super(input);
		InfoList=new ArrayList<CompilerInfoUnit>();
	}
	
	
	//********************以下内容为手动添加以实现错误信息收集********************

	//添加该函数以实现错误信息收集
	public void AddError(int type,int line,int row,String text)
	{
		CompilerInfoUnit x;
		x=new CompilerInfoUnit();
		x.setType(type);
		x.setLine(line);
		x.setRow(row);
		x.setText(text);
		InfoList.add(x);
	}
	//重载该函数以实现错误信息收集
	public void reportError(RecognitionException e) {
		// if we've already reported an error and have not matched a token
		// yet successfully, don't report any errors.
		if ( state.errorRecovery ) {
			//System.err.print("[SPURIOUS] ");
			return;
		}
		state.syntaxErrors++; // don't count spurious
		state.errorRecovery = true;

		displayRecognitionError(this.getTokenNames(), e);
		String msg = getErrorMessageOfCHS(e, this.getTokenNames());
		AddError(InterfaceCompiler.ENUM_ERROR_PARSE,e.line,e.charPositionInLine,msg);
	}
	//重写函数getErrorMessage为getErrorMessageOfCHS以实现错误信息汉化
	public String getErrorMessageOfCHS(RecognitionException e, String[] tokenNames) {
		String msg = e.getMessage();
		if ( e instanceof UnwantedTokenException ) {
			UnwantedTokenException ute = (UnwantedTokenException)e;
			String tokenName;
			if ( ute.expecting== Token.EOF ) {
				//tokenName = "EOF";
				tokenName = "结束符";
			}
			else {
				tokenName = tokenNames[ute.expecting];
			}
			msg = "无效内容  "+getTokenErrorDisplay(ute.getUnexpectedToken())+
				" 预期输入 "+tokenName;
		}
		else if ( e instanceof MissingTokenException ) {
			MissingTokenException mte = (MissingTokenException)e;
			String tokenName;
			if ( mte.expecting== Token.EOF ) {
				//tokenName = "EOF";
				tokenName = "结束符";
			}
			else {
				tokenName = tokenNames[mte.expecting];
			}
			msg = "缺少 "+tokenName+" 在 "+getTokenErrorDisplay(e.token);
		}
		else if ( e instanceof MismatchedTokenException ) {
			MismatchedTokenException mte = (MismatchedTokenException)e;
			String tokenName;
			if ( mte.expecting== Token.EOF ) {
				//tokenName = "EOF";
				tokenName = "结束符";
			}
			else {
				tokenName = tokenNames[mte.expecting];
			}
			msg = "不匹配的内容 "+getTokenErrorDisplay(e.token)+
				" 预期输入 "+tokenName;
		}
		else if ( e instanceof MismatchedTreeNodeException ) {
			MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
			String tokenName;
			if ( mtne.expecting==Token.EOF ) {
				//tokenName = "EOF";
				tokenName = "结束符";
			}
			else {
				tokenName = tokenNames[mtne.expecting];
			}
			msg = "不匹配的内容 "+mtne.node+
				" 预期输入 "+tokenName;
		}
		else if ( e instanceof NoViableAltException ) {
			//NoViableAltException nvae = (NoViableAltException)e;
			// for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
			// and "(decision="+nvae.decisionNumber+") and
			// "state "+nvae.stateNumber
			msg = "缺少适当的可选内容 "+getTokenErrorDisplay(e.token);
		}
		else if ( e instanceof EarlyExitException ) {
			//EarlyExitException eee = (EarlyExitException)e;
			// for development, can add "(decision="+eee.decisionNumber+")"
			msg = "无法匹配输入内容 "+
				getTokenErrorDisplay(e.token);
		}
		else if ( e instanceof MismatchedSetException ) {
			MismatchedSetException mse = (MismatchedSetException)e;
			msg = "不匹配的内容 "+getTokenErrorDisplay(e.token)+
				" 预期输入 "+mse.expecting;
		}
		else if ( e instanceof MismatchedNotSetException ) {
			MismatchedNotSetException mse = (MismatchedNotSetException)e;
			msg = "不匹配的内容 "+getTokenErrorDisplay(e.token)+
				" 预期输入 "+mse.expecting;
		}
		else if ( e instanceof FailedPredicateException ) {
			FailedPredicateException fpe = (FailedPredicateException)e;
			msg = "语法规则 "+fpe.ruleName+" 断言失败: {"+
				fpe.predicateText+"}?";
		}
		return msg;
	}
	//********************以上内容为手动添加以实现错误信息收集********************

}

package compiler;

import java.util.ArrayList;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedRangeException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;

public class MyAlarmLexer extends AlarmLexer {
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

	public MyAlarmLexer(CharStream input) {
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
		displayRecognitionError(this.getTokenNames(), e);
		String msg = getErrorMessageOfCHS(e, this.getTokenNames());
		AddError(InterfaceCompiler.ENUM_ERROR_SCAN,e.line,e.charPositionInLine,msg);
	}
	//重写函数getErrorMessage为getErrorMessageOfCHS以实现错误信息汉化
	public String getErrorMessageOfCHS(RecognitionException e, String[] tokenNames) {
		String msg;
		if ( e instanceof MismatchedTokenException ) {
			MismatchedTokenException mte = (MismatchedTokenException)e;
			msg = "不匹配的字符 "+getCharErrorDisplay(e.c)+" 预期输入 "+getCharErrorDisplay(mte.expecting);
		}
		else if ( e instanceof NoViableAltException ) {
			NoViableAltException nvae = (NoViableAltException)e;
			// for development, can add "decision=<<"+nvae.grammarDecisionDescription+">>"
			// and "(decision="+nvae.decisionNumber+") and
			// "state "+nvae.stateNumber
			msg = "不可识别的字符 "+getCharErrorDisplay(e.c);
		}
		else if ( e instanceof EarlyExitException ) {
			EarlyExitException eee = (EarlyExitException)e;
			// for development, can add "(decision="+eee.decisionNumber+")"
			msg = "不匹配的字符 "+getCharErrorDisplay(e.c);
		}
		else if ( e instanceof MismatchedNotSetException ) {
			MismatchedNotSetException mse = (MismatchedNotSetException)e;
			msg = "不匹配的字符 "+getCharErrorDisplay(e.c)+" 预期输入 "+mse.expecting;
		}
		else if ( e instanceof MismatchedSetException ) {
			MismatchedSetException mse = (MismatchedSetException)e;
			msg = "不匹配的字符"+getCharErrorDisplay(e.c)+" 预期输入 "+mse.expecting;
		}
		else if ( e instanceof MismatchedRangeException ) {
			MismatchedRangeException mre = (MismatchedRangeException)e;
			msg = "不匹配的字符 "+getCharErrorDisplay(e.c)+" 预期输入 "+
				  getCharErrorDisplay(mre.a)+".."+getCharErrorDisplay(mre.b);
		}
		else {
			msg = super.getErrorMessage(e, tokenNames);
		}
		return msg;
	}
}

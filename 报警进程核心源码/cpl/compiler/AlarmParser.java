package compiler;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class AlarmParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD", "BAND", "BNOT", "BOR", 
		"BXOR", "COMMENT", "DIV", "EQ", "ERROR_CHAR", "EXPONENT", "FLOAT", "GE", 
		"GT", "ID", "IDV", "INT", "KW_AND", "KW_NOT", "KW_OR", "LE", "LMOV", "LPAREN", 
		"LT", "MOD", "MUL", "NE", "RMOV", "RPAREN", "SUB", "TOKEN_SCRIPT_3LEVEL", 
		"TOKEN_STAT_EXPRESSION", "WS", "STRING", "TM", "TOKEN_ALL_OBJ", "TIME", 
		"BOOL", "NULL"
	};
	public static final int EOF=-1;
	public static final int ADD=4;
	public static final int BAND=5;
	public static final int BNOT=6;
	public static final int BOR=7;
	public static final int BXOR=8;
	public static final int COMMENT=9;
	public static final int DIV=10;
	public static final int EQ=11;
	public static final int ERROR_CHAR=12;
	public static final int EXPONENT=13;
	public static final int FLOAT=14;
	public static final int GE=15;
	public static final int GT=16;
	public static final int ID=17;
	public static final int IDV=18;
	public static final int INT=19;
	public static final int KW_AND=20;
	public static final int KW_NOT=21;
	public static final int KW_OR=22;
	public static final int LE=23;
	public static final int LMOV=24;
	public static final int LPAREN=25;
	public static final int LT=26;
	public static final int MOD=27;
	public static final int MUL=28;
	public static final int NE=29;
	public static final int RMOV=30;
	public static final int RPAREN=31;
	public static final int SUB=32;
	public static final int TOKEN_SCRIPT_3LEVEL=33;
	public static final int TOKEN_STAT_EXPRESSION=34;
	public static final int WS=35;
	public static final int STRING=36;
	public static final int TM=37;
	public static final int TOKEN_ALL_OBJ=38;
	public static final int TIME=39;
	public static final int BOOL=40;
	public static final int NULL=41;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public AlarmParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public AlarmParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return AlarmParser.tokenNames; }
	@Override public String getGrammarFileName() { return "D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g"; }


	public static class expression_only_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression_only"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:19:1: expression_only : expression EOF !;
	public final AlarmParser.expression_only_return expression_only() throws Exception  {
		AlarmParser.expression_only_return retval = new AlarmParser.expression_only_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token EOF2=null;
		ParserRuleReturnScope expression1 =null;

		CommonTree EOF2_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:20:2: ( expression EOF !)
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:20:4: expression EOF !
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_expression_in_expression_only65);
			expression1=expression();
			state._fsp--;

			adaptor.addChild(root_0, expression1.getTree());

			EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_expression_only68); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression_only"


	public static class expression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:22:1: expression : logicalOrExpression -> ^( TOKEN_SCRIPT_3LEVEL ^( TOKEN_STAT_EXPRESSION logicalOrExpression ) ) ;
	public final AlarmParser.expression_return expression() throws Exception  {
		AlarmParser.expression_return retval = new AlarmParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope logicalOrExpression3 =null;

		RewriteRuleSubtreeStream stream_logicalOrExpression=new RewriteRuleSubtreeStream(adaptor,"rule logicalOrExpression");

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:23:2: ( logicalOrExpression -> ^( TOKEN_SCRIPT_3LEVEL ^( TOKEN_STAT_EXPRESSION logicalOrExpression ) ) )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:23:4: logicalOrExpression
			{
			pushFollow(FOLLOW_logicalOrExpression_in_expression79);
			logicalOrExpression3=logicalOrExpression();
			state._fsp--;

			stream_logicalOrExpression.add(logicalOrExpression3.getTree());
			// AST REWRITE
			// elements: logicalOrExpression
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 24:3: -> ^( TOKEN_SCRIPT_3LEVEL ^( TOKEN_STAT_EXPRESSION logicalOrExpression ) )
			{
				// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:24:6: ^( TOKEN_SCRIPT_3LEVEL ^( TOKEN_STAT_EXPRESSION logicalOrExpression ) )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(TOKEN_SCRIPT_3LEVEL, "TOKEN_SCRIPT_3LEVEL"), root_1);
				// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:24:28: ^( TOKEN_STAT_EXPRESSION logicalOrExpression )
				{
				CommonTree root_2 = (CommonTree)adaptor.nil();
				root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(TOKEN_STAT_EXPRESSION, "TOKEN_STAT_EXPRESSION"), root_2);
				adaptor.addChild(root_2, stream_logicalOrExpression.nextTree());
				adaptor.addChild(root_1, root_2);
				}

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class logicalOrExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "logicalOrExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:26:1: logicalOrExpression : logicalAndExpression ( KW_OR ^ logicalAndExpression )* ;
	public final AlarmParser.logicalOrExpression_return logicalOrExpression() throws Exception  {
		AlarmParser.logicalOrExpression_return retval = new AlarmParser.logicalOrExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token KW_OR5=null;
		ParserRuleReturnScope logicalAndExpression4 =null;
		ParserRuleReturnScope logicalAndExpression6 =null;

		CommonTree KW_OR5_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:27:2: ( logicalAndExpression ( KW_OR ^ logicalAndExpression )* )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:27:4: logicalAndExpression ( KW_OR ^ logicalAndExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_logicalAndExpression_in_logicalOrExpression103);
			logicalAndExpression4=logicalAndExpression();
			state._fsp--;

			adaptor.addChild(root_0, logicalAndExpression4.getTree());

			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:27:25: ( KW_OR ^ logicalAndExpression )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==KW_OR) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:27:27: KW_OR ^ logicalAndExpression
					{
					KW_OR5=(Token)match(input,KW_OR,FOLLOW_KW_OR_in_logicalOrExpression107); 
					KW_OR5_tree = (CommonTree)adaptor.create(KW_OR5);
					root_0 = (CommonTree)adaptor.becomeRoot(KW_OR5_tree, root_0);

					pushFollow(FOLLOW_logicalAndExpression_in_logicalOrExpression111);
					logicalAndExpression6=logicalAndExpression();
					state._fsp--;

					adaptor.addChild(root_0, logicalAndExpression6.getTree());

					}
					break;

				default :
					break loop1;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logicalOrExpression"


	public static class logicalAndExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "logicalAndExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:29:1: logicalAndExpression : relationalExpression ( KW_AND ^ relationalExpression )* ;
	public final AlarmParser.logicalAndExpression_return logicalAndExpression() throws Exception  {
		AlarmParser.logicalAndExpression_return retval = new AlarmParser.logicalAndExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token KW_AND8=null;
		ParserRuleReturnScope relationalExpression7 =null;
		ParserRuleReturnScope relationalExpression9 =null;

		CommonTree KW_AND8_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:30:2: ( relationalExpression ( KW_AND ^ relationalExpression )* )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:30:4: relationalExpression ( KW_AND ^ relationalExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_relationalExpression_in_logicalAndExpression124);
			relationalExpression7=relationalExpression();
			state._fsp--;

			adaptor.addChild(root_0, relationalExpression7.getTree());

			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:30:25: ( KW_AND ^ relationalExpression )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==KW_AND) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:30:27: KW_AND ^ relationalExpression
					{
					KW_AND8=(Token)match(input,KW_AND,FOLLOW_KW_AND_in_logicalAndExpression128); 
					KW_AND8_tree = (CommonTree)adaptor.create(KW_AND8);
					root_0 = (CommonTree)adaptor.becomeRoot(KW_AND8_tree, root_0);

					pushFollow(FOLLOW_relationalExpression_in_logicalAndExpression132);
					relationalExpression9=relationalExpression();
					state._fsp--;

					adaptor.addChild(root_0, relationalExpression9.getTree());

					}
					break;

				default :
					break loop2;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "logicalAndExpression"


	public static class relationalExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relationalExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:32:1: relationalExpression : additiveExpression ( relationalOperator ^ additiveExpression )? ;
	public final AlarmParser.relationalExpression_return relationalExpression() throws Exception  {
		AlarmParser.relationalExpression_return retval = new AlarmParser.relationalExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope additiveExpression10 =null;
		ParserRuleReturnScope relationalOperator11 =null;
		ParserRuleReturnScope additiveExpression12 =null;


		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:33:2: ( additiveExpression ( relationalOperator ^ additiveExpression )? )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:33:4: additiveExpression ( relationalOperator ^ additiveExpression )?
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_additiveExpression_in_relationalExpression146);
			additiveExpression10=additiveExpression();
			state._fsp--;

			adaptor.addChild(root_0, additiveExpression10.getTree());

			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:33:23: ( relationalOperator ^ additiveExpression )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==EQ||(LA3_0 >= GE && LA3_0 <= GT)||LA3_0==LE||LA3_0==LT||LA3_0==NE) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:33:25: relationalOperator ^ additiveExpression
					{
					pushFollow(FOLLOW_relationalOperator_in_relationalExpression150);
					relationalOperator11=relationalOperator();
					state._fsp--;

					root_0 = (CommonTree)adaptor.becomeRoot(relationalOperator11.getTree(), root_0);
					pushFollow(FOLLOW_additiveExpression_in_relationalExpression154);
					additiveExpression12=additiveExpression();
					state._fsp--;

					adaptor.addChild(root_0, additiveExpression12.getTree());

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relationalExpression"


	public static class additiveExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "additiveExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:35:1: additiveExpression : multiplicativeExpression ( additiveOperator ^ multiplicativeExpression )* ;
	public final AlarmParser.additiveExpression_return additiveExpression() throws Exception  {
		AlarmParser.additiveExpression_return retval = new AlarmParser.additiveExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope multiplicativeExpression13 =null;
		ParserRuleReturnScope additiveOperator14 =null;
		ParserRuleReturnScope multiplicativeExpression15 =null;


		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:36:2: ( multiplicativeExpression ( additiveOperator ^ multiplicativeExpression )* )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:36:4: multiplicativeExpression ( additiveOperator ^ multiplicativeExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression167);
			multiplicativeExpression13=multiplicativeExpression();
			state._fsp--;

			adaptor.addChild(root_0, multiplicativeExpression13.getTree());

			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:36:29: ( additiveOperator ^ multiplicativeExpression )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==ADD||LA4_0==SUB) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:36:31: additiveOperator ^ multiplicativeExpression
					{
					pushFollow(FOLLOW_additiveOperator_in_additiveExpression171);
					additiveOperator14=additiveOperator();
					state._fsp--;

					root_0 = (CommonTree)adaptor.becomeRoot(additiveOperator14.getTree(), root_0);
					pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression175);
					multiplicativeExpression15=multiplicativeExpression();
					state._fsp--;

					adaptor.addChild(root_0, multiplicativeExpression15.getTree());

					}
					break;

				default :
					break loop4;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "additiveExpression"


	public static class multiplicativeExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "multiplicativeExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:38:1: multiplicativeExpression : primaryExpression ( multiplicativeOperator ^ primaryExpression )* ;
	public final AlarmParser.multiplicativeExpression_return multiplicativeExpression() throws Exception  {
		AlarmParser.multiplicativeExpression_return retval = new AlarmParser.multiplicativeExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope primaryExpression16 =null;
		ParserRuleReturnScope multiplicativeOperator17 =null;
		ParserRuleReturnScope primaryExpression18 =null;


		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:39:2: ( primaryExpression ( multiplicativeOperator ^ primaryExpression )* )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:39:4: primaryExpression ( multiplicativeOperator ^ primaryExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_primaryExpression_in_multiplicativeExpression188);
			primaryExpression16=primaryExpression();
			state._fsp--;

			adaptor.addChild(root_0, primaryExpression16.getTree());

			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:39:22: ( multiplicativeOperator ^ primaryExpression )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= BAND && LA5_0 <= BXOR)||LA5_0==DIV||LA5_0==IDV||LA5_0==LMOV||(LA5_0 >= MOD && LA5_0 <= MUL)||LA5_0==RMOV) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:39:24: multiplicativeOperator ^ primaryExpression
					{
					pushFollow(FOLLOW_multiplicativeOperator_in_multiplicativeExpression192);
					multiplicativeOperator17=multiplicativeOperator();
					state._fsp--;

					root_0 = (CommonTree)adaptor.becomeRoot(multiplicativeOperator17.getTree(), root_0);
					pushFollow(FOLLOW_primaryExpression_in_multiplicativeExpression196);
					primaryExpression18=primaryExpression();
					state._fsp--;

					adaptor.addChild(root_0, primaryExpression18.getTree());

					}
					break;

				default :
					break loop5;
				}
			}

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "multiplicativeExpression"


	public static class primaryExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "primaryExpression"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:41:1: primaryExpression : ( ID | const_Value | SUB ^ primaryExpression | KW_NOT ^ primaryExpression | LPAREN ! logicalOrExpression RPAREN !);
	public final AlarmParser.primaryExpression_return primaryExpression() throws Exception  {
		AlarmParser.primaryExpression_return retval = new AlarmParser.primaryExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token ID19=null;
		Token SUB21=null;
		Token KW_NOT23=null;
		Token LPAREN25=null;
		Token RPAREN27=null;
		ParserRuleReturnScope const_Value20 =null;
		ParserRuleReturnScope primaryExpression22 =null;
		ParserRuleReturnScope primaryExpression24 =null;
		ParserRuleReturnScope logicalOrExpression26 =null;

		CommonTree ID19_tree=null;
		CommonTree SUB21_tree=null;
		CommonTree KW_NOT23_tree=null;
		CommonTree LPAREN25_tree=null;
		CommonTree RPAREN27_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:42:2: ( ID | const_Value | SUB ^ primaryExpression | KW_NOT ^ primaryExpression | LPAREN ! logicalOrExpression RPAREN !)
			int alt6=5;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt6=1;
				}
				break;
			case FLOAT:
			case INT:
				{
				alt6=2;
				}
				break;
			case SUB:
				{
				alt6=3;
				}
				break;
			case KW_NOT:
				{
				alt6=4;
				}
				break;
			case LPAREN:
				{
				alt6=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:42:4: ID
					{
					root_0 = (CommonTree)adaptor.nil();


					ID19=(Token)match(input,ID,FOLLOW_ID_in_primaryExpression209); 
					ID19_tree = (CommonTree)adaptor.create(ID19);
					adaptor.addChild(root_0, ID19_tree);

					}
					break;
				case 2 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:43:4: const_Value
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_const_Value_in_primaryExpression214);
					const_Value20=const_Value();
					state._fsp--;

					adaptor.addChild(root_0, const_Value20.getTree());

					}
					break;
				case 3 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:44:4: SUB ^ primaryExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					SUB21=(Token)match(input,SUB,FOLLOW_SUB_in_primaryExpression220); 
					SUB21_tree = (CommonTree)adaptor.create(SUB21);
					root_0 = (CommonTree)adaptor.becomeRoot(SUB21_tree, root_0);

					pushFollow(FOLLOW_primaryExpression_in_primaryExpression224);
					primaryExpression22=primaryExpression();
					state._fsp--;

					adaptor.addChild(root_0, primaryExpression22.getTree());

					}
					break;
				case 4 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:45:4: KW_NOT ^ primaryExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					KW_NOT23=(Token)match(input,KW_NOT,FOLLOW_KW_NOT_in_primaryExpression229); 
					KW_NOT23_tree = (CommonTree)adaptor.create(KW_NOT23);
					root_0 = (CommonTree)adaptor.becomeRoot(KW_NOT23_tree, root_0);

					pushFollow(FOLLOW_primaryExpression_in_primaryExpression233);
					primaryExpression24=primaryExpression();
					state._fsp--;

					adaptor.addChild(root_0, primaryExpression24.getTree());

					}
					break;
				case 5 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:46:4: LPAREN ! logicalOrExpression RPAREN !
					{
					root_0 = (CommonTree)adaptor.nil();


					LPAREN25=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression238); 
					pushFollow(FOLLOW_logicalOrExpression_in_primaryExpression242);
					logicalOrExpression26=logicalOrExpression();
					state._fsp--;

					adaptor.addChild(root_0, logicalOrExpression26.getTree());

					RPAREN27=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression244); 
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "primaryExpression"


	public static class const_Value_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "const_Value"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:48:1: const_Value : ( INT | FLOAT );
	public final AlarmParser.const_Value_return const_Value() throws Exception  {
		AlarmParser.const_Value_return retval = new AlarmParser.const_Value_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token set28=null;

		CommonTree set28_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:49:2: ( INT | FLOAT )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:
			{
			root_0 = (CommonTree)adaptor.nil();


			set28=input.LT(1);
			if ( input.LA(1)==FLOAT||input.LA(1)==INT ) {
				input.consume();
				adaptor.addChild(root_0, (CommonTree)adaptor.create(set28));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "const_Value"


	public static class relationalOperator_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relationalOperator"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:52:1: relationalOperator : ( LT | GT | EQ | LE | GE | NE );
	public final AlarmParser.relationalOperator_return relationalOperator() throws Exception  {
		AlarmParser.relationalOperator_return retval = new AlarmParser.relationalOperator_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token set29=null;

		CommonTree set29_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:53:2: ( LT | GT | EQ | LE | GE | NE )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:
			{
			root_0 = (CommonTree)adaptor.nil();


			set29=input.LT(1);
			if ( input.LA(1)==EQ||(input.LA(1) >= GE && input.LA(1) <= GT)||input.LA(1)==LE||input.LA(1)==LT||input.LA(1)==NE ) {
				input.consume();
				adaptor.addChild(root_0, (CommonTree)adaptor.create(set29));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relationalOperator"


	public static class additiveOperator_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "additiveOperator"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:55:1: additiveOperator : ( ADD | SUB );
	public final AlarmParser.additiveOperator_return additiveOperator() throws Exception  {
		AlarmParser.additiveOperator_return retval = new AlarmParser.additiveOperator_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token set30=null;

		CommonTree set30_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:56:2: ( ADD | SUB )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:
			{
			root_0 = (CommonTree)adaptor.nil();


			set30=input.LT(1);
			if ( input.LA(1)==ADD||input.LA(1)==SUB ) {
				input.consume();
				adaptor.addChild(root_0, (CommonTree)adaptor.create(set30));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "additiveOperator"


	public static class multiplicativeOperator_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "multiplicativeOperator"
	// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:58:1: multiplicativeOperator : ( MUL | DIV | MOD | IDV | LMOV | RMOV | BAND | BOR | BNOT | BXOR );
	public final AlarmParser.multiplicativeOperator_return multiplicativeOperator() throws Exception  {
		AlarmParser.multiplicativeOperator_return retval = new AlarmParser.multiplicativeOperator_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token set31=null;

		CommonTree set31_tree=null;

		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:59:2: ( MUL | DIV | MOD | IDV | LMOV | RMOV | BAND | BOR | BNOT | BXOR )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\���ʽ�﷨\\Alarm.g:
			{
			root_0 = (CommonTree)adaptor.nil();


			set31=input.LT(1);
			if ( (input.LA(1) >= BAND && input.LA(1) <= BXOR)||input.LA(1)==DIV||input.LA(1)==IDV||input.LA(1)==LMOV||(input.LA(1) >= MOD && input.LA(1) <= MUL)||input.LA(1)==RMOV ) {
				input.consume();
				adaptor.addChild(root_0, (CommonTree)adaptor.create(set31));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "multiplicativeOperator"

	// Delegated rules



	public static final BitSet FOLLOW_expression_in_expression_only65 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_expression_only68 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_logicalOrExpression_in_expression79 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_logicalAndExpression_in_logicalOrExpression103 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_KW_OR_in_logicalOrExpression107 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_logicalAndExpression_in_logicalOrExpression111 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_relationalExpression_in_logicalAndExpression124 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_KW_AND_in_logicalAndExpression128 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_relationalExpression_in_logicalAndExpression132 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_additiveExpression_in_relationalExpression146 = new BitSet(new long[]{0x0000000024818802L});
	public static final BitSet FOLLOW_relationalOperator_in_relationalExpression150 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_additiveExpression_in_relationalExpression154 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression167 = new BitSet(new long[]{0x0000000100000012L});
	public static final BitSet FOLLOW_additiveOperator_in_additiveExpression171 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression175 = new BitSet(new long[]{0x0000000100000012L});
	public static final BitSet FOLLOW_primaryExpression_in_multiplicativeExpression188 = new BitSet(new long[]{0x00000000590405E2L});
	public static final BitSet FOLLOW_multiplicativeOperator_in_multiplicativeExpression192 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_primaryExpression_in_multiplicativeExpression196 = new BitSet(new long[]{0x00000000590405E2L});
	public static final BitSet FOLLOW_ID_in_primaryExpression209 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_const_Value_in_primaryExpression214 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SUB_in_primaryExpression220 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_primaryExpression_in_primaryExpression224 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_KW_NOT_in_primaryExpression229 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_primaryExpression_in_primaryExpression233 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_primaryExpression238 = new BitSet(new long[]{0x00000001022A4000L});
	public static final BitSet FOLLOW_logicalOrExpression_in_primaryExpression242 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_RPAREN_in_primaryExpression244 = new BitSet(new long[]{0x0000000000000002L});
}

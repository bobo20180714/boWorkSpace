package compiler;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class AlarmLexer extends Lexer {
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

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public AlarmLexer() {} 
	public AlarmLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public AlarmLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g"; }

	// $ANTLR start "KW_AND"
	public final void mKW_AND() throws RecognitionException {
		try {
			int _type = KW_AND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:67:8: ( '&&' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:67:10: '&&'
			{
			match("&&"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "KW_AND"

	// $ANTLR start "KW_OR"
	public final void mKW_OR() throws RecognitionException {
		try {
			int _type = KW_OR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:68:7: ( '||' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:68:9: '||'
			{
			match("||"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "KW_OR"

	// $ANTLR start "KW_NOT"
	public final void mKW_NOT() throws RecognitionException {
		try {
			int _type = KW_NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:69:8: ( '!' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:69:10: '!'
			{
			match('!'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "KW_NOT"

	// $ANTLR start "LPAREN"
	public final void mLPAREN() throws RecognitionException {
		try {
			int _type = LPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:72:8: ( '(' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:72:10: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LPAREN"

	// $ANTLR start "RPAREN"
	public final void mRPAREN() throws RecognitionException {
		try {
			int _type = RPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:73:8: ( ')' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:73:10: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RPAREN"

	// $ANTLR start "ADD"
	public final void mADD() throws RecognitionException {
		try {
			int _type = ADD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:74:5: ( '+' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:74:7: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ADD"

	// $ANTLR start "SUB"
	public final void mSUB() throws RecognitionException {
		try {
			int _type = SUB;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:75:5: ( '-' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:75:7: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUB"

	// $ANTLR start "MUL"
	public final void mMUL() throws RecognitionException {
		try {
			int _type = MUL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:76:5: ( '*' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:76:7: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MUL"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException {
		try {
			int _type = DIV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:77:5: ( '/' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:77:7: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIV"

	// $ANTLR start "MOD"
	public final void mMOD() throws RecognitionException {
		try {
			int _type = MOD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:78:5: ( '%' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:78:7: '%'
			{
			match('%'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MOD"

	// $ANTLR start "IDV"
	public final void mIDV() throws RecognitionException {
		try {
			int _type = IDV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:79:5: ( '\\\\' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:79:7: '\\\\'
			{
			match('\\'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IDV"

	// $ANTLR start "LMOV"
	public final void mLMOV() throws RecognitionException {
		try {
			int _type = LMOV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:80:6: ( '<<' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:80:8: '<<'
			{
			match("<<"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LMOV"

	// $ANTLR start "RMOV"
	public final void mRMOV() throws RecognitionException {
		try {
			int _type = RMOV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:81:6: ( '>>' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:81:8: '>>'
			{
			match(">>"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RMOV"

	// $ANTLR start "BAND"
	public final void mBAND() throws RecognitionException {
		try {
			int _type = BAND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:82:7: ( '&' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:82:9: '&'
			{
			match('&'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BAND"

	// $ANTLR start "BOR"
	public final void mBOR() throws RecognitionException {
		try {
			int _type = BOR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:83:5: ( '|' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:83:7: '|'
			{
			match('|'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOR"

	// $ANTLR start "BNOT"
	public final void mBNOT() throws RecognitionException {
		try {
			int _type = BNOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:84:9: ( '~' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:84:11: '~'
			{
			match('~'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BNOT"

	// $ANTLR start "BXOR"
	public final void mBXOR() throws RecognitionException {
		try {
			int _type = BXOR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:85:6: ( '^' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:85:8: '^'
			{
			match('^'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BXOR"

	// $ANTLR start "EQ"
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:86:4: ( '=' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:86:6: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQ"

	// $ANTLR start "NE"
	public final void mNE() throws RecognitionException {
		try {
			int _type = NE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:87:4: ( '<>' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:87:6: '<>'
			{
			match("<>"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NE"

	// $ANTLR start "LT"
	public final void mLT() throws RecognitionException {
		try {
			int _type = LT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:88:4: ( '<' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:88:6: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LT"

	// $ANTLR start "LE"
	public final void mLE() throws RecognitionException {
		try {
			int _type = LE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:89:4: ( '<=' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:89:6: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LE"

	// $ANTLR start "GT"
	public final void mGT() throws RecognitionException {
		try {
			int _type = GT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:90:4: ( '>' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:90:6: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GT"

	// $ANTLR start "GE"
	public final void mGE() throws RecognitionException {
		try {
			int _type = GE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:91:4: ( '>=' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:91:6: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "GE"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:95:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\\u4E00' .. '\\u9FA5' | '\\uF900' .. '\\uFA2D' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\u4E00' .. '\\u9FA5' | '\\uF900' .. '\\uFA2D' )* )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:95:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '\\u4E00' .. '\\u9FA5' | '\\uF900' .. '\\uFA2D' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\u4E00' .. '\\u9FA5' | '\\uF900' .. '\\uFA2D' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFA2D') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:95:75: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '\\u4E00' .. '\\u9FA5' | '\\uF900' .. '\\uFA2D' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||(LA1_0 >= '\u4E00' && LA1_0 <= '\u9FA5')||(LA1_0 >= '\uF900' && LA1_0 <= '\uFA2D')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFA2D') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop1;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:97:5: ( ( '0' .. '9' )+ )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:97:7: ( '0' .. '9' )+
			{
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:97:7: ( '0' .. '9' )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "ERROR_CHAR"
	public final void mERROR_CHAR() throws RecognitionException {
		try {
			int _type = ERROR_CHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:101:5: ( '`' | '@' | '#' | '$' | '{' | '}' | '[' | ']' | ':' | ';' | '\"' | '\\'' | ',' | '?' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
			{
			if ( (input.LA(1) >= '\"' && input.LA(1) <= '$')||input.LA(1)=='\''||input.LA(1)==','||(input.LA(1) >= ':' && input.LA(1) <= ';')||(input.LA(1) >= '?' && input.LA(1) <= '@')||input.LA(1)=='['||input.LA(1)==']'||input.LA(1)=='`'||input.LA(1)=='{'||input.LA(1)=='}' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ERROR_CHAR"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
			int alt9=3;
			alt9 = dfa9.predict(input);
			switch (alt9) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
					{
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:9: ( '0' .. '9' )+
					int cnt3=0;
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt3 >= 1 ) break loop3;
							EarlyExitException eee = new EarlyExitException(3, input);
							throw eee;
						}
						cnt3++;
					}

					match('.'); 
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:25: ( '0' .. '9' )*
					loop4:
					while (true) {
						int alt4=2;
						int LA4_0 = input.LA(1);
						if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop4;
						}
					}

					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:37: ( EXPONENT )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0=='E'||LA5_0=='e') ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:118:37: EXPONENT
							{
							mEXPONENT(); 

							}
							break;

					}

					}
					break;
				case 2 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:119:9: '.' ( '0' .. '9' )+ ( EXPONENT )?
					{
					match('.'); 
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:119:13: ( '0' .. '9' )+
					int cnt6=0;
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt6 >= 1 ) break loop6;
							EarlyExitException eee = new EarlyExitException(6, input);
							throw eee;
						}
						cnt6++;
					}

					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:119:25: ( EXPONENT )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0=='E'||LA7_0=='e') ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:119:25: EXPONENT
							{
							mEXPONENT(); 

							}
							break;

					}

					}
					break;
				case 3 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:120:9: ( '0' .. '9' )+ EXPONENT
					{
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:120:9: ( '0' .. '9' )+
					int cnt8=0;
					loop8:
					while (true) {
						int alt8=2;
						int LA8_0 = input.LA(1);
						if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
							alt8=1;
						}

						switch (alt8) {
						case 1 :
							// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt8 >= 1 ) break loop8;
							EarlyExitException eee = new EarlyExitException(8, input);
							throw eee;
						}
						cnt8++;
					}

					mEXPONENT(); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:124:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:124:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
			{
			match("//"); 

			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:124:14: (~ ( '\\n' | '\\r' ) )*
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( ((LA10_0 >= '\u0000' && LA10_0 <= '\t')||(LA10_0 >= '\u000B' && LA10_0 <= '\f')||(LA10_0 >= '\u000E' && LA10_0 <= '\uFFFF')) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop10;
				}
			}

			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:124:28: ( '\\r' )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0=='\r') ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:124:28: '\\r'
					{
					match('\r'); 
					}
					break;

			}

			match('\n'); 
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:127:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:127:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "EXPONENT"
	public final void mEXPONENT() throws RecognitionException {
		try {
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:136:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:136:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
			{
			if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:136:22: ( '+' | '-' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0=='+'||LA12_0=='-') ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:136:33: ( '0' .. '9' )+
			int cnt13=0;
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( ((LA13_0 >= '0' && LA13_0 <= '9')) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt13 >= 1 ) break loop13;
					EarlyExitException eee = new EarlyExitException(13, input);
					throw eee;
				}
				cnt13++;
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EXPONENT"

	@Override
	public void mTokens() throws RecognitionException {
		// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:8: ( KW_AND | KW_OR | KW_NOT | LPAREN | RPAREN | ADD | SUB | MUL | DIV | MOD | IDV | LMOV | RMOV | BAND | BOR | BNOT | BXOR | EQ | NE | LT | LE | GT | GE | ID | INT | ERROR_CHAR | FLOAT | COMMENT | WS )
		int alt14=29;
		alt14 = dfa14.predict(input);
		switch (alt14) {
			case 1 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:10: KW_AND
				{
				mKW_AND(); 

				}
				break;
			case 2 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:17: KW_OR
				{
				mKW_OR(); 

				}
				break;
			case 3 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:23: KW_NOT
				{
				mKW_NOT(); 

				}
				break;
			case 4 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:30: LPAREN
				{
				mLPAREN(); 

				}
				break;
			case 5 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:37: RPAREN
				{
				mRPAREN(); 

				}
				break;
			case 6 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:44: ADD
				{
				mADD(); 

				}
				break;
			case 7 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:48: SUB
				{
				mSUB(); 

				}
				break;
			case 8 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:52: MUL
				{
				mMUL(); 

				}
				break;
			case 9 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:56: DIV
				{
				mDIV(); 

				}
				break;
			case 10 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:60: MOD
				{
				mMOD(); 

				}
				break;
			case 11 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:64: IDV
				{
				mIDV(); 

				}
				break;
			case 12 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:68: LMOV
				{
				mLMOV(); 

				}
				break;
			case 13 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:73: RMOV
				{
				mRMOV(); 

				}
				break;
			case 14 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:78: BAND
				{
				mBAND(); 

				}
				break;
			case 15 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:83: BOR
				{
				mBOR(); 

				}
				break;
			case 16 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:87: BNOT
				{
				mBNOT(); 

				}
				break;
			case 17 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:92: BXOR
				{
				mBXOR(); 

				}
				break;
			case 18 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:97: EQ
				{
				mEQ(); 

				}
				break;
			case 19 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:100: NE
				{
				mNE(); 

				}
				break;
			case 20 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:103: LT
				{
				mLT(); 

				}
				break;
			case 21 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:106: LE
				{
				mLE(); 

				}
				break;
			case 22 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:109: GT
				{
				mGT(); 

				}
				break;
			case 23 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:112: GE
				{
				mGE(); 

				}
				break;
			case 24 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:115: ID
				{
				mID(); 

				}
				break;
			case 25 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:118: INT
				{
				mINT(); 

				}
				break;
			case 26 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:122: ERROR_CHAR
				{
				mERROR_CHAR(); 

				}
				break;
			case 27 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:133: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 28 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:139: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 29 :
				// D:\\Java\\eclipse\\workspace\\3\\doc\\表达式语法\\Alarm.g:1:147: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA9 dfa9 = new DFA9(this);
	protected DFA14 dfa14 = new DFA14(this);
	static final String DFA9_eotS =
		"\5\uffff";
	static final String DFA9_eofS =
		"\5\uffff";
	static final String DFA9_minS =
		"\2\56\3\uffff";
	static final String DFA9_maxS =
		"\1\71\1\145\3\uffff";
	static final String DFA9_acceptS =
		"\2\uffff\1\2\1\1\1\3";
	static final String DFA9_specialS =
		"\5\uffff}>";
	static final String[] DFA9_transitionS = {
			"\1\2\1\uffff\12\1",
			"\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
			"",
			"",
			""
	};

	static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
	static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
	static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
	static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
	static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
	static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
	static final short[][] DFA9_transition;

	static {
		int numStates = DFA9_transitionS.length;
		DFA9_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
		}
	}

	protected class DFA9 extends DFA {

		public DFA9(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 9;
			this.eot = DFA9_eot;
			this.eof = DFA9_eof;
			this.min = DFA9_min;
			this.max = DFA9_max;
			this.accept = DFA9_accept;
			this.special = DFA9_special;
			this.transition = DFA9_transition;
		}
		@Override
		public String getDescription() {
			return "117:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
		}
	}

	static final String DFA14_eotS =
		"\1\uffff\1\27\1\31\6\uffff\1\33\2\uffff\1\37\1\42\4\uffff\1\43\21\uffff";
	static final String DFA14_eofS =
		"\44\uffff";
	static final String DFA14_minS =
		"\1\11\1\46\1\174\6\uffff\1\57\2\uffff\1\74\1\75\4\uffff\1\56\21\uffff";
	static final String DFA14_maxS =
		"\1\ufa2d\1\46\1\174\6\uffff\1\57\2\uffff\2\76\4\uffff\1\145\21\uffff";
	static final String DFA14_acceptS =
		"\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\13\2\uffff\1\20\1\21"+
		"\1\22\1\30\1\uffff\1\32\1\33\1\35\1\1\1\16\1\2\1\17\1\34\1\11\1\14\1\23"+
		"\1\25\1\24\1\15\1\27\1\26\1\31";
	static final String DFA14_specialS =
		"\44\uffff}>";
	static final String[] DFA14_transitionS = {
			"\2\25\2\uffff\1\25\22\uffff\1\25\1\3\3\23\1\12\1\1\1\23\1\4\1\5\1\10"+
			"\1\6\1\23\1\7\1\24\1\11\12\22\2\23\1\14\1\20\1\15\2\23\32\21\1\23\1\13"+
			"\1\23\1\17\1\21\1\23\32\21\1\23\1\2\1\23\1\16\u4d81\uffff\u51a6\21\u595a"+
			"\uffff\u012e\21",
			"\1\26",
			"\1\30",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\32",
			"",
			"",
			"\1\34\1\36\1\35",
			"\1\41\1\40",
			"",
			"",
			"",
			"",
			"\1\24\1\uffff\12\22\13\uffff\1\24\37\uffff\1\24",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	};

	static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
	static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
	static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
	static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
	static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
	static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
	static final short[][] DFA14_transition;

	static {
		int numStates = DFA14_transitionS.length;
		DFA14_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
		}
	}

	protected class DFA14 extends DFA {

		public DFA14(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 14;
			this.eot = DFA14_eot;
			this.eof = DFA14_eof;
			this.min = DFA14_min;
			this.max = DFA14_max;
			this.accept = DFA14_accept;
			this.special = DFA14_special;
			this.transition = DFA14_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( KW_AND | KW_OR | KW_NOT | LPAREN | RPAREN | ADD | SUB | MUL | DIV | MOD | IDV | LMOV | RMOV | BAND | BOR | BNOT | BXOR | EQ | NE | LT | LE | GT | GE | ID | INT | ERROR_CHAR | FLOAT | COMMENT | WS );";
		}
	}

}

// Generated from E:/personal-workspace/JavaWorkspace/frog/src/main/java/com/bqp/frog/parser\FrogSql.g4 by ANTLR 4.7
package com.bqp.frog.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FrogSqlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INSERT=1, UPDATE=2, DELETE=3, SELECT=4, GLOBAL_TABLE=5, DOT=6, COLON=7, 
		LOGICAL_AND=8, LOGICAL_OR=9, LOGICAL_LT=10, LOGICAL_LE=11, LOGICAL_GT=12, 
		LOGICAL_GE=13, LOGICAL_EQ=14, LOGICAL_NE=15, LOGICAL_NOT=16, BLANK=17, 
		NUMBER=18, FIELD=19, PLAINTEXT=20, PARAMETER=21, ITERABLE_PARAMETER=22, 
		WS=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"INSERT", "UPDATE", "DELETE", "SELECT", "GLOBAL_TABLE", "DOT", "COLON", 
		"LOGICAL_AND", "LOGICAL_OR", "LOGICAL_LT", "LOGICAL_LE", "LOGICAL_GT", 
		"LOGICAL_GE", "LOGICAL_EQ", "LOGICAL_NE", "LOGICAL_NOT", "BLANK", "NUMBER", 
		"FIELD", "PLAINTEXT", "PARAMETER", "ITERABLE_PARAMETER", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'insert'", "'update'", "'delete'", "'select'", "'#table'", "'.'", 
		"':'", "'and'", "'or'", "'<'", "'<='", "'>'", "'>='", "'='", null, "'not'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "INSERT", "UPDATE", "DELETE", "SELECT", "GLOBAL_TABLE", "DOT", "COLON", 
		"LOGICAL_AND", "LOGICAL_OR", "LOGICAL_LT", "LOGICAL_LE", "LOGICAL_GT", 
		"LOGICAL_GE", "LOGICAL_EQ", "LOGICAL_NE", "LOGICAL_NOT", "BLANK", "NUMBER", 
		"FIELD", "PLAINTEXT", "PARAMETER", "ITERABLE_PARAMETER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public FrogSqlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FrogSql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00a7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r"+
		"\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\5\20p\n\20\3\21\3\21\3\21"+
		"\3\21\3\22\6\22w\n\22\r\22\16\22x\3\23\3\23\7\23}\n\23\f\23\16\23\u0080"+
		"\13\23\3\24\3\24\7\24\u0084\n\24\f\24\16\24\u0087\13\24\3\25\6\25\u008a"+
		"\n\25\r\25\16\25\u008b\3\26\3\26\3\26\5\26\u0091\n\26\3\26\3\26\3\26\7"+
		"\26\u0096\n\26\f\26\16\26\u0099\13\26\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\30\6\30\u00a2\n\30\r\30\16\30\u00a3\3\30\3\30\2\2\31\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\3\2\b\3\2\63;\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|"+
		"\n\2),..\60\60\62;C\\^^aac|\4\2\13\f\17\17\2\u00ae\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\58\3\2"+
		"\2\2\7?\3\2\2\2\tF\3\2\2\2\13M\3\2\2\2\rT\3\2\2\2\17V\3\2\2\2\21X\3\2"+
		"\2\2\23\\\3\2\2\2\25_\3\2\2\2\27a\3\2\2\2\31d\3\2\2\2\33f\3\2\2\2\35i"+
		"\3\2\2\2\37o\3\2\2\2!q\3\2\2\2#v\3\2\2\2%z\3\2\2\2\'\u0081\3\2\2\2)\u0089"+
		"\3\2\2\2+\u008d\3\2\2\2-\u009a\3\2\2\2/\u00a1\3\2\2\2\61\62\7k\2\2\62"+
		"\63\7p\2\2\63\64\7u\2\2\64\65\7g\2\2\65\66\7t\2\2\66\67\7v\2\2\67\4\3"+
		"\2\2\289\7w\2\29:\7r\2\2:;\7f\2\2;<\7c\2\2<=\7v\2\2=>\7g\2\2>\6\3\2\2"+
		"\2?@\7f\2\2@A\7g\2\2AB\7n\2\2BC\7g\2\2CD\7v\2\2DE\7g\2\2E\b\3\2\2\2FG"+
		"\7u\2\2GH\7g\2\2HI\7n\2\2IJ\7g\2\2JK\7e\2\2KL\7v\2\2L\n\3\2\2\2MN\7%\2"+
		"\2NO\7v\2\2OP\7c\2\2PQ\7d\2\2QR\7n\2\2RS\7g\2\2S\f\3\2\2\2TU\7\60\2\2"+
		"U\16\3\2\2\2VW\7<\2\2W\20\3\2\2\2XY\7c\2\2YZ\7p\2\2Z[\7f\2\2[\22\3\2\2"+
		"\2\\]\7q\2\2]^\7t\2\2^\24\3\2\2\2_`\7>\2\2`\26\3\2\2\2ab\7>\2\2bc\7?\2"+
		"\2c\30\3\2\2\2de\7@\2\2e\32\3\2\2\2fg\7@\2\2gh\7?\2\2h\34\3\2\2\2ij\7"+
		"?\2\2j\36\3\2\2\2kl\7#\2\2lp\7?\2\2mn\7>\2\2np\7@\2\2ok\3\2\2\2om\3\2"+
		"\2\2p \3\2\2\2qr\7p\2\2rs\7q\2\2st\7v\2\2t\"\3\2\2\2uw\7\"\2\2vu\3\2\2"+
		"\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2y$\3\2\2\2z~\t\2\2\2{}\t\3\2\2|{\3\2\2"+
		"\2}\u0080\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177&\3\2\2\2\u0080~\3\2\2\2\u0081"+
		"\u0085\t\4\2\2\u0082\u0084\t\5\2\2\u0083\u0082\3\2\2\2\u0084\u0087\3\2"+
		"\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086(\3\2\2\2\u0087\u0085"+
		"\3\2\2\2\u0088\u008a\t\6\2\2\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c*\3\2\2\2\u008d\u0090\5\17\b\2"+
		"\u008e\u0091\5%\23\2\u008f\u0091\5\'\24\2\u0090\u008e\3\2\2\2\u0090\u008f"+
		"\3\2\2\2\u0091\u0097\3\2\2\2\u0092\u0093\5\r\7\2\u0093\u0094\5\'\24\2"+
		"\u0094\u0096\3\2\2\2\u0095\u0092\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095"+
		"\3\2\2\2\u0097\u0098\3\2\2\2\u0098,\3\2\2\2\u0099\u0097\3\2\2\2\u009a"+
		"\u009b\7k\2\2\u009b\u009c\7p\2\2\u009c\u009d\3\2\2\2\u009d\u009e\5#\22"+
		"\2\u009e\u009f\5+\26\2\u009f.\3\2\2\2\u00a0\u00a2\t\7\2\2\u00a1\u00a0"+
		"\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a6\b\30\2\2\u00a6\60\3\2\2\2\13\2ox~\u0085\u008b"+
		"\u0090\u0097\u00a3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
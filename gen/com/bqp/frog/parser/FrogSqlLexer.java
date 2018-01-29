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
		INSERT=1, UPDATE=2, DELETE=3, SELECT=4, DOT=5, COLON=6, NUMBER=7, FIELD=8, 
		BLANK=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"INSERT", "UPDATE", "DELETE", "SELECT", "DOT", "COLON", "NUMBER", "FIELD", 
		"BLANK"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'insert'", "'update'", "'delete'", "'select'", "'.'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "INSERT", "UPDATE", "DELETE", "SELECT", "DOT", "COLON", "NUMBER", 
		"FIELD", "BLANK"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13F\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\7\b8\n\b\f"+
		"\b\16\b;\13\b\3\t\6\t>\n\t\r\t\16\t?\3\n\6\nC\n\n\r\n\16\nD\2\2\13\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\3\2\6\3\2\63;\3\2\62;\b\2\60\60"+
		"\62;C\\^^aac|\5\2\13\f\17\17\"\"\2H\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\3\25\3\2\2\2\5\34\3\2\2\2\7#\3\2\2\2\t*\3\2\2\2\13\61\3\2\2\2"+
		"\r\63\3\2\2\2\17\65\3\2\2\2\21=\3\2\2\2\23B\3\2\2\2\25\26\7k\2\2\26\27"+
		"\7p\2\2\27\30\7u\2\2\30\31\7g\2\2\31\32\7t\2\2\32\33\7v\2\2\33\4\3\2\2"+
		"\2\34\35\7w\2\2\35\36\7r\2\2\36\37\7f\2\2\37 \7c\2\2 !\7v\2\2!\"\7g\2"+
		"\2\"\6\3\2\2\2#$\7f\2\2$%\7g\2\2%&\7n\2\2&\'\7g\2\2\'(\7v\2\2()\7g\2\2"+
		")\b\3\2\2\2*+\7u\2\2+,\7g\2\2,-\7n\2\2-.\7g\2\2./\7e\2\2/\60\7v\2\2\60"+
		"\n\3\2\2\2\61\62\7\60\2\2\62\f\3\2\2\2\63\64\7<\2\2\64\16\3\2\2\2\659"+
		"\t\2\2\2\668\t\3\2\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:\20"+
		"\3\2\2\2;9\3\2\2\2<>\t\4\2\2=<\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3\2\2\2@"+
		"\22\3\2\2\2AC\t\5\2\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\24\3\2"+
		"\2\2\6\29?D\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
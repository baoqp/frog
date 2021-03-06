// Generated from E:/personal-workspace/JavaWorkspace/frog/src/main/java/com/bqp/frog/parser\FrogSql.g4 by ANTLR 4.7
package com.bqp.frog.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FrogSqlParser extends Parser {
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
	public static final int
		RULE_sql = 0, RULE_dml = 1, RULE_statement = 2, RULE_plainText = 3, RULE_logicalOp = 4;
	public static final String[] ruleNames = {
		"sql", "dml", "statement", "plainText", "logicalOp"
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

	@Override
	public String getGrammarFileName() { return "FrogSql.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FrogSqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SqlContext extends ParserRuleContext {
		public DmlContext dml() {
			return getRuleContext(DmlContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SqlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitSql(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SqlContext sql() throws RecognitionException {
		SqlContext _localctx = new SqlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sql);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			dml();
			setState(14);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GLOBAL_TABLE) | (1L << LOGICAL_AND) | (1L << LOGICAL_OR) | (1L << LOGICAL_LT) | (1L << LOGICAL_LE) | (1L << LOGICAL_GT) | (1L << LOGICAL_GE) | (1L << LOGICAL_EQ) | (1L << LOGICAL_NE) | (1L << LOGICAL_NOT) | (1L << BLANK) | (1L << FIELD) | (1L << PLAINTEXT) | (1L << PARAMETER) | (1L << ITERABLE_PARAMETER))) != 0)) {
				{
				{
				setState(11);
				statement();
				}
				}
				setState(16);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DmlContext extends ParserRuleContext {
		public DmlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dml; }
	 
		public DmlContext() { }
		public void copyFrom(DmlContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SelectContext extends DmlContext {
		public TerminalNode SELECT() { return getToken(FrogSqlParser.SELECT, 0); }
		public SelectContext(DmlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InsertContext extends DmlContext {
		public TerminalNode INSERT() { return getToken(FrogSqlParser.INSERT, 0); }
		public InsertContext(DmlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitInsert(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UpdateContext extends DmlContext {
		public TerminalNode UPDATE() { return getToken(FrogSqlParser.UPDATE, 0); }
		public UpdateContext(DmlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitUpdate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteContext extends DmlContext {
		public TerminalNode DELETE() { return getToken(FrogSqlParser.DELETE, 0); }
		public DeleteContext(DmlContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitDelete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DmlContext dml() throws RecognitionException {
		DmlContext _localctx = new DmlContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dml);
		try {
			setState(21);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INSERT:
				_localctx = new InsertContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(17);
				match(INSERT);
				}
				break;
			case UPDATE:
				_localctx = new UpdateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(18);
				match(UPDATE);
				}
				break;
			case DELETE:
				_localctx = new DeleteContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(19);
				match(DELETE);
				}
				break;
			case SELECT:
				_localctx = new SelectContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(20);
				match(SELECT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IterableParameterContext extends StatementContext {
		public TerminalNode ITERABLE_PARAMETER() { return getToken(FrogSqlParser.ITERABLE_PARAMETER, 0); }
		public IterableParameterContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitIterableParameter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlankContext extends StatementContext {
		public TerminalNode BLANK() { return getToken(FrogSqlParser.BLANK, 0); }
		public BlankContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitBlank(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParameterContext extends StatementContext {
		public TerminalNode PARAMETER() { return getToken(FrogSqlParser.PARAMETER, 0); }
		public ParameterContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TextContext extends StatementContext {
		public PlainTextContext plainText() {
			return getRuleContext(PlainTextContext.class,0);
		}
		public TextContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitText(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalContext extends StatementContext {
		public LogicalOpContext logicalOp() {
			return getRuleContext(LogicalOpContext.class,0);
		}
		public LogicalContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitLogical(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GlobalTableContext extends StatementContext {
		public TerminalNode GLOBAL_TABLE() { return getToken(FrogSqlParser.GLOBAL_TABLE, 0); }
		public GlobalTableContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitGlobalTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(29);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case GLOBAL_TABLE:
				_localctx = new GlobalTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(23);
				match(GLOBAL_TABLE);
				}
				break;
			case BLANK:
				_localctx = new BlankContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(24);
				match(BLANK);
				}
				break;
			case PARAMETER:
				_localctx = new ParameterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(25);
				match(PARAMETER);
				}
				break;
			case ITERABLE_PARAMETER:
				_localctx = new IterableParameterContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(26);
				match(ITERABLE_PARAMETER);
				}
				break;
			case FIELD:
			case PLAINTEXT:
				_localctx = new TextContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(27);
				plainText();
				}
				break;
			case LOGICAL_AND:
			case LOGICAL_OR:
			case LOGICAL_LT:
			case LOGICAL_LE:
			case LOGICAL_GT:
			case LOGICAL_GE:
			case LOGICAL_EQ:
			case LOGICAL_NE:
			case LOGICAL_NOT:
				_localctx = new LogicalContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(28);
				logicalOp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PlainTextContext extends ParserRuleContext {
		public TerminalNode FIELD() { return getToken(FrogSqlParser.FIELD, 0); }
		public TerminalNode PLAINTEXT() { return getToken(FrogSqlParser.PLAINTEXT, 0); }
		public PlainTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_plainText; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitPlainText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PlainTextContext plainText() throws RecognitionException {
		PlainTextContext _localctx = new PlainTextContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_plainText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			_la = _input.LA(1);
			if ( !(_la==FIELD || _la==PLAINTEXT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOpContext extends ParserRuleContext {
		public TerminalNode LOGICAL_AND() { return getToken(FrogSqlParser.LOGICAL_AND, 0); }
		public TerminalNode LOGICAL_OR() { return getToken(FrogSqlParser.LOGICAL_OR, 0); }
		public TerminalNode LOGICAL_LT() { return getToken(FrogSqlParser.LOGICAL_LT, 0); }
		public TerminalNode LOGICAL_LE() { return getToken(FrogSqlParser.LOGICAL_LE, 0); }
		public TerminalNode LOGICAL_GT() { return getToken(FrogSqlParser.LOGICAL_GT, 0); }
		public TerminalNode LOGICAL_GE() { return getToken(FrogSqlParser.LOGICAL_GE, 0); }
		public TerminalNode LOGICAL_EQ() { return getToken(FrogSqlParser.LOGICAL_EQ, 0); }
		public TerminalNode LOGICAL_NE() { return getToken(FrogSqlParser.LOGICAL_NE, 0); }
		public TerminalNode LOGICAL_NOT() { return getToken(FrogSqlParser.LOGICAL_NOT, 0); }
		public LogicalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitLogicalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOpContext logicalOp() throws RecognitionException {
		LogicalOpContext _localctx = new LogicalOpContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_logicalOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LOGICAL_AND) | (1L << LOGICAL_OR) | (1L << LOGICAL_LT) | (1L << LOGICAL_LE) | (1L << LOGICAL_GT) | (1L << LOGICAL_GE) | (1L << LOGICAL_EQ) | (1L << LOGICAL_NE) | (1L << LOGICAL_NOT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31&\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\7\2\17\n\2\f\2\16\2\22\13\2\3\3\3\3"+
		"\3\3\3\3\5\3\30\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4 \n\4\3\5\3\5\3\6\3\6\3"+
		"\6\2\2\7\2\4\6\b\n\2\4\3\2\25\26\3\2\n\22\2)\2\f\3\2\2\2\4\27\3\2\2\2"+
		"\6\37\3\2\2\2\b!\3\2\2\2\n#\3\2\2\2\f\20\5\4\3\2\r\17\5\6\4\2\16\r\3\2"+
		"\2\2\17\22\3\2\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21\3\3\2\2\2\22\20\3\2"+
		"\2\2\23\30\7\3\2\2\24\30\7\4\2\2\25\30\7\5\2\2\26\30\7\6\2\2\27\23\3\2"+
		"\2\2\27\24\3\2\2\2\27\25\3\2\2\2\27\26\3\2\2\2\30\5\3\2\2\2\31 \7\7\2"+
		"\2\32 \7\23\2\2\33 \7\27\2\2\34 \7\30\2\2\35 \5\b\5\2\36 \5\n\6\2\37\31"+
		"\3\2\2\2\37\32\3\2\2\2\37\33\3\2\2\2\37\34\3\2\2\2\37\35\3\2\2\2\37\36"+
		"\3\2\2\2 \7\3\2\2\2!\"\t\2\2\2\"\t\3\2\2\2#$\t\3\2\2$\13\3\2\2\2\5\20"+
		"\27\37";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
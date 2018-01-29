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
		INSERT=1, UPDATE=2, DELETE=3, SELECT=4, DOT=5, COLON=6, NUMBER=7, FIELD=8, 
		BLANK=9;
	public static final int
		RULE_sql = 0, RULE_dml = 1, RULE_statement = 2, RULE_jdbcParameter = 3;
	public static final String[] ruleNames = {
		"sql", "dml", "statement", "jdbcParameter"
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterSql(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitSql(this);
		}
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
			setState(8);
			dml();
			setState(12);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COLON) | (1L << FIELD) | (1L << BLANK))) != 0)) {
				{
				{
				setState(9);
				statement();
				}
				}
				setState(14);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitSelect(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterInsert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitInsert(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterUpdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitUpdate(this);
		}
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterDelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitDelete(this);
		}
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
			setState(19);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INSERT:
				_localctx = new InsertContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(15);
				match(INSERT);
				}
				break;
			case UPDATE:
				_localctx = new UpdateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(16);
				match(UPDATE);
				}
				break;
			case DELETE:
				_localctx = new DeleteContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(17);
				match(DELETE);
				}
				break;
			case SELECT:
				_localctx = new SelectContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(18);
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
	public static class BlankContext extends StatementContext {
		public TerminalNode BLANK() { return getToken(FrogSqlParser.BLANK, 0); }
		public BlankContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterBlank(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitBlank(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitBlank(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParameterContext extends StatementContext {
		public JdbcParameterContext jdbcParameter() {
			return getRuleContext(JdbcParameterContext.class,0);
		}
		public ParameterContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TextContext extends StatementContext {
		public TerminalNode FIELD() { return getToken(FrogSqlParser.FIELD, 0); }
		public TextContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(24);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FIELD:
				_localctx = new TextContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(21);
				match(FIELD);
				}
				break;
			case BLANK:
				_localctx = new BlankContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(22);
				match(BLANK);
				}
				break;
			case COLON:
				_localctx = new ParameterContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(23);
				jdbcParameter();
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

	public static class JdbcParameterContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(FrogSqlParser.COLON, 0); }
		public TerminalNode NUMBER() { return getToken(FrogSqlParser.NUMBER, 0); }
		public List<TerminalNode> FIELD() { return getTokens(FrogSqlParser.FIELD); }
		public TerminalNode FIELD(int i) {
			return getToken(FrogSqlParser.FIELD, i);
		}
		public List<TerminalNode> DOT() { return getTokens(FrogSqlParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(FrogSqlParser.DOT, i);
		}
		public JdbcParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jdbcParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).enterJdbcParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FrogSqlListener ) ((FrogSqlListener)listener).exitJdbcParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FrogSqlVisitor ) return ((FrogSqlVisitor<? extends T>)visitor).visitJdbcParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JdbcParameterContext jdbcParameter() throws RecognitionException {
		JdbcParameterContext _localctx = new JdbcParameterContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_jdbcParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(COLON);
			setState(27);
			_la = _input.LA(1);
			if ( !(_la==NUMBER || _la==FIELD) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(28);
				match(DOT);
				setState(29);
				match(FIELD);
				}
				}
				setState(34);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13&\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\7\2\r\n\2\f\2\16\2\20\13\2\3\3\3\3\3\3\3\3"+
		"\5\3\26\n\3\3\4\3\4\3\4\5\4\33\n\4\3\5\3\5\3\5\3\5\7\5!\n\5\f\5\16\5$"+
		"\13\5\3\5\2\2\6\2\4\6\b\2\3\3\2\t\n\2(\2\n\3\2\2\2\4\25\3\2\2\2\6\32\3"+
		"\2\2\2\b\34\3\2\2\2\n\16\5\4\3\2\13\r\5\6\4\2\f\13\3\2\2\2\r\20\3\2\2"+
		"\2\16\f\3\2\2\2\16\17\3\2\2\2\17\3\3\2\2\2\20\16\3\2\2\2\21\26\7\3\2\2"+
		"\22\26\7\4\2\2\23\26\7\5\2\2\24\26\7\6\2\2\25\21\3\2\2\2\25\22\3\2\2\2"+
		"\25\23\3\2\2\2\25\24\3\2\2\2\26\5\3\2\2\2\27\33\7\n\2\2\30\33\7\13\2\2"+
		"\31\33\5\b\5\2\32\27\3\2\2\2\32\30\3\2\2\2\32\31\3\2\2\2\33\7\3\2\2\2"+
		"\34\35\7\b\2\2\35\"\t\2\2\2\36\37\7\7\2\2\37!\7\n\2\2 \36\3\2\2\2!$\3"+
		"\2\2\2\" \3\2\2\2\"#\3\2\2\2#\t\3\2\2\2$\"\3\2\2\2\6\16\25\32\"";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
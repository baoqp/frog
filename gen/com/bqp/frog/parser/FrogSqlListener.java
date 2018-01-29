// Generated from E:/personal-workspace/JavaWorkspace/frog/src/main/java/com/bqp/frog/parser\FrogSql.g4 by ANTLR 4.7
package com.bqp.frog.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FrogSqlParser}.
 */
public interface FrogSqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FrogSqlParser#sql}.
	 * @param ctx the parse tree
	 */
	void enterSql(FrogSqlParser.SqlContext ctx);
	/**
	 * Exit a parse tree produced by {@link FrogSqlParser#sql}.
	 * @param ctx the parse tree
	 */
	void exitSql(FrogSqlParser.SqlContext ctx);
	/**
	 * Enter a parse tree produced by the {@code insert}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void enterInsert(FrogSqlParser.InsertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code insert}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void exitInsert(FrogSqlParser.InsertContext ctx);
	/**
	 * Enter a parse tree produced by the {@code update}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void enterUpdate(FrogSqlParser.UpdateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code update}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void exitUpdate(FrogSqlParser.UpdateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code delete}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void enterDelete(FrogSqlParser.DeleteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code delete}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void exitDelete(FrogSqlParser.DeleteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code select}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void enterSelect(FrogSqlParser.SelectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code select}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 */
	void exitSelect(FrogSqlParser.SelectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code text}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterText(FrogSqlParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code text}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitText(FrogSqlParser.TextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blank}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlank(FrogSqlParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlank(FrogSqlParser.BlankContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parameter}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FrogSqlParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameter}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FrogSqlParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link FrogSqlParser#jdbcParameter}.
	 * @param ctx the parse tree
	 */
	void enterJdbcParameter(FrogSqlParser.JdbcParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FrogSqlParser#jdbcParameter}.
	 * @param ctx the parse tree
	 */
	void exitJdbcParameter(FrogSqlParser.JdbcParameterContext ctx);
}
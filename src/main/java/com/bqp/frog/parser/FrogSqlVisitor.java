// Generated from /home/bao/code/frog/src/main/java/com/bqp/frog/parser/FrogSql.g4 by ANTLR 4.7
package com.bqp.frog.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FrogSqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FrogSqlVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FrogSqlParser#sql}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSql(FrogSqlParser.SqlContext ctx);
	/**
	 * Visit a parse tree produced by {@link FrogSqlParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(FrogSqlParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insert}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert(FrogSqlParser.InsertContext ctx);
	/**
	 * Visit a parse tree produced by the {@code update}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate(FrogSqlParser.UpdateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code delete}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelete(FrogSqlParser.DeleteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code select}
	 * labeled alternative in {@link FrogSqlParser#dml}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(FrogSqlParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code text}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitText(FrogSqlParser.TextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link FrogSqlParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(FrogSqlParser.BlankContext ctx);
}
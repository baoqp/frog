package com.bqp.frog.parser;

import com.bqp.frog.jdbc.JdbcType;
import com.bqp.frog.operator.BindingParameter;
import com.bqp.frog.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 每执行一次sql，都要调用render
public class FrogSqlRender extends FrogSqlBaseVisitor<String> {

    public FrogSqlRender() {

    }

    StringBuilder sb = new StringBuilder();

    @Override
    public String visitSql(FrogSqlParser.SqlContext ctx) {

        return super.visitSql(ctx);
    }

    @Override
    public String visitSelect(FrogSqlParser.SelectContext ctx) {
        sb.append(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitInsert(FrogSqlParser.InsertContext ctx) {
        sb.append(ctx.getText());
        return super.visitInsert(ctx);
    }


    @Override
    public String visitUpdate(FrogSqlParser.UpdateContext ctx) {
        sb.append(ctx.getText());
        return super.visitUpdate(ctx);
    }

    @Override
    public String visitDelete(FrogSqlParser.DeleteContext ctx) {
        sb.append(ctx.getText());
        return super.visitDelete(ctx);
    }

    @Override
    public String visitParameter(FrogSqlParser.ParameterContext ctx) {

        sb.append("?");
        return visitChildren(ctx);
    }



    @Override
    public String visitIterableParameter(FrogSqlParser.IterableParameterContext ctx) {
        sb.append("in ( ").append("?").append(" )");
        return visitChildren(ctx);
    }

    @Override
    public String visitLogical(FrogSqlParser.LogicalContext ctx) {
        sb.append(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitGlobalTable(FrogSqlParser.GlobalTableContext ctx) {
        sb.append("#table");
        return visitChildren(ctx);
    }

    @Override
    public String visitBlank(FrogSqlParser.BlankContext ctx) {
        sb.append(" ");
        return " ";
    }

    @Override
    public String visitText(FrogSqlParser.TextContext ctx) {
        sb.append(ctx.getText());
        return ctx.getText();
    }

    public String getSql() {
        return sb.toString();
    }
}

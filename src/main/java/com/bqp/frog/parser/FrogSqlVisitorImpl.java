package com.bqp.frog.parser;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.JdbcType;
import com.bqp.frog.operator.BindingParameter;
import com.bqp.frog.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrogSqlVisitorImpl extends FrogSqlBaseVisitor<String> {

    MethodDescriptor methodDescriptor;

    Object[] params;

    List<Integer> parameterIndices = new ArrayList<>();

    public FrogSqlVisitorImpl() {

    }

    StringBuilder sb = null;

    @Override
    public String visitSql(FrogSqlParser.SqlContext ctx) {
        sb = new StringBuilder();
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
        String parameter = ctx.getText();
        Pattern p = Pattern.compile(":(\\w+)((\\.\\w+)*)"); // 先不用jdbcType
        Matcher m = p.matcher(parameter);
        if (!m.matches()) {
            throw new IllegalStateException("Can't compile string '" + str + "'");
        }
        String group1 = m.group(1);
        String group2 = m.group(2);

        String parameterName = group1;
        String propertyPath = Strings.isNotEmpty(group2) ? group2.substring(1) : "";
        JdbcType jdbcType = null;
        BindingParameter bindingParameter = BindingParameter.create(parameterName, propertyPath, jdbcType);

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

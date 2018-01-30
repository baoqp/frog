package com.bqp.frog.parser;

import com.bqp.frog.descriptor.MethodDescriptor;

import java.util.ArrayList;
import java.util.List;

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
        sb.append("?");
        return visitChildren(ctx);
    }

    @Override
    public String visitLogical(FrogSqlParser.LogicalContext ctx) {
        sb.append(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitGlobalTable(FrogSqlParser.GlobalTableContext ctx) {
        System.out.println("--global table--");
        return visitChildren(ctx);
    }

    @Override
    public String visitBlank(FrogSqlParser.BlankContext ctx) {
        System.out.println("--blank--");
        return " ";
    }

    @Override
    public String visitText(FrogSqlParser.TextContext ctx) {
        System.out.println("--text--");
        System.out.println(ctx.getText());
        return ctx.getText();
    }
}

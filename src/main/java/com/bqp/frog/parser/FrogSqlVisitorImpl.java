package com.bqp.frog.parser;

public class FrogSqlVisitorImpl extends FrogSqlBaseVisitor<String> {


    @Override
    public String visitInsert(FrogSqlParser.InsertContext ctx) {
        System.out.println("--insert--");
        return super.visitInsert(ctx);
    }


    @Override
    public String visitUpdate(FrogSqlParser.UpdateContext ctx) {
        System.out.println("--update--");
        return super.visitUpdate(ctx);
    }

    @Override
    public String visitParameter(FrogSqlParser.ParameterContext ctx) {
        System.out.println("--parameter--");
        System.out.println(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitIterableParameter(FrogSqlParser.IterableParameterContext ctx) {
        System.out.println("-- Iterable parameter--");
        System.out.println(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitLogical(FrogSqlParser.LogicalContext ctx) {
        System.out.println("-- logical --");
        System.out.println(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitDelete(FrogSqlParser.DeleteContext ctx) {
        System.out.println("--delete--");
        return super.visitDelete(ctx);
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

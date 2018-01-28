package com.bqp.frog.parser;

public class FrogSqlVisitorImpl extends FrogSqlBaseVisitor<String> {


    @Override
    public String visitSql(FrogSqlParser.SqlContext ctx) {
        System.out.println(ctx.getText());
        return super.visitSql(ctx);
    }

    @Override
    public String visitBlock(FrogSqlParser.BlockContext ctx) {
        System.out.println(ctx.getText());
        return super.visitBlock(ctx);
    }


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
    public String visitDelete(FrogSqlParser.DeleteContext ctx) {
        System.out.println("--delete--");
        return super.visitDelete(ctx);
    }


    /*@Override
    public String visitJdbcParameter(FrogSqlParser.JdbcParameterContext ctx) {
        System.out.println("--delete--");
        return ctx.getText();
    }*/

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

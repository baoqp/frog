package com.bqp.frog.parser;

public class FrogSqlVisitorImpl extends FrogSqlBaseVisitor<String> {


    @Override
    public String visitSql(FrogSqlParser.SqlContext ctx) {

        return ctx.getText();
    }

    @Override
    public String visitBlock(FrogSqlParser.BlockContext ctx) {

         return ctx.getText();
    }


    @Override
    public String visitInsert(FrogSqlParser.InsertContext ctx) {
        System.out.println("--insert--");
        return ctx.getText();
    }


    @Override
    public String visitUpdate(FrogSqlParser.UpdateContext ctx) {
        System.out.println("--update--");
        return ctx.getText();
    }


    @Override
    public String visitDelete(FrogSqlParser.DeleteContext ctx) {
        System.out.println("--delete--");
        return ctx.getText();
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
        return ctx.getText();
    }
}

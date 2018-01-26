package com.bqp.frog.parser;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends CalcBaseVisitor<Integer> {

    Map<String, Integer> memory = new HashMap<String, Integer>();

    @Override
    public Integer visitPrintExpr(CalcParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr()); // evaluate the expr child
        System.out.println(value); // print the result
        return 0; // return dummy value
    }

    @Override
    public Integer visitAssign(CalcParser.AssignContext ctx) {
        String id = ctx.ID().getText(); // id is left-hand side of '='
        int value = visit(ctx.expr()); // compute value of expression on right
        memory.put(id, value); // store it in our memory
        return value;
    }

    @Override
    public Integer visitBlank(CalcParser.BlankContext ctx) {
        return super.visitBlank(ctx);
    }

    @Override
    public Integer visitParens(CalcParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }

    @Override
    public Integer visitMulDiv(CalcParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0)); // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if (ctx.op.getType() == CalcParser.MUL) return left * right;
        return left / right; // must be DIV
    }

    @Override
    public Integer visitAddSub(CalcParser.AddSubContext ctx) {

        int left = visit(ctx.expr(0)); // get value of left subexpression
        int right = visit(ctx.expr(1)); // get value of right subexpression
        if (ctx.op.getType() == CalcParser.ADD) return left + right;
        return left - right; // must be SUB
    }

    @Override
    public Integer visitId(CalcParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    @Override
    public Integer visitInt(CalcParser.IntContext ctx) {

        return Integer.valueOf(ctx.INT().getText());
    }
}  
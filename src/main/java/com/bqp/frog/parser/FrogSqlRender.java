package com.bqp.frog.parser;

import com.bqp.frog.exception.EmptyObjectException;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.operator.InvocationContext;
import com.bqp.frog.util.Iterables;
import com.google.common.reflect.TypeToken;


import java.util.List;

// 每执行一次sql，都要调用render
public class FrogSqlRender extends FrogSqlBaseVisitor<String> {
    StringBuilder sqlBuffer;

    InvocationContext invocationContext;

    List<BindingParameterInvoker> bindingParameterInvokers;

    List<TypeHandler<?>> typeHandlers;

    int idx;

    public FrogSqlRender(InvocationContext invocationContext,
                         List<BindingParameterInvoker> bindingParameterInvokers,
                         List<TypeHandler<?>> typeHandlers) {
        this.invocationContext = invocationContext;
        this.bindingParameterInvokers = bindingParameterInvokers;
        this.typeHandlers = typeHandlers;
        idx = 0;
    }

    @Override
    public String visitSelect(FrogSqlParser.SelectContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitInsert(FrogSqlParser.InsertContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return super.visitInsert(ctx);
    }


    @Override
    public String visitUpdate(FrogSqlParser.UpdateContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return super.visitUpdate(ctx);
    }

    @Override
    public String visitDelete(FrogSqlParser.DeleteContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return super.visitDelete(ctx);
    }

    @Override
    public String visitParameter(FrogSqlParser.ParameterContext ctx) {
        BindingParameterInvoker bindingParameterInvoker = bindingParameterInvokers.get(idx);
        if (bindingParameterInvoker == null) {
            throw new NullPointerException("invoker must set");
        }
        invocationContext.writeToSqlBuffer("?");
        Object obj = invocationContext.getNullableBindingValue(bindingParameterInvoker);
        invocationContext.appendToArgs(obj, typeHandlers.get(idx));
        idx ++;
        return visitChildren(ctx);
    }

    @Override
    public String visitIterableParameter(FrogSqlParser.IterableParameterContext ctx) {
        BindingParameterInvoker bindingParameterInvoker = bindingParameterInvokers.get(idx);
        TypeHandler<?> typeHandler = typeHandlers.get(idx);
        if (bindingParameterInvoker == null) {
            throw new NullPointerException("invoker must set");
        }
        Object objs = invocationContext.getNullableBindingValue(bindingParameterInvoker);
        if (objs == null) {
            throw new NullPointerException("value of " +
                    bindingParameterInvoker.getBindingParameter().getFullName() + " can't be null");
        }

        Iterables iterables = new Iterables(objs);
        if (iterables.isEmpty()) {
            if (iterables.isCollection()) {
                throw new EmptyObjectException("value of " +
                        bindingParameterInvoker.getBindingParameter().getFullName() + " can't be empty");
            } else {
                throw new EmptyObjectException("value of " +
                        bindingParameterInvoker.getBindingParameter().getFullName() + " can't be empty");
            }
        }
        invocationContext.writeToSqlBuffer("in (");
        int t = 0;
        for (Object obj : iterables) {
            invocationContext.appendToArgs(obj, typeHandler);
            if (t == 0) {
                invocationContext.writeToSqlBuffer("?");
            } else {
                invocationContext.writeToSqlBuffer(",?");
            }
            t++;
        }
        invocationContext.writeToSqlBuffer(")");
        idx ++;
        return visitChildren(ctx);
    }

    @Override
    public String visitLogical(FrogSqlParser.LogicalContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return visitChildren(ctx);
    }

    // TODO
    @Override
    public String visitGlobalTable(FrogSqlParser.GlobalTableContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public String visitBlank(FrogSqlParser.BlankContext ctx) {
        invocationContext.writeToSqlBuffer(" ");
        return " ";
    }

    @Override
    public String visitText(FrogSqlParser.TextContext ctx) {
        invocationContext.writeToSqlBuffer(ctx.getText());
        return ctx.getText();
    }


}

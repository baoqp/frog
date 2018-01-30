package com.bqp.frog.jdbc;


import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class BoundSql {

    private String sql;
    private final List<Object> args;
    private final List<TypeHandler<?>> typeHandlers;

    public BoundSql(String sql) {
        this.sql = sql;
        this.args = new ArrayList<>();
        this.typeHandlers = new ArrayList<>();
    }

    public BoundSql(String sql, List<Object> args, List<TypeHandler<?>> typeHandlers) {
        this.sql = sql;
        this.args = args;
        this.typeHandlers = typeHandlers;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addArg(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("arg can't be null, if arg is null please use method addNullArg");
        }
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(obj.getClass());
        args.add(obj);
        typeHandlers.add(typeHandler);
    }

    public void addArg(int index, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("arg can't be null, if arg is null please use method addNullArg");
        }
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(obj.getClass());
        args.add(index, obj);
        typeHandlers.add(index, typeHandler);
    }

    public void addNullArg(Class<?> type) {
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(type);
        args.add(null);
        typeHandlers.add(typeHandler);
    }

    public void addNullArg(int index, Class<?> type) {
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(type);
        args.add(index, null);
        typeHandlers.add(index, typeHandler);
    }

    public List<Object> getArgs() {
        return args;
    }

    public List<TypeHandler<?>> getTypeHandlers() {
        return typeHandlers;
    }

    public BoundSql copy() {
        List<Object> args = new ArrayList<Object>();
        for (Object arg : getArgs()) {
            args.add(arg);
        }
        List<TypeHandler<?>> typeHandlers = new ArrayList<TypeHandler<?>>();
        for (TypeHandler<?> typeHandler : getTypeHandlers()) {
            typeHandlers.add(typeHandler);
        }
        return new BoundSql(getSql(), args, typeHandlers);
    }

}

package com.bqp.frog.operator;

import com.bqp.frog.jdbc.BoundSql;
import com.bqp.frog.jdbc.type.TypeHandler;

import java.util.List;

/**
 * @author ash
 */
public interface InvocationContext {

    void addParameter(String parameterName, Object parameterValue);

    Object getBindingValue(BindingParameterInvoker invoker);

    Object getNullableBindingValue(BindingParameterInvoker invoker);

    void setBindingValue(BindingParameterInvoker invoker, Object value);

    String getGlobalTable();

    void setGlobalTable(String globalTable);

    void trim(String str);

    void writeToSqlBuffer(String str);

    void appendToArgs(Object obj, TypeHandler<?> typeHandler);

    BoundSql getBoundSql();

    List<Object> getParameterValues();
}

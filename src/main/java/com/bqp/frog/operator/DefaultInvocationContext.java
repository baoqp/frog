package com.bqp.frog.operator;

import com.bqp.frog.exception.BindingException;
import com.bqp.frog.jdbc.BoundSql;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.util.Strings;

import java.util.*;

/**
 * @author ash
 */
public class DefaultInvocationContext implements InvocationContext {

    private final Map<String, Object> parameterNameToValueMap = new LinkedHashMap<>();
    private final List<Object> parameterValues = new LinkedList<>();
    private final Map<String, Object> cache = new HashMap<>();

    private final StringBuilder sql = new StringBuilder();
    private final List<Object> args = new LinkedList<>();
    private final List<TypeHandler<?>> typeHandlers = new LinkedList<>();

    private String globalTable;

    private DefaultInvocationContext() {
    }

    public static DefaultInvocationContext create(ParameterContext parameterContext, Object[] values) {
        DefaultInvocationContext context = new DefaultInvocationContext();
        for (int i = 0; i < values.length; i++) {
            String parameterName = parameterContext.getParameterNameByPosition(i);
            context.addParameter(parameterName, values[i]);
        }
        return context;
    }

    @Override
    public void addParameter(String parameterName, Object parameterValue) {
        parameterNameToValueMap.put(parameterName, parameterValue);
        parameterValues.add(parameterValue);
    }

    @Override
    public Object getBindingValue(BindingParameterInvoker invoker) {
        Object value = getNullableBindingValue(invoker);
        if (value == null) {
            throw new BindingException("Parameter '" + invoker.getBindingParameter() + "' need a non-null value");
        }
        return value;
    }

    @Override
    public Object getNullableBindingValue(BindingParameterInvoker invoker) {
        String key = getCacheKey(invoker);
        if (cache.containsKey(key)) { // 有可能缓存null对象
            return cache.get(key);
        }
        String parameterName = invoker.getBindingParameter().getParameterName();
        if (!parameterNameToValueMap.containsKey(parameterName)) { // ParameterContext进行过检测，理论上这段代码执行不到
            throw new BindingException("Parameter '" + BindingParameter.create(parameterName, "", null) + "' not found, " +
                    "available root parameters are " + transToBindingParameters(parameterNameToValueMap.keySet()));
        }
        Object obj = parameterNameToValueMap.get(parameterName);
        Object value = invoker.invoke(obj);
        cache.put(key, value);
        return value;
    }

    @Override
    public void setBindingValue(BindingParameterInvoker invoker, Object value) {
        String key = getCacheKey(invoker);
        cache.put(key, value);
    }

    @Override
    public String getGlobalTable() {
        return globalTable;
    }

    @Override
    public void setGlobalTable(String globalTable) {
        this.globalTable = globalTable;
    }

    @Override
    public void trim(String str) {
        if (Strings.isEmpty(str)) {
            return;
        }
        int start = sql.lastIndexOf(str);
        if (start == -1) {
            return;
        }
        int end = sql.length();
        boolean needTrim = true;
        for (int i = start + str.length(); i < end; i++) {
            if (sql.charAt(i) != ' ') {
                needTrim = false;
                break;
            }
        }
        if (needTrim) {
            sql.delete(start, end);
        }
    }

    @Override
    public void writeToSqlBuffer(String str) {
        sql.append(str);
    }

    @Override
    public void appendToArgs(Object obj, TypeHandler<?> typeHandler) {
        args.add(obj);
        typeHandlers.add(typeHandler);
    }

    @Override
    public BoundSql getBoundSql() {
        return new BoundSql(sql.toString(), args, typeHandlers);
    }

    @Override
    public List<Object> getParameterValues() {
        return parameterValues;
    }

    private String getCacheKey(BindingParameterInvoker invoker) {
        return invoker.getBindingParameter().getFullName();
    }

    private Set<BindingParameter> transToBindingParameters(Collection<String> parameterNames) {
        Set<BindingParameter> rs = new LinkedHashSet<BindingParameter>();
        for (String parameterName : parameterNames) {
            rs.add(BindingParameter.create(parameterName, "", null));
        }
        return rs;
    }

}

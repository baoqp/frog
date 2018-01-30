package com.bqp.frog.util.reflect;

import java.lang.reflect.Method;

/**
 * @author ash
 */
public class ParamNameResolver {

    private static final String PARAMETER_CLASS = "java.lang.reflect.Parameter";
    private static Method GET_NAME;
    private static Method GET_PARAMS;

    static {
        try {
            Class<?> paramClass = Class.forName(PARAMETER_CLASS);
            GET_NAME = paramClass.getMethod("getName");
            GET_PARAMS = Method.class.getMethod("getParameters");
        } catch (Exception e) {
            // ignore
        }
    }

    public static String getActualParamName(Method method, int paramIndex) {
        if (GET_PARAMS == null) {
            return null;
        }
        try {
            Object[] params = (Object[]) GET_PARAMS.invoke(method);
            return (String) GET_NAME.invoke(params[paramIndex]);
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred when invoking Method#getParameters().", e);
        }
    }

}

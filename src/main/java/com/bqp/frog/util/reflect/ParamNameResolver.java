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

    // TODO 在java8中用过附加编译参数"-parameter"，可以在运行时获取参数名称
    // 兼容其他版本java,可以参考spring的parameterNameDiscoverer
    // http://grepcode.com/search/?query=LocalVariableTableParameterNameDiscoverer
    public static String getActualParamName(Method method, int paramIndex) {
        return method.getParameters()[paramIndex].getName();
        /*
        if (GET_PARAMS == null) {
            return null;
        }
        try {
            Object[] params = (Object[]) GET_PARAMS.invoke(method);
            return (String) GET_NAME.invoke(params[paramIndex]);
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred when invoking Method#getParameters().", e);
        }
        */
    }

}

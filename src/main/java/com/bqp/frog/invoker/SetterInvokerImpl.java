package com.bqp.frog.invoker;

import com.bqp.frog.annotation.Setter;
import com.bqp.frog.exception.UncheckedException;
import com.bqp.frog.util.Tuple;
import com.bqp.frog.util.reflect.Reflection;
import com.bqp.frog.util.reflect.TypeTokens;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author ash
 */
public class SetterInvokerImpl extends MethodNamedObject implements SetterInvoker {

    private SetterFunction function;
    private Type parameterType;
    private Class<?> parameterRawType;

    private Type runtimeOutputType;
    private Class<?> runtimeOutputRawType;

    // method：name所指属性的setter
    public SetterInvokerImpl(String name, Method method) {
        super(name, method);
        TypeToken<?> parameterToken = TypeToken.of(method.getGenericParameterTypes()[0]);
        runtimeOutputType = parameterToken.getType();
        runtimeOutputRawType = parameterToken.getRawType();

        Setter setterAnno = method.getAnnotation(Setter.class);

        if (setterAnno != null) { // 启用函数式调用功能
            Class<? extends SetterFunction<?, ?>> funcClass = setterAnno.value();

            SetterFunction function = Reflection.instantiateClass(funcClass);

            Tuple<TypeToken, TypeToken> tokenTuple = TypeTokens.resolveFatherClassTuple(TypeToken.of(funcClass), SetterFunction.class);
            TypeToken<?> inputToken = tokenTuple.getFirst();
            TypeToken<?> outputToken = tokenTuple.getSecond();

            TypeToken<?> wrapParameterToken = parameterToken.wrap();

            // 必须确保SetterFunction的返回类型是setter方法入参类型的子类型
            if (!outputToken.isSubtypeOf(wrapParameterToken)) {
                throw new ClassCastException("function [" + function.getClass() + "] " +
                        "on method[" + method + "] error,  method's parameterType[ " + parameterToken.getType()
                        + "] must be assignable from " + "function's outputType[" + outputToken.getType() + "] ");
            }
            parameterToken = inputToken;
        }
        parameterType = parameterToken.getType();
        parameterRawType = parameterToken.getRawType();
    }


    @Override
    public void invoke(Object object, Object parameter) {
        try {
            if (function != null) {
                parameter = function.apply(parameter);
            }

            if (parameter == null && runtimeOutputRawType.isPrimitive()) {
                throw new NullPointerException("property " + getName() + " of " +
                        object.getClass() + " is primitive, can not be assigned to null");
            }

            method.invoke(object, parameter);

        } catch (IllegalAccessException e) {
            throw new UncheckedException(e.getMessage(), e.getCause());

        } catch (InvocationTargetException e) {
            throw new UncheckedException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Type getParameterType() {
        return parameterType;
    }

    @Override
    public Class<?> getParameterRawType() {
        return parameterRawType;
    }


}

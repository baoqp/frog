package com.bqp.frog.invoker;

import com.bqp.frog.annotation.Getter;
import com.bqp.frog.exception.UncheckedException;
import com.bqp.frog.util.Tuple;
import com.bqp.frog.util.reflect.Reflection;
import com.bqp.frog.util.reflect.TypeTokens;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Bao Qingping
 */
public class GetterInvokerImpl extends MethodNamedObject implements GetterInvoker {

    private GetterFunction function;

    private Type returnType;

    private Class<?> returnRawType;

    public GetterInvokerImpl(String name, Method method) {
        super(name, method);
        Getter getterAnno = method.getAnnotation(Getter.class);

        TypeToken<?> returnToken = TypeToken.of(method.getGenericReturnType());

        if (getterAnno != null) {
            // 需要确保getter上注解的function返回值类型是参数的子类型，否则报错
            Class<? extends GetterFunction<?, ?>> funcClass = getterAnno.value();
            function = Reflection.instantiateClass(funcClass);

            Tuple<TypeToken, TypeToken> tokenTuple = TypeTokens.resolveFatherClassTuple(TypeToken.of(funcClass), GetterFunction.class);
            TypeToken<?> inputToken = tokenTuple.getFirst();
            TypeToken<?> outputToken = tokenTuple.getSecond();

            TypeToken<?> wrapReturnToken = returnToken.wrap();

            if (!inputToken.isSupertypeOf(wrapReturnToken)) {
                throw new ClassCastException("function[" + function.getClass() + "] " +
                        "on method[" + method + "] error, function's inputType[" + inputToken.getType() + "] " +
                        "must be assignable from method's returnType[" + returnToken.getType() + "]");
            }
            returnToken = outputToken;
        }
        returnType = returnToken.getType();
        returnRawType = returnToken.getRawType();

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object invoke(Object obj) {
        try {
            Object r = method.invoke(obj);
            if (function != null) {
                r = function.apply(r);
            }
            return r;
        } catch (IllegalAccessException e) {
            throw new UncheckedException(e.getMessage(), e.getCause());
        } catch (InvocationTargetException e) {
            throw new UncheckedException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Type getReturnType() {
        return null;
    }

    @Override
    public Class<?> getReturnRawType() {
        return null;
    }
}

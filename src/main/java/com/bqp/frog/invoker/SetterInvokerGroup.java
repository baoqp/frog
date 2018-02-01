package com.bqp.frog.invoker;

public interface SetterInvokerGroup {

    Class<?> getOriginalType();

    Class<?> getTargetType();

    void invoke(Object obj, Object value);

}
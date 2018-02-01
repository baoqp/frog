package com.bqp.frog.invoker;

import java.lang.reflect.Type;

/**
 * @author ash
 */
public interface SetterInvoker extends NamedObject {

    void invoke(Object object, Object parameter);

    Type getParameterType();

    Class<?> getParameterRawType();

}

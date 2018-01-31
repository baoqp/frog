package com.bqp.frog.invoker;

import java.lang.reflect.Type;

public interface GetterInvoker extends NamedObject {

   Object invoke(Object obj);

   Type getReturnType();

   Class<?> getReturnRawType();

}

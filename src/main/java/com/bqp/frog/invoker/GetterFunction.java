package com.bqp.frog.invoker;

// getter上的注解，对getter的返回值调用apply方法进行必要的变换
public interface GetterFunction<I, O> {
    O apply(I input);
}

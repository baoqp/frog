package com.bqp.frog.invoker;


public interface SetterFunction<I, O> {

    O apply(I input);
}

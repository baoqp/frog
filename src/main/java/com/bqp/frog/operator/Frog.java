package com.bqp.frog.operator;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.util.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.bqp.frog.util.Methods.getMethodDescriptor;

/**
 * @author Bao Qingping
 */
public class Frog {

    private static class FrogInvocationHandler extends AbstractInvocationHandler implements InvocationHandler {

        private Class<?> daoClass;

        public FrogInvocationHandler(Class<?> daoClass) {
            this.daoClass = daoClass;
        }

        @Override
        protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
            Operator operator = getOperator(method);
            try {
                Object r = operator.execute(args);
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        private Operator getOperator(Method method) {
            MethodDescriptor methodDescriptor = getMethodDescriptor(daoClass, method, true);
            Operator operator = new OperatorImpl(daoClass, methodDescriptor);
            return operator;
        }
    }
}

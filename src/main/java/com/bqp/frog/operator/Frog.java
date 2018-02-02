package com.bqp.frog.operator;

import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.util.cache.CacheLoader;
import com.bqp.frog.util.cache.DoubleCheckCache;
import com.bqp.frog.util.cache.LoadingCache;
import com.bqp.frog.util.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Bao Qingping
 */
public class Frog {

    private static class FrogInvocationHandler extends AbstractInvocationHandler implements InvocationHandler {

        private Class<?> daoClass;

        private DataSourceGroup dataSourceGroup;

        private MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper;

        private OperatorFactory operatorFactory;

        private final LoadingCache<Method, Operator> cache = new DoubleCheckCache<>(
                new CacheLoader<Method, Operator>() {
                    public Operator load(Method method) {
                        operatorFactory.getOperator(daoClass, method);
                        // datasource 通过 factory 传递
                        Operator operator = operatorFactory.getOperator(daoClass, method);

                        return operator;
                    }
                });

        // TODO
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

        public Operator getOperator(Method method) {
            return cache.get(method);
        }


    }


}

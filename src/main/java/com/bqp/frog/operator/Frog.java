package com.bqp.frog.operator;

import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
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

        // TODO
        public FrogInvocationHandler(Class<?> daoClass) {
            this.daoClass = daoClass;
        }

        @Override
        protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
            Operator operator = getOperator(daoClass, method);
            try {
                Object r = operator.execute(args);
                return r;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public BaseOperator getOperator(Class<?> daoClass, Method method) {
            return operatorFactory.getOperator(daoClass, method);
        }


    }


}

package com.bqp.frog.operator;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.exception.InitializationException;
import com.bqp.frog.util.Methods;
import com.bqp.frog.util.ToStringHelper;
import com.bqp.frog.util.cache.CacheLoader;
import com.bqp.frog.util.cache.DoubleCheckCache;
import com.bqp.frog.util.cache.LoadingCache;
import com.bqp.frog.util.reflect.AbstractInvocationHandler;
import com.bqp.frog.util.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Bao Qingping
 */
public class Frog {


    private DataSourceGroup dataSourceGroup;

    private MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper;

    private OperatorFactory operatorFactory;

    public Frog(DataSourceGroup dataSourceGroup) {
        this(dataSourceGroup, null);
    }

    public Frog(MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper) {
        this(null, masterSlaveDataSourceWrapper);
    }

    public Frog(DataSourceGroup dataSourceGroup, MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper) {
        this.dataSourceGroup = dataSourceGroup;
        this.masterSlaveDataSourceWrapper = masterSlaveDataSourceWrapper;
        this.operatorFactory = new OperatorFactory(dataSourceGroup, masterSlaveDataSourceWrapper);
    }

    /**
     * 创建Dao代理类
     */
    public <T> T create(Class<T> daoClass) {
        if (daoClass == null) {
            throw new NullPointerException("dao interface can't be null");
        }

        if (!daoClass.isInterface()) {
            throw new IllegalArgumentException("expected an interface to proxy, but " + daoClass);
        }

        DB dbAnno = daoClass.getAnnotation(DB.class);
        if (dbAnno == null) {
            throw new IllegalStateException("dao interface expected one @DB " +
                    "annotation but not found");
        }


        if (dataSourceGroup == null && masterSlaveDataSourceWrapper == null) {
            throw new IllegalArgumentException("please init frog with DataSourceGroup OR/AND MasterSlaveDataSourceWrapper instance");
        }

        FrogInvocationHandler handler = new FrogInvocationHandler(daoClass);

        List<Method> methods = Methods.listMethods(daoClass);
        for (Method method : methods) {
            try {
                handler.getOperator(method);
            } catch (Throwable e) {
                throw new InitializationException("initialize " + ToStringHelper.toString(method) + " error", e);
            }
        }

        return Reflection.newProxy(daoClass, handler);
    }

    private class FrogInvocationHandler extends AbstractInvocationHandler implements InvocationHandler {

        private Class<?> daoClass;

        private final LoadingCache<Method, Operator> cache = new DoubleCheckCache<>(
                new CacheLoader<Method, Operator>() {
                    public Operator load(Method method) {
                        operatorFactory.getOperator(daoClass, method);
                        Operator operator = operatorFactory.getOperator(daoClass, method);
                        return operator;
                    }
                });


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

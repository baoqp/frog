package com.bqp.frog.operator;

import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.JdbcOperations;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.sharding.TableGenerator;
import com.bqp.frog.util.reflect.TypeWrapper;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Bao Qingping
 */
public abstract class BaseOperator implements Operator {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    MethodDescriptor methodDescriptor;

    Class<?> daoClass;

    ParseTree tree;

    List<BindingParameter> bindingParameters;

    List<BindingParameterInvoker> bindingParameterInvokers;
    List<TypeHandler<?>> typeHandlers;

    ParameterContext parameterContext;

    DataSourceGroup dataSourceGroup;

    MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper;

    DataSource dataSource;

    JdbcOperations jdbcOperations;

    TableGenerator tableGenerator;

    public BaseOperator(Class<?> daoClass,
                        MethodDescriptor methodDescriptor,
                        List<BindingParameter> bindingParameters,
                        ParseTree tree,
                        ParameterContext parameterContext) {
        this.daoClass = daoClass;
        this.methodDescriptor = methodDescriptor;
        this.bindingParameters = bindingParameters;
        this.tree = tree;
        this.parameterContext = parameterContext;

        bindingParameterInvokers = new ArrayList<>();
        typeHandlers = new ArrayList<>();

        for (BindingParameter bindingParameter : this.bindingParameters) {
            BindingParameterInvoker invoker = this.parameterContext.getBindingParameterInvoker(bindingParameter);
            Type type = invoker.getTargetType();
            TypeWrapper tw = new TypeWrapper(type);
            Class<?> mappedClass = tw.getMappedClass();
            TypeHandler<?> typeHandler = TypeHandlerRegistry.getNullableTypeHandler(mappedClass); // 目前不考虑jdbcType
            bindingParameterInvokers.add(invoker);
            typeHandlers.add(typeHandler);
        }

    }

    public DataSourceGroup getDataSourceGroup() {
        return dataSourceGroup;
    }

    public void setDataSourceGroup(DataSourceGroup dataSourceGroup) {
        this.dataSourceGroup = dataSourceGroup;
    }

    public MasterSlaveDataSourceWrapper getMasterSlaveDataSourceWrapper() {
        return masterSlaveDataSourceWrapper;
    }

    public void setMasterSlaveDataSourceWrapper(MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper) {
        this.masterSlaveDataSourceWrapper = masterSlaveDataSourceWrapper;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }

    public void setJdbcOperations(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }
}

package com.bqp.frog.operator;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.parser.FrogSqlVisitor;
import com.bqp.frog.util.reflect.TypeWrapper;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    FrogSqlVisitor frogSqlVisitor;

    ParseTree tree;

    List<BindingParameter> bindingParameters;

    List<BindingParameterInvoker> bindingParameterInvokers;
    List<TypeHandler<?>> typeHandlers;

    ParameterContext parameterContext;


    public BaseOperator(Class<?> daoClass,
                        MethodDescriptor methodDescriptor,
                        List<BindingParameter> bindingParameters,
                        ParseTree tree) {
        this.daoClass = daoClass;
        this.methodDescriptor = methodDescriptor;
        this.bindingParameters = bindingParameters;
        this.tree = tree;
        this.parameterContext = DefaultParameterContext.create(this.methodDescriptor.getParameterDescriptors());

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

}

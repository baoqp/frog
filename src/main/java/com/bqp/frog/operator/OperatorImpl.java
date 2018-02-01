package com.bqp.frog.operator;

import com.bqp.frog.annotation.SQL;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.jdbc.BoundSql;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.parser.*;
import com.bqp.frog.util.reflect.TypeWrapper;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bao Qingping
 */
public class OperatorImpl implements Operator {

    private MethodDescriptor methodDescriptor;

    private Class<?> daoClass;

    private FrogSqlVisitor frogSqlVisitor;

    ParseTree tree;

    private List<BindingParameter> bindingParameters;

    private List<BindingParameterInvoker> bindingParameterInvokers;
    private List<TypeHandler<?>> typeHandlers;

    private ParameterContext parameterContext;

    // TODO 写成单例???
    public OperatorImpl(Class<?> daoClass, MethodDescriptor methodDescriptor) {
        this.daoClass = daoClass;
        this.methodDescriptor = methodDescriptor;
        this.bindingParameters = new ArrayList<>();
        this.parameterContext = DefaultParameterContext.create(this.methodDescriptor.getParameterDescriptors());

        SQL sqlAnnotation = methodDescriptor.getAnnotation(SQL.class);
        // TODO sqlAnnotation not null
        String rawSql = sqlAnnotation.value();
        // init visitor
        ANTLRInputStream input = new ANTLRInputStream(rawSql);
        FrogSqlLexer lexer = new FrogSqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FrogSqlParser parser = new FrogSqlParser(tokens);
        tree = parser.sql(); // parse
        frogSqlVisitor = new FrogSqlVisitorImpl(bindingParameters);
        frogSqlVisitor.visit(tree);

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

    @Override
    public Object execute(Object[] args) {
        //构建invocationContext, render sql, 调用jdbc
        DefaultInvocationContext invocationContext = DefaultInvocationContext.create(parameterContext, args);
        new FrogSqlRender(invocationContext, bindingParameterInvokers, typeHandlers).visit(tree);
        BoundSql boundSql = invocationContext.getBoundSql();
        System.out.println(boundSql);
        return null;
    }

}

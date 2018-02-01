package com.bqp.frog.operator;

import com.bqp.frog.annotation.Column;
import com.bqp.frog.annotation.Mapper;
import com.bqp.frog.annotation.Result;
import com.bqp.frog.annotation.Results;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.descriptor.ReturnDescriptor;
import com.bqp.frog.jdbc.BeanPropertyRowMapper;
import com.bqp.frog.jdbc.BoundSql;
import com.bqp.frog.jdbc.RowMapper;
import com.bqp.frog.jdbc.SingleColumnRowMapper;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.parser.FrogSqlRender;
import com.bqp.frog.util.bean.BeanUtil;
import com.bqp.frog.util.bean.PropertyMeta;
import com.bqp.frog.util.reflect.Reflection;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 写成单例
 *
 * @author Bao Qingping
 */
public class QueryOperator extends BaseOperator {

    RowMapper<?> rowMapper;

    ReturnDescriptor returnDescriptor;

    public QueryOperator(Class<?> daoClass,
                         MethodDescriptor methodDescriptor,
                         List<BindingParameter> bindingParameters,
                         ParseTree tree) {

        super(daoClass, methodDescriptor, bindingParameters, tree);

        init(methodDescriptor);
    }

    private void init(MethodDescriptor md) {
        returnDescriptor = md.getReturnDescriptor();
        rowMapper = getRowMapper(returnDescriptor.getMappedClass(), returnDescriptor);
    }


    @Override
    public Object execute(Object[] args) {
        //构建InvocationContext, render sql, 执行sql
        DefaultInvocationContext invocationContext = DefaultInvocationContext.create(parameterContext, args);
        new FrogSqlRender(invocationContext, bindingParameterInvokers, typeHandlers).visit(tree);
        BoundSql boundSql = invocationContext.getBoundSql();
        LOGGER.debug("bound sql generate by frog: " + boundSql.toString());
        System.out.println("bound sql generate by frog: " + boundSql.toString());



        return null;
    }


    private <T> RowMapper<?> getRowMapper(Class<T> clazz, ReturnDescriptor rd) {

        Mapper mapperAnno = rd.getAnnotation(Mapper.class);
        if (mapperAnno != null) { // 自定义mapper
            return Reflection.instantiateClass(mapperAnno.value());
        }

        // 如果有已注册的TypeHandler
        if (TypeHandlerRegistry.hasTypeHandler(clazz)) { // 单列mapper
            return new SingleColumnRowMapper<T>(clazz);
        }

        // 类属性mapper
        Results resultsAnoo = rd.getAnnotation(Results.class);
        Map<String, String> ptc = getPropToColMap(clazz); // 属性到列名的映射
        if (resultsAnoo != null) {
            Result[] resultAnnos = resultsAnoo.value();
            if (resultAnnos != null) {
                for (Result resultAnno : resultAnnos) {
                    ptc.put(resultAnno.property().trim(), resultAnno.column().trim());
                }
            }
        }
        return new BeanPropertyRowMapper<>(clazz, ptc, false);
    }

    private Map<String, String> getPropToColMap(Class<?> clazz) {

        Map<String, String> propToColMap = new HashMap<>();

        for (PropertyMeta propertyMeta : BeanUtil.fetchPropertyMetas(clazz)) {
            Column colAnno = propertyMeta.getPropertyAnno(Column.class);
            if (colAnno != null) {
                String prop = propertyMeta.getName();
                String col = colAnno.value();
                propToColMap.put(prop, col);
            }
        }
        return propToColMap;
    }

}

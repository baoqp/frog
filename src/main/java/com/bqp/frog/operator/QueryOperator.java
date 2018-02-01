package com.bqp.frog.operator;

import com.bqp.frog.annotation.Column;
import com.bqp.frog.annotation.Mapper;
import com.bqp.frog.annotation.Result;
import com.bqp.frog.annotation.Results;
import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.descriptor.ReturnDescriptor;
import com.bqp.frog.jdbc.*;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.parser.FrogSqlRender;
import com.bqp.frog.util.ListSupplier;
import com.bqp.frog.util.SetSupplier;
import com.bqp.frog.util.bean.BeanUtil;
import com.bqp.frog.util.bean.PropertyMeta;
import com.bqp.frog.util.reflect.Reflection;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.sql.DataSource;
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

    ListSupplier listSupplier;
    SetSupplier setSupplier;

    public QueryOperator(Class<?> daoClass,
                         MethodDescriptor methodDescriptor,
                         List<BindingParameter> bindingParameters,
                         ParseTree tree) {

        super(daoClass, methodDescriptor, bindingParameters, tree);

        if (returnDescriptor.isCollection()
                || returnDescriptor.isList()
                || returnDescriptor.isLinkedList()) {
            listSupplier = new LinkedListSuppliter();
        } else if (returnDescriptor.isArrayList()) {
            listSupplier = new ArrayListSupplier();
        } else if (returnDescriptor.isSetAssignable()) {
            setSupplier = new HashSetSupplier();
        }

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

    private Object executeFromDb(final DataSource ds, final BoundSql boundSql) {
        Object r;
        boolean success = false;
        long now = System.nanoTime();
        try {

            //
            r = new QueryVisitor() {

                @Override
                Object visitForList() {
                    return jdbcOperations.queryForList(ds, boundSql, listSupplier, rowMapper);
                }

                @Override
                Object visitForSet() {
                    return jdbcOperations.queryForSet(ds, boundSql, setSupplier, rowMapper);
                }

                @Override
                Object visitForArray() {
                    return jdbcOperations.queryForArray(ds, boundSql, rowMapper);

                }

                @Override
                Object visitForObject() {
                    return jdbcOperations.queryForObject(ds, boundSql, rowMapper);
                }
            }.visit();

            success = true;
        } finally {
            long cost = System.nanoTime() - now;
            System.out.println("--quety cost -- " + cost);
        }
        return r;
    }


    abstract class QueryVisitor {

        public Object visit() {
            Object r;
            if (returnDescriptor.isCollection()
                    || returnDescriptor.isListAssignable()) {
                r = visitForList();
            } else if (returnDescriptor.isSetAssignable()) {
                r = visitForSet();
            } else if (returnDescriptor.isArray()) {
                r = visitForArray();
            } else {
                r = visitForObject();
            }
            return r;
        }

        abstract Object visitForList();

        abstract Object visitForSet();

        abstract Object visitForArray();

        abstract Object visitForObject();

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

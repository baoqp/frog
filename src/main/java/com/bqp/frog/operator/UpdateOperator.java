package com.bqp.frog.operator;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.exception.DescriptionException;
import com.bqp.frog.jdbc.BoundSql;
import com.bqp.frog.jdbc.GeneratedKeyHolder;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.parser.FrogSqlRender;
import com.bqp.frog.util.ToStringHelper;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class UpdateOperator extends BaseOperator {

    private boolean returnGeneratedId;

    private Transformer transformer;

    private TypeHandler<? extends Number> generatedKeyTypeHandler;

    public UpdateOperator(Class<?> daoClass,
                          MethodDescriptor methodDescriptor,
                          List<BindingParameter> bindingParameters,
                          ParseTree tree, OperatorType operatorType,
                          ParameterContext parameterContext) {
        super(daoClass, methodDescriptor, bindingParameters, tree, parameterContext);

        init(methodDescriptor, operatorType);
    }

    private void init(MethodDescriptor md, OperatorType operatorType) {
        returnGeneratedId = md.isReturnGeneratedId()
                && operatorType == OperatorType.INSERT;

        Class<?> returnRawType = md.getReturnRawType();
        if (returnGeneratedId) {
            GeneratedTransformer gt = GENERATED_TRANSFORMERS.get(returnRawType);
            if (gt == null) {
                String expected = ToStringHelper.toString(GENERATED_TRANSFORMERS.keySet());
                throw new DescriptionException("the return type of update(returnGeneratedId) " +
                        "expected one of " + expected + " but " + returnRawType + " found.");
            }
            generatedKeyTypeHandler = TypeHandlerRegistry.getTypeHandler(gt.getRawType());
            transformer = gt;
        } else {
            transformer = TRANSFORMERS.get(returnRawType);
            if (transformer == null) {
                String expected = ToStringHelper.toString(TRANSFORMERS.keySet());
                throw new DescriptionException("the return type of update " +
                        "expected one of " + expected + " but " + returnRawType + " found.");
            }
        }
    }

    @Override
    public Object execute(Object[] args) {
        DefaultInvocationContext invocationContext = DefaultInvocationContext.create(parameterContext, args);
        new FrogSqlRender(invocationContext, bindingParameterInvokers, typeHandlers).visit(tree);
        BoundSql boundSql = invocationContext.getBoundSql();
        LOGGER.info("bound sql generate by frog: " + boundSql.toString());
        return execute(invocationContext, boundSql);
    }

    public Object execute(InvocationContext context, final BoundSql boundSql) {

        // TODO
        /*DataSource ds = dataSourceGenerator.getDataSource(context, daoClass);
        invocationInterceptorChain.intercept(boundSql, context, ds); */

        Number r = executeDb(dataSource, boundSql);
        return transformer.transform(r);
    }

    private Number executeDb(DataSource ds, BoundSql boundSql) {
        Number r = null;
        long now = System.currentTimeMillis();
        try {
            if (returnGeneratedId) {
                GeneratedKeyHolder holder = new GeneratedKeyHolder(generatedKeyTypeHandler);
                jdbcOperations.update(ds, boundSql, holder);
                r = holder.getKey();
            } else {
                r = jdbcOperations.update(ds, boundSql);
            }
        } finally {
            long cost = System.currentTimeMillis() - now;
            System.out.println("--update cost--" + cost);
        }
        return r;
    }

    /**
     * 更新操作支持的返回类型
     */
    private final static Map<Class, Transformer> TRANSFORMERS = new LinkedHashMap<Class, Transformer>();

    static {
        TRANSFORMERS.put(void.class, VoidTransformer.INSTANCE);
        TRANSFORMERS.put(int.class, IntegerTransformer.INSTANCE);
        TRANSFORMERS.put(long.class, LongTransformer.INSTANCE);
        TRANSFORMERS.put(boolean.class, BooleanTransformer.INSTANCE);
        TRANSFORMERS.put(Void.class, VoidTransformer.INSTANCE);
        TRANSFORMERS.put(Integer.class, IntegerTransformer.INSTANCE);
        TRANSFORMERS.put(Long.class, LongTransformer.INSTANCE);
        TRANSFORMERS.put(Boolean.class, BooleanTransformer.INSTANCE);
    }

    /**
     * 生成自增id的更新操作支持的返回类型
     */
    private final static Map<Class, GeneratedTransformer> GENERATED_TRANSFORMERS = new LinkedHashMap<Class, GeneratedTransformer>();

    static {
        GENERATED_TRANSFORMERS.put(int.class, IntegerTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(long.class, LongTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(Integer.class, IntegerTransformer.INSTANCE);
        GENERATED_TRANSFORMERS.put(Long.class, LongTransformer.INSTANCE);
    }

    interface Transformer {
        Object transform(Number n);
    }

    interface GeneratedTransformer extends Transformer {
        Class<? extends Number> getRawType();
    }

    enum IntegerTransformer implements GeneratedTransformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.intValue();
        }

        @Override
        public Class<? extends Number> getRawType() {
            return int.class;
        }
    }

    enum LongTransformer implements GeneratedTransformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.longValue();
        }

        @Override
        public Class<? extends Number> getRawType() {
            return long.class;
        }
    }

    enum VoidTransformer implements Transformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return null;
        }
    }

    enum BooleanTransformer implements Transformer {
        INSTANCE;

        @Override
        public Object transform(Number n) {
            return n.intValue() > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
    }

}

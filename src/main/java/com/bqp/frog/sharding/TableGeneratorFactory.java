package com.bqp.frog.sharding;

import com.bqp.frog.annotation.Sharding;
import com.bqp.frog.annotation.TableShardingBy;
import com.bqp.frog.descriptor.ParameterDescriptor;
import com.bqp.frog.exception.DescriptionException;
import com.bqp.frog.exception.IncorrectParameterTypeException;
import com.bqp.frog.operator.BindingParameter;
import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.operator.ParameterContext;
import com.bqp.frog.util.reflect.Reflection;
import com.bqp.frog.util.reflect.TypeTokens;
import com.bqp.frog.util.reflect.TypeWrapper;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author ash
 */
public class TableGeneratorFactory {

    public TableGenerator getTableGenerator(
            Sharding shardingAnno, String table,
            boolean isSqlUseGlobalTable, ParameterContext context) {

        TableShardingStrategy strategy = getTableShardingStrategy(shardingAnno);
        TypeToken<?> strategyToken = null;
        if (strategy != null) {
            strategyToken = TypeTokens.resolveFatherClass(TypeToken.of(strategy.getClass()), TableShardingStrategy.class);
        }

        // 是否配置使用全局表
        boolean isUseGlobalTable = table != null;

        // 是否配置使用表切分
        boolean isUseTableShardingStrategy = strategy != null;

        if (isSqlUseGlobalTable && !isUseGlobalTable) {
            throw new DescriptionException("if sql use global table '#table'," +
                    " @DB.table must be defined");
        }
        if (isUseTableShardingStrategy && !isUseGlobalTable) {
            throw new DescriptionException("if @Sharding.tableShardingStrategy is defined, " +
                    "@DB.table must be defined");
        }

        int num = 0;
        String parameterName = null;
        String propertyPath = null;
        for (ParameterDescriptor pd : context.getParameterDescriptors()) {
            TableShardingBy tableShardingByAnno = pd.getAnnotation(TableShardingBy.class);
            if (tableShardingByAnno != null) {
                parameterName = context.getParameterNameByPosition(pd.getPosition());
                propertyPath = tableShardingByAnno.value();
                num++;
            }
        }


        TableGenerator tableGenerator;
        if (isUseTableShardingStrategy) {
            if (num == 1) {
                // sharding里面的表达式和BindingParameter是类似的
                BindingParameter bp = BindingParameter.create(parameterName, propertyPath, null);
                BindingParameterInvoker invoker = context.getBindingParameterInvoker(bp);
                Type targetType = invoker.getTargetType();
                TypeWrapper tw = new TypeWrapper(targetType);
                Class<?> mappedClass = tw.getMappedClass();
                if (mappedClass == null || tw.isIterable()) {
                    throw new IncorrectParameterTypeException("the type of parameter Modified @TableShardingBy is error, " +
                            "type is " + targetType);
                }
                TypeToken<?> shardToken = TypeToken.of(targetType);
                if (!strategyToken.isSupertypeOf(shardToken.wrap())) {
                    throw new ClassCastException("TableShardingStrategy[" + strategy.getClass() + "]'s " +
                            "generic type[" + strategyToken.getType() + "] must be assignable from " +
                            "the type of parameter Modified @TableShardingBy [" + shardToken.getType() + "]");
                }
                tableGenerator = new ShardedTableGenerator(table, invoker, strategy);
            } else {
                throw new DescriptionException("if @Sharding.tableShardingStrategy is defined, " +
                        "need one and only one @TableShardingBy on method's parameter but found " + num + ", " +
                        "please note that @ShardingBy = @TableShardingBy + @DatabaseShardingBy");
            }
        } else { // 不分表
            tableGenerator = new SimpleTableGenerator(table);
        }
        return tableGenerator;
    }


    private TableShardingStrategy getTableShardingStrategy(Sharding shardingAnno) {
        if (shardingAnno == null) {
            return null;
        }
        Class<? extends TableShardingStrategy> strategyClass = shardingAnno.tableShardingStrategy();
        if (!strategyClass.equals(NotUseTableShardingStrategy.class)) {
            TableShardingStrategy strategy = Reflection.instantiateClass(strategyClass);
            return strategy;
        }
        return null;
    }

}

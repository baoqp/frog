package com.bqp.frog.sharding;

import com.bqp.frog.annotation.DatabaseShardingBy;
import com.bqp.frog.annotation.Sharding;
import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.DataSourceType;
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
 * TODO 目前只支持一个属性
 *
 * @author ash
 */
public class DataSourceGeneratorFactory {

    private final DataSourceGroup dataSourceGroup;

    public DataSourceGeneratorFactory(DataSourceGroup dataSourceGroup) {
        this.dataSourceGroup = dataSourceGroup;
    }

    public DataSourceGenerator getDataSourceGenerator(
            DataSourceType dataSourceType, Sharding shardingAnno,
            String dataSourceFactoryName, ParameterContext context) {

        DatabaseShardingStrategy strategy = getDatabaseShardingStrategy(shardingAnno);
        TypeToken<?> strategyToken = null;
        if (strategy != null) {
            strategyToken = TypeTokens.resolveFatherClass(TypeToken.of(strategy.getClass()),
                    DatabaseShardingStrategy.class);
        }


        int shardingParameterNum = 0;
        String shardingParameterName = null;
        String shardingParameterProperty = null;
        for (ParameterDescriptor pd : context.getParameterDescriptors()) {
            DatabaseShardingBy databaseShardingByAnno = pd.getAnnotation(DatabaseShardingBy.class);
            if (databaseShardingByAnno != null) {
                shardingParameterName = context.getParameterNameByPosition(pd.getPosition());
                shardingParameterProperty = databaseShardingByAnno.value();
                shardingParameterNum++;
                break;
            }
        }
        DataSourceGenerator dataSourceGenerator = null;
        if (strategy != null) {
            if (shardingParameterNum == 1) {
                BindingParameterInvoker shardingParameterInvoker
                        = context.getBindingParameterInvoker(BindingParameter.create(shardingParameterName, shardingParameterProperty, null));
                Type shardingParameterType = shardingParameterInvoker.getTargetType();
                TypeWrapper tw = new TypeWrapper(shardingParameterType);
                Class<?> mappedClass = tw.getMappedClass();
                if (mappedClass == null || tw.isIterable()) {
                    throw new IncorrectParameterTypeException("the type of parameter Modified @DatabaseShardingBy is error, " +
                            "type is " + shardingParameterType);
                }
                TypeToken<?> shardToken = TypeToken.of(shardingParameterType);
                if (!strategyToken.isSupertypeOf(shardToken.wrap())) {
                    throw new ClassCastException("DatabaseShardingStrategy[" + strategy.getClass() + "]'s " +
                            "generic type[" + strategyToken.getType() + "] must be assignable from " +
                            "the type of parameter Modified @DatabaseShardingBy [" + shardToken.getType() + "], " +
                            "please note that @ShardingBy = @TableShardingBy + @DatabaseShardingBy");
                }


                dataSourceGenerator = new ShardedDataSourceGenerator(dataSourceGroup, shardingParameterInvoker, strategy);
            } else {
                throw new DescriptionException("if @Sharding.databaseShardingStrategy is defined, " +
                        "need one and only one @DatabaseShardingBy on method's parameter but found " + shardingParameterNum + ", " +
                        "please note that @ShardingBy = @TableShardingBy + @DatabaseShardingBy");
            }
        }

        return dataSourceGenerator;

    }

    private DatabaseShardingStrategy getDatabaseShardingStrategy(Sharding shardingAnno) {
        if (shardingAnno == null) {
            return null;
        }
        Class<? extends DatabaseShardingStrategy> strategyClass = shardingAnno.databaseShardingStrategy();
        if (!strategyClass.equals(NotUseDatabaseShardingStrategy.class)) {
            DatabaseShardingStrategy strategy = Reflection.instantiateClass(strategyClass);
            return strategy;
        }
        return null;
    }

}

package com.bqp.frog.sharding;

import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.operator.InvocationContext;

/**
 * 分表表名生成器，以从@DB注解的table()中取得的表名作为原始表名，
 * 使用TableShardingBy修饰的参数作为分表参数，
 * 使用TableShardingStrategy作为分表策略，共同生成分表后表名。
 * @author ash
 */

public class ShardedTableGenerator implements TableGenerator {

    private final String table; // 原始表名称
    private final BindingParameterInvoker bindingParameterInvoker;  // 绑定参数执行器
    private final TableShardingStrategy tableShardingStrategy;      // 分表策略

    public ShardedTableGenerator(
            String table, BindingParameterInvoker bindingParameterInvoker,
            TableShardingStrategy tableShardingStrategy) {

        this.table = table;
        this.bindingParameterInvoker = bindingParameterInvoker;
        this.tableShardingStrategy = tableShardingStrategy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getTable(InvocationContext context) {
        Object shardParam = context.getBindingValue(bindingParameterInvoker);
        return tableShardingStrategy.getTargetTable(table, shardParam);
    }

}

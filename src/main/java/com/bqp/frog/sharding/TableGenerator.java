package com.bqp.frog.sharding;

import com.bqp.frog.operator.InvocationContext;

/**
 * 表名生成器，生成的表名用于替换掉SQL中的#table
 *
 * @author ash
 */
public interface TableGenerator {
    String getTable(InvocationContext context);
}

package com.bqp.frog.sharding;

import com.bqp.frog.operator.InvocationContext;

/**
 * 简单表名生成器，即不使用分表
 *
 * @author ash
 */
public class SimpleTableGenerator implements TableGenerator {

    private final String table;

    public SimpleTableGenerator(String table) {
        this.table = table;
    }


    @Override
    public String getTable(InvocationContext context) {
        return table;
    }

}
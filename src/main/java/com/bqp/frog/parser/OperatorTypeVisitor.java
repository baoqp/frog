package com.bqp.frog.parser;

import com.bqp.frog.operator.OperatorType;

/**
 * @author Bao Qingping
 */
public interface OperatorTypeVisitor<T> extends FrogSqlVisitor<T> {

    // 获取是哪种类型的sql操作
    OperatorType getOperatorType();

}

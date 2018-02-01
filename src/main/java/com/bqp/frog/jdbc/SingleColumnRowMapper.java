package com.bqp.frog.jdbc;

import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleColumnRowMapper<T> implements RowMapper<T> {

    private Class<T> mappedClass;

    public SingleColumnRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
    }

    @SuppressWarnings("unchecked")
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetWrapper rsw = new ResultSetWrapper(rs);
        int index = 1;
        TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(mappedClass, rsw.getJdbcType(index));
        Object value = typeHandler.getResult(rsw.getResultSet(), index);
        return (T) value;
    }

    @Override
    public Class<T> getMappedClass() {
        return mappedClass;
    }

}
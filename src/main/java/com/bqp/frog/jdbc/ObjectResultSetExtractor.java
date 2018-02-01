package com.bqp.frog.jdbc;

import com.bqp.frog.exception.MappingException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ash
 */
public class ObjectResultSetExtractor<T> implements ResultSetExtractor<T> {

    private final RowMapper<T> rowMapper;

    public ObjectResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public T extractData(ResultSet rs) throws SQLException {
        Class<T> mappedClass = rowMapper.getMappedClass();
        if (!mappedClass.isPrimitive()) {
            return rs.next() ? rowMapper.mapRow(rs, 0) : null;
        }

        // 原生类型
        if (!rs.next()) {
            throw new MappingException("no data, can't cast null to primitive type " + mappedClass);
        }
        T r = rowMapper.mapRow(rs, 0);
        if (r == null) {
            throw new MappingException("data is null, can't cast null to primitive type " + mappedClass);
        }
        return r;
    }

}